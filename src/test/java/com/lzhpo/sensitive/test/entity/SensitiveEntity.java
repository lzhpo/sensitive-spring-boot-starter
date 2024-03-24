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
package com.lzhpo.sensitive.test.entity;

import com.lzhpo.sensitive.SensitiveStrategy;
import com.lzhpo.sensitive.annocation.Sensitive;
import com.lzhpo.sensitive.annocation.SensitiveFilterWords;
import com.lzhpo.sensitive.annocation.SensitiveHandler;
import com.lzhpo.sensitive.annocation.SensitiveKeepLength;
import com.lzhpo.sensitive.test.handler.FaceCustomizeSensitiveHandler;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/** @author lzhpo */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SensitiveEntity {

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

    @Sensitive(strategy = SensitiveStrategy.IPV4)
    private String ipv4;

    @Sensitive(strategy = SensitiveStrategy.IPV6)
    private String ipv6;

    @Sensitive(strategy = SensitiveStrategy.FIRST_MASK)
    private String firstMask;

    @Sensitive(strategy = SensitiveStrategy.CLEAR_TO_NULL)
    private String clearToNull;

    @Sensitive(strategy = SensitiveStrategy.CLEAR_TO_EMPTY)
    private String clearToEmpty;

    @Sensitive(strategy = SensitiveStrategy.CUSTOMIZE_KEEP_LENGTH)
    @SensitiveKeepLength(preKeep = 1, postKeep = 1)
    private String keepLength1;

    @Sensitive(strategy = SensitiveStrategy.CUSTOMIZE_FILTER_WORDS)
    @SensitiveFilterWords({"他妈的", "去你大爷", "卧槽", "草泥马", "废物"})
    private String filterWords1;

    @Sensitive(strategy = SensitiveStrategy.CUSTOMIZE_HANDLER)
    @SensitiveHandler(FaceCustomizeSensitiveHandler.class)
    private String handler1;

    @Sensitive(strategy = SensitiveStrategy.AES)
    private String aes;

    @Sensitive(strategy = SensitiveStrategy.DES)
    private String des;

    @Sensitive(strategy = SensitiveStrategy.BASE64)
    private String base64;

    @Sensitive(strategy = SensitiveStrategy.RSA)
    private String rsa;
}
