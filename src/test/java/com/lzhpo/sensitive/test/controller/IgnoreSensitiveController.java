/*
 * Copyright lzhpo
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
import com.lzhpo.sensitive.test.entity.SensitiveEntity;
import com.lzhpo.sensitive.test.mock.MockHelper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/** @author lzhpo */
@IgnoreSensitive
@RestController
@RequestMapping("/")
public class IgnoreSensitiveController {

    @GetMapping("ignore")
    public ResponseEntity<SensitiveEntity> ignore() {
        return ResponseEntity.ok(MockHelper.sensitive());
    }
}
