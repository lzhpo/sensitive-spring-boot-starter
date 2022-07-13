package com.lzhpo.sensitive.serializer;

import cn.hutool.core.annotation.AnnotationUtil;
import cn.hutool.core.util.ReflectUtil;
import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.ObjectSerializer;
import com.alibaba.fastjson.serializer.SerialContext;
import com.alibaba.fastjson.serializer.SerializeWriter;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.lzhpo.sensitive.annocation.Sensitive;
import com.lzhpo.sensitive.SensitiveUtil;
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
      String finalFieldValue = SensitiveUtil.invokeSensitiveField(sensitive, filedValue);
      out.writeString(finalFieldValue);
    } else {
      out.writeString(filedValue);
    }
  }
}
