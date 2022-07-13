package com.lzhpo.samples;

import com.lzhpo.sensitive.annocation.EnableSensitive;
import com.lzhpo.sensitive.enums.JsonConverter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author lzhpo
 */
@SpringBootApplication
@EnableSensitive(converter = JsonConverter.FASTJSON)
public class SensitiveApplication {

  public static void main(String[] args) {
    SpringApplication.run(SensitiveApplication.class, args);
  }
}
