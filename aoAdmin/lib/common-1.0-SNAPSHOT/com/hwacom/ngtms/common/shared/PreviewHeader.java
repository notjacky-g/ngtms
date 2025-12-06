/*
 * © HwaCom Systems Inc. 2013
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.common.shared;

import java.io.Serializable;

/** @author bryan.lin */
public class PreviewHeader implements Serializable {

  private static final long serialVersionUID = 1L;

  private String id;

  private String header;

  private Integer width;

  @Override
  public int hashCode() {
    int hash = 0;
    hash += (id != null ? id.hashCode() : 0);
    return hash;
  }

  @Override
  public boolean equals(Object object) {
    if (!(object instanceof PreviewHeader)) {
      return false;
    }
    PreviewHeader other = (PreviewHeader) object;
    if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "com.hwacom.ngtms.rpt.fm.remote.PreviewHeader [id="
        + id
        + ", header="
        + header
        + ", width="
        + width
        + "]";
  }

  /** @return the id */
  public String getId() {
    return id;
  }

  /** @param id the id to set */
  public void setId(String id) {
    this.id = id;
  }

  /** @return the header */
  public String getHeader() {
    return header;
  }

  /** @param header the header to set */
  public void setHeader(String header) {
    this.header = header;
  }

  /** @return the width */
  public Integer getWidth() {
    return width;
  }

  /** @param width the width to set */
  public void setWidth(Integer width) {
    this.width = width;
  }
}
