/*
 * © HwaCom Systems Inc. 2015
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.ao.shared.dto;

import com.google.gwt.user.client.rpc.IsSerializable;
import java.io.Serializable;

public class ReportLineGraphBtDTO implements Serializable, IsSerializable {
  private static final long serialVersionUID = 4415520493898551076L;
  private String lineGraphId;
  private String displayName; // 流量圖表、速度圖表

  public String getLineGraphId() {
    return lineGraphId;
  }

  public void setLineGraphId(String lineGraphId) {
    this.lineGraphId = lineGraphId;
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
    hash += (displayName != null ? displayName.hashCode() : 0);
    return hash;
  }

  @Override
  public boolean equals(Object object) {
    if (!(object instanceof ReportLineGraphBtDTO)) {
      return false;
    }
    ReportLineGraphBtDTO other = (ReportLineGraphBtDTO) object;
    if ((this.lineGraphId == null && other.lineGraphId != null)
        || (this.lineGraphId != null && !this.lineGraphId.equals(other.lineGraphId))) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "ReportLineGraphBtInfoDTO [lineGraphId="
        + lineGraphId
        + ", displayName="
        + displayName
        + "]";
  }
}
