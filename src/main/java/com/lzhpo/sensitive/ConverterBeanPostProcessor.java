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

import com.lzhpo.sensitive.resolve.HandlerMethodResolver;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;

/**
 * @author lzhpo
 */
@Slf4j
@RequiredArgsConstructor
public class ConverterBeanPostProcessor implements BeanPostProcessor {

  private final HandlerMethodResolver handlerMethodResolver;

  @Override
  @SuppressWarnings("unchecked")
  public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
    if (HttpMessageConverter.class.isAssignableFrom(bean.getClass())
        && !(bean instanceof StringHttpMessageConverter)) {
      HttpMessageConverter<Object> messageConverter = (HttpMessageConverter<Object>) bean;
      log.info("Injected sensitive logic to {}[{}]", beanName, bean);
      return new ProxyHttpMessageConverter(messageConverter, handlerMethodResolver);
    }
    return bean;
  }
}
