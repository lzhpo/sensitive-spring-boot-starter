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
package com.lzhpo.sensitive.util;

import cn.hutool.core.text.CharPool;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.DesensitizedUtil;
import com.lzhpo.sensitive.SensitiveConstants;
import lombok.experimental.UtilityClass;
import org.springframework.util.StringUtils;

/**
 * 参考{@link DesensitizedUtil}
 *
 * @author lzhpo
 */
@UtilityClass
public class SensitiveUtils extends DesensitizedUtil {

    /**
     * 中文姓名
     *
     * <pre>
     * 只显示第一个汉字，其他替换为2个{@code replacer}。
     *
     * 比如脱敏替换符为星号：
     * e.g: 刘子豪 -> 刘**
     * </pre>
     *
     * @param fullName 中文姓名
     * @param replacer 脱敏替换符
     * @return 脱敏后的姓名
     */
    public static String chineseName(String fullName, char replacer) {
        if (!StringUtils.hasLength(fullName)) {
            return CharSequenceUtil.EMPTY;
        }

        return CharSequenceUtil.replace(fullName, 1, fullName.length(), replacer);
    }

    /**
     * 身份证号
     *
     * <pre>
     * 保留前1位和后2位
     *
     * 比如脱敏替换符为星号：
     * e.g: 530321199204074611 -> 5***************11
     * </pre>
     *
     * @param idCardNum 身份证
     * @param front 保留：前面的front位数；从1开始
     * @param end 保留：后面的end位数；从1开始
     * @param replacer 脱敏替换符
     * @return 脱敏后的身份证
     */
    public static String idCardNum(String idCardNum, int front, int end, char replacer) {
        // 身份证不能为空
        if (!StringUtils.hasLength(idCardNum)) {
            return CharSequenceUtil.EMPTY;
        }

        // 需要截取的长度不能大于身份证号长度
        if ((front + end) > idCardNum.length()) {
            return CharSequenceUtil.EMPTY;
        }

        // 需要截取的不能小于0
        if (front < 0 || end < 0) {
            return CharSequenceUtil.EMPTY;
        }

        return CharSequenceUtil.replace(idCardNum, front, idCardNum.length() - end, replacer);
    }

    /**
     * 固定电话
     *
     * <pre>
     * 保留前4位和后2位
     *
     * 比如脱敏替换符为星号：
     * e.g: 01086551122 -> 0108*****22
     * </pre>
     *
     * @param num 固定电话
     * @param replacer 脱敏替换符
     * @return 脱敏后的固定电话；
     */
    public static String fixedPhone(String num, char replacer) {
        if (!StringUtils.hasLength(num)) {
            return CharSequenceUtil.EMPTY;
        }

        return CharSequenceUtil.replace(num, 4, num.length() - 2, replacer);
    }

    /**
     * 手机号码
     *
     * <pre>
     * 保留前3位和后4位
     *
     * 比如脱敏替换符为星号：
     * e.g: 13248765917 -> 132****5917
     * </pre>
     *
     * @param num 移动电话；
     * @param replacer 脱敏替换符
     * @return 脱敏后的移动电话；
     */
    public static String mobilePhone(String num, char replacer) {
        if (!StringUtils.hasLength(num)) {
            return CharSequenceUtil.EMPTY;
        }

        return CharSequenceUtil.replace(num, 3, num.length() - 4, replacer);
    }

    /**
     * 地址
     *
     * <pre>
     * 只显示到地区，不显示详细地址，地址长度减去8即为前缀保留的长度，后缀均用脱敏替换符代替。
     *
     * 比如脱敏替换符为星号：
     * e.g: 13248765917 -> 132****5917
     * </pre>
     *
     * @param address 地址
     * @param sensitiveSize 敏感信息长度
     * @param replacer 脱敏替换符
     * @return 脱敏后的家庭地址
     */
    public static String address(String address, int sensitiveSize, char replacer) {
        if (!StringUtils.hasLength(address)) {
            return CharSequenceUtil.EMPTY;
        }

        int length = address.length();
        return CharSequenceUtil.replace(address, length - sensitiveSize, length, replacer);
    }

    /**
     * 电子邮箱
     *
     * <pre>
     * 邮箱前缀仅显示第一个字母，前缀其他隐藏，用星号代替，@及后面的地址显示。
     *
     * 比如脱敏替换符为星号：
     * e.g: example@gmail.com -> e******@gmail.com
     * </pre>
     *
     * @param email 邮箱
     * @param replacer 脱敏替换符
     * @return 脱敏后的邮箱
     */
    public static String email(String email, char replacer) {
        if (!StringUtils.hasLength(email)) {
            return CharSequenceUtil.EMPTY;
        }

        int index = CharSequenceUtil.indexOf(email, '@');
        if (index <= 1) {
            return email;
        }

        return CharSequenceUtil.replace(email, 1, index, replacer);
    }

    /**
     * 密码
     *
     * <pre>
     * 全部字符都用脱敏替换符代替。
     *
     * 比如脱敏替换符为星号：
     * e.g: 123456 -> ******
     * </pre>
     *
     * @param password 密码
     * @param replacer 脱敏替换符
     * @return 脱敏后的密码
     */
    public static String password(String password, char replacer) {
        if (!StringUtils.hasLength(password)) {
            return CharSequenceUtil.EMPTY;
        }

        return CharSequenceUtil.repeat(replacer, password.length());
    }

    /**
     * 中国车牌
     *
     * <pre>
     * 车牌中间用脱敏替换符代替。
     *
     * 比如脱敏替换符为星号：
     * eg1：null       -> ""
     * eg1：""         -> ""
     * eg3：苏D40000   -> 苏D4***0
     * eg4：陕A12345D  -> 陕A1****D
     * eg5：京A123     -> 京A123     如果是错误的车牌，不处理
     * </pre>
     *
     * @param carLicense 车牌号
     * @param replacer 脱敏替换符
     * @return 脱敏后的车牌
     */
    public static String carLicense(String carLicense, char replacer) {
        if (!StringUtils.hasLength(carLicense)) {
            return CharSequenceUtil.EMPTY;
        }

        // 普通车牌
        if (carLicense.length() == SensitiveConstants.ORDINARY_CAR_LICENSE_LENGTH) {
            return CharSequenceUtil.replace(carLicense, 3, 6, replacer);
        } else if (carLicense.length() == SensitiveConstants.NEW_ENERGY_CAR_LICENSE_LENGTH) {
            // 新能源车牌
            return CharSequenceUtil.replace(carLicense, 3, 7, replacer);
        }

        return carLicense;
    }

    /**
     * 银行卡号
     *
     * <pre>
     * 保留前4位和后4位，中间的使用脱敏替换符代替，且中间的从第1位起，每隔4位添加一个空格用来美化。
     *
     * 比如脱敏替换符为星号：
     * e.g: 9988002866797031 -> 9988 **** **** 7031
     * </pre>
     *
     * @param bankCardNo 银行卡号
     * @param replacer 脱敏替换符
     * @return 脱敏之后的银行卡号
     */
    public static String bankCard(String bankCardNo, char replacer) {
        if (!StringUtils.hasLength(bankCardNo)) {
            return bankCardNo;
        }

        String trimBankCardNo = bankCardNo.trim();
        if (trimBankCardNo.length() < SensitiveConstants.ERROR_BANK_CARD_LENGTH) {
            return trimBankCardNo;
        }

        final int length = trimBankCardNo.length();
        final int midLength = length - 8;
        final StringBuilder buf = new StringBuilder();

        buf.append(trimBankCardNo, 0, 4);
        for (int i = 0; i < midLength; ++i) {
            if (i % 4 == 0) {
                buf.append(CharPool.SPACE);
            }
            buf.append(replacer);
        }

        buf.append(CharPool.SPACE).append(trimBankCardNo, length - 4, length);
        return buf.toString();
    }

    /**
     * IPv4脱敏，如：脱敏前：192.0.2.1；脱敏后：192.*.*.*。
     *
     * @param ipv4 IPv4地址
     * @return 脱敏后的地址
     */
    public static String ipv4(String ipv4, char replacer) {
        final StringBuilder builder = new StringBuilder();
        for (int i = 0; i < 3; i++) {
            builder.append(SensitiveConstants.SEPARATOR_IPV4);
            builder.append(replacer);
        }
        return CharSequenceUtil.subBefore(ipv4, SensitiveConstants.SEPARATOR_IPV4, false) + builder;
    }

    /**
     * IPv4脱敏，如：脱敏前：2001:0db8:86a3:08d3:1319:8a2e:0370:7344；脱敏后：2001:*:*:*:*:*:*:*
     *
     * @param ipv6 IPv4地址
     * @return 脱敏后的地址
     */
    public static String ipv6(String ipv6, char replacer) {
        final StringBuilder builder = new StringBuilder();
        for (int i = 0; i < 7; i++) {
            builder.append(SensitiveConstants.SEPARATOR_IPV6);
            builder.append(replacer);
        }
        return CharSequenceUtil.subBefore(ipv6, SensitiveConstants.SEPARATOR_IPV6, false) + builder;
    }

    /**
     * 定义了一个first_mask的规则，只显示第一个字符。<br>
     * 脱敏前：123456789；脱敏后：1********。
     *
     * @param str 字符串
     * @return 脱敏后的字符串
     */
    public static String firstMask(String str, char replacer) {
        if (CharSequenceUtil.isBlank(str)) {
            return CharSequenceUtil.EMPTY;
        }
        return CharSequenceUtil.replace(str, 1, str.length(), replacer);
    }
}
