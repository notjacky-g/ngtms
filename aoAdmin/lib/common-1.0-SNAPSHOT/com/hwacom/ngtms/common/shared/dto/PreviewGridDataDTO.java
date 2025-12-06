/*
 * © HwaCom Systems Inc. 2013
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.common.shared.dto;

import com.google.gwt.user.client.rpc.IsSerializable;
import java.io.Serializable;
import java.util.Collections;
import java.util.List;

/** @author devin.zeng */
public class PreviewGridDataDTO implements Serializable, IsSerializable {

  private static final long serialVersionUID = 1L;

  private List<PreviewGridHeaderDTO> headers = Collections.emptyList();

  private List<PreviewGridRowDTO> rows = Collections.emptyList();

  public List<PreviewGridHeaderDTO> getHeaders() {
    return headers;
  }

  public void setHeaders(List<PreviewGridHeaderDTO> headers) {
    this.headers = headers;
  }

  public List<PreviewGridRowDTO> getRows() {
    return rows;
  }

  public void setRows(List<PreviewGridRowDTO> rows) {
    this.rows = rows;
  }

  @Override
  public String toString() {
    return "com.hwacom.ngtms.rpt.am.dto.PreviewGridDataDTO [headers="
        + headers
        + ", rows="
        + rows
        + "]";
  }
}
