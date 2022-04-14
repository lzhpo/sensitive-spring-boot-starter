package com.lzhpo.sensitive.config.support;

import com.google.gson.Gson;
import com.lzhpo.sensitive.config.AbstractSensitiveConfiguration;
import java.io.Writer;
import java.lang.reflect.Type;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.GsonHttpMessageConverter;

/**
 * Use gson as sensitive converter
 *
 * @author lzhpo
 * @see org.springframework.boot.autoconfigure.http.GsonHttpMessageConvertersConfiguration
 * @see org.springframework.boot.autoconfigure.gson.GsonAutoConfiguration
 */
@Configuration
@ConditionalOnClass({Gson.class})
public class GsonSensitiveConverterConfiguration extends AbstractSensitiveConfiguration {

  @Bean
  @ConditionalOnMissingBean
  public Gson gson() {
    return new Gson();
  }

  @Bean
  @ConditionalOnMissingBean
  public GsonHttpMessageConverter gsonHttpMessageConverter(Gson gson) {
    GsonHttpMessageConverter converter = new GsonHttpMessageConverter() {
      @Override
      protected void writeInternal(Object object, Type type, Writer writer) throws Exception {
        invokeSensitive(object);
        super.writeInternal(object, type, writer);
      }
    };
    converter.setGson(gson);
    return converter;
  }
}
