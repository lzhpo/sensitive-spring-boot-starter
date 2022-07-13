package com.lzhpo.sensitive.enums;

import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.DesensitizedUtil;
import com.lzhpo.sensitive.annocation.Sensitive;
import java.util.Optional;
import java.util.function.Supplier;
import org.springframework.util.Assert;

/**
 * Sensitive strategy
 *
 * @author lzhpo
 */
public enum SensitiveStrategy {

  /** chinese name */
  CHINESE_NAME() {
    @Override
    public String apply(String value, Sensitive sensitive) {
      return SensitiveStrategy.invoke(value, sensitive, () -> DesensitizedUtil.chineseName(value));
    }
  },

  /** id card */
  ID_CARD() {
    @Override
    public String apply(String value, Sensitive sensitive) {
      return SensitiveStrategy.invoke(
          value, sensitive, () -> DesensitizedUtil.idCardNum(value, 1, 2));
    }
  },

  /** fixed phone */
  FIXED_PHONE() {
    @Override
    public String apply(String value, Sensitive sensitive) {
      return SensitiveStrategy.invoke(value, sensitive, () -> DesensitizedUtil.fixedPhone(value));
    }
  },

  /** mobile phone */
  MOBILE_PHONE() {
    @Override
    public String apply(String value, Sensitive sensitive) {
      return SensitiveStrategy.invoke(value, sensitive, () -> DesensitizedUtil.mobilePhone(value));
    }
  },

  /** address */
  ADDRESS() {
    @Override
    public String apply(String value, Sensitive sensitive) {
      return SensitiveStrategy.invoke(value, sensitive, () -> DesensitizedUtil.address(value, 8));
    }
  },

  /** email */
  EMAIL() {
    @Override
    public String apply(String value, Sensitive sensitive) {
      return SensitiveStrategy.invoke(value, sensitive, () -> DesensitizedUtil.email(value));
    }
  },

  /** password */
  PASSWORD() {
    @Override
    public String apply(String value, Sensitive sensitive) {
      return SensitiveStrategy.invoke(value, sensitive, () -> DesensitizedUtil.password(value));
    }
  },

  /** Chinese mainland license plates, including ordinary vehicles, new energy vehicles */
  CAR_LICENSE() {
    @Override
    public String apply(String value, Sensitive sensitive) {
      return SensitiveStrategy.invoke(value, sensitive, () -> DesensitizedUtil.carLicense(value));
    }
  },

  /** bank card */
  BANK_CARD() {
    @Override
    public String apply(String value, Sensitive sensitive) {
      return SensitiveStrategy.invoke(value, sensitive, () -> DesensitizedUtil.bankCard(value));
    }
  },

  /** customize */
  CUSTOMIZE() {
    @Override
    public String apply(String value, Sensitive sensitive) {
      return SensitiveStrategy.invoke(value, sensitive, null);
    }
  };

  /**
   * Determine the strategy and execute the field sensitive method
   *
   * @param value to sensitive value
   * @param sensitive {@link Sensitive}
   * @param orElse will be executed if pre-reserved and post-reserved digits are not specified
   * @return after sensitive value
   */
  private static String invoke(String value, Sensitive sensitive, Supplier<String> orElse) {
    int preKeep = sensitive.preKeep();
    int postKeep = sensitive.postKeep();
    Assert.isTrue(preKeep >= -1, "preKeep must greater than -1");
    Assert.isTrue(postKeep >= -1, "postKeep must greater than -1");

    boolean ignorePreKeep = preKeep <= 0;
    boolean ignoreSuffixKeep = postKeep <= 0;
    if (ignorePreKeep && ignoreSuffixKeep) {
      return Optional.ofNullable(orElse).map(Supplier::get).orElse(value);
    }

    if (CharSequenceUtil.isBlank(value)) {
      return CharSequenceUtil.EMPTY;
    }

    char replacer = sensitive.replacer();
    return CharSequenceUtil.replace(value, preKeep, value.length() - postKeep, replacer);
  }

  /**
   * Field sensitive strategy method
   *
   * @param value to sensitive value
   * @param sensitive {@link Sensitive}
   * @return after sensitive value
   */
  public abstract String apply(String value, Sensitive sensitive);
}
