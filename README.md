## 它是什么？

> 一款强大的数据脱敏插件，支持多种脱敏策略（中文姓名、身份证号、固定电话、手机号码、地址、电子邮箱、密码、车牌号、银行卡号...），支持自定义脱敏策略，支持自定义脱敏替换符，支持在Controller上使用注解跳过脱敏...

## 如何使用？

### 1.导入依赖

> 依赖已发布至Maven中央仓库，可直接引入依赖。

#### Maven

```xml
<dependency>
  <groupId>com.lzhpo</groupId>
  <artifactId>sensitive-spring-boot-starter</artifactId>
  <version>${latest-version}</version>
</dependency>
```

#### Gradle

```groovy
implementation 'com.lzhpo:panda-sensitive-boot-starter:${latest-version}'
```

### 2.实体类字段上使用`@Sensitive`注解配置脱敏规则

`@Sensitive`注解说明：
- `strategy`是脱敏策略。
- `preKeep`是字符串前置保留字符个数。
- `postKeep`是字符串后置保留字符个数。
- `replacer`是脱敏替换符。

#### 2.1.支持的脱敏策略

*以下数据均为随意构造的测试数据，如有相同，纯属巧合。*

1. 中文姓名：只显示第一个汉字，其他隐藏为2个星号，比如：`刘**`
   ```java
   @Sensitive(strategy = SensitiveStrategy.CHINESE_NAME)
   private String name;
   ```
   比如：`刘子豪`脱敏之后为`刘**`。

2. 身份证号：保留前1位和后2位。
   ```java
   @Sensitive(strategy = SensitiveStrategy.ID_CARD)
   private String idCard;
   ```
   比如：`530321199204074611`脱敏之后为`5***************11`。

3. 固定电话：保留前4位和后2位。
   ```java
   @Sensitive(strategy = SensitiveStrategy.FIXED_PHONE)
   private String fixedPhone;
   ```
   比如：`01086551122`脱敏之后为`0108*****22`。

4. 手机号码：保留前3位，后4位。
   ```java
   @Sensitive(strategy = SensitiveStrategy.MOBILE_PHONE)
   private String mobilePhone;
   ```
   比如：`13248765917`脱敏之后为`132****5917`。

5. 地址：只显示到地区，不显示详细地址，地址长度减去8即为前缀保留的长度，后缀均用星号代替。
   ```java
   @Sensitive(strategy = SensitiveStrategy.ADDRESS)
   private String address;
   ```
   比如：`广州市天河区幸福小区102号`脱敏之后为`广州市天河区********`。

6. 电子邮箱：邮箱前缀仅显示第一个字母，前缀其他隐藏，用星号代替，@及后面的地址显示。
   ```java
   @Sensitive(strategy = SensitiveStrategy.EMAIL)
   private String email;
   ```
   比如：`example@gmail.com`脱敏之后为`e******@gmail.com`。

7. 密码：全部字符都用星号`*`代替。
   ```java
   @Sensitive(strategy = SensitiveStrategy.PASSWORD)
   private String password;
   ```
   比如：`123456`脱敏之后为`******`。

8. 车牌号：车牌中间用星号`*`代替。
   ```java
   @Sensitive(strategy = SensitiveStrategy.CAR_LICENSE)
   private String carLicense;
   ```
   比如：`粤A66666`脱敏之后为`粤A6***6`。

9. 银行卡号：保留前4位和后4位，中间的使用星号`*`代替，且中间的从第1位起，每隔4位添加一个空格用来美化。
   ```java
   @Sensitive(strategy = SensitiveStrategy.BANK_CARD)
   private String bankCard;
   ```
   例如：`9988002866797031`脱敏之后为`9988 **** **** 7031`。

10. 等等...

#### 2.2.自定义脱敏策略

##### 示例1：字符串前后都只保留1个字符。

```java
@Sensitive(strategy = SensitiveStrategy.CUSTOMIZE, preKeep = 1, postKeep = 1)
private String preKeep1PostKeep1;
```

如果值为`1234`，那么脱敏之后就变成`1**4`

#### 2.3自定义脱敏替换符

默认脱敏替换符为`*`，可以配置为任意字符。

```java
@Sensitive(strategy = SensitiveStrategy.CUSTOMIZE, preKeep = 1, postKeep = 1, replacer = '#')
private String preKeep1PostKeep1;
```

如果值为`1234`，那么脱敏之后就变成`1##4`

### 3.使用`@IgnoreSensitive`注解标注在controller上可忽略脱敏

#### 3.1.在Controller类上使用`@IgnoreSensitive`表示此类下所有接口都忽略脱敏

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

#### 3.2.在Controller的方法中使用`@IgnoreSensitive`表示此接口忽略脱敏

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

## 其它配置

### 1.支持使用注解指定Spring的`HttpMessageConverter`

*这部分是为了方便不想自己手动写代码配置`HttpMessageConverter`的，和数据脱敏逻辑无关，如果不需要更改Spring默认的Jackson，则无需配置。*

配置方式如下：

1. Jackson：`@HttpMessageConverter(JsonConverter.JACKSON)`，默认就是Jackson，可以不用配置。
2. JsonB：`@HttpMessageConverter(JsonConverter.JSONB)`
3. FastJson：`@HttpMessageConverter(JsonConverter.FASTJSON)`
4. Gson：`@HttpMessageConverter(JsonConverter.GSON)`





