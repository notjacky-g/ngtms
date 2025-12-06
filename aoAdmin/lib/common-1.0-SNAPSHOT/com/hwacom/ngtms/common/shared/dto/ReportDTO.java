package com.hwacom.ngtms.common.shared.dto;

import com.google.gwt.user.client.rpc.IsSerializable;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class ReportDTO implements Serializable, IsSerializable {

  private static final long serialVersionUID = 1L;

  private String id;

  /** 模組 */
  private String module;

  /** 報表大類別，例如 "紀錄及統計報表"、"即時資料報表"… */
  private String category;

  /** 報表次類別，例如 "資料收集紀錄報表"、"設備狀態紀錄報表"… */
  private String subCategory;

  /** 報表名稱 */
  private String name;

  /** 是否為路網圖報表 */
  private Boolean roadMap;

  /** 報表設計主檔內容 */
  private String masterReport;

  /** RIP Viewer 的類別名稱 */
  private String ripViewerClass;

  /** 依 RIP Viewer 的輸入參數進行查詢的邏輯類別名稱 */
  private String reportInquiryClass;

  /** ENABLE（Y = ENABLE、N=DISABLE） 0=Y 1=N */
  private Boolean enable;

  /** Chart Key and Chart Name */
  private Map<String, String> charts = new HashMap<>();

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getModule() {
    return module;
  }

  public void setModule(String module) {
    this.module = module;
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

  public Boolean getRoadMap() {
    return roadMap;
  }

  public void setRoadMap(Boolean roadMap) {
    this.roadMap = roadMap;
  }

  public String getMasterReport() {
    return masterReport;
  }

  public void setMasterReport(String masterReport) {
    this.masterReport = masterReport;
  }

  public String getRipViewerClass() {
    return ripViewerClass;
  }

  public void setRipViewerClass(String ripViewerClass) {
    this.ripViewerClass = ripViewerClass;
  }

  public String getReportInquiryClass() {
    return reportInquiryClass;
  }

  public void setReportInquiryClass(String reportInquiryClass) {
    this.reportInquiryClass = reportInquiryClass;
  }

  public Boolean getEnable() {
    return enable;
  }

  public void setEnable(Boolean enable) {
    this.enable = enable;
  }

  public Map<String, String> getCharts() {
    return charts;
  }

  public void setCharts(Map<String, String> charts) {
    this.charts = charts;
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
    ReportDTO other = (ReportDTO) obj;
    if (id == null) {
      if (other.id != null) return false;
    } else if (!id.equals(other.id)) return false;
    return true;
  }

  @Override
  public String toString() {
    return "ReportDTO [id="
        + id
        + ", module="
        + module
        + ", category="
        + category
        + ", subCategory="
        + subCategory
        + ", name="
        + name
        + ", roadMap="
        + roadMap
        + ", masterReport="
        + masterReport
        + ", ripViewerClass="
        + ripViewerClass
        + ", reportInquiryClass="
        + reportInquiryClass
        + ", enable="
        + enable
        + ", charts="
        + charts
        + "]";
  }
}
