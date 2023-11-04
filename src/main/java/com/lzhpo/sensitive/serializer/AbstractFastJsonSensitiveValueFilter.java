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

package com.lzhpo.sensitive.serializer;

import cn.hutool.core.util.ReflectUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.lzhpo.sensitive.SensitiveStrategy;
import com.lzhpo.sensitive.SensitiveWrapper;
import com.lzhpo.sensitive.annocation.IgnoreSensitive;
import com.lzhpo.sensitive.annocation.Sensitive;
import com.lzhpo.sensitive.resolve.HandlerMethodResolver;
import com.lzhpo.sensitive.utils.HandlerMethodUtil;
import java.lang.reflect.Field;
import java.util.Objects;
import org.springframework.util.ObjectUtils;
import org.springframework.web.method.HandlerMethod;

/**
 * Common method of {@code ContextValueFilter} for fastjson1 and fastjson2.
 *
 * @author lzhpo
 */
public abstract class AbstractFastJsonSensitiveValueFilter {

    protected Object process(Object object, String name, Object value) {
        HandlerMethodResolver methodResolver = SpringUtil.getBean(HandlerMethodResolver.class);
        HandlerMethod handlerMethod = methodResolver.resolve();
        if (ObjectUtils.isEmpty(value)
                || !String.class.isAssignableFrom(value.getClass())
                || Objects.isNull(handlerMethod)
                || Objects.nonNull(HandlerMethodUtil.getAnnotation(handlerMethod, IgnoreSensitive.class))) {
            return value;
        }

        Class<?> clazz = object.getClass();
        Field field = ReflectUtil.getField(clazz, name);
        Sensitive sensitive = field.getAnnotation(Sensitive.class);
        if (Objects.isNull(sensitive)) {
            return value;
        }

        SensitiveStrategy strategy = sensitive.strategy();
        return strategy.apply(new SensitiveWrapper(object, name, (String) value, sensitive.replacer()));
    }
}
