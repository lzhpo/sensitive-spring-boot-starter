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
import org.springframework.util.ObjectUtils;
import org.springframework.web.method.HandlerMethod;

/**
 * Reference: {@link HttpMessageConvertersAutoConfiguration}
 *
 * @author lzhpo
 */
@Slf4j
public abstract class AbstractSensitiveInvoker {

  protected HandlerMethodResolver handlerMethodResolver;

  private static final SimpleCache<Class<?>, Field[]> SENSITIVE_FIELDS_CACHE = new SimpleCache<>();

  @Autowired
  protected void setHandlerMethodResolver(HandlerMethodResolver handlerMethodResolver) {
    this.handlerMethodResolver = handlerMethodResolver;
  }

  /**
   * Invoke object field sensitive
   *
   * @param object object
   */
  protected void invokeSensitive(Object object) {
    Class<?> clazz = object.getClass();
    if (!ignoreSensitive()) {
      Field[] requireFields = SENSITIVE_FIELDS_CACHE.get(clazz, () -> getSensitiveFields(clazz));
      for (Field field : requireFields) {
        Sensitive sensitive = field.getAnnotation(Sensitive.class);
        String fieldName = field.getName();
        String fieldValue = (String) ReflectUtil.getFieldValue(object, field);

        SensitiveStrategy strategy = sensitive.strategy();
        if (isBlankFieldValue(fieldName, fieldValue)
            || isNullSensitive(sensitive, fieldName)
            || isNullStrategy(strategy, fieldName)) {
          continue;
        }

        fieldValue = strategy.apply(new SensitiveWrapper(object, field, fieldValue, sensitive));
        ReflectUtil.setFieldValue(object, fieldName, fieldValue);
      }
    }
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

  /**
   * Whether fieldValue is empty
   *
   * @param fieldName fieldName
   * @param fieldValue fieldValue
   * @return whether fieldValue is empty
   */
  private boolean isBlankFieldValue(String fieldName, String fieldValue) {
    boolean empty = ObjectUtils.isEmpty(fieldValue);
    if (log.isDebugEnabled()) {
      log.warn("{} is blank, will ignore sensitive it.", fieldName);
    }
    return empty;
  }

  /**
   * Whether sensitive is empty
   *
   * @param sensitive {@link Sensitive}
   * @param fieldName fieldName
   * @return whether sensitive is empty
   */
  private boolean isNullSensitive(Sensitive sensitive, String fieldName) {
    boolean empty = ObjectUtils.isEmpty(sensitive);
    if (log.isDebugEnabled()) {
      log.warn("{} not has @Sensitive, will ignored sensitive it", fieldName);
    }
    return empty;
  }

  /**
   * Whether strategy is empty
   *
   * @param strategy {@link SensitiveStrategy}
   * @param fieldName fieldName
   * @return whether strategy is empty
   */
  private boolean isNullStrategy(SensitiveStrategy strategy, String fieldName) {
    boolean empty = ObjectUtils.isEmpty(strategy);
    if (log.isDebugEnabled()) {
      log.warn("{} not has @Sensitive strategy, will ignored sensitive it", fieldName);
    }
    return empty;
  }
}
