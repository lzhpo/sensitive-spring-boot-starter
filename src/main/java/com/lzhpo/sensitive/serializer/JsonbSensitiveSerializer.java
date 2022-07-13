package com.lzhpo.sensitive.serializer;

import javax.json.bind.serializer.JsonbSerializer;
import javax.json.bind.serializer.SerializationContext;
import javax.json.stream.JsonGenerator;

/**
 * TODO
 *
 * @author lzhpo
 * @see org.eclipse.yasson.internal.serializer.ObjectSerializer
 */
public class JsonbSensitiveSerializer implements JsonbSerializer<String> {

  @Override
  public void serialize(String object, JsonGenerator generator, SerializationContext ctx) {
//    Marshaller marshaller = (Marshaller) ctx;
//    System.out.println(marshaller);
    generator.write(object);
  }
}
