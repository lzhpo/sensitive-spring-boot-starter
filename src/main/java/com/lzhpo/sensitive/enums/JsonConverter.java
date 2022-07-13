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
public enum JsonConverter {

  /** jackson converter type */
  JACKSON,

  /** jsonb converter type */
  JSONB,

  /** fastjson converter type */
  FASTJSON,

  /** gson converter type */
  GSON
}
