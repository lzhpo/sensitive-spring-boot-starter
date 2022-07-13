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

import com.google.gson.Gson;
import com.lzhpo.sensitive.utils.SensitiveInvokeUtil;
import java.io.Writer;
import java.lang.reflect.Type;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.json.GsonHttpMessageConverter;

/**
 * Use gson as sensitive converter
 *
 * @author lzhpo
 * @see org.springframework.boot.autoconfigure.http.GsonHttpMessageConvertersConfiguration
 * @see org.springframework.boot.autoconfigure.gson.GsonAutoConfiguration
 */
@ConditionalOnClass({Gson.class})
public class GsonSensitiveConfiguration {

  @Bean
  @ConditionalOnMissingBean
  public Gson gson() {
    return new Gson();
  }

  @Bean
  @ConditionalOnMissingBean
  public GsonHttpMessageConverter gsonHttpMessageConverter(Gson gson) {
    GsonHttpMessageConverter converter =
        new GsonHttpMessageConverter() {
          @Override
          protected void writeInternal(Object object, Type type, Writer writer) throws Exception {
            SensitiveInvokeUtil.invokeSensitiveObject(object);
            super.writeInternal(object, type, writer);
          }
        };
    converter.setGson(gson);
    return converter;
  }
}
