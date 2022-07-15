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

package com.lzhpo.sensitive.annocation;

import com.lzhpo.sensitive.SensitiveStrategy;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Sensitive strategy for the javaBean {@link String} type field
 *
 * @author lzhpo
 */
@Inherited
@Documented
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Sensitive {

  /**
   * Sensitive strategy
   *
   * @return strategy
   */
  SensitiveStrategy strategy();

  /**
   * Pre-reserved digits, all types of {@link SensitiveStrategy} are supported
   *
   * <pre>
   * If less than or equal to 0, it means ignore
   * </pre>
   *
   * @return pre-reserved digits
   * @throws IllegalArgumentException If it is less than -1, an exception will be thrown
   */
  int preKeep() default -1;

  /**
   * Post-reserved digits, all types of {@link SensitiveStrategy} are supported
   *
   * <pre>
   * If less than or equal to 0, it means ignore
   * </pre>
   *
   * @return post-reserved digits
   * @throws IllegalArgumentException If it is less than -1, an exception will be thrown
   */
  int postKeep() default -1;

  /**
   * Sensitive replacer
   *
   * @return replacer
   */
  char replacer() default '*';
}
