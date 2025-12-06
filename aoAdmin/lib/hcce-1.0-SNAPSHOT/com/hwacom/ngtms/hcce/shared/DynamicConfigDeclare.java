/*
 * © HwaCom Systems Inc. 2013
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.hcce.shared;

import java.io.Serializable;

public class DynamicConfigDeclare implements Serializable {
  private static final long serialVersionUID = 1L;
  private String name;
  private String defaultValue;

  public DynamicConfigDeclare() {}

  public DynamicConfigDeclare(String name, String defaultValue) {
    this.name = name;
    this.defaultValue = defaultValue;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDefaultValue() {
    return defaultValue;
  }

  public void setDefaultValue(String defaultValue) {
    this.defaultValue = defaultValue;
  }

  @Override
  public String toString() {
    return "DynamicConfigDeclare [name=" + name + ", defaultValue=" + defaultValue + "]";
  }
}
