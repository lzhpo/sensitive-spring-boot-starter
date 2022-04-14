package com.lzhpo.samples;

import com.lzhpo.sensitive.annocation.EnableSensitive;
import com.lzhpo.sensitive.annocation.IgnoreSensitive;
import com.lzhpo.sensitive.annocation.Sensitive;
import com.lzhpo.sensitive.enums.SensitiveConverterType;
import com.lzhpo.sensitive.enums.SensitiveStrategy;
import lombok.Builder;
import lombok.Data;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lzhpo
 */
@RestController
// @IgnoreSensitive
@RequestMapping("/")
@SpringBootApplication
@EnableSensitive(converterType = SensitiveConverterType.JACKSON)
public class SensitiveApplication {

  public static void main(String[] args) {
    SpringApplication.run(SensitiveApplication.class, args);
  }

  /**
   * 忽略字段脱敏
   *
   * @return mock {@link JavaBean}
   */
  @IgnoreSensitive
  @GetMapping("ignoreSensitive")
  public JavaBean ignoreSensitive() {
    return mockJavaBean();
  }

  /**
   * 字段脱敏
   *
   * @return mock {@link JavaBean}
   */
  @GetMapping("sensitive")
  public JavaBean sensitive() {
    return mockJavaBean();
  }

  /**
   * 模拟数据，非真实数据，仅可用作测试
   *
   * @return {@link JavaBean}
   */
  private JavaBean mockJavaBean() {
    return JavaBean.builder()
        .name("刘子豪")
        .idCard("530321199204074611")
        .fixedPhone("01086551122")
        .mobilePhone("13248765917")
        .address("广东省广州市天河区")
        .email("example@gmail.com")
        .password("123456")
        .carLicense("粤A66666")
        .bankCard("9988002866797031")
        .customize("ABC666")
        .build();
  }

  /** 用于测试的结构 */
  @Data
  @Builder
  public static class JavaBean {

    @Sensitive(strategy = SensitiveStrategy.CHINESE_NAME)
    private String name;

    @Sensitive(strategy = SensitiveStrategy.ID_CARD)
    private String idCard;

    @Sensitive(strategy = SensitiveStrategy.FIXED_PHONE)
    private String fixedPhone;

    @Sensitive(strategy = SensitiveStrategy.MOBILE_PHONE)
    private String mobilePhone;

    @Sensitive(strategy = SensitiveStrategy.ADDRESS)
    private String address;

    @Sensitive(strategy = SensitiveStrategy.EMAIL)
    private String email;

    @Sensitive(strategy = SensitiveStrategy.PASSWORD)
    private String password;

    @Sensitive(strategy = SensitiveStrategy.CAR_LICENSE)
    private String carLicense;

    @Sensitive(strategy = SensitiveStrategy.BANK_CARD)
    private String bankCard;

    @Sensitive(strategy = SensitiveStrategy.CUSTOMIZE, preKeep = 1, suffixKeep = 1)
    private String customize;
  }
}
