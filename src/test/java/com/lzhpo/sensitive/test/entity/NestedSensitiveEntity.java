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

package com.lzhpo.sensitive.test.entity;

import com.lzhpo.sensitive.SensitiveStrategy;
import com.lzhpo.sensitive.annocation.Sensitive;
import java.util.List;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/** @author lzhpo */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NestedSensitiveEntity {

    @Sensitive(strategy = SensitiveStrategy.CHINESE_NAME)
    private String parentName;

    private SensitiveEntity sensitiveEntity;

    private List<SensitiveEntity> list;

    private Map<Integer, SensitiveEntity> map;

    private SensitiveEntity[] array;
}
