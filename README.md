![](https://img.shields.io/badge/JDK-1.8+-success.svg)
![](https://maven-badges.herokuapp.com/maven-central/com.lzhpo/sensitive-spring-boot-starter/badge.svg?color=blueviolet)
![](https://img.shields.io/:license-Apache2-orange.svg)
[![Style check](https://github.com/lzhpo/sensitive-spring-boot-starter/actions/workflows/style-check.yml/badge.svg)](https://github.com/lzhpo/sensitive-spring-boot-starter/actions/workflows/style-check.yml)
[![Codacy Badge](https://app.codacy.com/project/badge/Grade/e42fca0b3faf4daeb7d2f9e32f396376)](https://www.codacy.com/gh/lzhpo/sensitive-spring-boot-starter/dashboard?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=lzhpo/sensitive-spring-boot-starter&amp;utm_campaign=Badge_Grade)

## 开源地址

- GitHub：[https://github.com/lzhpo/sensitive-spring-boot-starter](https://github.com/lzhpo/sensitive-spring-boot-starter)
- Gitee：[https://gitee.com/lzhpo/sensitive-spring-boot-starter](https://gitee.com/lzhpo/sensitive-spring-boot-starter)

## 如何使用？

*sensitive-spring-boot-starter也支持SpringBoot3*

> 3.0.0及以上版本的sensitive-spring-boot-starter只针对使用SpringBoot3用户，SpringBoot2用户请使用低于3.0.0版本的sensitive-spring-boot-starter，两者功能不受影响，均会同步更新！

### 1.导入依赖

> 依赖已发布至Maven中央仓库，可直接引入依赖。

- Maven：

  ```xml
  <dependency>
    <groupId>com.lzhpo</groupId>
    <artifactId>sensitive-spring-boot-starter</artifactId>
    <version>${latest-version}</version>
  </dependency>
  ```
- Gradle:
  ```groovy
  implementation 'com.lzhpo:sensitive-spring-boot-starter:${latest-version}'
  ```

### 2.实体类字段上使用`@Sensitive`注解配置脱敏规则

`@Sensitive`注解说明：
- `strategy`：脱敏策略，支持21种脱敏策略。

#### 2.1.支持的脱敏策略

*以下数据均为随意构造的测试数据，如有相同，纯属巧合。*

##### 2.1.1 中文姓名

只显示第一个汉字，其他隐藏为2个星号。

```java
@Sensitive(strategy = SensitiveStrategy.CHINESE_NAME)
private String name;
```
比如：`刘子豪`脱敏之后为`刘**`。

##### 2.1.2 身份证号

保留前1位和后2位。

```java
@Sensitive(strategy = SensitiveStrategy.ID_CARD)
private String idCard;
```
比如：`530321199204074611`脱敏之后为`5***************11`。

##### 2.1.3 固定电话

保留前4位和后2位。

```java
@Sensitive(strategy = SensitiveStrategy.FIXED_PHONE)
private String fixedPhone;
```
比如：`01086551122`脱敏之后为`0108*****22`。

##### 2.1.4 手机号码

保留前3位和后4位。

```java
@Sensitive(strategy = SensitiveStrategy.MOBILE_PHONE)
private String mobilePhone;
```
比如：`13248765917`脱敏之后为`132****5917`。

##### 2.1.5 地址

只显示到地区，不显示详细地址，地址长度减去8即为前缀保留的长度，后缀均用星号代替。

```java
@Sensitive(strategy = SensitiveStrategy.ADDRESS)
private String address;
```
比如：`广州市天河区幸福小区102号`脱敏之后为`广州市天河区********`。

##### 2.1.6 电子邮箱

邮箱前缀仅显示第一个字母，前缀其他隐藏，用星号代替，@及后面的地址显示。

```java
@Sensitive(strategy = SensitiveStrategy.EMAIL)
private String email;
```
比如：`example@gmail.com`脱敏之后为`e******@gmail.com`。

##### 2.1.7 密码

全部字符都用星号`*`代替。

```java
@Sensitive(strategy = SensitiveStrategy.PASSWORD)
private String password;
```
比如：`123456`脱敏之后为`******`。

##### 2.1.8 车牌号

车牌中间用星号`*`代替。

```java
@Sensitive(strategy = SensitiveStrategy.CAR_LICENSE)
private String carLicense;
```
比如：`粤A66666`脱敏之后为`粤A6***6`。

##### 2.1.9 银行卡号

保留前4位和后4位，中间的使用星号`*`代替，且中间的从第1位起，每隔4位添加一个空格用来美化。

```java
@Sensitive(strategy = SensitiveStrategy.BANK_CARD)
private String bankCard;
```
例如：`9988002866797031`脱敏之后为`9988 **** **** 7031`。

##### 2.2.0 IPV4地址

IPV4地址脱敏。

```java
@Sensitive(strategy = SensitiveStrategy.IPV4)
private String ipv4;
```

例如：`192.0.2.1`脱敏之后为`192.*.*.*`。

##### 2.2.1 IPV6地址

IPV6地址脱敏。

```java
@Sensitive(strategy = SensitiveStrategy.IPV6)
private String ipv6;
```

例如：`2001:0db8:86a3:08d3:1319:8a2e:0370:7344`脱敏之后为`2001:*:*:*:*:*:*:*`。

##### 2.2.2 Base64加密

```java
@Sensitive(strategy = SensitiveStrategy.BASE64)
private String base64;
```

##### 2.2.3 AES加密

```java
@Sensitive(strategy = SensitiveStrategy.AES)
private String aes;
```

需要配置 AES 加密的 key，例如：
```yaml
sensitive:
  encrypt:
    aes:
      key: "1234567890123456"
```

##### 2.2.4 DES加密

```java
@Sensitive(strategy = SensitiveStrategy.DES)
private String des;
```

需要配置 DES 加密的 key，例如：
```yaml
sensitive:
  encrypt:
    des:
      key: "12345678"
```

##### 2.2.5 RSA加密

```java
@Sensitive(strategy = SensitiveStrategy.RSA)
private String rsa;
```

需要配置 RSA 加密的 private key 和 public key，例如：
```yaml
sensitive:
  encrypt:
    rsa:
      private-key: "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBANyhI/9e5evQyzRLVsUmbQesdRl7fXu9ZAl6lZqVyL+ypf6FmQouH89OTEv/JjmybuAha9zsYwNAKSobSRATaqYSCvdwoPRgUFRFfq6ed61kpO5+D+T/X3v85JmXIngkieCe9n5b5KT3XNtHFBXVsZ3/onWEYZRhFMTsMsKkvijBAgMBAAECgYAKV2fEbC5vAp0JvRfKuym8ZLgi6wPHWWnfW154jdmIab9n2huBq4aMbSU8oS+pn+xcR1jC1NYxxG/BhGCk9yIGIzE/57tggjibNpiqC/uS12SiaJPz9oqOVJPI+l5uf9xqdytzvNJe6AGMViZdS+nnQRZfdDrs5cgghv7lx+kjiQJBAOQWmEJukHaIUXvW8ZWNekIgb8/Frq7gNvRaeqjqpZMqUIXXDj80eODGsNjIUwwEdlFX4//C7udmLfWfhyOq1bkCQQD3oOGP8rjIkouhbJldaILeuaN3ee3v3dtsmLM8epC9HH3EcFBD2O+l60wCa67uM/ArPn3XjL/lidqnVAJHPG9JAkEAumz1WicAkMFuyGew4enXKcFVYl9THcBJaoOhifrwBk8prZtPG74Jpr7/wNBLgKENDANoaZ2soxnTKtWPIUn6kQJAAmcxSTBV0rx5VmuzYVCuVHMAvxwTzwwcIQWqV5/o36zzG4Drhn0Idle+ORfKbs1aO1Ez72+SPSwFTzJlg0N24QJATQu2dlhbm87uGh0fUHpV6Nw6lf/mBMek1stC8PQXB0MtNPeYd+Ul45zfc+k5mIWUHwt47To5uAo2ywsCSdWBCw=="
      public-key: "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDcoSP/XuXr0Ms0S1bFJm0HrHUZe317vWQJepWalci/sqX+hZkKLh/PTkxL/yY5sm7gIWvc7GMDQCkqG0kQE2qmEgr3cKD0YFBURX6unnetZKTufg/k/197/OSZlyJ4JIngnvZ+W+Sk91zbRxQV1bGd/6J1hGGUYRTE7DLCpL4owQIDAQAB"
```

关于 private key 和 public key 的生成方式：
```java
KeyPair keyPair = SecureUtil.generateKeyPair("RSA");

String privateKey = Base64.encode(keyPair.getPrivate().getEncoded());
System.out.println("privateKey: " + privateKey);

String publicKey = Base64.encode(keyPair.getPublic().getEncoded());
System.out.println("publicKey: " + publicKey);
```

##### 2.2.6 只保留首字符

```java
@Sensitive(strategy = SensitiveStrategy.FIRST_MASK)
private String firstMask;
```

例如：`123456789`脱敏之后为`1********`。

##### 2.2.7 清空为null

```java
@Sensitive(strategy = SensitiveStrategy.CLEAR_TO_NULL)
private String clearToNull;
```

##### 2.2.8 清空为空字符串

```java
@Sensitive(strategy = SensitiveStrategy.CLEAR_TO_NULL)
private String clearToNull;
```

##### 2.2.9 自定义脱敏策略

当前支持三种风格的自定义脱敏策略：
1. 保留前后缀脱敏策略。
2. 敏感字符脱敏策略。
3. Handler脱敏策略。

###### 2.2.1.1 保留前后缀脱敏策略

使用`@Sensitive(strategy = SensitiveStrategy.CUSTOMIZE_KEEP_LENGTH)`配合`@SensitiveKeepLength`一起使用。

`@SensitiveFilterWords`注解：
- `preKeep`是字符串前置保留字符个数。
- `postKeep`是字符串后置保留字符个数。

举个例子：`name`前后都只保留1个字符。

```java
@Sensitive(strategy = SensitiveStrategy.CUSTOMIZE_KEEP_LENGTH)
@SensitiveKeepLength(preKeep = 1, postKeep = 1)
private String name;
```

如果`name`为`1234`，脱敏之后就是`1**4`。

###### 2.2.1.2 敏感字符脱敏策略

使用`@Sensitive(strategy = SensitiveStrategy.CUSTOMIZE_FILTER_WORDS)`配合`@SensitiveFilterWords`一起使用。

`@SensitiveFilterWords`注解：定义敏感字符。

举个例子：脏话关键字脱敏。

```java
@Sensitive(strategy = SensitiveStrategy.CUSTOMIZE_FILTER_WORDS)
@SensitiveFilterWords({"他妈的", "去你大爷", "卧槽", "草泥马", "废物"})
private String description;
```

如果`description`的值为`卧槽，他妈的，我去你大爷的，草泥马`，脱敏之后就是`**，***，我****的，***`。

###### 2.2.1.3 Handler脱敏策略

Handler脱敏策略完全由开发者进行处理，不受`@Sensitive`注解上的`replacer`脱敏替换符影响。

使用`@Sensitive(strategy = SensitiveStrategy.CUSTOMIZE_HANDLER)`配合`@SensitiveHandler`一起使用。

`@SensitiveHandler`注解：表示处理脱敏的Handler。

例如：将`name`字段都设置为`@#@`。

```java
@Sensitive(strategy = SensitiveStrategy.CUSTOMIZE_HANDLER)
@SensitiveHandler(FaceCustomizeSensitiveHandler.class)
private String name;
```

```java
public class FaceCustomizeSensitiveHandler implements CustomizeSensitiveHandler {

  @Override
  public String customize(SensitiveWrapper sensitiveWrapper) {
    // 字段
    Field field = sensitiveWrapper.getField();
    // 字段归属的对象
    Class<?> objectClass = field.getDeclaringClass();
    // 字段上的注解
    Annotation[] annotations = field.getAnnotations();
    // 字段值
    String fieldValue = sensitiveWrapper.getFieldValue();
    // 注解信息
    Sensitive sensitive = sensitiveWrapper.getSensitive();
    return "@#@";
  }
}
```

可以看到，提供了含有`@Sensitive`注解的字段、字段值、字段归属的对象、注解的信息等等供开发者定制。

### 3.使用`@IgnoreSensitive`注解标注在controller上可忽略脱敏

如果实体类标注了`@Sensitive`脱敏，被多个接口共用的，接口A要脱敏，但是接口B不需要脱敏，就可以使用`@IgnoreSensitive`注解忽略脱敏，同时也支持忽略指定字段脱敏。

#### 1.在Controller类上使用`@IgnoreSensitive`表示此类下所有接口都忽略脱敏

此controller下的所有接口都将忽略脱敏。

```java
@RestController
@IgnoreSensitive
@RequestMapping("/ignore")
public class NoSensitiveController {

  @GetMapping("sample3")
  public ResponseEntity<SampleJavaBean> sample3() {
    return ResponseEntity.ok(SampleJavaBeanMock.sampleJavaBean());
  }

  @GetMapping("sample4")
  public ResponseEntity<SampleJavaBean> sample4() {
    return ResponseEntity.ok(SampleJavaBeanMock.sampleJavaBean());
  }
}
```

#### 2.在Controller的方法中使用`@IgnoreSensitive`表示此接口忽略脱敏

```java
@RestController
@RequestMapping("/")
public class SensitiveController {

  @GetMapping("sample1")
  public ResponseEntity<SampleJavaBean> sample1() {
    return ResponseEntity.ok(SampleJavaBeanMock.sampleJavaBean());
  }

  @IgnoreSensitive
  @GetMapping("ignore/sample2")
  public ResponseEntity<SampleJavaBean> sample2() {
    return ResponseEntity.ok(SampleJavaBeanMock.sampleJavaBean());
  }
}
```

sample2将忽略`SampleJavaBean`对象的字段脱敏，sample1不影响。

#### 3.在Controller的类或方法中使用`@IgnoreSensitive`忽略指定字段脱敏

示例：忽略脱敏`SampleJavaBean`中的`name`和`email`字段的脱敏。
```java
@RestController
@RequestMapping("/")
public class SensitiveController {

  @IgnoreSensitive({"name", "email"})
  @GetMapping("ignore/sample")
  public ResponseEntity<SampleJavaBean> sample() {
    return ResponseEntity.ok(SampleJavaBeanMock.sampleJavaBean());
  }
}
```

## 注意事项

### 1.有关单独使用`@Builder`/`@SuperBuilder`注解、实体类多层嵌套问题

需要数据脱敏的实体类以及嵌套类都应提供对应成员变量的get方法，否则JSON组件无法获取到嵌套的成员变量进行脱敏！

- 错误示范：嵌套对象单独使用一个`@Builder`/`@SuperBuilder`

  ![](./docs/images/单独使用@Builder注解问题.png)

- **正确示范**：如果需要使用到`@Builder`/`@SuperBuilder`，那么需要配合`@Data`或`@Getter`一起使用。
  
  例如：
  ```java
  @Data
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  public class SensitiveEntity {
  
    @Sensitive(strategy = SensitiveStrategy.CHINESE_NAME)
    private String name;
  
    @Sensitive(strategy = SensitiveStrategy.ID_CARD)
    private String idCard;
  }
  ```

### 2.将默认的Jackson切换为FastJson（不推荐）

1. 加入FastJson依赖（支持FastJson1和FastJson2）：
    ```xml
    <dependency>
      <groupId>com.alibaba</groupId>
      <artifactId>fastjson</artifactId>
      <version>1.x.x/2.x.x</version>
    </dependency>
    ```
2. 将`FastJsonHttpMessageConverter`声明为Bean即可，`sensitive-spring-boot-starter`会自动注入相关逻辑。
    ```java
    @Bean
    public FastJsonHttpMessageConverter fastJsonHttpMessageConverter() {
      return new FastJsonHttpMessageConverter();
    }
    ```

### 3.关闭此数据脱敏功能

移除此maven依赖即可

## 微信公众号

<img src="./docs/images/WeChat-MP.png" width="453" height="150" alt="会打篮球的程序猿">
