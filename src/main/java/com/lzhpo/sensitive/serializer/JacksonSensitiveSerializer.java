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
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.lzhpo.sensitive.SensitiveStrategy;
import com.lzhpo.sensitive.SensitiveWrapper;
import com.lzhpo.sensitive.annocation.IgnoreSensitive;
import com.lzhpo.sensitive.annocation.Sensitive;
import com.lzhpo.sensitive.resolver.HandlerMethodResolver;
import com.lzhpo.sensitive.util.AnnotationUtils;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ObjectUtils;
import org.springframework.web.method.HandlerMethod;

/**
 * @author lzhpo
 */
@Slf4j
public class JacksonSensitiveSerializer extends JsonSerializer<String> {

    @Override
    public Class<String> handledType() {
        return String.class;
    }

    // spotless:off
    @Override
    public void serialize(String fieldValue, JsonGenerator gen, SerializerProvider serializerProvider) throws IOException {
        if (Objects.isNull(fieldValue)) {
            log.debug("Skip sensitive, because value is null.");
            gen.writeNull();
            return;
        }

        HandlerMethodResolver handlerMethodResolver = SpringUtil.getBean(HandlerMethodResolver.class);
        HandlerMethod handlerMethod = handlerMethodResolver.resolve();
        if (ObjectUtils.isEmpty(handlerMethod)) {
            log.debug("Skip sensitive, because HandlerMethod is null.");
            gen.writeString(fieldValue);
            return;
        }

        String fieldName = gen.getOutputContext().getCurrentName();
        IgnoreSensitive ignSensitive = AnnotationUtils.getAnnotation(handlerMethod, IgnoreSensitive.class);
        Optional<IgnoreSensitive> ignSensitiveOpt = Optional.ofNullable(ignSensitive);
        Optional<String[]> ignFieldNamesOpt = ignSensitiveOpt.map(IgnoreSensitive::value);
        if ((ignSensitiveOpt.isPresent() && !ignFieldNamesOpt.filter(ArrayUtil::isNotEmpty).isPresent())
                || ignFieldNamesOpt.filter(names -> Arrays.asList(names).contains(fieldName)).isPresent()) {
            log.debug("Skip sensitive for {}, because @IgnoreSensitive is null or not contains it.", fieldName);
            gen.writeString(fieldValue);
            return;
        }

        Object object = gen.getCurrentValue();
        Class<?> objectClass = object.getClass();
        Field field = ReflectUtil.getField(objectClass, fieldName);
        Sensitive sensitive = field.getAnnotation(Sensitive.class);
        if (Objects.isNull(sensitive)) {
            log.debug("Skip sensitive for {}, because @Sensitive is null.", fieldName);
            gen.writeString(fieldValue);
            return;
        }

        SensitiveStrategy strategy = sensitive.strategy();
        String finalValue = strategy.apply(new SensitiveWrapper(object, fieldName, fieldValue));
        log.debug("Sensitive for [{}={}] with {} strategy in {}.",
                fieldName, finalValue, strategy.name(), objectClass.getSimpleName());
        gen.writeString(finalValue);
    }
    // spotless:on
}
