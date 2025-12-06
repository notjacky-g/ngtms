/*
 * © HwaCom Systems Inc. 2013
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.common.shared;

import java.io.Serializable;

/** @author devin.zeng */
public class PreviewGridHeader implements Serializable {

  private static final long serialVersionUID = 1L;

  private String columnId;

  private String header;

  private Integer width;

  public String getColumnId() {
    return columnId;
  }

  public void setColumnId(String columnId) {
    this.columnId = columnId;
  }

  public String getHeader() {
    return header;
  }

  public void setHeader(String header) {
    this.header = header;
  }

  public Integer getWidth() {
    return width;
  }

  public void setWidth(Integer width) {
    this.width = width;
  }

  @Override
  public int hashCode() {
    int hash = 0;
    hash += (columnId != null ? columnId.hashCode() : 0);
    return hash;
  }

  @Override
  public boolean equals(Object object) {
    if (!(object instanceof PreviewGridHeader)) {
      return false;
    }
    PreviewGridHeader other = (PreviewGridHeader) object;
    if ((this.columnId == null && other.columnId != null)
        || (this.columnId != null && !this.columnId.equals(other.columnId))) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "com.hwacom.ngtms.rpt.fm.remote.PreviewGridHeader [columnId="
        + columnId
        + ", header="
        + header
        + ", width="
        + width
        + "]";
  }
}
