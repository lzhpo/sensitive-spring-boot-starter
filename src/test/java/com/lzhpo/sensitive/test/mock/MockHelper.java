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
package com.lzhpo.sensitive.test.mock;

import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.map.MapUtil;
import com.lzhpo.sensitive.test.entity.NestedSensitiveEntity;
import com.lzhpo.sensitive.test.entity.NoSensitiveEntity;
import com.lzhpo.sensitive.test.entity.SensitiveEntity;
import lombok.experimental.UtilityClass;

/**
 * @author lzhpo
 */
@UtilityClass
public class MockHelper {

    public static SensitiveEntity sensitive() {
        return SensitiveEntity.builder()
                .name("刘子豪")
                .idCard("530321199204074611")
                .fixedPhone("01086551122")
                .mobilePhone("13248765917")
                .address("广州市天河区幸福小区102号")
                .email("example@gmail.com")
                .password("123456")
                .carLicense("粤A66666")
                .bankCard("9988002866797031")
                .ipv4("192.0.2.1")
                .ipv6("2001:0db8:86a3:08d3:1319:8a2e:0370:7344")
                .firstMask("123456789")
                .clearToEmpty("123456")
                .clearToNull("12345")
                .bankCard("9988002866797031")
                .keepLength1("1234")
                .handler1("12345")
                .filterWords1("卧槽，他妈的，我去你大爷的，草泥马")
                .aes("123456")
                .des("123456")
                .base64("123456")
                .rsa("123456")
                .build();
    }

    public static NoSensitiveEntity noSensitive() {
        return NoSensitiveEntity.builder()
                .name("张小明")
                .address("江西省南昌市幸福小区102号")
                .email("example@qq.com")
                .mobilePhone("13248765916")
                .build();
    }

    public static NestedSensitiveEntity nestedSensitive() {
        return NestedSensitiveEntity.builder()
                .parentName("123456")
                .sensitiveEntity(sensitive())
                .array(new SensitiveEntity[] {sensitive()})
                .map(MapUtil.of(0, sensitive()))
                .list(ListUtil.list(false, sensitive()))
                .build();
    }
}
