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
import com.lzhpo.sensitive.annocation.SensitiveFilterWords;
import com.lzhpo.sensitive.annocation.SensitiveHandler;
import com.lzhpo.sensitive.annocation.SensitiveKeepLength;
import com.lzhpo.sensitive.util.SensitiveUtil;
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
        public String apply(SensitiveWrapper wrapper) {
            return SensitiveUtil.chineseName(wrapper.getFieldValue(), wrapper.getReplacer());
        }
    },

    /** ID card */
    ID_CARD() {
        @Override
        public String apply(SensitiveWrapper wrapper) {
            return SensitiveUtil.idCardNum(wrapper.getFieldValue(), 1, 2, wrapper.getReplacer());
        }
    },

    /** Fixed phone */
    FIXED_PHONE() {
        @Override
        public String apply(SensitiveWrapper wrapper) {
            return SensitiveUtil.fixedPhone(wrapper.getFieldValue(), wrapper.getReplacer());
        }
    },

    /** Mobile phone */
    MOBILE_PHONE() {
        @Override
        public String apply(SensitiveWrapper wrapper) {
            return SensitiveUtil.mobilePhone(wrapper.getFieldValue(), wrapper.getReplacer());
        }
    },

    /** Address */
    ADDRESS() {
        @Override
        public String apply(SensitiveWrapper wrapper) {
            return SensitiveUtil.address(wrapper.getFieldValue(), 8, wrapper.getReplacer());
        }
    },

    /** Email */
    EMAIL() {
        @Override
        public String apply(SensitiveWrapper wrapper) {
            return SensitiveUtil.email(wrapper.getFieldValue(), wrapper.getReplacer());
        }
    },

    /** Password */
    PASSWORD() {
        @Override
        public String apply(SensitiveWrapper wrapper) {
            return SensitiveUtil.password(wrapper.getFieldValue(), wrapper.getReplacer());
        }
    },

    /** Chinese mainland license plates, including ordinary vehicles, new energy vehicles */
    CAR_LICENSE() {
        @Override
        public String apply(SensitiveWrapper wrapper) {
            return SensitiveUtil.carLicense(wrapper.getFieldValue(), wrapper.getReplacer());
        }
    },

    /** Bank card */
    BANK_CARD() {
        @Override
        public String apply(SensitiveWrapper wrapper) {
            return SensitiveUtil.bankCard(wrapper.getFieldValue(), wrapper.getReplacer());
        }
    },

    /** Customize sensitive keep length */
    CUSTOMIZE_FILTER_WORDS() {
        @Override
        public String apply(SensitiveWrapper wrapper) {
            String fieldValue = wrapper.getFieldValue();
            String fieldName = wrapper.getFieldName();
            Object object = wrapper.getObject();
            Field field = ReflectUtil.getField(object.getClass(), fieldName);
            SensitiveFilterWords filterWords = field.getAnnotation(SensitiveFilterWords.class);
            if (ObjectUtils.isEmpty(filterWords)) {
                log.warn(
                        "{} is marked CUSTOMIZE_FILTER_WORDS strategy, "
                                + "but not has @SensitiveFilterWords, will ignore sensitive it.",
                        field.getName());
                return fieldValue;
            }

            char replacer = wrapper.getReplacer();
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
        public String apply(SensitiveWrapper wrapper) {
            String fieldValue = wrapper.getFieldValue();
            String fieldName = wrapper.getFieldName();
            Object object = wrapper.getObject();
            Field field = ReflectUtil.getField(object.getClass(), fieldName);
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

            char replacer = wrapper.getReplacer();
            return CharSequenceUtil.replace(fieldValue, preKeep, fieldValue.length() - postKeep, replacer);
        }
    },

    /** Customize sensitive handler */
    CUSTOMIZE_HANDLER() {
        @Override
        public String apply(SensitiveWrapper wrapper) {
            String fieldName = wrapper.getFieldName();
            Object object = wrapper.getObject();
            Field field = ReflectUtil.getField(object.getClass(), fieldName);
            SensitiveHandler customizeHandler = field.getAnnotation(SensitiveHandler.class);
            Class<? extends CustomizeSensitiveHandler> handlerClass = customizeHandler.value();
            CustomizeSensitiveHandler handler = ReflectUtil.newInstance(handlerClass);
            return handler.customize(wrapper);
        }
    };

    /**
     * Field sensitive strategy method
     *
     * @param wrapper {@link SensitiveWrapper}
     * @return after sensitive value
     */
    public abstract String apply(SensitiveWrapper wrapper);
}
