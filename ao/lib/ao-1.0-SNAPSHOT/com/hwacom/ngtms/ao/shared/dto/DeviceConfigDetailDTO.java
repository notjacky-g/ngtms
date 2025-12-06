/*
 * © HwaCom Systems Inc. 2015
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.ao.shared.dto;

import com.google.gwt.user.client.rpc.IsSerializable;
import java.io.Serializable;

/** @author YiHsing Chen */
public class DeviceConfigDetailDTO extends DeviceConfigBaseDTO
    implements Serializable, IsSerializable {

  private static final long serialVersionUID = -7844845744901344657L;
  /** Device Name (eg. vd001) */
  private String deviceName;
  /** The Device Number on Script File */
  private String tcId;
  /** ENABLE (Y = ENABLE、N=DISABLE) 0=Y 1=N */
  private Boolean enable;
  /** Device Type (eg. VD、VI、CMS) */
  private String deviceType;

  public String getDeviceName() {
    return deviceName;
  }

  public void setDeviceName(String deviceName) {
    this.deviceName = deviceName;
  }

  public String getTcId() {
    return tcId;
  }

  public void setTcId(String tcId) {
    this.tcId = tcId;
  }

  public Boolean getEnable() {
    return enable;
  }

  public void setEnable(Boolean enable) {
    this.enable = enable;
  }

  public String getDeviceType() {
    return deviceType;
  }

  public void setDeviceType(String deviceType) {
    this.deviceType = deviceType;
  }

  @Override
  public String toString() {
    return "DeviceConfigDetailDTO [id=" + getId() + ", deviceName=" + deviceName + "]";
  }
}
