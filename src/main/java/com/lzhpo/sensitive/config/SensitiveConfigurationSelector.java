package com.lzhpo.sensitive.config;

import com.lzhpo.sensitive.annocation.EnableSensitive;
import com.lzhpo.sensitive.config.support.FastJsonSensitiveConverterConfiguration;
import com.lzhpo.sensitive.config.support.GsonSensitiveConverterConfiguration;
import com.lzhpo.sensitive.config.support.JacksonSensitiveConverterConfiguration;
import com.lzhpo.sensitive.config.support.JsonbSensitiveConverterConfiguration;
import com.lzhpo.sensitive.enums.SensitiveConverterType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.annotation.MergedAnnotation;
import org.springframework.core.annotation.MergedAnnotations;
import org.springframework.core.type.AnnotationMetadata;

/**
 * Sensitive converterType configuration selector
 *
 * @author lzhpo
 * @see EnableSensitive
 */
@Slf4j
public class SensitiveConfigurationSelector implements ImportSelector {

  @Override
  public String[] selectImports(AnnotationMetadata importingClassMetadata) {
    MergedAnnotations annotations = importingClassMetadata.getAnnotations();
    MergedAnnotation<EnableSensitive> sensitiveMergedAnnotation =
        annotations.get(EnableSensitive.class);
    SensitiveConverterType converterType =
        sensitiveMergedAnnotation.getEnum("converterType", SensitiveConverterType.class);
    switch (converterType) {
      case GSON:
        return new String[] {GsonSensitiveConverterConfiguration.class.getName()};
      case FASTJSON:
        return new String[] {FastJsonSensitiveConverterConfiguration.class.getName()};
      case JSONB:
        return new String[] {JsonbSensitiveConverterConfiguration.class.getName()};
      case JACKSON:
      default:
        return new String[] {JacksonSensitiveConverterConfiguration.class.getName()};
    }
  }
}
