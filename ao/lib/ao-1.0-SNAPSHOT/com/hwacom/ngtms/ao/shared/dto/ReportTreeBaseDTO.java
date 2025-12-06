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

public class ReportTreeBaseDTO implements Serializable, IsSerializable {
  private static final long serialVersionUID = 7888818729580733664L;
  private String id;
  private String displayName;
  private boolean roadMap;
  private List<ReportTreeBaseDTO> children = new ArrayList<ReportTreeBaseDTO>();

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

  public List<ReportTreeBaseDTO> getChildren() {
    return children;
  }

  public void setChildren(List<ReportTreeBaseDTO> children) {
    this.children = children;
  }

  /** @return the roadMap */
  public boolean isRoadMap() {
    return roadMap;
  }

  /** @param roadMap the roadMap to set */
  public void setRoadMap(boolean roadMap) {
    this.roadMap = roadMap;
  }

  @Override
  public int hashCode() {
    int hash = 0;
    hash += (id != null ? id.hashCode() : 0);
    return hash;
  }

  @Override
  public boolean equals(Object object) {
    if (!(object instanceof ReportTreeBaseDTO)) {
      return false;
    }
    ReportTreeBaseDTO other = (ReportTreeBaseDTO) object;
    if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "ReportTreeBaseDTO [id="
        + id
        + ", displayName="
        + displayName
        + ", children="
        + children
        + ", roadMap="
        + roadMap
        + "]";
  }
}
