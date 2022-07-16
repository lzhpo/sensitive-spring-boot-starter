package com.lzhpo.sensitive;

/**
 * @author lzhpo
 */
public class FaceCustomizeSensitiveHandler implements CustomizeSensitiveHandler {

  @Override
  public String customize(SensitiveWrapper sensitiveWrapper) {
    return "@#@";
  }
}
