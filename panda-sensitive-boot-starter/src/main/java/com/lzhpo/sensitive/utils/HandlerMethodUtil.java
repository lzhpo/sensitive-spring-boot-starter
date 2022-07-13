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

package com.lzhpo.sensitive.utils;

import cn.hutool.core.annotation.AnnotationUtil;
import java.lang.annotation.Annotation;
import java.util.Objects;
import lombok.experimental.UtilityClass;
import org.springframework.web.method.HandlerMethod;

/**
 * @author lzhpo
 */
@UtilityClass
public class HandlerMethodUtil {

  /**
   * According to {@code annotationType}, get the annotation from {@code handlerMethod}
   *
   * @param handlerMethod {@link HandlerMethod}
   * @param annotationType annotationType
   * @return {@link Annotation}
   */
  public static <T extends Annotation> T getAnnotation(
      HandlerMethod handlerMethod, Class<T> annotationType) {

    if (Objects.isNull(handlerMethod)) {
      return null;
    }

    Class<?> beanType = handlerMethod.getBeanType();
    // First get the annotation from the class of the method
    T annotation = AnnotationUtil.getAnnotation(beanType, annotationType);
    if (Objects.isNull(annotation)) {
      // If not get the annotation from this class, will get it from the current method
      annotation = handlerMethod.getMethodAnnotation(annotationType);
    }

    return annotation;
  }
}
