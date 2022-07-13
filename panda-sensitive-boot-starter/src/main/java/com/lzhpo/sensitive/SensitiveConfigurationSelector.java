package com.lzhpo.sensitive;

import com.lzhpo.sensitive.annocation.EnableSensitive;
import com.lzhpo.sensitive.enums.JsonConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.annotation.MergedAnnotation;
import org.springframework.core.annotation.MergedAnnotations;
import org.springframework.core.type.AnnotationMetadata;

/**
 * Json sensitive configuration import selector.
 *
 * @author lzhpo
 */
@RequiredArgsConstructor
public class SensitiveConfigurationSelector implements ImportSelector {

  @Override
  public String[] selectImports(AnnotationMetadata importingClassMetadata) {
    MergedAnnotations annotations = importingClassMetadata.getAnnotations();
    MergedAnnotation<EnableSensitive> mergedAnnotation = annotations.get(EnableSensitive.class);

    if (!mergedAnnotation.isPresent()) {
      return new String[0];
    }

    boolean proxy = mergedAnnotation.getBoolean("proxy");
    if (!proxy) {
      return new String[0];
    }

    JsonConverter converter = mergedAnnotation.getEnum("converter", JsonConverter.class);
    switch (converter) {
      case GSON:
        return new String[] {GsonSensitiveConfiguration.class.getName()};
      case FASTJSON:
        return new String[] {FastJsonSensitiveConfiguration.class.getName()};
      case JSONB:
        return new String[] {JsonbSensitiveConfiguration.class.getName()};
      case JACKSON:
      default:
        return new String[] {JacksonSensitiveConfiguration.class.getName()};
    }
  }
}
