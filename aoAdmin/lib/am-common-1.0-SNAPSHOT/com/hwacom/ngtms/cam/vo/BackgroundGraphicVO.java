/*
 * © HwaCom Systems Inc. 2015
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.cam.vo;

import com.hwacom.ngtms.c.shared.HasDeviceConfig;
import com.hwacom.ngtms.c.shared.dto.DeviceConfigDTO;

public class BackgroundGraphicVO implements HasDeviceConfig {

  private String id;

  private String deviceName;

  private String displayName;

  private Integer codeId;

  private String description;

  public BackgroundGraphicVO(String deviceName, String displayName) {
    this(deviceName, (Integer) null);
    this.displayName = displayName;
  }

  public BackgroundGraphicVO(String deviceName, Integer codeId) {
    if (codeId == null) {
      this.id = deviceName;
    } else {
      this.id = deviceName + "_" + codeId;
    }
    this.deviceName = deviceName;
    this.codeId = codeId;
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
    BackgroundGraphicVO other = (BackgroundGraphicVO) obj;
    if (id == null) {
      if (other.id != null) return false;
    } else if (!id.equals(other.id)) return false;
    return true;
  }

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

  public Integer getCodeId() {
    return codeId;
  }

  public void setCodeId(Integer codeId) {
    this.codeId = codeId;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  @Override
  public DeviceConfigDTO getDeviceConfig() {
    DeviceConfigDTO dto = new DeviceConfigDTO();
    dto.setDeviceName(deviceName);
    return dto;
  }

  @Override
  public void setDeviceConfig(DeviceConfigDTO deviceConfig) {}
}
