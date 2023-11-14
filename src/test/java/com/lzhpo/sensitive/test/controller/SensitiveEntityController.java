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

package com.lzhpo.sensitive.test.controller;

import com.lzhpo.sensitive.annocation.IgnoreSensitive;
import com.lzhpo.sensitive.test.entity.NestedSensitiveEntity;
import com.lzhpo.sensitive.test.entity.SensitiveEntity;
import com.lzhpo.sensitive.test.mock.MockHelper;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lzhpo
 */
@RestController
@RequestMapping("/")
public class SensitiveEntityController {

    @GetMapping("hello")
    public String hello() {
        return "hello";
    }

    @IgnoreSensitive({"name", "email"})
    @GetMapping("sensitive")
    public ResponseEntity<SensitiveEntity> sensitive() {
        return ResponseEntity.ok(MockHelper.sensitive());
    }

    @IgnoreSensitive
    @GetMapping("none")
    public ResponseEntity<SensitiveEntity> none() {
        return ResponseEntity.ok(MockHelper.sensitive());
    }

    @GetMapping("array")
    public ResponseEntity<SensitiveEntity[]> array() {
        return ResponseEntity.ok(new SensitiveEntity[] {MockHelper.sensitive()});
    }

    @GetMapping("list")
    public ResponseEntity<List<SensitiveEntity>> list() {
        List<SensitiveEntity> sensitiveEntities = new ArrayList<>();
        sensitiveEntities.add(MockHelper.sensitive());
        return ResponseEntity.ok(sensitiveEntities);
    }

    @GetMapping("map")
    public ResponseEntity<Map<Integer, SensitiveEntity>> map() {
        Map<Integer, SensitiveEntity> sensitiveEntities = new HashMap<>();
        sensitiveEntities.put(0, MockHelper.sensitive());
        return ResponseEntity.ok(sensitiveEntities);
    }

    @GetMapping("nested")
    public ResponseEntity<NestedSensitiveEntity> nested() {
        return ResponseEntity.ok(MockHelper.nestedSensitive());
    }
}
