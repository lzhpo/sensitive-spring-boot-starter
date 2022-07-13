package com.lzhpo.sensitive;

import com.lzhpo.sensitive.utils.SensitiveInvokeUtil;
import java.io.Writer;
import java.lang.reflect.Type;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnResource;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.json.JsonbHttpMessageConverter;

/**
 * Use jsonb as sensitive converter
 *
 * @author lzhpo
 * @see org.springframework.boot.autoconfigure.http.JsonbHttpMessageConvertersConfiguration
 * @see org.springframework.boot.autoconfigure.jsonb.JsonbAutoConfiguration
 */
@ConditionalOnClass(Jsonb.class)
@ConditionalOnResource(
    resources = {
      "classpath:META-INF/services/javax.json.bind.spi.JsonbProvider",
      "classpath:META-INF/services/javax.json.spi.JsonProvider"
    })
public class JsonbSensitiveConfiguration {

  @Bean
  @ConditionalOnMissingBean
  public Jsonb jsonb() {
    return JsonbBuilder.create();
  }

  @Bean
  @ConditionalOnMissingBean
  public JsonbHttpMessageConverter jsonbHttpMessageConverter(
      Jsonb jsonb) {
    JsonbHttpMessageConverter jsonbHttpMessageConverter =
        new JsonbHttpMessageConverter() {
          @Override
          protected void writeInternal(Object object, Type type, Writer writer) throws Exception {
            SensitiveInvokeUtil.invokeSensitiveObject(object);
            super.writeInternal(object, type, writer);
          }
        };
    jsonbHttpMessageConverter.setJsonb(jsonb);
    return jsonbHttpMessageConverter;
  }
}
