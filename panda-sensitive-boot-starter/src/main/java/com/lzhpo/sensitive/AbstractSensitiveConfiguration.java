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

package com.lzhpo.sensitive;

import cn.hutool.core.lang.SimpleCache;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.ReflectUtil;
import com.lzhpo.sensitive.annocation.IgnoreSensitive;
import com.lzhpo.sensitive.annocation.Sensitive;
import com.lzhpo.sensitive.resolve.HandlerMethodResolver;
import com.lzhpo.sensitive.utils.HandlerMethodUtil;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.http.HttpMessageConvertersAutoConfiguration;
import org.springframework.web.method.HandlerMethod;

/**
 * Reference: {@link HttpMessageConvertersAutoConfiguration}
 *
 * @author lzhpo
 */
@Slf4j
public abstract class AbstractSensitiveConfiguration {

  protected HandlerMethodResolver handlerMethodResolver;

  private static final SimpleCache<Class<?>, Field[]> SENSITIVE_FIELDS_CACHE = new SimpleCache<>();

  protected HandlerMethodResolver getHandlerMethodResolver() {
    return handlerMethodResolver;
  }

  @Autowired
  protected void setHandlerMethodResolver(HandlerMethodResolver handlerMethodResolver) {
    this.handlerMethodResolver = handlerMethodResolver;
  }

  /**
   * Invoke object field sensitive
   *
   * @param object object
   */
  protected void invokeSensitiveObject(Object object) {
    Class<?> clazz = object.getClass();
    if (!ignoreSensitive()) {
      Field[] requireFields = SENSITIVE_FIELDS_CACHE.get(clazz, () -> getSensitiveFields(clazz));
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
  protected String invokeSensitiveField(Sensitive sensitive, String fieldValue) {
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
  protected boolean ignoreSensitive() {
    return Optional.ofNullable(getHandlerMethod())
        .map(x -> Objects.nonNull(HandlerMethodUtil.getAnnotation(x, IgnoreSensitive.class)))
        .orElse(true);
  }

  /**
   * Get current handlerMethod
   *
   * @return {@link HandlerMethod}
   */
  protected HandlerMethod getHandlerMethod() {
    return handlerMethodResolver.resolve();
  }

  /**
   * Get has {@link Sensitive} fields from {@code clazz}
   *
   * @param clazz object class
   * @return object fields
   */
  protected Field[] getSensitiveFields(Class<?> clazz) {
    return Arrays.stream(ObjectUtil.defaultIfNull(ReflectUtil.getFields(clazz), new Field[0]))
        .filter(
            field ->
                field.isAnnotationPresent(Sensitive.class)
                    && field.getType().isAssignableFrom(String.class))
        .toArray(Field[]::new);
  }
}
