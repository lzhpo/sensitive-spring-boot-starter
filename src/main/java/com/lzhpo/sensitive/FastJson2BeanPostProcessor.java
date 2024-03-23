/*
 * Copyright lzhpo
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
import com.alibaba.fastjson2.filter.Filter;
import com.alibaba.fastjson2.support.config.FastJsonConfig;
import com.alibaba.fastjson2.support.spring6.http.converter.FastJsonHttpMessageConverter;
import com.lzhpo.sensitive.serializer.FastJson2SensitiveValueFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

/**
 * @author lzhpo
 */
@Slf4j
public class FastJson2BeanPostProcessor implements BeanPostProcessor {

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof FastJsonHttpMessageConverter fastJsonConverter) {
            FastJsonConfig fastJsonConfig = fastJsonConverter.getFastJsonConfig();
            Filter[] oldWriterFilters = fastJsonConfig.getWriterFilters();

            FastJson2SensitiveValueFilter[] injectWriterFilters = {new FastJson2SensitiveValueFilter()};
            Filter[] newWriterFilters = ArrayUtil.addAll(oldWriterFilters, injectWriterFilters);
            fastJsonConfig.setWriterFilters(newWriterFilters);

            log.info(
                    "Injected [{}] WriterFilter to [{}]",
                    FastJson2SensitiveValueFilter.class.getName(),
                    FastJsonHttpMessageConverter.class.getName());

            return fastJsonConverter;
        }
        return bean;
    }
}
