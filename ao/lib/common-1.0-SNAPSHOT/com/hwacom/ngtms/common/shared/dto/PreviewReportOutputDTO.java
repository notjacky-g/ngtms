package com.hwacom.ngtms.common.shared.dto;

import java.io.Serializable;

public class PreviewReportOutputDTO implements Serializable {

  private static final long serialVersionUID = 1L;

  private String report;

  private int totalPage;

  public String getReport() {
    return report;
  }

  public void setReport(String report) {
    this.report = report;
  }

  public int getTotalPage() {
    return totalPage;
  }

  public void setTotalPage(int totalPage) {
    this.totalPage = totalPage;
  }
}
