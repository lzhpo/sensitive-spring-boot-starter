package com.lzhpo.sensitive;

import com.lzhpo.sensitive.enums.JsonConverter;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author lzhpo
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "sensitive")
public class SensitiveProperties {

  private boolean enabled = true;

  private boolean proxy = true;

  private JsonConverter converter = JsonConverter.JACKSON;
}
