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

package com.lzhpo.sensitive;

import com.lzhpo.sensitive.annocation.Sensitive;
import java.lang.reflect.Field;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/** @author lzhpo */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SensitiveWrapper {

  /** 字段 */
  private Field field;

  /** 字段值 */
  private String fieldValue;

  /** {@link Sensitive}注解信息 */
  private Sensitive sensitive;
}
