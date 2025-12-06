/*
 * © HwaCom Systems Inc. 2015
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.c.shared.dto;

import com.google.gwt.user.client.rpc.IsSerializable;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class DeviceGroupDTO implements Serializable, IsSerializable {
  private static final long serialVersionUID = 845738522549780137L;
  private String groupId;
  /** 群組名稱（中文名稱，供USER查詢用） */
  private String groupName;

  private String groupType;
  /** 設備名稱(群組) */
  private Set<DeviceGroupDeviceConfigDTO> devices = new HashSet<>();

  public DeviceGroupDTO() {}

  public DeviceGroupDTO(String groupid) {
    this.groupId = groupid;
  }

  public String getGroupId() {
    return groupId;
  }

  public void setGroupId(String groupId) {
    this.groupId = groupId;
  }

  public String getGroupName() {
    return groupName;
  }

  public void setGroupName(String groupName) {
    this.groupName = groupName;
  }

  public String getGroupType() {
    return groupType;
  }

  public void setGroupType(String groupType) {
    this.groupType = groupType;
  }

  public Set<DeviceGroupDeviceConfigDTO> getDevices() {
    return devices;
  }

  public void setDevices(Set<DeviceGroupDeviceConfigDTO> devices) {
    this.devices = devices;
  }

  /* (non-Javadoc)
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    return "DeviceGroupDTO [groupId="
        + groupId
        + ", groupName="
        + groupName
        + ", groupType="
        + groupType
        + ", devices="
        + devices
        + "]";
  }
}
