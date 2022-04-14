package com.lzhpo.samples.entity;

import com.lzhpo.sensitive.annocation.Sensitive;
import com.lzhpo.sensitive.enums.SensitiveStrategy;
import com.lzhpo.sensitive.serializer.JsonbSensitiveSerializer;
import javax.json.bind.annotation.JsonbTypeSerializer;
import lombok.Builder;
import lombok.Data;

/**
 * @author lzhpo
 */
@Data
@Builder
@JsonbTypeSerializer(JsonbSensitiveSerializer.class)
public class JsonbSerializerJavaBean {

  @Sensitive(strategy = SensitiveStrategy.CHINESE_NAME)
  //  @JsonbTypeSerializer(JsonbSensitiveSerializer.class)
  private String name;

  @Sensitive(strategy = SensitiveStrategy.PASSWORD)
  //  @JsonbTypeSerializer(JsonbSensitiveSerializer.class)
  private String password;

  @Sensitive(strategy = SensitiveStrategy.ID_CARD)
  //  @JsonbTypeSerializer(JsonbSensitiveSerializer.class)
  private String idCard;
}
