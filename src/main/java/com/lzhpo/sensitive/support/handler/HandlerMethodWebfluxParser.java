package com.lzhpo.sensitive.support.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.reactive.HandlerMapping;
import reactor.core.publisher.Mono;

/**
 * @author lzhpo
 */
@Slf4j
public class HandlerMethodWebfluxParser implements HandlerMethodParser {

  @Autowired
  private HandlerMapping handlerMapping;

  @Override
  public HandlerMethod get() {
    // TODO
    Mono<Object> handler = handlerMapping.getHandler(null);
    return (HandlerMethod) handler.block();
  }
}
