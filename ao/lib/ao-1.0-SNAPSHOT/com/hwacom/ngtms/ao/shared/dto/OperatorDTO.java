/*
 * © HwaCom Systems Inc. 2015
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.ao.shared.dto;

import com.google.gwt.user.client.rpc.IsSerializable;
import java.io.Serializable;

/** @author YiHsing Chen */
public class OperatorDTO implements Serializable, IsSerializable {

  private static final long serialVersionUID = -3353258736073889459L;
  /** ID */
  private String id;
  /** Displayed on UI */
  private String displayName;

  public OperatorDTO() {}

  public OperatorDTO(String id, String displayName) {
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

  @Override
  public int hashCode() {
    int hash = 0;
    hash += (id != null ? id.hashCode() : 0);
    return hash;
  }

  @Override
  public boolean equals(Object object) {
    if (!(object instanceof OperatorDTO)) {
      return false;
    }
    OperatorDTO other = (OperatorDTO) object;
    if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "com.hwacom.ngtms.rpt.am.dto.OperatorDTO [id="
        + id
        + ", displayName="
        + displayName
        + "]";
  }
}
