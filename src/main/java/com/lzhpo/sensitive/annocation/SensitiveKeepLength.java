package com.lzhpo.sensitive.annocation;

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
public @interface SensitiveKeepLength {

  /**
   * Pre-reserved digits
   *
   * <pre>
   * If less than or equal to 0, it means ignore
   * </pre>
   *
   * @return pre-reserved digits
   * @throws IllegalArgumentException If it is less than -1, an exception will be thrown
   */
  int preKeep();

  /**
   * Post-reserved digits
   *
   * <pre>
   * If less than or equal to 0, it means ignore
   * </pre>
   *
   * @return post-reserved digits
   * @throws IllegalArgumentException If it is less than -1, an exception will be thrown
   */
  int postKeep();
}
