package com.lzhpo.sensitive.support.handler;

import org.springframework.web.method.HandlerMethod;

/**
 * @author lzhpo
 */
public interface HandlerMethodParser {

  /**
   * Get HandlerMethod
   *
   * @return {@link HandlerMethod}
   */
  HandlerMethod get();
}
