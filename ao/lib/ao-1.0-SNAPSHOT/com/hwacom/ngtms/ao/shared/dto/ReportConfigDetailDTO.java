/*
 * © HwaCom Systems Inc. 2015
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.ao.shared.dto;

import com.google.gwt.user.client.rpc.IsSerializable;
import java.io.Serializable;

/**
 * The detail
 *
 * @author YiHsing Chen
 */
public class ReportConfigDetailDTO extends ReportConfigBaseDTO
    implements Serializable, IsSerializable {

  private static final long serialVersionUID = -4923209852651154495L;

  /** eg. DATA01, DATA_02 */
  private String reportName;

  /** All Query Requests (targetWidget, conditions, charts, action) is defined in this class */
  private ReportQueryRequestDTO reportQueryRequestDTO;

  /** check whether get query result together with header */
  private boolean queryWithHeader;

  /** ENABLE (Y = ENABLE、N=DISABLE) 0=Y 1=N */
  private boolean enable;

  public ReportConfigDetailDTO() {}

  public String getReportName() {
    return reportName;
  }

  public void setReportName(String reportName) {
    this.reportName = reportName;
  }

  public ReportQueryRequestDTO getReportQueryRequestDTO() {
    return reportQueryRequestDTO;
  }

  public void setReportQueryRequestDTO(ReportQueryRequestDTO reportQueryRequestDTO) {
    this.reportQueryRequestDTO = reportQueryRequestDTO;
  }

  public boolean isQueryWithHeader() {
    return queryWithHeader;
  }

  public void setQueryWithHeader(boolean queryWithHeader) {
    this.queryWithHeader = queryWithHeader;
  }

  public boolean isEnable() {
    return enable;
  }

  public void setEnable(boolean enable) {
    this.enable = enable;
  }

  @Override
  public String toString() {
    return "ReportConfigDetailDTO [id="
        + getId()
        + ", reportName="
        + reportName
        + ", reportQueryRequestDTO="
        + reportQueryRequestDTO
        + "]";
  }
}
