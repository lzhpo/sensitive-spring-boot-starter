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

import com.lzhpo.sensitive.support.HttpMessageConverterEnum;
import com.lzhpo.sensitive.support.HttpMessageConverterSelector;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.context.annotation.Import;

/**
 * Specifies which JSON component to use.
 *
 * <p>If is jackson, unnecessary configure, because jackson is default {@link
 * org.springframework.http.converter.HttpMessageConverter} for spring.
 *
 * @author lzhpo
 */
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import(HttpMessageConverterSelector.class)
public @interface HttpMessageConverter {

  /**
   * Which {@link HttpMessageConverterEnum} to use.
   *
   * <p>Default use {@link HttpMessageConverterEnum#JACKSON}
   *
   * @return {@link HttpMessageConverterEnum}
   */
  HttpMessageConverterEnum value() default HttpMessageConverterEnum.JACKSON;
}
