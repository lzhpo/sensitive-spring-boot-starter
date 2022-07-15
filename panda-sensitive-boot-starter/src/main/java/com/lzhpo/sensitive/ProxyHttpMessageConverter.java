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

package com.lzhpo.sensitive;

import com.lzhpo.sensitive.resolve.HandlerMethodResolver;
import java.io.IOException;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;

/**
 * @author lzhpo
 */
@Slf4j
public class ProxyHttpMessageConverter extends AbstractSensitiveConfiguration
    implements HttpMessageConverter<Object> {

  private final HttpMessageConverter<Object> httpMessageConverter;

  public ProxyHttpMessageConverter(
      HttpMessageConverter<Object> httpMessageConverter,
      HandlerMethodResolver handlerMethodResolver) {
    this.httpMessageConverter = httpMessageConverter;
    setHandlerMethodResolver(handlerMethodResolver);
  }

  @Override
  public boolean canRead(Class clazz, MediaType mediaType) {
    return httpMessageConverter.canRead(clazz, mediaType);
  }

  @Override
  public boolean canWrite(Class clazz, MediaType mediaType) {
    return httpMessageConverter.canWrite(clazz, mediaType);
  }

  @Override
  public List<MediaType> getSupportedMediaTypes() {
    return httpMessageConverter.getSupportedMediaTypes();
  }

  @Override
  public List<MediaType> getSupportedMediaTypes(Class clazz) {
    return httpMessageConverter.getSupportedMediaTypes(clazz);
  }

  @Override
  public Object read(Class clazz, HttpInputMessage inputMessage)
      throws IOException, HttpMessageNotReadableException {
    return httpMessageConverter.read(clazz, inputMessage);
  }

  @Override
  public void write(Object o, MediaType contentType, HttpOutputMessage outputMessage)
      throws IOException, HttpMessageNotWritableException {
    invokeSensitiveObject(o);
    httpMessageConverter.write(o, contentType, outputMessage);
  }
}
