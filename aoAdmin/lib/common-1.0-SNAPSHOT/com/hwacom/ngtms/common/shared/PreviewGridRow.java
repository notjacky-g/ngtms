/*
 * © HwaCom Systems Inc. 2013
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.common.shared;

import java.io.Serializable;
import java.util.Map;

/** @author devin.zeng */
public class PreviewGridRow implements Serializable {

  private static final long serialVersionUID = 1L;

  /** Grid id */
  private String id;

  /** Key is ReportGridHeader.columnId Value is column value */
  private Map<String, Object> columnValMap;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public Map<String, Object> getColumnValMap() {
    return columnValMap;
  }

  public void setColumnValMap(Map<String, Object> columnValMap) {
    this.columnValMap = columnValMap;
  }

  @Override
  public int hashCode() {
    int hash = 0;
    hash += (id != null ? id.hashCode() : 0);
    return hash;
  }

  @Override
  public boolean equals(Object object) {
    if (!(object instanceof PreviewGridRow)) {
      return false;
    }
    PreviewGridRow other = (PreviewGridRow) object;
    if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "com.hwacom.ngtms.rpt.fm.remote.PreviewGridRow [id="
        + id
        + ", columnValMap="
        + columnValMap
        + "]";
  }
}
