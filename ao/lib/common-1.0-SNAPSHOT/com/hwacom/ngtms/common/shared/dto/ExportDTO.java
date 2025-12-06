/*
 * © HwaCom Systems Inc. 2018
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.common.shared.dto;

import com.hwacom.ngtms.common.shared.ReportFormat;
import java.io.Serializable;
import java.util.Map;

public class ExportDTO implements Serializable {

  private static final long serialVersionUID = -7319842823823870765L;

  private String reportId;

  private Integer chartPage;

  private Map<String, Object> inputParameters;

  private ReportFormat reportFormat;

  public String getReportId() {
    return reportId;
  }

  public void setReportId(String reportId) {
    this.reportId = reportId;
  }

  public Map<String, Object> getInputParameters() {
    return inputParameters;
  }

  public void setInputParameters(Map<String, Object> inputParameters) {
    this.inputParameters = inputParameters;
  }

  public ReportFormat getReportFormat() {
    return reportFormat;
  }

  public void setReportFormat(ReportFormat reportFormat) {
    this.reportFormat = reportFormat;
  }

  public Integer getChartPage() {
    return chartPage;
  }

  public void setChartPage(Integer chartPage) {
    this.chartPage = chartPage;
  }
}
