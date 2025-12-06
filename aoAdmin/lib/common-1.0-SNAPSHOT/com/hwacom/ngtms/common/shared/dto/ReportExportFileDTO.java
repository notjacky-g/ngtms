package com.hwacom.ngtms.common.shared.dto;

import com.hwacom.ngtms.common.shared.ReportFormat;
import java.io.Serializable;
import java.util.Date;

public class ReportExportFileDTO implements Serializable {

  private static final long serialVersionUID = 1L;

  /** reportId */
  private String id;

  /** 類別 */
  private String category;

  /** 次類別 */
  private String subCategory;

  /** 對應的排程id */
  private Long schedulerConfigId;

  /** 名稱 */
  private String name;

  /** 預計開始時間 (for排程) */
  private Date scheduleStart;

  /** 開始時間 */
  private Date start;

  /** 結束時間 */
  private Date end;

  /** 來源，例如使用者名稱或排程名稱 */
  private String source;

  /** 檔案格式 */
  private ReportFormat format;

  /** 檔案名稱 */
  private String file;

  /** 產出報表所需的輸入參數 */
  private byte[] inputParameter;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getCategory() {
    return category;
  }

  public void setCategory(String category) {
    this.category = category;
  }

  public String getSubCategory() {
    return subCategory;
  }

  public void setSubCategory(String subCategory) {
    this.subCategory = subCategory;
  }

  public Long getSchedulerConfigId() {
    return schedulerConfigId;
  }

  public void setSchedulerConfigId(Long schedulerConfigId) {
    this.schedulerConfigId = schedulerConfigId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Date getScheduleStart() {
    return scheduleStart;
  }

  public void setScheduleStart(Date scheduleStart) {
    this.scheduleStart = scheduleStart;
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

  public ReportFormat getFormat() {
    return format;
  }

  public void setFormat(ReportFormat format) {
    this.format = format;
  }

  public String getFile() {
    return file;
  }

  public void setFile(String file) {
    this.file = file;
  }

  public byte[] getInputParameter() {
    return inputParameter;
  }

  public void setInputParameter(byte[] inputParameter) {
    this.inputParameter = inputParameter;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((id == null) ? 0 : id.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null) return false;
    if (getClass() != obj.getClass()) return false;
    ReportExportFileDTO other = (ReportExportFileDTO) obj;
    if (id == null) {
      if (other.id != null) return false;
    } else if (!id.equals(other.id)) return false;
    return true;
  }
}
