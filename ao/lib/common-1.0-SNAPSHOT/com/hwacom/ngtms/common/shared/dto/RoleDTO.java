/*
 * © HwaCom Systems Inc. 2013
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.common.shared.dto;

import com.google.gwt.user.client.rpc.IsSerializable;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class RoleDTO implements Serializable, IsSerializable {

  private static final long serialVersionUID = 6912819924925114760L;

  private String name;

  private String description;

  private Set<FunctionPermissionDTO> functionPermissions = new HashSet<FunctionPermissionDTO>();

  private Date updateTime;

  private Boolean enable;

  public RoleDTO() {}

  @Override
  public int hashCode() {
    int hash = 0;
    hash += (name != null ? name.hashCode() : 0);
    return hash;
  }

  @Override
  public boolean equals(Object object) {
    if (!(object instanceof RoleDTO)) {
      return false;
    }
    RoleDTO other = (RoleDTO) object;
    if ((this.name == null && other.name != null)
        || (this.name != null && !this.name.equals(other.name))) {
      return false;
    }
    return true;
  }

  /** @return the name */
  public String getName() {
    return name;
  }

  /** @param name the name to set */
  public void setName(String name) {
    this.name = name;
  }

  /** @return the description */
  public String getDescription() {
    return description;
  }

  /** @param description the description to set */
  public void setDescription(String description) {
    this.description = description;
  }

  /** @return the updateTime */
  public Date getUpdateTime() {
    return updateTime;
  }

  /** @param updateTime the updateTime to set */
  public void setUpdateTime(Date updateTime) {
    this.updateTime = updateTime;
  }

  /** @return the functionPermissions */
  public Set<FunctionPermissionDTO> getFunctionPermissions() {
    return functionPermissions;
  }

  /** @param functionPermissions the functionPermissions to set */
  public void setFunctionPermissions(Set<FunctionPermissionDTO> functionPermissions) {
    this.functionPermissions = functionPermissions;
  }

  public void addFunctionPermission(FunctionPermissionDTO functionPermission) {
    this.functionPermissions.add(functionPermission);
  }

  public Boolean getEnable() {
    return enable;
  }

  public void setEnable(Boolean enable) {
    this.enable = enable;
  }

  @Override
  public String toString() {
    return "RoleDTO [name="
        + name
        + ", description="
        + description
        + ", functionPermissions="
        + functionPermissions
            .stream()
            .map(FunctionPermissionDTO::getId)
            .collect(Collectors.toList())
        + ", updateTime="
        + updateTime
        + ", enable="
        + enable
        + "]";
  }
}
