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

import com.alibaba.fastjson2.support.spring.http.converter.FastJsonHttpMessageConverter;
import com.lzhpo.sensitive.resolve.RequestMappingResolver;
import com.lzhpo.sensitive.serializer.JacksonSensitiveSerializer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication.Type;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

/**
 * @author lzhpo
 */
@Configuration
@ConditionalOnWebApplication(type = Type.SERVLET)
@EnableConfigurationProperties({SensitiveProperties.class})
@ConditionalOnProperty(
    prefix = "sensitive",
    value = "enabled",
    havingValue = "true",
    matchIfMissing = true)
public class SensitiveAutoConfiguration {

  @Bean
  @ConditionalOnMissingBean
  public RequestMappingResolver handlerMethodServletParser(
      @Qualifier("requestMappingHandlerMapping")
          RequestMappingHandlerMapping requestMappingHandlerMapping) {
    return new RequestMappingResolver(requestMappingHandlerMapping);
  }

  @Bean
  public Jackson2ObjectMapperBuilderCustomizer jackson2ObjectMapperBuilderCustomizer() {
    return builder -> builder.serializers(new JacksonSensitiveSerializer());
  }

  @Bean
  @ConditionalOnBean({FastJsonHttpMessageConverter.class})
  public FastjsonBeanPostProcessor httpMessageConverterBeanPostProcessor() {
    return new FastjsonBeanPostProcessor();
  }
}
