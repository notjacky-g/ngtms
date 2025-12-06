/*
 * © HwaCom Systems Inc. 2015
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.room.shared.dto;

import java.io.Serializable;

public class RoomDeviceStatusDTO implements Serializable {

  private static final long serialVersionUID = -6247372423639904061L;

  //設備編號
  private String deviceName;

  //設備種類
  private String deviceType;

  //設備狀態
  private Integer status;

  //設備狀態顯示內容
  private String statusContent;

  public String getDeviceName() {
    return deviceName;
  }

  public void setDeviceName(String deviceName) {
    this.deviceName = deviceName;
  }

  public String getDeviceType() {
    return deviceType;
  }

  public void setDeviceType(String deviceType) {
    this.deviceType = deviceType;
  }

  public Integer getStatus() {
    return status;
  }

  public void setStatus(Integer status) {
    this.status = status;
  }

  public String getStatusContent() {
    return statusContent;
  }

  public void setStatusContent(String statusContent) {
    this.statusContent = statusContent;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((deviceName == null) ? 0 : deviceName.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null) return false;
    if (getClass() != obj.getClass()) return false;
    RoomDeviceStatusDTO other = (RoomDeviceStatusDTO) obj;
    if (deviceName == null) {
      if (other.deviceName != null) return false;
    } else if (!deviceName.equals(other.deviceName)) return false;
    return true;
  }

  @Override
  public String toString() {
    return "RoomDeviceStatusConfigDTO [deviceName="
        + deviceName
        + ", deviceType="
        + deviceType
        + ", status="
        + status
        + ", statusContent="
        + statusContent
        + "]";
  }
}
