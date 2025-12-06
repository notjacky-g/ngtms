/*
 * © HwaCom Systems Inc. 2013
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.common.shared;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

/** @author devin.zeng */
public class PreviewGridData implements Serializable {

  private static final long serialVersionUID = 1L;

  private List<PreviewGridHeader> headers = Collections.emptyList();

  private List<PreviewGridRow> rows = Collections.emptyList();

  public List<PreviewGridHeader> getHeaders() {
    return headers;
  }

  public void setHeaders(List<PreviewGridHeader> headers) {
    this.headers = headers;
  }

  public List<PreviewGridRow> getRows() {
    return rows;
  }

  public void setRows(List<PreviewGridRow> rows) {
    this.rows = rows;
  }

  @Override
  public String toString() {
    return "com.hwacom.ngtms.rpt.fm.remote.PreviewGridData [headers="
        + headers
        + ", rows="
        + rows
        + "]";
  }
}
