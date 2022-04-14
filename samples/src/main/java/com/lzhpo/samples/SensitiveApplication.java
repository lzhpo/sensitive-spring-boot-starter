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
        .customize1("1234567891")
        .customize2("1234567892")
        .customize3("1234567892")
        .customize4("12345")
        .customize5("1234567")
        .customizePreKeep1("ABC66651")
        .customizePostKeep1("ABC66691")
        .customizeIgnoreKeep1("ABC6661")
        .customizeIgnoreKeep2("ABC6662")
        .customizeIgnoreKeep3("ABC6663")
        .customizeIgnoreKeep4("ABC6664")
        .customizeOutOfRange1("1234567890")
        .customizeOutOfRange2("1234567890")
        .customizeOutOfRange3("123456789")
        .customizeOutOfRange4("123456789")
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

    @Sensitive(strategy = SensitiveStrategy.CUSTOMIZE, preKeep = 1, postKeep = 1)
    private String customize1;

    @Sensitive(strategy = SensitiveStrategy.CUSTOMIZE, preKeep = 1, postKeep = 2)
    private String customize2;

    @Sensitive(strategy = SensitiveStrategy.CUSTOMIZE, preKeep = 1, postKeep = 3)
    private String customize3;

    @Sensitive(strategy = SensitiveStrategy.CUSTOMIZE, preKeep = 6, postKeep = 0)
    private String customize4;

    @Sensitive(strategy = SensitiveStrategy.CUSTOMIZE, preKeep = 0, postKeep = 6)
    private String customize5;

    @Sensitive(strategy = SensitiveStrategy.CUSTOMIZE)
    private String customizeIgnoreKeep1;

    @Sensitive(strategy = SensitiveStrategy.CUSTOMIZE, preKeep = 0)
    private String customizeIgnoreKeep2;

    @Sensitive(strategy = SensitiveStrategy.CUSTOMIZE, postKeep = 0)
    private String customizeIgnoreKeep3;

    @Sensitive(strategy = SensitiveStrategy.CUSTOMIZE, preKeep = 0, postKeep = 0)
    private String customizeIgnoreKeep4;

    @Sensitive(strategy = SensitiveStrategy.CUSTOMIZE, preKeep = 1)
    private String customizePreKeep1;

    @Sensitive(strategy = SensitiveStrategy.CUSTOMIZE, postKeep = 1)
    private String customizePostKeep1;

    @Sensitive(strategy = SensitiveStrategy.CUSTOMIZE, preKeep = 10, postKeep = 0)
    private String customizeOutOfRange1;

    @Sensitive(strategy = SensitiveStrategy.CUSTOMIZE, preKeep = 0, postKeep = 10)
    private String customizeOutOfRange2;

    @Sensitive(strategy = SensitiveStrategy.CUSTOMIZE, preKeep = 9, postKeep = 0)
    private String customizeOutOfRange3;

    @Sensitive(strategy = SensitiveStrategy.CUSTOMIZE, preKeep = 10, postKeep = 9)
    private String customizeOutOfRange4;
  }
}
