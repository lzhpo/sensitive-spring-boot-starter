package com.lzhpo.samples.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.lzhpo.sensitive.annocation.Sensitive;
import com.lzhpo.sensitive.enums.SensitiveStrategy;
import com.lzhpo.sensitive.serializer.FastJsonSensitiveSerializer;
import lombok.Builder;
import lombok.Data;

/**
 * @author lzhpo
 */
@Data
@Builder
public class FastJsonSerializerJavaBean {

  @Sensitive(strategy = SensitiveStrategy.CHINESE_NAME)
  private String name;

  @Sensitive(strategy = SensitiveStrategy.PASSWORD)
  @JSONField(serializeUsing = FastJsonSensitiveSerializer.class)
  private String password;

  @Sensitive(strategy = SensitiveStrategy.ID_CARD)
  @JSONField(serializeUsing = FastJsonSensitiveSerializer.class)
  private String idCard;
}
