/*
 * © HwaCom Systems Inc. 2015
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.room.shared.dto;

import java.io.Serializable;
import java.util.List;

public class RoomDeviceLocationConfigDTO implements Serializable {

  private static final long serialVersionUID = -3647795514574175775L;

  //機房編號
  private Integer id;

  //機房名稱
  private String locationName;

  //機房位置
  private List<String> subLocation;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getLocationName() {
    return locationName;
  }

  public void setLocationName(String locationName) {
    this.locationName = locationName;
  }

  public List<String> getSubLocation() {
    return subLocation;
  }

  public void setSubLocation(List<String> subLocation) {
    this.subLocation = subLocation;
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
    RoomDeviceLocationConfigDTO other = (RoomDeviceLocationConfigDTO) obj;
    if (id == null) {
      if (other.id != null) return false;
    } else if (!id.equals(other.id)) return false;
    return true;
  }

  @Override
  public String toString() {
    return "RoomDeviceLocationConfigDTO [id="
        + id
        + ", locationName="
        + locationName
        + ", subLocation="
        + subLocation
        + "]";
  }
}
