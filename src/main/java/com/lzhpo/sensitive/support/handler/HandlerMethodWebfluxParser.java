package com.lzhpo.sensitive.support.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.reactive.result.method.annotation.RequestMappingHandlerMapping;
import reactor.core.publisher.Mono;

/**
 * @author lzhpo
 */
@Slf4j
public class HandlerMethodWebfluxParser implements HandlerMethodParser {

  @Autowired
  private RequestMappingHandlerMapping handlerMapping;

  @Override
  public HandlerMethod get() {
    // TODO
    Mono<Object> handler = handlerMapping.getHandler(null);
    return (HandlerMethod) handler.block();
  }
}
