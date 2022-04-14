package com.lzhpo.samples;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author lzhpo
 */
@SpringBootApplication
// @EnableSensitive(converterType = SensitiveConverterType.JACKSON)
public class SensitiveApplication {

  public static void main(String[] args) {
    SpringApplication.run(SensitiveApplication.class, args);
  }
}
