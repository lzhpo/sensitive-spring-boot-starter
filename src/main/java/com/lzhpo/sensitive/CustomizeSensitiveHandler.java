package com.lzhpo.sensitive;

/**
 * @author lzhpo
 */
public interface CustomizeSensitiveHandler {

  /**
   * Customize the filed value
   *
   * @param sensitiveWrapper sensitive require message
   * @return after customize field value
   */
  String customize(SensitiveWrapper sensitiveWrapper);
}
