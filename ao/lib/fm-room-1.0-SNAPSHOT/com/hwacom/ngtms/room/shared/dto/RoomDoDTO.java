/*
 * © HwaCom Systems Inc. 2015
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.room.shared.dto;

import java.io.Serializable;

public class RoomDoDTO implements Serializable {

  private static final long serialVersionUID = 1L;

  /** 設備編號 */
  private String deviceName;

  /** 輸入值 */
  private Boolean value;

  public String getDeviceName() {
    return deviceName;
  }

  public void setDeviceName(String deviceName) {
    this.deviceName = deviceName;
  }

  public Boolean getValue() {
    return value;
  }

  public void setValue(Boolean value) {
    this.value = value;
  }

  @Override
  public String toString() {
    return "RoomDoDTO [deviceName=" + deviceName + ", value=" + value + "]";
  }
}
