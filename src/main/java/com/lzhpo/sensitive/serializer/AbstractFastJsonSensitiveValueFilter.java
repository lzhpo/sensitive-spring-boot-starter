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
package com.lzhpo.sensitive.serializer;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.lzhpo.sensitive.SensitiveStrategy;
import com.lzhpo.sensitive.SensitiveWrapper;
import com.lzhpo.sensitive.annocation.IgnoreSensitive;
import com.lzhpo.sensitive.annocation.Sensitive;
import com.lzhpo.sensitive.resolve.HandlerMethodResolver;
import com.lzhpo.sensitive.util.AnnotationUtils;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ObjectUtils;
import org.springframework.web.method.HandlerMethod;

/**
 * Common method of {@code ContextValueFilter} for fastjson1 and fastjson2.
 *
 * @author lzhpo
 */
@Slf4j
public abstract class AbstractFastJsonSensitiveValueFilter {

    // spotless:off
    protected Object process(Object object, String fieldName, Object fieldValue) {
        if (ObjectUtils.isEmpty(fieldValue) || !String.class.isAssignableFrom(fieldValue.getClass())) {
            return fieldValue;
        }

        HandlerMethodResolver methodResolver = SpringUtil.getBean(HandlerMethodResolver.class);
        HandlerMethod handlerMethod = methodResolver.resolve();
        if (Objects.isNull(handlerMethod)) {
            return fieldValue;
        }

        IgnoreSensitive ignoreSensitive = AnnotationUtils.getAnnotation(handlerMethod, IgnoreSensitive.class);
        Optional<IgnoreSensitive> ignSensitiveOpt = Optional.ofNullable(ignoreSensitive);
        Optional<String[]> ignFieldNamesOpt = ignSensitiveOpt.map(IgnoreSensitive::value);
        if ((ignSensitiveOpt.isPresent() && !ignFieldNamesOpt.filter(ArrayUtil::isNotEmpty).isPresent())
                || ignFieldNamesOpt.filter(names -> Arrays.asList(names).contains(fieldName)).isPresent()) {
            log.debug("Skip sensitive for {}, because @IgnoreSensitive is null or not contains it.", fieldName);
            return fieldValue;
        }

        Field field = ReflectUtil.getField(object.getClass(), fieldName);
        Sensitive sensitive = field.getAnnotation(Sensitive.class);
        if (Objects.isNull(sensitive)) {
            return fieldValue;
        }

        SensitiveStrategy strategy = sensitive.strategy();
        log.debug("Sensitive for {} with {} strategy, replacer={}", fieldName, strategy.name(), sensitive.replacer());
        return strategy.apply(new SensitiveWrapper(object, fieldName, (String) fieldValue, sensitive.replacer()));
    }
    // spotless:on
}
