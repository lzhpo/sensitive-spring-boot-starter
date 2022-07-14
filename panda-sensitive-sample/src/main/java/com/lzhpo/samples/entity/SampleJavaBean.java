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

package com.lzhpo.samples.entity;

import com.lzhpo.sensitive.annocation.Sensitive;
import com.lzhpo.sensitive.enums.SensitiveStrategy;
import lombok.Builder;
import lombok.Data;

/**
 * @author lzhpo
 */
@Data
@Builder
public class SampleJavaBean {

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

  @Sensitive(strategy = SensitiveStrategy.CUSTOMIZE)
  private String customize;

  @Sensitive(strategy = SensitiveStrategy.CUSTOMIZE, preKeep = 0)
  private String preKeep0;

  @Sensitive(strategy = SensitiveStrategy.CUSTOMIZE, preKeep = 1)
  private String preKeep1;

  @Sensitive(strategy = SensitiveStrategy.CUSTOMIZE, postKeep = 0)
  private String postKeep0;

  @Sensitive(strategy = SensitiveStrategy.CUSTOMIZE, postKeep = 1)
  private String postKeep1;

  @Sensitive(strategy = SensitiveStrategy.CUSTOMIZE, preKeep = 0, postKeep = 0)
  private String preKeep0PostKeep0;

  @Sensitive(strategy = SensitiveStrategy.CUSTOMIZE, preKeep = 1, postKeep = 1, replacer = '#')
  private String preKeep1PostKeep1;

  @Sensitive(strategy = SensitiveStrategy.CUSTOMIZE, preKeep = 1, postKeep = 2)
  private String preKeep1PostKeep2;

  @Sensitive(strategy = SensitiveStrategy.CUSTOMIZE, preKeep = 1, postKeep = 3)
  private String preKeep1PostKeep3;

  @Sensitive(strategy = SensitiveStrategy.CUSTOMIZE, preKeep = 6, postKeep = 0)
  private String preKeep6PostKeep0;

  @Sensitive(strategy = SensitiveStrategy.CUSTOMIZE, preKeep = 0, postKeep = 6)
  private String preKeep0PostKeep6;

  @Sensitive(strategy = SensitiveStrategy.CUSTOMIZE, preKeep = 7, postKeep = 0)
  private String preKeep7PostKeep0;

  @Sensitive(strategy = SensitiveStrategy.CUSTOMIZE, preKeep = 0, postKeep = 7)
  private String preKeep0PostKeep7;

  @Sensitive(strategy = SensitiveStrategy.CUSTOMIZE, preKeep = 8, postKeep = 0)
  private String preKeep8PostKeep0;

  @Sensitive(strategy = SensitiveStrategy.CUSTOMIZE, preKeep = 0, postKeep = 8)
  private String preKeep0PostKeep8;
}
