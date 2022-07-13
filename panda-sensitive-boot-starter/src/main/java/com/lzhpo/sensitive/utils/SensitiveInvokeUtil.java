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

import cn.hutool.core.lang.SimpleCache;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.lzhpo.sensitive.annocation.IgnoreSensitive;
import com.lzhpo.sensitive.annocation.Sensitive;
import com.lzhpo.sensitive.enums.SensitiveStrategy;
import com.lzhpo.sensitive.support.handler.HandlerMethodParser;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Objects;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.method.HandlerMethod;

/**
 * @author lzhpo
 * @see org.springframework.boot.autoconfigure.http.HttpMessageConvertersAutoConfiguration
 */
@Slf4j
@UtilityClass
public class SensitiveInvokeUtil {

  /** For Class, field cache with @Sensitive annotation and String type */
  private static final SimpleCache<Class<?>, Field[]> REQUIRE_FIELDS_CACHE = new SimpleCache<>();

  /**
   * Invoke object field sensitive
   *
   * @param object object
   */
  public static void invokeSensitiveObject(Object object) {
    Class<?> clazz = object.getClass();
    if (!ignoreSensitive()) {
      Field[] requireFields =
          REQUIRE_FIELDS_CACHE.get(
              clazz,
              () ->
                  Arrays.stream(
                          ObjectUtil.defaultIfNull(ReflectUtil.getFields(clazz), new Field[0]))
                      .filter(
                          field ->
                              field.isAnnotationPresent(Sensitive.class)
                                  && field.getType().isAssignableFrom(String.class))
                      .toArray(Field[]::new));

      for (Field field : requireFields) {
        Sensitive sensitive = field.getAnnotation(Sensitive.class);
        Object fieldValue = ReflectUtil.getFieldValue(object, field);

        if (Objects.nonNull(sensitive) && Objects.nonNull(fieldValue)) {
          String finalFieldValue = invokeSensitiveField(sensitive, (String) fieldValue);
          ReflectUtil.setFieldValue(object, field.getName(), finalFieldValue);
        }
      }
    }
  }

  /**
   * Invoke field value sensitive
   *
   * @param sensitive {@link Sensitive}
   * @param fieldValue field value
   * @return after sensitive field value
   */
  public static String invokeSensitiveField(Sensitive sensitive, String fieldValue) {
    if (Objects.isNull(sensitive)) {
      log.warn("@Sensitive is null, ignored sensitive fieldValue [{}]", fieldValue);
      return fieldValue;
    }

    if (Objects.isNull(sensitive.strategy())) {
      log.warn("@Sensitive strategy is null, ignored sensitive fieldValue [{}]", fieldValue);
      return fieldValue;
    }

    return Arrays.stream(SensitiveStrategy.values())
        .filter(x -> x == sensitive.strategy())
        .findAny()
        .map(handler -> handler.apply(fieldValue, sensitive))
        .orElseThrow(() -> new IllegalArgumentException("Unknown sensitive strategy type"));
  }

  /**
   * Whether the current request needs to ignore sensitive
   *
   * @return whether the current request needs to ignore sensitive
   */
  public static boolean ignoreSensitive() {
    HandlerMethod handlerMethod = getHandlerMethod();
    return Objects.nonNull(HandlerMethodUtil.getAnnotation(handlerMethod, IgnoreSensitive.class));
  }

  public static HandlerMethod getHandlerMethod() {
    return SpringUtil.getBean(HandlerMethodParser.class).get();
  }
}
