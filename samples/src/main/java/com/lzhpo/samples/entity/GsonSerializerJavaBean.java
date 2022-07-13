package com.lzhpo.samples.entity;

import com.google.gson.annotations.JsonAdapter;
import com.lzhpo.sensitive.annocation.Sensitive;
import com.lzhpo.sensitive.enums.SensitiveStrategy;
import com.lzhpo.sensitive.support.serializer.GsonSensitiveSerializer;
import lombok.Builder;
import lombok.Data;

/**
 * @author lzhpo
 */
@Data
@Builder
public class GsonSerializerJavaBean {

  @Sensitive(strategy = SensitiveStrategy.CHINESE_NAME)
  @JsonAdapter(GsonSensitiveSerializer.class)
  private String name;

  @Sensitive(strategy = SensitiveStrategy.PASSWORD)
  @JsonAdapter(GsonSensitiveSerializer.class)
  private String password;

  @Sensitive(strategy = SensitiveStrategy.ID_CARD)
  @JsonAdapter(GsonSensitiveSerializer.class)
  private String idCard;
}
