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

public class FunctionPermissionDTO
    implements Serializable, Comparable<FunctionPermissionDTO>, IsSerializable {

  private static final long serialVersionUID = 1882934179062975300L;

  private String id;

  private String name;

  private String description;

  private String parentId;

  private Integer parentSequence;

  private Integer level;

  private String urlMapping;

  private Integer sequence;

  private Date updateTime;

  private Boolean enable;

  /** @return the id */
  public String getId() {
    return id;
  }

  /** @param id the id to set */
  public void setId(String id) {
    this.id = id;
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

  /** @return the parentId */
  public String getParentId() {
    return parentId;
  }

  /** @param parentId the parentId to set */
  public void setParentId(String parentId) {
    this.parentId = parentId;
  }

  public Integer getParentSequence() {
    return parentSequence;
  }

  public void setParentSequence(Integer parentSequence) {
    this.parentSequence = parentSequence;
  }

  /** @return the updateTime */
  public Date getUpdateTime() {
    return updateTime;
  }

  /** @param updateTime the updateTime to set */
  public void setUpdateTime(Date updateTime) {
    this.updateTime = updateTime;
  }

  /** @return the level */
  public Integer getLevel() {
    return level;
  }

  /** @param level the level to set */
  public void setLevel(Integer level) {
    this.level = level;
  }

  /** @return the urlMapping */
  public String getUrlMapping() {
    return urlMapping;
  }

  /** @param urlMapping the urlMapping to set */
  public void setUrlMapping(String urlMapping) {
    this.urlMapping = urlMapping;
  }

  /** @return the sequence */
  public Integer getSequence() {
    return sequence;
  }

  /** @param sequence the sequence to set */
  public void setSequence(Integer sequence) {
    this.sequence = sequence;
  }

  /** @return the enable */
  public Boolean getEnable() {
    return enable;
  }

  /** @param enable the enable to set */
  public void setEnable(Boolean enable) {
    this.enable = enable;
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
    FunctionPermissionDTO other = (FunctionPermissionDTO) obj;
    if (id == null) {
      if (other.id != null) return false;
    } else if (!id.equals(other.id)) return false;
    return true;
  }

  /* (non-Javadoc)
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    return "FunctionPermissionDTO [id="
        + id
        + ", name="
        + name
        + ", description="
        + description
        + ", parentId="
        + parentId
        + ", level="
        + level
        + ", urlMapping="
        + urlMapping
        + ", sequence="
        + sequence
        + ", updateTime="
        + updateTime
        + ", enable="
        + enable
        + "]";
  }

  @Override
  public int compareTo(FunctionPermissionDTO o) {
    int lastCmp = parentId.compareTo(o.parentId);
    return (lastCmp != 0 ? lastCmp : sequence.compareTo(o.sequence));
  }
}
