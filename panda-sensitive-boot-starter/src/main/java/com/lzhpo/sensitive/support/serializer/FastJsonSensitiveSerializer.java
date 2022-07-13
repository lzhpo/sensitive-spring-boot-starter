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

package com.lzhpo.sensitive.support.serializer;

import cn.hutool.core.annotation.AnnotationUtil;
import cn.hutool.core.util.ReflectUtil;
import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.ObjectSerializer;
import com.alibaba.fastjson.serializer.SerialContext;
import com.alibaba.fastjson.serializer.SerializeWriter;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.lzhpo.sensitive.annocation.Sensitive;
import com.lzhpo.sensitive.utils.SensitiveInvokeUtil;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.IdentityHashMap;
import java.util.Map.Entry;
import java.util.Objects;

/**
 * Sensitive <b>String type field</b> for fastjson
 *
 * @author lzhpo
 */
public class FastJsonSensitiveSerializer implements ObjectSerializer {

  @Override
  @SuppressWarnings({"unchecked"})
  public void write(
      JSONSerializer serializer, Object object, Object fieldNameObj, Type fieldType, int features) {

    SerializeWriter out = serializer.out;
    if (Objects.isNull(object)) {
      out.writeNull(SerializerFeature.WriteNullStringAsEmpty);
      return;
    }

    String fieldName = (String) fieldNameObj;
    String filedValue = (String) object;

    Object targetObject = null;
    IdentityHashMap<Object, SerialContext> references =
        (IdentityHashMap<Object, SerialContext>)
            ReflectUtil.getFieldValue(serializer, "references");
    SerialContext serialContext = serializer.getContext();

    for (Entry<Object, SerialContext> entry : references.entrySet()) {
      Object key = entry.getKey();
      SerialContext value = entry.getValue();
      if (serialContext == value) {
        targetObject = key;
        break;
      }
    }

    if (Objects.nonNull(targetObject)) {
      Field field = ReflectUtil.getField(targetObject.getClass(), fieldName);
      Sensitive sensitive = AnnotationUtil.getAnnotation(field, Sensitive.class);
      String finalFieldValue = SensitiveInvokeUtil.invokeSensitiveField(sensitive, filedValue);
      out.writeString(finalFieldValue);
    } else {
      out.writeString(filedValue);
    }
  }
}
