package com.lzhpo.sensitive.annocation;

import com.lzhpo.sensitive.enums.SensitiveStrategy;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Sensitive strategy for the javaBean {@link String} type field
 *
 * @author lzhpo
 */
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Sensitive {

  /**
   * Sensitive strategy
   *
   * @return strategy
   */
  SensitiveStrategy strategy();

  /**
   * Pre-reserved digits, must be greater than -1，all types of {@link SensitiveStrategy} are
   * supported
   *
   * <pre>
   * -1: default, do not process
   *  0: not retained
   * </pre>
   *
   * @return pre-reserved digits
   * @throws IllegalArgumentException If it is less than -1, an exception will be thrown
   */
  int preKeep() default -1;

  /**
   * Post reserved digits, must be greater than -1, all types of {@link SensitiveStrategy} are
   * supported
   *
   * <pre>
   * -1: default, do not process
   *  0: not retained
   * </pre>
   *
   * @return post-reserved digits
   * @throws IllegalArgumentException If it is less than -1, an exception will be thrown
   */
  int suffixKeep() default -1;

  /**
   * Sensitive replacer
   *
   * @return replacer
   */
  char replacer() default '*';
}
