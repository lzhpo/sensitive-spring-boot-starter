package com.lzhpo.sensitive.serializer;

import com.google.gson.JsonElement;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;

/**
 * TODO
 *
 * @author lzhpo
 */
public class GsonSensitiveSerializer implements JsonSerializer<String> {

  @Override
  public JsonElement serialize(String src, Type typeOfSrc, JsonSerializationContext context) {
    return null;
  }
}
