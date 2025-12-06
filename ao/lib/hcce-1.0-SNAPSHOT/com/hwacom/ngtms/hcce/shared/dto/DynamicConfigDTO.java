/*
 * © HwaCom Systems Inc. 2013
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.hcce.shared.dto;

import com.google.gwt.user.client.rpc.IsSerializable;
import java.io.Serializable;

public class DynamicConfigDTO implements Serializable, IsSerializable {

  private static final long serialVersionUID = -8992087537563763786L;

  public DynamicConfigDTO() {}

  public DynamicConfigDTO(String groupName, String fmeName, String name, String value) {
    this.groupName = groupName;
    this.fmeName = fmeName;
    this.name = name;
    this.value = value;
  }

  /** Cluster 群組名稱 */
  private String groupName;

  /** FME 名稱 */
  private String fmeName;

  /** 組態名稱 */
  private String name;

  /** 組態值 */
  private String value;

  public String getGroupName() {
    return groupName;
  }

  public void setGroupName(String groupName) {
    this.groupName = groupName;
  }

  public String getFmeName() {
    return fmeName;
  }

  public void setFmeName(String fmeName) {
    this.fmeName = fmeName;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }

  public String getKey() {
    return this.groupName + this.fmeName + this.name;
  }

  @Override
  public String toString() {
    return "DynamicConfigDTO [groupName="
        + groupName
        + ", fmeName="
        + fmeName
        + ", name="
        + name
        + ", value="
        + value
        + "]";
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((fmeName == null) ? 0 : fmeName.hashCode());
    result = prime * result + ((groupName == null) ? 0 : groupName.hashCode());
    result = prime * result + ((name == null) ? 0 : name.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null) return false;
    if (getClass() != obj.getClass()) return false;
    DynamicConfigDTO other = (DynamicConfigDTO) obj;
    if (fmeName == null) {
      if (other.fmeName != null) return false;
    } else if (!fmeName.equals(other.fmeName)) return false;
    if (groupName == null) {
      if (other.groupName != null) return false;
    } else if (!groupName.equals(other.groupName)) return false;
    if (name == null) {
      if (other.name != null) return false;
    } else if (!name.equals(other.name)) return false;
    return true;
  }
}
