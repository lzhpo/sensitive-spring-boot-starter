package com.lzhpo.sensitive.config.support;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lzhpo.sensitive.config.AbstractSensitiveConfiguration;
import java.io.IOException;
import java.lang.reflect.Type;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

/**
 * Use jackson as sensitive converter
 *
 * @author lzhpo
 * @see org.springframework.boot.autoconfigure.http.JacksonHttpMessageConvertersConfiguration
 * @see org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration
 */
@Configuration
@ConditionalOnClass({ObjectMapper.class})
public class JacksonSensitiveConverterConfiguration extends AbstractSensitiveConfiguration {

  @Bean
  @ConditionalOnMissingBean
  public ObjectMapper jacksonObjectMapper() {
    return new ObjectMapper();
  }

  @Bean
  @ConditionalOnMissingBean
  public MappingJackson2HttpMessageConverter jackson2HttpMessageConverter(
      ObjectMapper objectMapper) {
    return new MappingJackson2HttpMessageConverter(objectMapper) {
      @Override
      protected void writeInternal(Object object, Type type, HttpOutputMessage outputMessage)
          throws IOException, HttpMessageNotWritableException {
        invokeSensitive(object);
        super.writeInternal(object, type, outputMessage);
      }
    };
  }
}
