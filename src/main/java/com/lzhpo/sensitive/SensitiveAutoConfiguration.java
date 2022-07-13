package com.lzhpo.sensitive;

import com.lzhpo.sensitive.support.handler.HandlerMethodServletParser;
import com.lzhpo.sensitive.support.handler.HandlerMethodWebfluxParser;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication.Type;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @author lzhpo
 */
@Configuration
@Import({SensitiveConfigurationSelector.class})
public class SensitiveAutoConfiguration {

  @Bean
  @ConditionalOnWebApplication(type = Type.SERVLET)
  public HandlerMethodServletParser handlerMethodServletParser() {
    return new HandlerMethodServletParser();
  }

  @Bean
  @ConditionalOnWebApplication(type = Type.REACTIVE)
  public HandlerMethodWebfluxParser handlerMethodWebfluxParser() {
    return new HandlerMethodWebfluxParser();
  }
}
