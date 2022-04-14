package com.lzhpo.sensitive.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Sensitive converter type
 *
 * @author lzhpo
 */
@Getter
@AllArgsConstructor
public enum SensitiveConverterType {

  /** jackson converter type */
  JACKSON("jackson"),

  /** jsonb converter type */
  JSONB("jsonb"),

  /** fastjson converter type */
  FASTJSON("fastjson"),

  /** gson converter type */
  GSON("gson");

  private final String name;
}
