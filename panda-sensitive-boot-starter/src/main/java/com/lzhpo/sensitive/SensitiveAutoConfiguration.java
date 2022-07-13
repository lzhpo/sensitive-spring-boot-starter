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

import com.lzhpo.sensitive.support.handler.HandlerMethodServletParser;
import com.lzhpo.sensitive.support.handler.HandlerMethodWebfluxParser;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication.Type;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @author lzhpo
 */
@Configuration
@Import({SensitiveConfigurationSelector.class})
public class SensitiveAutoConfiguration {

  @Bean
  @ConditionalOnWebApplication(type = Type.SERVLET)
  public HandlerMethodServletParser handlerMethodServletParser() {
    return new HandlerMethodServletParser();
  }

  @Bean
  @ConditionalOnWebApplication(type = Type.REACTIVE)
  public HandlerMethodWebfluxParser handlerMethodWebfluxParser() {
    return new HandlerMethodWebfluxParser();
  }
}
