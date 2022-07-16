package com.lzhpo.sensitive;

import com.lzhpo.sensitive.annocation.Sensitive;
import java.lang.reflect.Field;
import lombok.Data;
import lombok.NonNull;

/**
 * 有{@link Sensitive}注解的关联对象、字段、字段值...均不为空
 *
 * @author lzhpo
 */
@Data
public class SensitiveWrapper {

  @NonNull private Object object;

  @NonNull private Field field;

  @NonNull private String fieldValue;

  @NonNull private Sensitive sensitive;
}
