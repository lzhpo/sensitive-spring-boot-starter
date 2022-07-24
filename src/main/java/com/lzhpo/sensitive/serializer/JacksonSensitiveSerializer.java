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

package com.lzhpo.sensitive.serializer;

import cn.hutool.core.util.ReflectUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.lzhpo.sensitive.SensitiveStrategy;
import com.lzhpo.sensitive.SensitiveWrapper;
import com.lzhpo.sensitive.annocation.IgnoreSensitive;
import com.lzhpo.sensitive.annocation.Sensitive;
import com.lzhpo.sensitive.resolve.HandlerMethodResolver;
import com.lzhpo.sensitive.utils.HandlerMethodUtil;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Objects;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.method.HandlerMethod;

/**
 * @author lzhpo
 */
@Slf4j
public class JacksonSensitiveSerializer extends JsonSerializer<String> {

  @Override
  public Class<String> handledType() {
    return String.class;
  }

  @Override
  public void serialize(
      final String value, final JsonGenerator gen, final SerializerProvider serializerProvider)
      throws IOException {

    if (Objects.isNull(value)) {
      gen.writeNull();
      return;
    }

    HandlerMethodResolver handlerMethodResolver = SpringUtil.getBean(HandlerMethodResolver.class);
    HandlerMethod handlerMethod = handlerMethodResolver.resolve();
    Optional<IgnoreSensitive> ignoreSensitive =
        Optional.ofNullable(handlerMethod)
            .map(method -> HandlerMethodUtil.getAnnotation(method, IgnoreSensitive.class));

    if (!ignoreSensitive.isPresent()) {
      String currentName = gen.getOutputContext().getCurrentName();
      Object currentValue = gen.getCurrentValue();
      Class<?> currentValueClass = currentValue.getClass();
      Field field = ReflectUtil.getField(currentValueClass, currentName);
      Sensitive sensitive = field.getAnnotation(Sensitive.class);

      if (Objects.nonNull(sensitive)) {
        SensitiveStrategy strategy = sensitive.strategy();
        String finalValue = strategy.apply(new SensitiveWrapper(field, value, sensitive));
        gen.writeString(finalValue);
        return;
      }
    }

    gen.writeString(value);
  }
}
