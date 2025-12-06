/*
 * © HwaCom Systems Inc. 2015
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.room.shared.dto;

import java.io.Serializable;

public class RoomDeviceSubLocationConfigDTO implements Serializable {

  private static final long serialVersionUID = 1L;

  private String id;

  //機房編號
  private Integer locationNo;

  //機房名稱
  private String locationName;

  //機房位置
  private String subLocation;

  private Integer status;

  private String backgroundSvgId;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public Integer getLocationNo() {
    return locationNo;
  }

  public void setLocationNo(Integer locationNo) {
    this.locationNo = locationNo;
  }

  public String getLocationName() {
    return locationName;
  }

  public void setLocationName(String locationName) {
    this.locationName = locationName;
  }

  public String getSubLocation() {
    return subLocation;
  }

  public void setSubLocation(String subLocation) {
    this.subLocation = subLocation;
  }

  public Integer getStatus() {
    return status;
  }

  public void setStatus(Integer status) {
    this.status = status;
  }

  public String getBackgroundSvgId() {
    return backgroundSvgId;
  }

  public void setBackgroundSvgId(String backgroundSvgId) {
    this.backgroundSvgId = backgroundSvgId;
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
    RoomDeviceSubLocationConfigDTO other = (RoomDeviceSubLocationConfigDTO) obj;
    if (id == null) {
      if (other.id != null) return false;
    } else if (!id.equals(other.id)) return false;
    return true;
  }

  @Override
  public String toString() {
    return "RoomDeviceLocationConfigDTO [id="
        + id
        + ", locationNo="
        + locationNo
        + ", locationName="
        + locationName
        + ", subLocation="
        + subLocation
        + ", status="
        + status
        + ", backgroundSvgId="
        + backgroundSvgId
        + "]";
  }
}
