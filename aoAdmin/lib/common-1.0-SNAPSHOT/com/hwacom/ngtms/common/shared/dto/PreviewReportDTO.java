package com.hwacom.ngtms.common.shared.dto;

import java.io.Serializable;
import java.util.Map;

public class PreviewReportDTO implements Serializable {

  private static final long serialVersionUID = 1L;

  private String reportId;

  private Map<String, Object> inputParameters;

  private int pageIndex;

  private float zoomRatio;

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

  public int getPageIndex() {
    return pageIndex;
  }

  public void setPageIndex(int pageIndex) {
    this.pageIndex = pageIndex;
  }

  public float getZoomRatio() {
    return zoomRatio;
  }

  public void setZoomRatio(float zoomRatio) {
    this.zoomRatio = zoomRatio;
  }
}
