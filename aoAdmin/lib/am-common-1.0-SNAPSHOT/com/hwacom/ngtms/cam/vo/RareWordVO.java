/*
 * © HwaCom Systems Inc. 2015
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.cam.vo;

import com.google.gwt.user.client.rpc.IsSerializable;
import com.hwacom.ngtms.c.shared.HasDeviceConfig;
import com.hwacom.ngtms.c.shared.dto.DeviceConfigDTO;
import java.io.Serializable;

public class RareWordVO implements Serializable, HasDeviceConfig, IsSerializable {

  private static final long serialVersionUID = 2147675286525348302L;

  private String id;

  private String deviceName;

  private String displayName;

  private String codeId;

  public RareWordVO(String deviceName, String codeId) {
    if (codeId == null) {
      this.id = deviceName;
    } else {
      this.id = deviceName + "_" + codeId;
    }
    this.deviceName = deviceName;
    this.codeId = codeId;
  }

  public RareWordVO() {}

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
    RareWordVO other = (RareWordVO) obj;
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

  public String getCodeId() {
    return codeId;
  }

  public void setCodeId(String codeId) {
    this.codeId = codeId;
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
