package com.lzhpo.samples;

import com.lzhpo.sensitive.annocation.EnableSensitive;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author lzhpo
 */
@SpringBootApplication
@EnableSensitive
public class SensitiveApplication {

  public static void main(String[] args) {
    SpringApplication.run(SensitiveApplication.class, args);
  }
}
