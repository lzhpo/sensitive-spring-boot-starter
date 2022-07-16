package com.lzhpo.sensitive.entity;

import com.lzhpo.sensitive.FaceCustomizeSensitiveHandler;
import com.lzhpo.sensitive.SensitiveStrategy;
import com.lzhpo.sensitive.annocation.Sensitive;
import com.lzhpo.sensitive.annocation.SensitiveFilterWords;
import com.lzhpo.sensitive.annocation.SensitiveHandler;
import com.lzhpo.sensitive.annocation.SensitiveKeepLength;
import lombok.Builder;
import lombok.Data;

/**
 * @author lzhpo
 */
@Data
@Builder
public class SampleEntity {

  @Sensitive(strategy = SensitiveStrategy.CHINESE_NAME, replacer = '#')
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

  @Sensitive(strategy = SensitiveStrategy.CUSTOMIZE_KEEP_LENGTH)
  @SensitiveKeepLength(preKeep = 1, postKeep = 1)
  private String keepLength1;

  @Sensitive(strategy = SensitiveStrategy.CUSTOMIZE_FILTER_WORDS)
  @SensitiveFilterWords({"他妈的", "去你大爷", "卧槽", "草泥马", "废物"})
  private String filterWords1;

  @Sensitive(strategy = SensitiveStrategy.CUSTOMIZE_HANDLER)
  @SensitiveHandler(FaceCustomizeSensitiveHandler.class)
  private String handler1;
}
