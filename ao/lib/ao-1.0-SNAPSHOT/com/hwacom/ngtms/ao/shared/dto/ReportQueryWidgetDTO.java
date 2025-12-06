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

public class ReportQueryWidgetDTO implements Serializable, IsSerializable {
  private static final long serialVersionUID = 295837559175343782L;
  /** 查詢條件Widget path(package+class), 例:com.hwacom.ngtms.hda.am.view.QueryConditionByTime */
  private String queryConditionWidgetName;

  /** 圖表集合資料(流量圖表、速度圖表) */
  private List<ReportLineGraphBtDTO> reportLineGraphBtDTOs = new ArrayList<ReportLineGraphBtDTO>();

  public String getQueryConditionWidgetName() {
    return queryConditionWidgetName;
  }

  public void setQueryConditionWidgetName(String queryConditionWidgetName) {
    this.queryConditionWidgetName = queryConditionWidgetName;
  }

  public List<ReportLineGraphBtDTO> getReportLineGraphBtDTOs() {
    return reportLineGraphBtDTOs;
  }

  public void setReportLineGraphBtDTOs(List<ReportLineGraphBtDTO> reportLineGraphBtDTOs) {
    this.reportLineGraphBtDTOs = reportLineGraphBtDTOs;
  }

  @Override
  public int hashCode() {
    int hash = 0;
    hash += (queryConditionWidgetName != null ? queryConditionWidgetName.hashCode() : 0);
    return hash;
  }

  @Override
  public boolean equals(Object object) {
    if (!(object instanceof ReportQueryWidgetDTO)) {
      return false;
    }
    ReportQueryWidgetDTO other = (ReportQueryWidgetDTO) object;
    if ((this.queryConditionWidgetName == null && other.queryConditionWidgetName != null)
        || (this.queryConditionWidgetName != null
            && !this.queryConditionWidgetName.equals(other.queryConditionWidgetName))) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "ReportQueryInfoDTO [queryConditionWidgetName="
        + queryConditionWidgetName
        + ", reportLineGraphBtDTOs="
        + reportLineGraphBtDTOs
        + "]";
  }
}
