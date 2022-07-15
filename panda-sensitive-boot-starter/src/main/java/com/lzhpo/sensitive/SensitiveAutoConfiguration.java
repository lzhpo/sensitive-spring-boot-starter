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
import com.lzhpo.sensitive.resolve.RequestMappingResolver;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication.Type;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

/**
 * @author lzhpo
 */
@Configuration
@ConditionalOnWebApplication(type = Type.SERVLET)
public class SensitiveAutoConfiguration {

  @Bean
  public RequestMappingResolver handlerMethodServletParser(
      RequestMappingHandlerMapping handlerMapping) {
    return new RequestMappingResolver(handlerMapping);
  }

  @Bean
  public ConverterBeanPostProcessor converterBeanPostProcessor(
      @Lazy HandlerMethodResolver handlerMethodResolver) {
    return new ConverterBeanPostProcessor(handlerMethodResolver);
  }
}
