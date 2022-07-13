package com.lzhpo.sensitive.support.serializer;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;

/**
 * TODO: Currently not support it.
 *
 * @author lzhpo
 * @see com.google.gson.internal.bind.TreeTypeAdapter
 */
public class GsonSensitiveSerializer implements JsonSerializer<String> {

  @Override
  public JsonElement serialize(String src, Type typeOfSrc, JsonSerializationContext context) {
    return new JsonPrimitive(src);
  }
}
