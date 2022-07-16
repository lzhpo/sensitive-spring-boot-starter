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

package com.lzhpo.sensitive;

import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.ReflectUtil;
import com.lzhpo.sensitive.annocation.Sensitive;
import com.lzhpo.sensitive.annocation.SensitiveFilterWords;
import com.lzhpo.sensitive.annocation.SensitiveHandler;
import com.lzhpo.sensitive.annocation.SensitiveKeepLength;
import com.lzhpo.sensitive.utils.SensitiveUtil;
import java.lang.reflect.Field;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;

/**
 * Sensitive strategy
 *
 * @author lzhpo
 */
@Slf4j
public enum SensitiveStrategy {

  /** Chinese name */
  CHINESE_NAME() {
    @Override
    public String apply(SensitiveWrapper sensitiveWrapper) {
      Sensitive sensitive = sensitiveWrapper.getSensitive();
      return SensitiveUtil.chineseName(sensitiveWrapper.getFieldValue(), sensitive.replacer());
    }
  },

  /** ID card */
  ID_CARD() {
    @Override
    public String apply(SensitiveWrapper sensitiveWrapper) {
      Sensitive sensitive = sensitiveWrapper.getSensitive();
      return SensitiveUtil.idCardNum(sensitiveWrapper.getFieldValue(), 1, 2, sensitive.replacer());
    }
  },

  /** Fixed phone */
  FIXED_PHONE() {
    @Override
    public String apply(SensitiveWrapper sensitiveWrapper) {
      Sensitive sensitive = sensitiveWrapper.getSensitive();
      return SensitiveUtil.fixedPhone(sensitiveWrapper.getFieldValue(), sensitive.replacer());
    }
  },

  /** Mobile phone */
  MOBILE_PHONE() {
    @Override
    public String apply(SensitiveWrapper sensitiveWrapper) {
      Sensitive sensitive = sensitiveWrapper.getSensitive();
      return SensitiveUtil.mobilePhone(sensitiveWrapper.getFieldValue(), sensitive.replacer());
    }
  },

  /** Address */
  ADDRESS() {
    @Override
    public String apply(SensitiveWrapper sensitiveWrapper) {
      Sensitive sensitive = sensitiveWrapper.getSensitive();
      return SensitiveUtil.address(sensitiveWrapper.getFieldValue(), 8, sensitive.replacer());
    }
  },

  /** Email */
  EMAIL() {
    @Override
    public String apply(SensitiveWrapper sensitiveWrapper) {
      Sensitive sensitive = sensitiveWrapper.getSensitive();
      return SensitiveUtil.email(sensitiveWrapper.getFieldValue(), sensitive.replacer());
    }
  },

  /** Password */
  PASSWORD() {
    @Override
    public String apply(SensitiveWrapper sensitiveWrapper) {
      Sensitive sensitive = sensitiveWrapper.getSensitive();
      return SensitiveUtil.password(sensitiveWrapper.getFieldValue(), sensitive.replacer());
    }
  },

  /** Chinese mainland license plates, including ordinary vehicles, new energy vehicles */
  CAR_LICENSE() {
    @Override
    public String apply(SensitiveWrapper sensitiveWrapper) {
      Sensitive sensitive = sensitiveWrapper.getSensitive();
      return SensitiveUtil.carLicense(sensitiveWrapper.getFieldValue(), sensitive.replacer());
    }
  },

  /** Bank card */
  BANK_CARD() {
    @Override
    public String apply(SensitiveWrapper sensitiveWrapper) {
      Sensitive sensitive = sensitiveWrapper.getSensitive();
      return SensitiveUtil.bankCard(sensitiveWrapper.getFieldValue(), sensitive.replacer());
    }
  },

  /** Customize sensitive keep length */
  CUSTOMIZE_FILTER_WORDS() {
    @Override
    public String apply(SensitiveWrapper sensitiveWrapper) {
      String fieldValue = sensitiveWrapper.getFieldValue();
      Field field = sensitiveWrapper.getField();
      Sensitive sensitive = sensitiveWrapper.getSensitive();
      SensitiveFilterWords filterWords = field.getAnnotation(SensitiveFilterWords.class);
      if (ObjectUtils.isEmpty(filterWords)) {
        log.warn(
            "{} is marked CUSTOMIZE_FILTER_WORDS strategy, "
                + "but not has @SensitiveFilterWords, will ignore sensitive it.",
            field.getName());
        return fieldValue;
      }

      char replacer = sensitive.replacer();
      String[] words = filterWords.value();
      if (!ObjectUtils.isEmpty(words)) {
        for (String filterWord : words) {
          if (fieldValue.contains(filterWord)) {
            String replacers = CharSequenceUtil.repeat(replacer, filterWord.length());
            fieldValue = fieldValue.replace(filterWord, replacers);
          }
        }
      }

      return fieldValue;
    }
  },

  /** Customize sensitive keep length */
  CUSTOMIZE_KEEP_LENGTH() {
    @Override
    public String apply(SensitiveWrapper sensitiveWrapper) {
      Field field = sensitiveWrapper.getField();
      String fieldValue = sensitiveWrapper.getFieldValue();
      Sensitive sensitive = sensitiveWrapper.getSensitive();
      SensitiveKeepLength sensitiveKeepLength = field.getAnnotation(SensitiveKeepLength.class);
      int preKeep = sensitiveKeepLength.preKeep();
      int postKeep = sensitiveKeepLength.postKeep();
      Assert.isTrue(preKeep >= SensitiveConstants.NOP_KEEP, "preKeep must greater than -1");
      Assert.isTrue(postKeep >= SensitiveConstants.NOP_KEEP, "postKeep must greater than -1");

      boolean ignorePreKeep = preKeep <= 0;
      boolean ignoreSuffixKeep = postKeep <= 0;
      if (ignorePreKeep && ignoreSuffixKeep) {
        return fieldValue;
      }

      char replacer = sensitive.replacer();
      return CharSequenceUtil.replace(
          fieldValue, preKeep, fieldValue.length() - postKeep, replacer);
    }
  },

  /** Customize sensitive handler */
  CUSTOMIZE_HANDLER() {
    @Override
    public String apply(SensitiveWrapper sensitiveWrapper) {
      Field field = sensitiveWrapper.getField();
      SensitiveHandler customizeHandler = field.getAnnotation(SensitiveHandler.class);
      Class<? extends CustomizeSensitiveHandler> handlerClass = customizeHandler.value();
      CustomizeSensitiveHandler handler = ReflectUtil.newInstance(handlerClass);
      return handler.customize(sensitiveWrapper);
    }
  };

  /**
   * Field sensitive strategy method
   *
   * @param sensitiveWrapper sensitive require message
   * @return after sensitive value
   */
  public abstract String apply(SensitiveWrapper sensitiveWrapper);
}
