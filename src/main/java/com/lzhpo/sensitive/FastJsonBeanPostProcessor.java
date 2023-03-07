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

import cn.hutool.core.util.ArrayUtil;
import com.alibaba.fastjson.serializer.SerializeFilter;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.lzhpo.sensitive.serializer.FastJsonSensitiveValueFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

/**
 * @author lzhpo
 */
@Slf4j
public class FastJsonBeanPostProcessor implements BeanPostProcessor {

  @Override
  public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
    if (bean instanceof FastJsonHttpMessageConverter fastJsonConverter) {
      FastJsonConfig fastJsonConfig = fastJsonConverter.getFastJsonConfig();
      SerializeFilter[] oldFilters = fastJsonConfig.getSerializeFilters();

      SerializeFilter[] injectFilters = {new FastJsonSensitiveValueFilter()};
      SerializeFilter[] newFilters = ArrayUtil.addAll(oldFilters, injectFilters);
      fastJsonConfig.setSerializeFilters(newFilters);

      log.info(
          "Injected [{}] WriterFilter to [{}]",
          FastJsonSensitiveValueFilter.class.getName(),
          FastJsonHttpMessageConverter.class.getName());

      return fastJsonConverter;
    }
    return bean;
  }
}
