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
