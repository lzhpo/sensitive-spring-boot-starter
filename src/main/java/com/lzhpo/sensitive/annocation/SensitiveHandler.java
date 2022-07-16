package com.lzhpo.sensitive.annocation;

import com.lzhpo.sensitive.CustomizeSensitiveHandler;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author lzhpo
 */
@Inherited
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.ANNOTATION_TYPE})
public @interface SensitiveHandler {

  /**
   * Sensitive customizes handler
   *
   * @return {@link CustomizeSensitiveHandler}
   */
  Class<? extends CustomizeSensitiveHandler> value();
}
