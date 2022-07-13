package com.lzhpo.samples.controller;

import com.lzhpo.samples.entity.FastJsonSerializerJavaBean;
import com.lzhpo.samples.entity.GsonSerializerJavaBean;
import com.lzhpo.samples.entity.JacksonSerializerJavaBean;
import com.lzhpo.samples.entity.JsonbSerializerJavaBean;
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
        .password("egfiuajlsdk")
        .idCard("530321199204074611")
        .build();
  }

  @GetMapping("fastJsonSerializer")
  public FastJsonSerializerJavaBean fastJsonSerializer() {
    return FastJsonSerializerJavaBean.builder()
        .name("王小明")
        .password("gifuebakds")
        .idCard("530321199204074622")
        .build();
  }

  @GetMapping("jsonbSerializer")
  public JsonbSerializerJavaBean jsonbSerializer() {
    return JsonbSerializerJavaBean.builder()
        .name("刘小明")
        .password("gewfuadsfsf")
        .idCard("530321199204074633")
        .build();
  }

  @GetMapping("gsonSerializer")
  public GsonSerializerJavaBean gsonSerializer() {
    return GsonSerializerJavaBean.builder()
        .name("张三丰")
        .password("graharehtht")
        .idCard("530321199204074644")
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
        .preKeep1("ABC66651")
        .postKeep1("ABC66691")
        .customize("ABC6661")
        .preKeep0("ABC6662")
        .postKeep0("ABC6663")
        .preKeep0PostKeep0("123")
        .preKeep1PostKeep1("1234")
        .preKeep1PostKeep2("12345")
        .preKeep1PostKeep3("123456")
        .preKeep6PostKeep0("1234567")
        .preKeep0PostKeep6("1234567")
        .preKeep7PostKeep0("12345678")
        .preKeep0PostKeep7("12345678")
        .preKeep8PostKeep0("123456789")
        .preKeep0PostKeep8("123456789")
        .build();
  }
}
