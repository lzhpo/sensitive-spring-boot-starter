package com.lzhpo.sensitive.annocation;

import com.lzhpo.sensitive.SensitiveConfigurationSelector;
import com.lzhpo.sensitive.enums.JsonConverter;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.context.annotation.Import;

/**
 * Enabled sensitive.
 *
 * @author lzhpo
 */
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import(SensitiveConfigurationSelector.class)
public @interface EnableSensitive {

  /**
   * Whether to proxy sensitive by the internal json sensitive.
   *
   * <p>Default proxy
   *
   * @return proxy
   */
  boolean proxy() default true;

  /**
   * If {@link EnableSensitive#proxy()}, Which {@link JsonConverter} to use.
   *
   * <p>Default use {@link JsonConverter#JACKSON}
   *
   * @return {@link JsonConverter}
   */
  JsonConverter converter() default JsonConverter.JACKSON;
}
