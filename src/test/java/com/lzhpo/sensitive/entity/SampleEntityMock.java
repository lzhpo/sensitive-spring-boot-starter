package com.lzhpo.sensitive.entity;

import lombok.experimental.UtilityClass;

/**
 * @author lzhpo
 */
@UtilityClass
public class SampleEntityMock {

  public static SampleEntity test1() {
    return SampleEntity.builder()
        .name("刘子豪")
        .idCard("530321199204074611")
        .fixedPhone("01086551122")
        .mobilePhone("13248765917")
        .address("广州市天河区幸福小区102号")
        .email("example@gmail.com")
        .password("123456")
        .carLicense("粤A66666")
        .bankCard("9988002866797031")
        .keepLength1("1234")
        .handler1("12345")
        .filterWords1("卧槽，他妈的，我去你大爷的，草泥马")
        .build();
  }
}
