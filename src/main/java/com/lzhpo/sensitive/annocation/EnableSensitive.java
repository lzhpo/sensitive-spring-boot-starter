package com.lzhpo.sensitive.annocation;

import com.lzhpo.sensitive.config.SensitiveConfigurationSelector;
import com.lzhpo.sensitive.enums.SensitiveConverterType;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.context.annotation.Import;

/**
 * Enable sensitive
 *
 * @author lzhpo
 */
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import(SensitiveConfigurationSelector.class)
public @interface EnableSensitive {

  /**
   * Sensitive converterType
   *
   * @return {@link SensitiveConverterType}
   */
  SensitiveConverterType converterType() default SensitiveConverterType.JACKSON;

  /**
   * Whether automatic sensitive convert, no need to write the @JsonSerialize annotation, just mark
   * the field with @Sensitive
   *
   * <p>If false, you need to do:
   *
   * <pre>{@code
   * @Sensitive(strategy = SensitiveStrategy.PASSWORD)
   * @JsonSerialize(using = JacksonSensitiveSerializer.class)
   * private String password;
   * }</pre>
   *
   * @return whether automatic sensitive convert
   */
  boolean autoConvert() default false;
}
