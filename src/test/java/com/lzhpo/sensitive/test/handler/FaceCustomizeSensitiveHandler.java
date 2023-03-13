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

package com.lzhpo.sensitive.test.handler;

import com.lzhpo.sensitive.CustomizeSensitiveHandler;
import com.lzhpo.sensitive.SensitiveWrapper;
import com.lzhpo.sensitive.annocation.Sensitive;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

/**
 * @author lzhpo
 */
public class FaceCustomizeSensitiveHandler implements CustomizeSensitiveHandler {

    @Override
    public String customize(SensitiveWrapper sensitiveWrapper) {
        // 字段
        Field field = sensitiveWrapper.getField();
        // 字段归属的对象
        Class<?> objectClass = field.getDeclaringClass();
        // 字段上的注解
        Annotation[] annotations = field.getAnnotations();
        // 字段值
        String fieldValue = sensitiveWrapper.getFieldValue();
        // 注解信息
        Sensitive sensitive = sensitiveWrapper.getSensitive();
        return "@#@";
    }
}
