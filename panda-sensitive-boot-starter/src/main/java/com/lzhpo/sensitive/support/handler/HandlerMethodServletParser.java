/*
 * Copyright 2022 lzhpo
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.lzhpo.sensitive.support.handler;

import com.lzhpo.sensitive.utils.ServletContextUtil;
import javax.servlet.http.HttpServletRequest;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerExecutionChain;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

/**
 * @author lzhpo
 */
@Slf4j
public class HandlerMethodServletParser implements HandlerMethodParser {

  @Autowired private RequestMappingHandlerMapping handlerMapping;

  @Override
  @SneakyThrows
  public HandlerMethod get() {
    HttpServletRequest servletRequest = ServletContextUtil.getRequest();
    HandlerExecutionChain executionChain = handlerMapping.getHandler(servletRequest);
    Assert.notNull(executionChain, "Cannot parse to HandlerExecutionChain.");
    return (HandlerMethod) executionChain.getHandler();
  }
}
