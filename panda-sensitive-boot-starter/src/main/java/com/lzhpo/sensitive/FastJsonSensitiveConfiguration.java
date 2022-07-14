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

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.converter.HttpMessageNotWritableException;

/**
 * Use fastJson as sensitive converter
 *
 * @author lzhpo
 */
@ConditionalOnClass({FastJsonConfig.class, FastJsonHttpMessageConverter.class})
public class FastJsonSensitiveConfiguration extends AbstractSensitiveConfiguration {

  @Bean
  @ConditionalOnMissingBean
  public HttpMessageConverters fastJsonHttpMessageConverters() {
    FastJsonHttpMessageConverter fastJsonHttpMessageConverter =
        new FastJsonHttpMessageConverter() {
          @Override
          protected void writeInternal(Object object, HttpOutputMessage outputMessage)
              throws IOException, HttpMessageNotWritableException {
            invokeSensitiveObject(object);
            super.writeInternal(object, outputMessage);
          }
        };
    // Avoid Chinese garbled characters
    fastJsonHttpMessageConverter.setDefaultCharset(StandardCharsets.UTF_8);
    FastJsonConfig fastJsonConfig = new FastJsonConfig();
    fastJsonConfig.setSerializerFeatures(SerializerFeature.PrettyFormat);
    fastJsonHttpMessageConverter.setFastJsonConfig(fastJsonConfig);
    return new HttpMessageConverters(fastJsonHttpMessageConverter);
  }
}
