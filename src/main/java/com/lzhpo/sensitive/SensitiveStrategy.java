/*
 * Copyright lzhpo
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

import cn.hutool.core.codec.Base64;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.DesensitizedUtil;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.extra.spring.SpringUtil;
import com.lzhpo.sensitive.annocation.SensitiveFilterWords;
import com.lzhpo.sensitive.annocation.SensitiveHandler;
import com.lzhpo.sensitive.annocation.SensitiveKeepLength;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.util.Optional;

/**
 * Sensitive strategy
 *
 * @author lzhpo
 */
@Slf4j
public enum SensitiveStrategy {

    /**
     * Chinese name
     */
    CHINESE_NAME() {
        @Override
        public String apply(SensitiveWrapper wrapper) {
            return DesensitizedUtil.chineseName(wrapper.getFieldValue());
        }
    },

    /**
     * ID card
     */
    ID_CARD() {
        @Override
        public String apply(SensitiveWrapper wrapper) {
            return DesensitizedUtil.idCardNum(wrapper.getFieldValue(), 1, 2);
        }
    },

    /**
     * Fixed phone
     */
    FIXED_PHONE() {
        @Override
        public String apply(SensitiveWrapper wrapper) {
            return DesensitizedUtil.fixedPhone(wrapper.getFieldValue());
        }
    },

    /**
     * Mobile phone
     */
    MOBILE_PHONE() {
        @Override
        public String apply(SensitiveWrapper wrapper) {
            return DesensitizedUtil.mobilePhone(wrapper.getFieldValue());
        }
    },

    /**
     * Address
     */
    ADDRESS() {
        @Override
        public String apply(SensitiveWrapper wrapper) {
            return DesensitizedUtil.address(wrapper.getFieldValue(), 8);
        }
    },

    /**
     * Email
     */
    EMAIL() {
        @Override
        public String apply(SensitiveWrapper wrapper) {
            return DesensitizedUtil.email(wrapper.getFieldValue());
        }
    },

    /**
     * Password
     */
    PASSWORD() {
        @Override
        public String apply(SensitiveWrapper wrapper) {
            return DesensitizedUtil.password(wrapper.getFieldValue());
        }
    },

    /**
     * Chinese mainland license plates, including ordinary vehicles, new energy vehicles
     */
    CAR_LICENSE() {
        @Override
        public String apply(SensitiveWrapper wrapper) {
            return DesensitizedUtil.carLicense(wrapper.getFieldValue());
        }
    },

    /**
     * Bank card
     */
    BANK_CARD() {
        @Override
        public String apply(SensitiveWrapper wrapper) {
            return DesensitizedUtil.bankCard(wrapper.getFieldValue());
        }
    },

    /**
     * IPV4
     */
    IPV4() {
        @Override
        public String apply(SensitiveWrapper wrapper) {
            return DesensitizedUtil.ipv4(wrapper.getFieldValue());
        }
    },

    /**
     * IPV6
     */
    IPV6() {
        @Override
        public String apply(SensitiveWrapper wrapper) {
            return DesensitizedUtil.ipv6(wrapper.getFieldValue());
        }
    },

    /**
     * Only show the first one
     */
    FIRST_MASK() {
        @Override
        public String apply(SensitiveWrapper wrapper) {
            return DesensitizedUtil.firstMask(wrapper.getFieldValue());
        }
    },

    /**
     * Clear to null
     */
    CLEAR_TO_NULL() {
        @Override
        public String apply(SensitiveWrapper wrapper) {
            return null;
        }
    },

    /**
     * Clear to empty
     */
    CLEAR_TO_EMPTY() {
        @Override
        public String apply(SensitiveWrapper wrapper) {
            return CharSequenceUtil.EMPTY;
        }
    },

    /**
     * AES.
     */
    AES() {
        @Override
        public String apply(SensitiveWrapper wrapper) {
            SensitiveProperties sensitiveProperties = SpringUtil.getBean(SensitiveProperties.class);
            String key = Optional.ofNullable(sensitiveProperties.getEncrypt())
                    .map(SensitiveProperties.Encrypt::getAes)
                    .map(SensitiveProperties.Aes::getKey)
                    .filter(StringUtils::hasText)
                    .orElseThrow(() -> new IllegalArgumentException("Not configure aes encrypt key."));
            return SecureUtil.aes(key.getBytes()).encryptHex(wrapper.getFieldValue());
        }
    },

    /**
     * DES.
     */
    DES() {
        @Override
        public String apply(SensitiveWrapper wrapper) {
            SensitiveProperties sensitiveProperties = SpringUtil.getBean(SensitiveProperties.class);
            String key = Optional.ofNullable(sensitiveProperties.getEncrypt())
                    .map(SensitiveProperties.Encrypt::getDes)
                    .map(SensitiveProperties.Des::getKey)
                    .filter(StringUtils::hasText)
                    .orElseThrow(() -> new IllegalArgumentException("Not configure des encrypt key."));
            return SecureUtil.des(key.getBytes()).encryptHex(wrapper.getFieldValue());
        }
    },

    /**
     * RSA.
     */
    RSA() {
        @Override
        public String apply(SensitiveWrapper wrapper) {
            Optional<SensitiveProperties.Rsa> rsaOptional = Optional.ofNullable(
                            SpringUtil.getBean(SensitiveProperties.class))
                    .map(SensitiveProperties::getEncrypt)
                    .map(SensitiveProperties.Encrypt::getRsa);
            String privateKey = rsaOptional
                    .map(SensitiveProperties.Rsa::getPrivateKey)
                    .orElseThrow(() -> new IllegalArgumentException("Not configure rsa encrypt privateKey."));
            String publicKey = rsaOptional
                    .map(SensitiveProperties.Rsa::getPublicKey)
                    .orElseThrow(() -> new IllegalArgumentException("Not configure rsa encrypt privateKey."));
            return SecureUtil.rsa(privateKey, publicKey).encryptHex(wrapper.getFieldValue(), KeyType.PublicKey);
        }
    },

    /**
     * BASE64.
     */
    BASE64() {
        @Override
        public String apply(SensitiveWrapper wrapper) {
            return Base64.encode(wrapper.getFieldValue());
        }
    },

    /**
     * Customize sensitive keep length
     */
    CUSTOMIZE_FILTER_WORDS() {
        @Override
        public String apply(SensitiveWrapper wrapper) {
            String fieldValue = wrapper.getFieldValue();
            String fieldName = wrapper.getFieldName();
            Object object = wrapper.getObject();
            Field field = ReflectUtil.getField(object.getClass(), fieldName);
            SensitiveFilterWords filterWords = field.getAnnotation(SensitiveFilterWords.class);
            Assert.notNull(filterWords, CharSequenceUtil.format("{} missing @SensitiveFilterWords.", fieldName));

            String[] words = filterWords.value();
            Assert.notNull(words, CharSequenceUtil.format("{} missing filter words.", fieldName));
            for (String filterWord : words) {
                if (fieldValue.contains(filterWord)) {
                    String replacers = CharSequenceUtil.repeat(SensitiveConstants.REPLACER, filterWord.length());
                    fieldValue = fieldValue.replace(filterWord, replacers);
                }
            }

            return fieldValue;
        }
    },

    /**
     * Customize sensitive keep length
     */
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

            char replacer = SensitiveConstants.REPLACER;
            return CharSequenceUtil.replace(fieldValue, preKeep, fieldValue.length() - postKeep, replacer);
        }
    },

    /**
     * Customize sensitive handler
     */
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
