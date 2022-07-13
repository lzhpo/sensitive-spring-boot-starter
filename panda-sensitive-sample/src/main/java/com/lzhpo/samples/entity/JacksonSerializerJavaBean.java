package com.lzhpo.samples.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.lzhpo.sensitive.annocation.Sensitive;
import com.lzhpo.sensitive.enums.SensitiveStrategy;
import com.lzhpo.sensitive.support.serializer.JacksonSensitiveSerializer;
import lombok.Builder;
import lombok.Data;

/**
 * @author lzhpo
 */
@Data
@Builder
public class JacksonSerializerJavaBean {

  @Sensitive(strategy = SensitiveStrategy.CHINESE_NAME)
  @JsonSerialize(using = JacksonSensitiveSerializer.class)
  private String name;

  @Sensitive(strategy = SensitiveStrategy.PASSWORD)
  @JsonSerialize(using = JacksonSensitiveSerializer.class)
  private String password;

  @Sensitive(strategy = SensitiveStrategy.ID_CARD)
  @JsonSerialize(using = JacksonSensitiveSerializer.class)
  private String idCard;
}