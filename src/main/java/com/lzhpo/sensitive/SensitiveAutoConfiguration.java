package com.lzhpo.sensitive;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author lzhpo
 */
@Configuration
@EnableConfigurationProperties({SensitiveProperties.class})
public class SensitiveAutoConfiguration {
}
