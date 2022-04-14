package com.lzhpo.samples.controller;

import com.lzhpo.samples.entity.FastJsonSerializerJavaBean;
import com.lzhpo.samples.entity.JacksonSerializerJavaBean;
import com.lzhpo.samples.entity.SampleJavaBean;
import com.lzhpo.sensitive.annocation.IgnoreSensitive;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lzhpo
 */
@RestController
// @IgnoreSensitive
@RequestMapping("/")
public class TestController {

  @GetMapping("jacksonSerializer")
  public JacksonSerializerJavaBean jacksonSerializer() {
    return JacksonSerializerJavaBean.builder()
        .name("张小明")
        .password("123456")
        .idCard("530321199204074611")
        .build();
  }

  @GetMapping("fastJsonSerializer")
  public FastJsonSerializerJavaBean fastJsonSerializer() {
    return FastJsonSerializerJavaBean.builder()
        .name("王小明")
        .password("1234567")
        .idCard("530321199204074622")
        .build();
  }

  /**
   * 忽略字段脱敏
   *
   * @return mock {@link SampleJavaBean}
   */
  @IgnoreSensitive
  @GetMapping("ignoreSensitive")
  public SampleJavaBean ignoreSensitive() {
    return mockJavaBean();
  }

  /**
   * 字段脱敏
   *
   * @return mock {@link SampleJavaBean}
   */
  @GetMapping("sensitive")
  public SampleJavaBean sensitive() {
    return mockJavaBean();
  }

  /**
   * 模拟数据，非真实数据，仅可用作测试
   *
   * @return {@link SampleJavaBean}
   */
  private SampleJavaBean mockJavaBean() {
    return SampleJavaBean.builder()
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
}
