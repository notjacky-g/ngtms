/*
 * © HwaCom Systems Inc. 2015
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.room.shared.dto;

import com.hwacom.ngtms.room.shared.SignalType;
import java.io.Serializable;

public class RoomDeviceConfigDTO implements Serializable {

  private static final long serialVersionUID = -6247372423639904061L;

  /** 設備編號 */
  private String deviceName;

  /** 設備名稱 */
  private String displayName;

  /** 訊號類別 */
  private SignalType signalType;

  /** 設備種類 */
  private String category;

  /** 設備類別 */
  private String type;

  /** 設備類別名稱 */
  private String typeDiscription;

  /** 單位 */
  private String unit;

  /** 上限值 */
  private Integer upperLimit;

  /** 下限值 */
  private Integer lowerLimit;

  /** 是否發送警報 */
  private Boolean alarmCheck;

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

  public SignalType getSignalType() {
    return signalType;
  }

  public void setSignalType(SignalType signalType) {
    this.signalType = signalType;
  }

  public String getCategory() {
    return category;
  }

  public void setCategory(String category) {
    this.category = category;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getTypeDiscription() {
    return typeDiscription;
  }

  public void setTypeDiscription(String typeDiscription) {
    this.typeDiscription = typeDiscription;
  }

  public String getUnit() {
    return unit;
  }

  public void setUnit(String unit) {
    this.unit = unit;
  }

  public Integer getUpperLimit() {
    return upperLimit;
  }

  public void setUpperLimit(Integer upperLimit) {
    this.upperLimit = upperLimit;
  }

  public Integer getLowerLimit() {
    return lowerLimit;
  }

  public void setLowerLimit(Integer lowerLimit) {
    this.lowerLimit = lowerLimit;
  }

  public Boolean getAlarmCheck() {
    return alarmCheck;
  }

  public void setAlarmCheck(Boolean alarmCheck) {
    this.alarmCheck = alarmCheck;
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
    RoomDeviceConfigDTO other = (RoomDeviceConfigDTO) obj;
    if (deviceName == null) {
      if (other.deviceName != null) return false;
    } else if (!deviceName.equals(other.deviceName)) return false;
    return true;
  }

  @Override
  public String toString() {
    return "RoomDeviceConfigDTO [deviceName="
        + deviceName
        + ", displayName="
        + displayName
        + ", signalType="
        + signalType
        + ", category="
        + category
        + ", type="
        + type
        + ", unit="
        + unit
        + ", upperLimit="
        + upperLimit
        + ", lowerLimit="
        + lowerLimit
        + ", alarmCheck="
        + alarmCheck
        + "]";
  }
}
