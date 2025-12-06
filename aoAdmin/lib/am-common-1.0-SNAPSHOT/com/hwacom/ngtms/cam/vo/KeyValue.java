/*
 * © HwaCom Systems Inc. 2015
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.cam.vo;

public class KeyValue {

  private String key;
  private String value;

  public KeyValue(String key, String value) {
    this.key = key;
    this.value = value;
  }
  /** @return the key */
  public String getKey() {
    return key;
  }
  /** @param key the key to set */
  public void setKey(String key) {
    this.key = key;
  }
  /** @return the value */
  public String getValue() {
    return value;
  }
  /** @param value the value to set */
  public void setValue(String value) {
    this.value = value;
  }
  /* (non-Javadoc)
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    return "KeyValue [key=" + key + ", value=" + value + "]";
  }
}
