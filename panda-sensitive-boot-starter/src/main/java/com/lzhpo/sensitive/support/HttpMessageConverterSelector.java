/*
 * Copyright 2022 lzhpo
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

package com.lzhpo.sensitive.support;

import com.lzhpo.sensitive.annocation.HttpMessageConverter;
import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.annotation.MergedAnnotation;
import org.springframework.core.annotation.MergedAnnotations;
import org.springframework.core.type.AnnotationMetadata;

/**
 * Json sensitive configuration import selector.
 *
 * @author lzhpo
 */
public class HttpMessageConverterSelector implements ImportSelector {

  @Override
  public String[] selectImports(AnnotationMetadata importingClassMetadata) {
    MergedAnnotations annotations = importingClassMetadata.getAnnotations();
    MergedAnnotation<HttpMessageConverter> mergedAnnotation =
        annotations.get(HttpMessageConverter.class);
    if (!mergedAnnotation.isPresent()) {
      return new String[0];
    }

    HttpMessageConverterEnum converter =
        mergedAnnotation.getEnum("value", HttpMessageConverterEnum.class);
    switch (converter) {
      case GSON:
        return new String[] {GsonConverterConfiguration.class.getName()};
      case FASTJSON:
        return new String[] {FastJsonConverterConfiguration.class.getName()};
      case JSONB:
        return new String[] {JsonbConverterConfiguration.class.getName()};
      case JACKSON:
      default:
        return new String[] {JacksonConverterConfiguration.class.getName()};
    }
  }
}
