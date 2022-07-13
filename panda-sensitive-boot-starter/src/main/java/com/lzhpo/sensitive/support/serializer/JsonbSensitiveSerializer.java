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

package com.lzhpo.sensitive.support.serializer;

import javax.json.bind.serializer.JsonbSerializer;
import javax.json.bind.serializer.SerializationContext;
import javax.json.stream.JsonGenerator;
import org.eclipse.yasson.internal.Marshaller;

/**
 * TODO: Currently not support it.
 *
 * @author lzhpo
 * @see org.eclipse.yasson.internal.serializer.ObjectSerializer
 */
public class JsonbSensitiveSerializer implements JsonbSerializer<String> {

  @Override
  public void serialize(String object, JsonGenerator generator, SerializationContext ctx) {
    Marshaller marshaller = (Marshaller) ctx;
    System.out.println(marshaller);
    generator.write(object);
  }
}
