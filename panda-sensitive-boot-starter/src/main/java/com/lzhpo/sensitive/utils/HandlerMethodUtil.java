package com.lzhpo.sensitive.utils;

import cn.hutool.core.annotation.AnnotationUtil;
import java.lang.annotation.Annotation;
import java.util.Objects;
import lombok.experimental.UtilityClass;
import org.springframework.web.method.HandlerMethod;

/**
 * @author lzhpo
 */
@UtilityClass
public class HandlerMethodUtil {

  /**
   * According to {@code annotationType}, get the annotation from {@code handlerMethod}
   *
   * @param handlerMethod {@link HandlerMethod}
   * @param annotationType annotationType
   * @return {@link Annotation}
   */
  public static <T extends Annotation> T getAnnotation(
      HandlerMethod handlerMethod, Class<T> annotationType) {

    if (Objects.isNull(handlerMethod)) {
      return null;
    }

    Class<?> beanType = handlerMethod.getBeanType();
    // First get the annotation from the class of the method
    T annotation = AnnotationUtil.getAnnotation(beanType, annotationType);
    if (Objects.isNull(annotation)) {
      // If not get the annotation from this class, will get it from the current method
      annotation = handlerMethod.getMethodAnnotation(annotationType);
    }

    return annotation;
  }
}
