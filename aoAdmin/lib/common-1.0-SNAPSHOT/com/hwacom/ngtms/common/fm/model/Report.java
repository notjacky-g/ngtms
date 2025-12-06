/*     */ package com.hwacom.ngtms.common.fm.model;
/*     */ 
/*     */ import com.hwacom.ngtms.base.annotation.Comment;
/*     */ import java.io.Serializable;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import javax.persistence.Basic;
/*     */ import javax.persistence.Column;
/*     */ import javax.persistence.ElementCollection;
/*     */ import javax.persistence.Entity;
/*     */ import javax.persistence.FetchType;
/*     */ import javax.persistence.GeneratedValue;
/*     */ import javax.persistence.Id;
/*     */ import javax.persistence.Index;
/*     */ import javax.persistence.Lob;
/*     */ import javax.persistence.MapKeyColumn;
/*     */ import javax.persistence.NamedQueries;
/*     */ import javax.persistence.NamedQuery;
/*     */ import javax.persistence.Table;
/*     */ import javax.persistence.UniqueConstraint;
/*     */ import org.hibernate.annotations.GenericGenerator;
/*     */ import org.hibernate.annotations.Type;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Entity
/*     */ @Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"category", "sub_category", "name"})}, indexes = {@Index(columnList = "name"), @Index(columnList = "module"), @Index(columnList = "module,category"), @Index(columnList = "module,category,sub_category")})
/*     */ @NamedQueries({@NamedQuery(name = "Report.findAllCategory", query = "select distinct r.category from Report r"), @NamedQuery(name = "Report.findWithCategory", query = "select r from Report r where r.category = :category")})
/*     */ public class Report
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 3209231877093317895L;
/*     */   @Id
/*     */   @GeneratedValue(generator = "system-uuid")
/*     */   @GenericGenerator(name = "system-uuid", strategy = "uuid2")
/*     */   @Column(length = 40)
/*     */   private String id;
/*     */   @Comment("模組")
/*     */   private String module;
/*     */   @Comment("報表大類別")
/*     */   private String category;
/*     */   @Comment("報表次類別")
/*     */   private String subCategory;
/*     */   @Comment("報表名稱")
/*     */   private String name;
/*     */   @Type(type = "yes_no")
/*     */   @Column(length = 1)
/*     */   @Comment("是否為路網圖報表")
/*     */   private Boolean roadMap;
/*     */   @Lob
/*     */   @Column(length = 10485760)
/*     */   @Basic(fetch = FetchType.LAZY)
/*     */   @Comment("報表設計主檔內容")
/*     */   private String masterReport;
/*     */   @Lob
/*     */   @Column(length = 10485760)
/*     */   @Basic(fetch = FetchType.LAZY)
/*     */   @Comment("報表設計副檔1內容")
/*     */   private byte[] slaveReport1;
/*     */   @Lob
/*     */   @Column(length = 10485760)
/*     */   @Basic(fetch = FetchType.LAZY)
/*     */   @Comment("報表設計副檔2內容")
/*     */   private byte[] slaveReport2;
/*     */   @Comment("報表設計子檔名稱")
/*     */   private String subReport1Name;
/*     */   @Lob
/*     */   @Column(length = 10485760)
/*     */   @Basic(fetch = FetchType.LAZY)
/*     */   @Comment("報表設計子檔內容")
/*     */   private String subReport1;
/*     */   @Comment("報表設計子檔名稱")
/*     */   private String subReport2Name;
/*     */   @Lob
/*     */   @Column(length = 10485760)
/*     */   @Basic(fetch = FetchType.LAZY)
/*     */   @Comment("報表設計子檔內容")
/*     */   private String subReport2;
/*     */   @Comment("報表設計子檔名稱")
/*     */   private String subReport3Name;
/*     */   @Lob
/*     */   @Column(length = 10485760)
/*     */   @Basic(fetch = FetchType.LAZY)
/*     */   @Comment("報表設計子檔內容")
/*     */   private String subReport3;
/*     */   @Comment("報表設計子檔名稱")
/*     */   private String subReport4Name;
/*     */   @Lob
/*     */   @Column(length = 10485760)
/*     */   @Basic(fetch = FetchType.LAZY)
/*     */   @Comment("報表設計子檔內容")
/*     */   private String subReport4;
/*     */   @Comment("報表設計子檔名稱")
/*     */   private String subReport5Name;
/*     */   @Lob
/*     */   @Column(length = 10485760)
/*     */   @Basic(fetch = FetchType.LAZY)
/*     */   @Comment("報表設計子檔內容")
/*     */   private String subReport5;
/*     */   @Comment("報表設計子檔名稱")
/*     */   private String subReport6Name;
/*     */   @Lob
/*     */   @Column(length = 10485760)
/*     */   @Basic(fetch = FetchType.LAZY)
/*     */   @Comment("報表設計子檔內容")
/*     */   private String subReport6;
/*     */   @Comment("報表設計子檔名稱")
/*     */   private String subReport7Name;
/*     */   @Lob
/*     */   @Column(length = 10485760)
/*     */   @Basic(fetch = FetchType.LAZY)
/*     */   @Comment("報表設計子檔內容")
/*     */   private String subReport7;
/*     */   @Comment("報表設計子檔名稱")
/*     */   private String subReport8Name;
/*     */   @Lob
/*     */   @Column(length = 10485760)
/*     */   @Basic(fetch = FetchType.LAZY)
/*     */   @Comment("報表設計子檔內容")
/*     */   private String subReport8;
/*     */   @Comment("報表設計子檔名稱")
/*     */   private String subReport9Name;
/*     */   @Lob
/*     */   @Column(length = 10485760)
/*     */   @Basic(fetch = FetchType.LAZY)
/*     */   @Comment("報表設計子檔內容")
/*     */   private String subReport9;
/*     */   @Comment("報表設計子檔名稱")
/*     */   private String subReport10Name;
/*     */   @Lob
/*     */   @Column(length = 10485760)
/*     */   @Basic(fetch = FetchType.LAZY)
/*     */   @Comment("報表設計子檔內容")
/*     */   private String subReport10;
/*     */   @Comment("RIP Viewer 的類別名稱")
/*     */   private String ripViewerClass;
/*     */   @Comment("依 RIP Viewer 的輸入參數進行查詢的邏輯類別名稱")
/*     */   private String reportInquiryClass;
/*     */   @Comment("0=Y 1=N")
/*     */   private Boolean enable;
/*     */   @MapKeyColumn(name = "CHART_KEY")
/*     */   @ElementCollection(fetch = FetchType.EAGER)
/*     */   @Comment("Chart Key and Chart Name")
/* 227 */   private Map<String, String> charts = new HashMap<>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 234 */     int hash = 0;
/* 235 */     hash += (this.id != null) ? this.id.hashCode() : 0;
/*     */     
/* 237 */     return hash;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Object object) {
/* 243 */     if (!(object instanceof Report)) {
/* 244 */       return false;
/*     */     }
/* 246 */     Report other = (Report)object;
/* 247 */     if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
/* 248 */       return false;
/*     */     }
/* 250 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 255 */     return "com.hwacom.ngtms.common.fm.model.Report[ id = " + this.id + " ]";
/*     */   }
/*     */   
/*     */   public String getId() {
/* 259 */     return this.id;
/*     */   }
/*     */   
/*     */   public void setId(String id) {
/* 263 */     this.id = id;
/*     */   }
/*     */   
/*     */   public String getCategory() {
/* 267 */     return this.category;
/*     */   }
/*     */   
/*     */   public void setCategory(String category) {
/* 271 */     this.category = category;
/*     */   }
/*     */   
/*     */   public String getName() {
/* 275 */     return this.name;
/*     */   }
/*     */   
/*     */   public void setName(String name) {
/* 279 */     this.name = name;
/*     */   }
/*     */   
/*     */   public String getMasterReport() {
/* 283 */     return this.masterReport;
/*     */   }
/*     */   
/*     */   public void setMasterReport(String masterReport) {
/* 287 */     this.masterReport = masterReport;
/*     */   }
/*     */   
/*     */   public byte[] getSlaveReport1() {
/* 291 */     return this.slaveReport1;
/*     */   }
/*     */   
/*     */   public void setSlaveReport1(byte[] slaveReport1) {
/* 295 */     this.slaveReport1 = slaveReport1;
/*     */   }
/*     */   
/*     */   public byte[] getSlaveReport2() {
/* 299 */     return this.slaveReport2;
/*     */   }
/*     */   
/*     */   public void setSlaveReport2(byte[] slaveReport2) {
/* 303 */     this.slaveReport2 = slaveReport2;
/*     */   }
/*     */   
/*     */   public String getSubReport1Name() {
/* 307 */     return this.subReport1Name;
/*     */   }
/*     */   
/*     */   public void setSubReport1Name(String subReport1Name) {
/* 311 */     this.subReport1Name = subReport1Name;
/*     */   }
/*     */   
/*     */   public String getSubReport1() {
/* 315 */     return this.subReport1;
/*     */   }
/*     */   
/*     */   public void setSubReport1(String subReport1) {
/* 319 */     this.subReport1 = subReport1;
/*     */   }
/*     */   
/*     */   public String getSubReport2Name() {
/* 323 */     return this.subReport2Name;
/*     */   }
/*     */   
/*     */   public void setSubReport2Name(String subReport2Name) {
/* 327 */     this.subReport2Name = subReport2Name;
/*     */   }
/*     */   
/*     */   public String getSubReport2() {
/* 331 */     return this.subReport2;
/*     */   }
/*     */   
/*     */   public void setSubReport2(String subReport2) {
/* 335 */     this.subReport2 = subReport2;
/*     */   }
/*     */   
/*     */   public String getSubReport3Name() {
/* 339 */     return this.subReport3Name;
/*     */   }
/*     */   
/*     */   public void setSubReport3Name(String subReport3Name) {
/* 343 */     this.subReport3Name = subReport3Name;
/*     */   }
/*     */   
/*     */   public String getSubReport3() {
/* 347 */     return this.subReport3;
/*     */   }
/*     */   
/*     */   public void setSubReport3(String subReport3) {
/* 351 */     this.subReport3 = subReport3;
/*     */   }
/*     */   
/*     */   public String getSubReport4Name() {
/* 355 */     return this.subReport4Name;
/*     */   }
/*     */   
/*     */   public void setSubReport4Name(String subReport4Name) {
/* 359 */     this.subReport4Name = subReport4Name;
/*     */   }
/*     */   
/*     */   public String getSubReport4() {
/* 363 */     return this.subReport4;
/*     */   }
/*     */   
/*     */   public void setSubReport4(String subReport4) {
/* 367 */     this.subReport4 = subReport4;
/*     */   }
/*     */   
/*     */   public String getSubReport5Name() {
/* 371 */     return this.subReport5Name;
/*     */   }
/*     */   
/*     */   public void setSubReport5Name(String subReport5Name) {
/* 375 */     this.subReport5Name = subReport5Name;
/*     */   }
/*     */   
/*     */   public String getSubReport5() {
/* 379 */     return this.subReport5;
/*     */   }
/*     */   
/*     */   public void setSubReport5(String subReport5) {
/* 383 */     this.subReport5 = subReport5;
/*     */   }
/*     */   
/*     */   public String getSubReport6Name() {
/* 387 */     return this.subReport6Name;
/*     */   }
/*     */   
/*     */   public void setSubReport6Name(String subReport6Name) {
/* 391 */     this.subReport6Name = subReport6Name;
/*     */   }
/*     */   
/*     */   public String getSubReport6() {
/* 395 */     return this.subReport6;
/*     */   }
/*     */   
/*     */   public void setSubReport6(String subReport6) {
/* 399 */     this.subReport6 = subReport6;
/*     */   }
/*     */   
/*     */   public String getSubReport7Name() {
/* 403 */     return this.subReport7Name;
/*     */   }
/*     */   
/*     */   public void setSubReport7Name(String subReport7Name) {
/* 407 */     this.subReport7Name = subReport7Name;
/*     */   }
/*     */   
/*     */   public String getSubReport7() {
/* 411 */     return this.subReport7;
/*     */   }
/*     */   
/*     */   public void setSubReport7(String subReport7) {
/* 415 */     this.subReport7 = subReport7;
/*     */   }
/*     */   
/*     */   public String getSubReport8Name() {
/* 419 */     return this.subReport8Name;
/*     */   }
/*     */   
/*     */   public void setSubReport8Name(String subReport8Name) {
/* 423 */     this.subReport8Name = subReport8Name;
/*     */   }
/*     */   
/*     */   public String getSubReport8() {
/* 427 */     return this.subReport8;
/*     */   }
/*     */   
/*     */   public void setSubReport8(String subReport8) {
/* 431 */     this.subReport8 = subReport8;
/*     */   }
/*     */   
/*     */   public String getSubReport9Name() {
/* 435 */     return this.subReport9Name;
/*     */   }
/*     */   
/*     */   public void setSubReport9Name(String subReport9Name) {
/* 439 */     this.subReport9Name = subReport9Name;
/*     */   }
/*     */   
/*     */   public String getSubReport9() {
/* 443 */     return this.subReport9;
/*     */   }
/*     */   
/*     */   public void setSubReport9(String subReport9) {
/* 447 */     this.subReport9 = subReport9;
/*     */   }
/*     */   
/*     */   public String getSubReport10Name() {
/* 451 */     return this.subReport10Name;
/*     */   }
/*     */   
/*     */   public void setSubReport10Name(String subReport10Name) {
/* 455 */     this.subReport10Name = subReport10Name;
/*     */   }
/*     */   
/*     */   public String getSubReport10() {
/* 459 */     return this.subReport10;
/*     */   }
/*     */   
/*     */   public void setSubReport10(String subReport10) {
/* 463 */     this.subReport10 = subReport10;
/*     */   }
/*     */   
/*     */   public String getRipViewerClass() {
/* 467 */     return this.ripViewerClass;
/*     */   }
/*     */   
/*     */   public void setRipViewerClass(String ripViewerClass) {
/* 471 */     this.ripViewerClass = ripViewerClass;
/*     */   }
/*     */   
/*     */   public String getReportInquiryClass() {
/* 475 */     return this.reportInquiryClass;
/*     */   }
/*     */   
/*     */   public void setReportInquiryClass(String reportInquiryClass) {
/* 479 */     this.reportInquiryClass = reportInquiryClass;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getSubCategory() {
/* 484 */     return this.subCategory;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setSubCategory(String subCategory) {
/* 489 */     this.subCategory = subCategory;
/*     */   }
/*     */ 
/*     */   
/*     */   public Map<String, String> getCharts() {
/* 494 */     return this.charts;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setCharts(Map<String, String> charts) {
/* 499 */     this.charts = charts;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getModule() {
/* 504 */     return this.module;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setModule(String module) {
/* 509 */     this.module = module;
/*     */   }
/*     */ 
/*     */   
/*     */   public Boolean getRoadMap() {
/* 514 */     return this.roadMap;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setRoadMap(Boolean roadMap) {
/* 519 */     this.roadMap = roadMap;
/*     */   }
/*     */   
/*     */   public Boolean isEnable() {
/* 523 */     if (this.enable != null) {
/* 524 */       return this.enable;
/*     */     }
/* 526 */     return Boolean.FALSE;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setEnable(Boolean enable) {
/* 531 */     this.enable = enable;
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\lib\common-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\common\fm\model\Report.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */