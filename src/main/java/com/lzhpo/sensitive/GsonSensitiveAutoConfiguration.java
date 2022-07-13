package com.lzhpo.sensitive;

import com.google.gson.Gson;
import com.lzhpo.sensitive.utils.SensitiveInvokeUtil;
import java.io.Writer;
import java.lang.reflect.Type;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
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
@ConditionalOnExpression("${sensitive.enabled}")
@ConditionalOnProperty(prefix = "sensitive", value = "converter", havingValue = "gson")
public class GsonSensitiveAutoConfiguration {

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
        SensitiveInvokeUtil.invokeSensitiveObject(object);
        super.writeInternal(object, type, writer);
      }
    };
    converter.setGson(gson);
    return converter;
  }
}
