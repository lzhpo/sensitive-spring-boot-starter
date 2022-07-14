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

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.lang.reflect.Type;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

/**
 * Use jackson as sensitive converter
 *
 * @author lzhpo
 * @see org.springframework.boot.autoconfigure.http.JacksonHttpMessageConvertersConfiguration
 * @see org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration
 */
@ConditionalOnClass({ObjectMapper.class})
public class JacksonSensitiveConfiguration extends AbstractSensitiveConfiguration {

  @Bean
  @ConditionalOnMissingBean
  public ObjectMapper jacksonObjectMapper() {
    return new ObjectMapper();
  }

  @Bean
  @ConditionalOnMissingBean
  public MappingJackson2HttpMessageConverter jackson2HttpMessageConverter(
      ObjectMapper objectMapper) {
    return new MappingJackson2HttpMessageConverter(objectMapper) {
      @Override
      protected void writeInternal(Object object, Type type, HttpOutputMessage outputMessage)
          throws IOException, HttpMessageNotWritableException {
        invokeSensitiveObject(object);
        super.writeInternal(object, type, outputMessage);
      }
    };
  }
}
