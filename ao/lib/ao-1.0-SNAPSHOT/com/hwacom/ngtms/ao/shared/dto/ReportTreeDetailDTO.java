/*
 * © HwaCom Systems Inc. 2015
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.ao.shared.dto;

import com.google.gwt.user.client.rpc.IsSerializable;
import java.io.Serializable;

public class ReportTreeDetailDTO extends ReportTreeBaseDTO implements Serializable, IsSerializable {
  private static final long serialVersionUID = -8396517587406447512L;
  /** 報表名稱(ID), 例:HDA_01、HDA_02... */
  private String reportId;

  /** 查詢所需的UI資料. */
  private ReportQueryWidgetDTO reportQueryWidgetDTO;

  public String getReportId() {
    return reportId;
  }

  public void setReportId(String reportId) {
    this.reportId = reportId;
  }

  public ReportQueryWidgetDTO getReportQueryWidgetDTO() {
    return reportQueryWidgetDTO;
  }

  public void setReportQueryWidgetDTO(ReportQueryWidgetDTO reportQueryWidgetDTO) {
    this.reportQueryWidgetDTO = reportQueryWidgetDTO;
  }
}
