/*
 * Copyright 2022 lzhpo
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.lzhpo.sensitive.support.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.ContextualSerializer;
import com.lzhpo.sensitive.annocation.Sensitive;
import com.lzhpo.sensitive.utils.SensitiveInvokeUtil;
import java.io.IOException;
import java.util.Objects;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.method.HandlerMethod;

/**
 * Sensitive <b>String type field</b> for jackson
 *
 * <pre>
 * Reference code:
 * - {@link com.fasterxml.jackson.databind.ser.std.UUIDSerializer}
 * - {@link com.fasterxml.jackson.databind.ser.std.BooleanSerializer}
 * </pre>
 *
 * @author lzhpo
 */
@Slf4j
@NoArgsConstructor
@AllArgsConstructor
public class JacksonSensitiveSerializer extends JsonSerializer<String>
    implements ContextualSerializer {

  private Sensitive sensitive;

  @Override
  public void serialize(
      final String value,
      final JsonGenerator jsonGenerator,
      final SerializerProvider serializerProvider)
      throws IOException {
    // Write this value after sensitive
    jsonGenerator.writeString(SensitiveInvokeUtil.invokeSensitiveField(sensitive, value));
  }

  @Override
  public JsonSerializer<?> createContextual(
      final SerializerProvider serializerProvider, final BeanProperty beanProperty)
      throws JsonMappingException {

    JavaType javaType = beanProperty.getType();
    HandlerMethod handlerMethod = SensitiveInvokeUtil.getHandlerMethod();

    if (Objects.nonNull(handlerMethod)) {
      boolean ignoreSensitive = SensitiveInvokeUtil.ignoreSensitive();
      // If neither the current class nor the method has the @IgnoreSensitive annotation,
      // it means that the field sensitive is not ignored
      if (!ignoreSensitive) {
        this.sensitive =
            Optional.ofNullable(beanProperty.getAnnotation(Sensitive.class))
                .orElseGet(() -> beanProperty.getContextAnnotation(Sensitive.class));

        if (Objects.nonNull(sensitive)) {
          Class<?> rawClass = javaType.getRawClass();
          // Only sensitive fields of String type
          if (rawClass.isAssignableFrom(String.class)) {
            return this;
          } else {
            String propertyName = beanProperty.getName();
            log.error("Cannot sensitive {}, {} is not String type", propertyName, rawClass);
          }
        }
      }
    }

    return serializerProvider.findValueSerializer(javaType, beanProperty);
  }
}
