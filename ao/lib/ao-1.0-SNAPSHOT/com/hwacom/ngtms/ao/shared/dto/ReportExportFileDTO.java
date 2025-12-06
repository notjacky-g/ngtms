/*
 * © HwaCom Systems Inc. 2015
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.ao.shared.dto;

import com.google.gwt.user.client.rpc.IsSerializable;
import java.io.Serializable;
import java.util.Date;

/** @author devin.zeng */
public class ReportExportFileDTO implements Serializable, IsSerializable {

  private static final long serialVersionUID = 1L;

  private String id;

  /** 類別 */
  private String category;

  /** 次類別 */
  private String subCategory;

  /** 名稱 */
  private String name;

  /** 檔案格式 */
  private String format;

  /** 開始時間 */
  private Date start;

  /** 結束時間 */
  private Date end;

  /** 排程時間 */
  private Date scheduleStart;

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

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getFormat() {
    return format;
  }

  public void setFormat(String format) {
    this.format = format;
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

  public Date getScheduleStart() {
    return scheduleStart;
  }

  public void setScheduleStart(Date scheduleStart) {
    this.scheduleStart = scheduleStart;
  }

  @Override
  public int hashCode() {
    int hash = 0;
    hash += (id != null ? id.hashCode() : 0);
    return hash;
  }

  @Override
  public boolean equals(Object object) {
    if (!(object instanceof ReportExportFileDTO)) {
      return false;
    }
    ReportExportFileDTO other = (ReportExportFileDTO) object;
    if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "com.hwacom.ngtms.ao.shared.dto [category="
        + category
        + ", subCategory="
        + subCategory
        + ", name="
        + name
        + ", format="
        + format
        + ", start="
        + start
        + ", end="
        + end
        + "]";
  }
}
