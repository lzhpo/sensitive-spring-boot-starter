package com.lzhpo.sensitive;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.lzhpo.sensitive.utils.SensitiveInvokeUtil;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.converter.HttpMessageNotWritableException;

/**
 * Use fastJson as sensitive converter
 *
 * @author lzhpo
 */
@ConditionalOnClass({FastJsonConfig.class, FastJsonHttpMessageConverter.class})
public class FastJsonSensitiveConfiguration {

  @Bean
  @ConditionalOnMissingBean
  public HttpMessageConverters fastJsonHttpMessageConverters() {
    FastJsonHttpMessageConverter fastJsonHttpMessageConverter =
        new FastJsonHttpMessageConverter() {
          @Override
          protected void writeInternal(Object object, HttpOutputMessage outputMessage)
              throws IOException, HttpMessageNotWritableException {
            SensitiveInvokeUtil.invokeSensitiveObject(object);
            super.writeInternal(object, outputMessage);
          }
        };
    // Avoid Chinese garbled characters
    fastJsonHttpMessageConverter.setDefaultCharset(StandardCharsets.UTF_8);
    FastJsonConfig fastJsonConfig = new FastJsonConfig();
    fastJsonConfig.setSerializerFeatures(SerializerFeature.PrettyFormat);
    fastJsonHttpMessageConverter.setFastJsonConfig(fastJsonConfig);
    return new HttpMessageConverters(fastJsonHttpMessageConverter);
  }
}