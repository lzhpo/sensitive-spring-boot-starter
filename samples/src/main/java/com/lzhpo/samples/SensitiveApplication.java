package com.lzhpo.samples;

import com.lzhpo.sensitive.annocation.EnableSensitive;
import com.lzhpo.sensitive.enums.SensitiveConverterType;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author lzhpo
 */
@SpringBootApplication
@EnableSensitive(converterType = SensitiveConverterType.FASTJSON)
public class SensitiveApplication {

  public static void main(String[] args) {
    SpringApplication.run(SensitiveApplication.class, args);
  }
}
