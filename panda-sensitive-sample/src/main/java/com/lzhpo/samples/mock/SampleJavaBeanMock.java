/*
 * Copyright 2022 lzhpo
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.lzhpo.samples.mock;

import com.lzhpo.samples.entity.SampleJavaBean;
import lombok.experimental.UtilityClass;

/**
 * @author lzhpo
 */
@UtilityClass
public class SampleJavaBeanMock {

  public static SampleJavaBean sampleJavaBean() {
    return SampleJavaBean.builder()
        .name("刘子豪")
        .idCard("530321199204074611")
        .fixedPhone("01086551122")
        .mobilePhone("13248765917")
        .address("广州市天河区幸福小区102号")
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
