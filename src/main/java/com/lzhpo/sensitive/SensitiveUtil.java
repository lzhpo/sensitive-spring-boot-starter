package com.lzhpo.sensitive;

import cn.hutool.core.annotation.AnnotationUtil;
import cn.hutool.core.lang.SimpleCache;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.lzhpo.sensitive.annocation.IgnoreSensitive;
import com.lzhpo.sensitive.annocation.Sensitive;
import com.lzhpo.sensitive.enums.SensitiveStrategy;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerExecutionChain;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

/**
 * @author lzhpo
 * @see org.springframework.boot.autoconfigure.http.HttpMessageConvertersAutoConfiguration
 */
@Slf4j
@UtilityClass
public class SensitiveUtil {

  /** For Class, field cache with @Sensitive annotation and String type */
  private static final SimpleCache<Class<?>, Field[]> REQUIRE_FIELDS_CACHE = new SimpleCache<>();

  /**
   * Invoke object field sensitive
   *
   * @param object object
   */
  public static void invokeSensitiveObject(Object object) {
    SensitiveProperties sensitiveProperties = SpringUtil.getBean(SensitiveProperties.class);
    if (!sensitiveProperties.isProxy()) {
      return;
    }

    long startMillis = System.currentTimeMillis();
    Class<?> clazz = object.getClass();

    if (!isIgnoreSensitive()) {
      Field[] requireFields =
          REQUIRE_FIELDS_CACHE.get(
              clazz,
              () ->
                  Arrays.stream(
                          ObjectUtil.defaultIfNull(ReflectUtil.getFields(clazz), new Field[0]))
                      .filter(
                          field ->
                              field.isAnnotationPresent(Sensitive.class)
                                  && field.getType().isAssignableFrom(String.class))
                      .toArray(Field[]::new));

      for (Field field : requireFields) {
        Sensitive sensitive = field.getAnnotation(Sensitive.class);
        Object fieldValue = ReflectUtil.getFieldValue(object, field);

        if (Objects.nonNull(sensitive) && Objects.nonNull(fieldValue)) {
          String finalFieldValue = invokeSensitiveField(sensitive, (String) fieldValue);
          ReflectUtil.setFieldValue(object, field.getName(), finalFieldValue);
        }
      }
    }

    if (log.isDebugEnabled()) {
      log.debug(
          "Completed invoke sensitive [{}] in {} ms",
          clazz,
          System.currentTimeMillis() - startMillis);
    }
  }

  /**
   * Invoke field value sensitive
   *
   * @param sensitive {@link Sensitive}
   * @param fieldValue field value
   * @return after sensitive field value
   */
  public static String invokeSensitiveField(Sensitive sensitive, String fieldValue) {
    if (Objects.isNull(sensitive)) {
      log.warn("@Sensitive is null, ignored sensitive fieldValue [{}]", fieldValue);
      return fieldValue;
    }

    if (Objects.isNull(sensitive.strategy())) {
      log.warn("@Sensitive strategy is null, ignored sensitive fieldValue [{}]", fieldValue);
      return fieldValue;
    }

    return Arrays.stream(SensitiveStrategy.values())
        .filter(x -> x == sensitive.strategy())
        .findAny()
        .map(handler -> handler.accept(fieldValue, sensitive))
        .orElseThrow(() -> new IllegalArgumentException("Unknown sensitive strategy type"));
  }

  /**
   * Whether the current request needs to ignore sensitive
   *
   * @return whether the current request needs to ignore sensitive
   */
  public static boolean isIgnoreSensitive() {
    HandlerMethod handlerMethod = getHandlerMethod();
    return Objects.nonNull(getAnnotation(handlerMethod, IgnoreSensitive.class));
  }

  /**
   * According to {@code annotationType}, get the annotation from {@code handlerMethod}
   *
   * @param handlerMethod {@link HandlerMethod}
   * @param annotationType annotationType
   * @return {@link Annotation}
   */
  public static <T extends Annotation> T getAnnotation(
      HandlerMethod handlerMethod, Class<T> annotationType) {
    if (Objects.nonNull(handlerMethod)) {
      Class<?> beanType = handlerMethod.getBeanType();
      // First get the annotation from the class of the method
      T annotation = AnnotationUtil.getAnnotation(beanType, annotationType);
      if (Objects.isNull(annotation)) {
        // If not get the annotation from this class, will get it from the current method
        annotation = handlerMethod.getMethodAnnotation(annotationType);
      }
      return annotation;
    }
    return null;
  }

  /**
   * Get {@link HttpServletRequest}
   *
   * @return {@link HttpServletRequest}
   */
  public static HttpServletRequest getRequest() {
    return getRequestAttributes().getRequest();
  }

  /**
   * Find the corresponding {@link HandlerMethod} through {@link HttpServletRequest}
   *
   * @return when not found or an error is reported, instead of throw exception, it returns null
   */
  public static HandlerMethod getHandlerMethod() {
    try {
      RequestMappingHandlerMapping mapping = SpringUtil.getBean(RequestMappingHandlerMapping.class);
      HttpServletRequest request = getRequest();
      return getHandlerMethod(mapping, request);
    } catch (Exception e) {
      log.error("Unable to find the corresponding HandlerMethod", e);
    }
    return null;
  }

  /**
   * Find the corresponding {@link HandlerMethod} through {@link HttpServletRequest}
   *
   * @param mapping {@link RequestMappingHandlerMapping}
   * @param request {@link HttpServletRequest}
   * @return when not found or an error is reported, instead of throw exception, it returns null
   */
  public static HandlerMethod getHandlerMethod(
      RequestMappingHandlerMapping mapping, HttpServletRequest request) {
    try {
      HandlerExecutionChain handlerChain = mapping.getHandler(request);
      if (Objects.nonNull(handlerChain)) {
        return (HandlerMethod) handlerChain.getHandler();
      }
    } catch (Exception e) {
      log.error("Unable to find the corresponding HandlerMethod", e);
    }
    return null;
  }

  /**
   * Get {@link ServletRequestAttributes}
   *
   * @return {@link ServletRequestAttributes}
   */
  public static ServletRequestAttributes getRequestAttributes() {
    return Optional.ofNullable(RequestContextHolder.getRequestAttributes())
        .filter(ServletRequestAttributes.class::isInstance)
        .map(ServletRequestAttributes.class::cast)
        .orElseThrow(() -> new IllegalArgumentException("Unable to get RequestAttributes"));
  }
}
