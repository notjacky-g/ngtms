/*
 * © HwaCom Systems Inc. 2013
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.common.shared;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

public class ReportFile implements Serializable {

  private static final long serialVersionUID = -2716739784585934325L;

  private String id;

  /** 次類別 */
  private String subCategory;

  /** 報表名稱 */
  private String reportName;

  /** 開始時間 */
  private Date start;

  /** 結束時間 */
  private Date end;

  private String source;

  private ReportFormat reportFormat;

  private Map<String, String> inputParameter;

  @Override
  public int hashCode() {
    int hash = 0;
    hash += (id != null ? id.hashCode() : 0);
    return hash;
  }

  @Override
  public boolean equals(Object object) {
    if (!(object instanceof ReportFile)) {
      return false;
    }
    ReportFile other = (ReportFile) object;
    if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
      return false;
    }
    return true;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getReportName() {
    return reportName;
  }

  public void setReportName(String reportName) {
    this.reportName = reportName;
  }

  public Date getStart() {
    return start;
  }

  public void setStart(Date start) {
    this.start = start;
  }

  public Date getEnd() {
    return end;
  }

  public void setEnd(Date end) {
    this.end = end;
  }

  public String getSource() {
    return source;
  }

  public void setSource(String source) {
    this.source = source;
  }

  public ReportFormat getReportFormat() {
    return reportFormat;
  }

  public void setReportFormat(ReportFormat reportFormat) {
    this.reportFormat = reportFormat;
  }

  public Map<String, String> getInputParameter() {
    return inputParameter;
  }

  public void setInputParameter(Map<String, String> inputParameter) {
    this.inputParameter = inputParameter;
  }

  /** @return the subCategory */
  public String getSubCategory() {
    return subCategory;
  }

  /** @param subCategory the subCategory to set */
  public void setSubCategory(String subCategory) {
    this.subCategory = subCategory;
  }
}
