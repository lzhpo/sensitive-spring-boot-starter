package com.lzhpo.sensitive;

import com.lzhpo.sensitive.annocation.Sensitive;
import java.lang.reflect.Field;

/**
 * @author lzhpo
 */
public class FaceCustomizeSensitiveHandler implements CustomizeSensitiveHandler {

  @Override
  public String customize(SensitiveWrapper sensitiveWrapper) {
    // 字段归属的对象
    Object object = sensitiveWrapper.getObject();
    // 字段
    Field field = sensitiveWrapper.getField();
    // 字段值
    String fieldValue = sensitiveWrapper.getFieldValue();
    // 注解信息
    Sensitive sensitive = sensitiveWrapper.getSensitive();
    return "@#@";
  }
}
