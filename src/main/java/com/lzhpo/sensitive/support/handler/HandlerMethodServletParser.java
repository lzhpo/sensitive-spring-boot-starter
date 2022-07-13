package com.lzhpo.sensitive.support.handler;

import com.lzhpo.sensitive.utils.ServletContextUtil;
import javax.servlet.http.HttpServletRequest;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerExecutionChain;
import org.springframework.web.servlet.HandlerMapping;

/**
 * @author lzhpo
 */
@Slf4j
public class HandlerMethodServletParser implements HandlerMethodParser {

  @Autowired
  private HandlerMapping handlerMapping;

  @Override
  @SneakyThrows
  public HandlerMethod get() {
    HttpServletRequest servletRequest = ServletContextUtil.getRequest();
    HandlerExecutionChain executionChain = handlerMapping.getHandler(servletRequest);
    Assert.notNull(executionChain, "Cannot parse handler from the request.");
    return (HandlerMethod) executionChain.getHandler();
  }
}
