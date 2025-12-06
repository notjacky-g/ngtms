/*
 * © HwaCom Systems Inc. 2015
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.c.shared.dto;

import com.google.gwt.user.client.rpc.IsSerializable;
import java.io.Serializable;

public class DeviceGroupDeviceConfigDTO implements Serializable, IsSerializable {
  private static final long serialVersionUID = 6285331684267261650L;

  private String id;
  private String groupId;
  private String deviceName;

  public DeviceGroupDeviceConfigDTO() {}

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getGroupId() {
    return groupId;
  }

  public void setGroupId(String groupId) {
    this.groupId = groupId;
  }

  public String getDeviceName() {
    return deviceName;
  }

  public void setDeviceName(String deviceName) {
    this.deviceName = deviceName;
  }

  /* (non-Javadoc)
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    return "DeviceGroupDeviceConfigDTO [id="
        + id
        + ", groupId="
        + groupId
        + ", deviceName="
        + deviceName
        + "]";
  }
}
