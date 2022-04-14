package com.lzhpo.samples.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.lzhpo.sensitive.annocation.Sensitive;
import com.lzhpo.sensitive.enums.SensitiveStrategy;
import com.lzhpo.sensitive.serializer.JacksonSensitiveSerializer;
import lombok.Builder;
import lombok.Data;

/**
 * @author lzhpo
 */
@Data
@Builder
// @JsonSerialize(using = JacksonSensitiveSerializer.class)
public class JacksonSerializerJavaBean {

  @Sensitive(strategy = SensitiveStrategy.CHINESE_NAME)
  private String name;

  @Sensitive(strategy = SensitiveStrategy.PASSWORD)
  @JsonSerialize(using = JacksonSensitiveSerializer.class)
  private String password;

  @Sensitive(strategy = SensitiveStrategy.ID_CARD)
  @JsonSerialize(using = JacksonSensitiveSerializer.class)
  private String idCard;
}
