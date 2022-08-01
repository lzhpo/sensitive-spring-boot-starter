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

import com.fasterxml.jackson.annotation.JacksonAnnotationsInside;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.lzhpo.sensitive.SensitiveConstants;
import com.lzhpo.sensitive.SensitiveStrategy;
import com.lzhpo.sensitive.serializer.JacksonSensitiveSerializer;
import java.lang.annotation.*;

/**
 * Sensitive strategy for the javaBean {@link String} type field
 *
 * @author lzhpo
 */
@Inherited
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.ANNOTATION_TYPE})
@JacksonAnnotationsInside
@JsonSerialize(using = JacksonSensitiveSerializer.class)
public @interface Sensitive {

  /**
   * Sensitive strategy
   *
   * @return strategy
   */
  SensitiveStrategy strategy();

  /**
   * Sensitive replacer
   *
   * @return replacer
   */
  char replacer() default SensitiveConstants.REPLACER;
}
