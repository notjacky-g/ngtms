package com.hwacom.ngtms.ao.shared.dto;

import java.io.Serializable;

public class RoomGroupDeviceStatusDTO implements Serializable {

  private static final long serialVersionUID = 8423837522867792562L;

  // 設備編號
  private String deviceName;

  // 設備顯示名稱
  private String displayName;

  // 設備種類
  private String deviceType;

  // 設備狀態
  private Integer status;

  // 設備狀態顯示內容
  private String statusContent;

  public String getDeviceName() {
    return deviceName;
  }

  public void setDeviceName(String deviceName) {
    this.deviceName = deviceName;
  }

  public String getDisplayName() {
    return displayName;
  }

  public void setDisplayName(String displayName) {
    this.displayName = displayName;
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
  public String toString() {
    return "RoomDeviceStatusDTO [deviceName="
        + deviceName
        + ", displayName="
        + displayName
        + ", deviceType="
        + deviceType
        + ", status="
        + status
        + ", statusContent="
        + statusContent
        + "]";
  }
}
