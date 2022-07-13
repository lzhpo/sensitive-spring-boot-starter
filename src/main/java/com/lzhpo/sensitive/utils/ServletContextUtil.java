package com.lzhpo.sensitive.utils;

import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import lombok.experimental.UtilityClass;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * @author lzhpo
 */
@UtilityClass
public class ServletContextUtil {

  /**
   * Get {@link HttpServletRequest}
   *
   * @return {@link HttpServletRequest}
   */
  public static HttpServletRequest getRequest() {
    return getRequestAttributes().getRequest();
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
