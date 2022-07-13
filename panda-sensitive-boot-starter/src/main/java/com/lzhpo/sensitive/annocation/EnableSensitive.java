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
   * Which {@link JsonConverter} to use.
   *
   * <p>Default use {@link JsonConverter#JACKSON}
   *
   * @return {@link JsonConverter}
   */
  JsonConverter value() default JsonConverter.JACKSON;
}
