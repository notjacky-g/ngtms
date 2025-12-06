/*
 * © HwaCom Systems Inc. 2015
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.ao.shared.dto;

import com.google.gwt.user.client.rpc.IsSerializable;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/** @author YiHsing Chen */
public class DeviceConfigBaseDTO implements Serializable, IsSerializable {

  private static final long serialVersionUID = 3491491498418484769L;

  public enum Type {
    /** line */
    LINE,
    /** direction */
    DIRECTION,
    /** section */
    SECTION,
    /** device */
    DEVICE
  }

  /** ID(device ID or UUID) */
  private String id;

  private String lineId;

  private String direction;

  private String sectionId;

  private int sectionMileage;

  private int deviceMilepost;

  /** Displayed on UI */
  private String displayName;

  private Type type;

  /** The content of an {@link DeviceConfigBaseDTO} instance, if there exists any */
  private List<DeviceConfigBaseDTO> children = new ArrayList<DeviceConfigBaseDTO>();

  public DeviceConfigBaseDTO() {}

  public DeviceConfigBaseDTO(String id, String displayName) {
    this.id = id;
    this.displayName = displayName;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getDisplayName() {
    return displayName;
  }

  public void setDisplayName(String displayName) {
    this.displayName = displayName;
  }

  public List<DeviceConfigBaseDTO> getChildren() {
    return children;
  }

  public void setChildren(List<DeviceConfigBaseDTO> children) {
    this.children = children;
  }

  @Override
  public int hashCode() {
    int hash = 0;
    hash += (id != null ? id.hashCode() : 0);
    return hash;
  }

  @Override
  public boolean equals(Object object) {
    if (!(object instanceof DeviceConfigBaseDTO)) {
      return false;
    }
    DeviceConfigBaseDTO other = (DeviceConfigBaseDTO) object;
    if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "DeviceConfigBaseDTO [id="
        + id
        + ", displayName="
        + displayName
        + ", children="
        + children
        + "]";
  }

  /** @return the type */
  public Type getType() {
    return type;
  }

  /** @param type the type to set */
  public void setType(Type type) {
    this.type = type;
  }

  /** @return the lineId */
  public String getLineId() {
    return lineId;
  }

  /** @param lineId the lineId to set */
  public void setLineId(String lineId) {
    this.lineId = lineId;
  }

  /** @return the sectionId */
  public String getSectionId() {
    return sectionId;
  }

  /** @param sectionId the sectionId to set */
  public void setSectionId(String sectionId) {
    this.sectionId = sectionId;
  }

  /** @return the direction */
  public String getDirection() {
    return direction;
  }

  /** @param direction the direction to set */
  public void setDirection(String direction) {
    this.direction = direction;
  }

  /** @return the sectionMileage */
  public int getSectionMileage() {
    return sectionMileage;
  }

  /** @param sectionMileage the sectionMileage to set */
  public void setSectionMileage(int sectionMileage) {
    this.sectionMileage = sectionMileage;
  }

  /** @return the deviceMilepost */
  public int getDeviceMilepost() {
    return deviceMilepost;
  }

  /** @param deviceMilepost the deviceMilepost to set */
  public void setDeviceMilepost(int deviceMilepost) {
    this.deviceMilepost = deviceMilepost;
  }
}
