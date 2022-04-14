package com.lzhpo.sensitive.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.ContextualSerializer;
import com.lzhpo.sensitive.annocation.Sensitive;
import com.lzhpo.sensitive.utils.SensitiveUtil;
import java.io.IOException;
import java.util.Objects;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.method.HandlerMethod;

/**
 * Sensitive field for jackson
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
    jsonGenerator.writeString(SensitiveUtil.invokeSensitiveField(sensitive, value));
  }

  @Override
  public JsonSerializer<?> createContextual(
      final SerializerProvider serializerProvider, final BeanProperty beanProperty)
      throws JsonMappingException {

    JavaType javaType = beanProperty.getType();
    HandlerMethod handlerMethod = SensitiveUtil.getHandlerMethod();

    if (Objects.nonNull(handlerMethod)) {
      boolean ignoreSensitive = SensitiveUtil.isIgnoreSensitive();
      // If neither the current class nor the method has the @IgnoreSensitive annotation
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
