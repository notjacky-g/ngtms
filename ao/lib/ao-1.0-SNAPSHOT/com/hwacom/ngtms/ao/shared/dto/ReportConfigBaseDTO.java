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

/**
 * A Model to be a base of a report tree
 *
 * @author YiHsing Chen
 */
public class ReportConfigBaseDTO implements Serializable, IsSerializable {

  private static final long serialVersionUID = -4923209852651154495L;
  /** ID */
  private String id;

  /** Displayed on UI */
  private String displayName;

  /** The content of an {@link ReportConfigBaseDTO} instance, if there exists any */
  private List<ReportConfigBaseDTO> children = new ArrayList<>();

  public ReportConfigBaseDTO() {}

  public ReportConfigBaseDTO(String id, String displayName) {
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

  public List<ReportConfigBaseDTO> getChildren() {
    return children;
  }

  public void setChildren(List<ReportConfigBaseDTO> children) {
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
    if (!(object instanceof ReportConfigBaseDTO)) {
      return false;
    }
    ReportConfigBaseDTO other = (ReportConfigBaseDTO) object;
    if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "ReportConfigBaseDTO [id="
        + id
        + ", name="
        + displayName
        + ", children="
        + children
        + "]";
  }
}
