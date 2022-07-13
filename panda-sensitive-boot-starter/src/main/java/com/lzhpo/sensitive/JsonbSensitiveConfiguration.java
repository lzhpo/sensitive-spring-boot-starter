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

import com.lzhpo.sensitive.utils.SensitiveInvokeUtil;
import java.io.Writer;
import java.lang.reflect.Type;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnResource;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.json.JsonbHttpMessageConverter;

/**
 * Use jsonb as sensitive converter
 *
 * @author lzhpo
 * @see org.springframework.boot.autoconfigure.http.JsonbHttpMessageConvertersConfiguration
 * @see org.springframework.boot.autoconfigure.jsonb.JsonbAutoConfiguration
 */
@ConditionalOnClass(Jsonb.class)
@ConditionalOnResource(
    resources = {
      "classpath:META-INF/services/javax.json.bind.spi.JsonbProvider",
      "classpath:META-INF/services/javax.json.spi.JsonProvider"
    })
public class JsonbSensitiveConfiguration {

  @Bean
  @ConditionalOnMissingBean
  public Jsonb jsonb() {
    return JsonbBuilder.create();
  }

  @Bean
  @ConditionalOnMissingBean
  public JsonbHttpMessageConverter jsonbHttpMessageConverter(Jsonb jsonb) {
    JsonbHttpMessageConverter jsonbHttpMessageConverter =
        new JsonbHttpMessageConverter() {
          @Override
          protected void writeInternal(Object object, Type type, Writer writer) throws Exception {
            SensitiveInvokeUtil.invokeSensitiveObject(object);
            super.writeInternal(object, type, writer);
          }
        };
    jsonbHttpMessageConverter.setJsonb(jsonb);
    return jsonbHttpMessageConverter;
  }
}
