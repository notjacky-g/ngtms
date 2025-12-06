/*
 * © HwaCom Systems Inc. 2015
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.c.shared.dto;

import com.hwacom.ngtms.c.shared.Direction;
import java.io.Serializable;

public class DeviceSvgPositionConfigDTO implements Serializable {
  private static final long serialVersionUID = -1291094291611035592L;

  private String id;
  /** 設備編號 */
  private String deviceName;
  /** 設備顯示名稱 */
  private String displayName;
  /** 設備類型 */
  private String deviceType;
  /** 面板種類 (CMS 需依此判斷類型) */
  private String panelCategory;
  /** 底圖名稱 */
  private String svgName;
  /** 設備位於圖片上的 x 座標 */
  private Float positionX;
  /** 設備位於圖片上的 y 座標 */
  private Float positionY;
  /** 設備的行車方向 */
  private Direction trafficDirection;
  /** 設備群組名稱 */
  private String deviceGroupName;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

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

  public String getPanelCategory() {
    return panelCategory;
  }

  public void setPanelCategory(String panelCategory) {
    this.panelCategory = panelCategory;
  }

  public String getSvgName() {
    return svgName;
  }

  public void setSvgName(String svgName) {
    this.svgName = svgName;
  }

  public Float getPositionX() {
    return positionX;
  }

  public void setPositionX(Float positionX) {
    this.positionX = positionX;
  }

  public Float getPositionY() {
    return positionY;
  }

  public void setPositionY(Float positionY) {
    this.positionY = positionY;
  }

  public Direction getTrafficDirection() {
    return trafficDirection;
  }

  public void setTrafficDirection(Direction trafficDirection) {
    this.trafficDirection = trafficDirection;
  }

  public String getDeviceGroupName() {
    return deviceGroupName;
  }

  public void setDeviceGroupName(String deviceGroupName) {
    this.deviceGroupName = deviceGroupName;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((id == null) ? 0 : id.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null) return false;
    if (getClass() != obj.getClass()) return false;
    DeviceSvgPositionConfigDTO other = (DeviceSvgPositionConfigDTO) obj;
    if (id == null) {
      if (other.id != null) return false;
    } else if (!id.equals(other.id)) return false;
    return true;
  }
}
