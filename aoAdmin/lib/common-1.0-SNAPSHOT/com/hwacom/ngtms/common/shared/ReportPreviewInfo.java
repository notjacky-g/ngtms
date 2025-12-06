/*
 * © HwaCom Systems Inc. 2013
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.common.shared;

import java.io.Serializable;
import java.util.Date;

public class ReportPreviewInfo implements Serializable {

  private static final long serialVersionUID = 3722107175738606582L;

  /** 報表預覽內容 */
  private String reportContent;

  /** 報表總頁數 */
  private int totalPage;

  /** 預覽時間 */
  private Date previewTime;

  public String getReportContent() {
    return reportContent;
  }

  public void setReportContent(String reportContent) {
    this.reportContent = reportContent;
  }

  public int getTotalPage() {
    return totalPage;
  }

  public void setTotalPage(int totalPage) {
    this.totalPage = totalPage;
  }

  public Date getPreviewTime() {
    return previewTime;
  }

  public void setPreviewTime(Date previewTime) {
    this.previewTime = previewTime;
  }
}
