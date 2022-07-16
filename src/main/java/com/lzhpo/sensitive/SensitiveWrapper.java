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

  /** 字段归属的对象 */
  @NonNull private Object object;

  /** 字段 */
  @NonNull private Field field;

  /** 字段值 */
  @NonNull private String fieldValue;

  /** {@link Sensitive}注解信息 */
  @NonNull private Sensitive sensitive;
}
