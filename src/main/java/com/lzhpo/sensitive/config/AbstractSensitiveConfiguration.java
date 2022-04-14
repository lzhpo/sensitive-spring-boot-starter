package com.lzhpo.sensitive.config;

import cn.hutool.core.annotation.AnnotationUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.TimeInterval;
import cn.hutool.core.lang.SimpleCache;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.lzhpo.sensitive.annocation.IgnoreSensitive;
import com.lzhpo.sensitive.annocation.Sensitive;
import com.lzhpo.sensitive.enums.SensitiveStrategy;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
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
public abstract class AbstractSensitiveConfiguration {

  private static final SimpleCache<Class<?>, Field[]> REQUIRE_FIELDS_CACHE = new SimpleCache<>();

  /**
   * Invoke object field sensitive
   *
   * @param object object
   */
  protected void invokeSensitive(Object object) {
    TimeInterval timer = DateUtil.timer();
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
          String finalFieldValue =
              Arrays.stream(SensitiveStrategy.values())
                  .filter(x -> x == sensitive.strategy())
                  .findAny()
                  .map(handler -> handler.accept((String) fieldValue, sensitive))
                  .orElseThrow(
                      () -> new IllegalArgumentException("Unknown sensitive strategy type"));
          ReflectUtil.setFieldValue(object, field.getName(), finalFieldValue);
        }
      }
    }

    if (log.isDebugEnabled()) {
      log.debug("Completed invoke sensitive [{}] in {} ms", clazz, timer.intervalMs());
    }
  }

  /**
   * Whether the current request needs to ignore sensitive
   *
   * @return whether the current request needs to ignore sensitive
   */
  protected boolean isIgnoreSensitive() {
    HandlerMethod handlerMethod = getHandlerMethod();
    if (Objects.nonNull(handlerMethod)) {
      Class<?> beanType = handlerMethod.getBeanType();
      // First get the @IgnoreSensitive annotation from the class of the method
      boolean ignoreSensitive = AnnotationUtil.hasAnnotation(beanType, IgnoreSensitive.class);
      if (!ignoreSensitive) {
        // If not get @IgnoreSensitive from this class, will get it from the current method
        ignoreSensitive = handlerMethod.hasMethodAnnotation(IgnoreSensitive.class);
      }
      // If neither the current class nor the method has the @IgnoreSensitive
      // it means that the sensitive is not ignored.
      return ignoreSensitive;
    }
    return true;
  }

  /**
   * Get {@link HttpServletRequest}
   *
   * @return {@link HttpServletRequest}
   */
  protected HttpServletRequest getRequest() {
    return getRequestAttributes().getRequest();
  }

  /**
   * Find the corresponding {@link HandlerMethod} through {@link HttpServletRequest}
   *
   * @return when not found or an error is reported, instead of throw exception, it returns null
   */
  protected HandlerMethod getHandlerMethod() {
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
  protected HandlerMethod getHandlerMethod(
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
  protected ServletRequestAttributes getRequestAttributes() {
    return Optional.ofNullable(RequestContextHolder.getRequestAttributes())
        .filter(ServletRequestAttributes.class::isInstance)
        .map(ServletRequestAttributes.class::cast)
        .orElseThrow(() -> new IllegalArgumentException("Unable to get RequestAttributes"));
  }
}
