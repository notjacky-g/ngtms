/*     */ package com.hwacom.ngtms.common.fm.model;
/*     */ 
/*     */ import com.hwacom.ngtms.base.annotation.Comment;
/*     */ import com.hwacom.ngtms.common.shared.ReportFormat;
/*     */ import java.io.Serializable;
/*     */ import java.util.Date;
/*     */ import javax.persistence.Basic;
/*     */ import javax.persistence.Column;
/*     */ import javax.persistence.Entity;
/*     */ import javax.persistence.EnumType;
/*     */ import javax.persistence.Enumerated;
/*     */ import javax.persistence.FetchType;
/*     */ import javax.persistence.GeneratedValue;
/*     */ import javax.persistence.Id;
/*     */ import javax.persistence.Index;
/*     */ import javax.persistence.Lob;
/*     */ import javax.persistence.ManyToOne;
/*     */ import javax.persistence.NamedQueries;
/*     */ import javax.persistence.NamedQuery;
/*     */ import javax.persistence.PrePersist;
/*     */ import javax.persistence.Table;
/*     */ import javax.persistence.Temporal;
/*     */ import javax.persistence.TemporalType;
/*     */ import javax.persistence.Transient;
/*     */ import org.hibernate.annotations.GenericGenerator;
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
/*     */ @NamedQueries({@NamedQuery(name = "ReportExportFile.findAllCategory", query = "select distinct f.category from ReportExportFile f"), @NamedQuery(name = "ReportExportFile.findWithCategory", query = "select f from ReportExportFile f where f.category = :category"), @NamedQuery(name = "ReportExportFile.findRemoveCandidate", query = "select f from ReportExportFile f where f.end < :end")})
/*     */ @Table(indexes = {@Index(columnList = "category"), @Index(columnList = "end")})
/*     */ public class ReportExportFile
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -2465783954613848273L;
/*     */   @Id
/*     */   @GeneratedValue(generator = "system-uuid")
/*     */   @GenericGenerator(name = "system-uuid", strategy = "uuid2")
/*     */   @Column(length = 40)
/*     */   private String id;
/*     */   @Comment("類別")
/*     */   private String category;
/*     */   @Comment("次類別")
/*     */   private String subCategory;
/*     */   @Comment("對應的排程id")
/*     */   private Long schedulerConfigId;
/*     */   @Comment("名稱")
/*     */   private String name;
/*     */   @Temporal(TemporalType.TIMESTAMP)
/*     */   @Comment("預計開始時間")
/*     */   private Date scheduleStart;
/*     */   @Temporal(TemporalType.TIMESTAMP)
/*     */   @Comment("開始時間")
/*     */   private Date start;
/*     */   @Temporal(TemporalType.TIMESTAMP)
/*     */   @Comment("結束時間")
/*     */   private Date end;
/*     */   @Comment("來源，例如使用者名稱或排程名稱")
/*     */   private String source;
/*     */   @Enumerated(EnumType.STRING)
/*     */   @Comment("檔案格式")
/*     */   private ReportFormat format;
/*     */   @Comment("檔案名稱")
/*     */   private String file;
/*     */   @Transient
/*     */   private byte[] fileData;
/*     */   @ManyToOne(optional = false)
/*     */   private Report report;
/*     */   @Lob
/*     */   @Basic(fetch = FetchType.LAZY)
/*     */   @Comment("產出報表所需的輸入參數")
/*     */   private byte[] inputParameter;
/*     */   
/*     */   public int hashCode() {
/* 122 */     int hash = 0;
/* 123 */     hash += (this.id != null) ? this.id.hashCode() : 0;
/*     */     
/* 125 */     return hash;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Object object) {
/* 131 */     if (!(object instanceof ReportExportFile)) {
/* 132 */       return false;
/*     */     }
/* 134 */     ReportExportFile other = (ReportExportFile)object;
/* 135 */     if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
/* 136 */       return false;
/*     */     }
/* 138 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 143 */     return "com.hwacom.ngtms.common.fm.model.ReportExportFile[ id = " + this.id + " ]";
/*     */   }
/*     */   
/*     */   public String getId() {
/* 147 */     return this.id;
/*     */   }
/*     */   
/*     */   public void setId(String id) {
/* 151 */     this.id = id;
/*     */   }
/*     */   
/*     */   public String getCategory() {
/* 155 */     return this.category;
/*     */   }
/*     */   
/*     */   public void setCategory(String category) {
/* 159 */     this.category = category;
/*     */   }
/*     */   
/*     */   public String getName() {
/* 163 */     return this.name;
/*     */   }
/*     */   
/*     */   public void setName(String name) {
/* 167 */     this.name = name;
/*     */   }
/*     */   
/*     */   public Date getScheduleStart() {
/* 171 */     return this.scheduleStart;
/*     */   }
/*     */   
/*     */   public void setScheduleStart(Date scheduleStart) {
/* 175 */     this.scheduleStart = scheduleStart;
/*     */   }
/*     */   
/*     */   public Date getStart() {
/* 179 */     return this.start;
/*     */   }
/*     */   
/*     */   public void setStart(Date start) {
/* 183 */     this.start = start;
/*     */   }
/*     */   
/*     */   public Date getEnd() {
/* 187 */     return this.end;
/*     */   }
/*     */   
/*     */   public void setEnd(Date end) {
/* 191 */     this.end = end;
/*     */   }
/*     */   
/*     */   public String getSource() {
/* 195 */     return this.source;
/*     */   }
/*     */   
/*     */   public void setSource(String source) {
/* 199 */     this.source = source;
/*     */   }
/*     */   
/*     */   public ReportFormat getFormat() {
/* 203 */     return this.format;
/*     */   }
/*     */   
/*     */   public void setFormat(ReportFormat format) {
/* 207 */     this.format = format;
/*     */   }
/*     */   
/*     */   public byte[] getInputParameter() {
/* 211 */     return this.inputParameter;
/*     */   }
/*     */   
/*     */   public void setInputParameter(byte[] inputParameter) {
/* 215 */     this.inputParameter = inputParameter;
/*     */   }
/*     */   
/*     */   public String getFile() {
/* 219 */     return this.file;
/*     */   }
/*     */   
/*     */   public void setFile(String file) {
/* 223 */     this.file = file;
/*     */   }
/*     */   
/*     */   public byte[] getFileData() {
/* 227 */     return this.fileData;
/*     */   }
/*     */   
/*     */   public void setFileData(byte[] fileData) {
/* 231 */     this.fileData = fileData;
/*     */   }
/*     */   
/*     */   public Report getReport() {
/* 235 */     return this.report;
/*     */   }
/*     */   
/*     */   public void setReport(Report report) {
/* 239 */     this.report = report;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getSubCategory() {
/* 244 */     return this.subCategory;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setSubCategory(String subCategory) {
/* 249 */     this.subCategory = subCategory;
/*     */   }
/*     */   
/*     */   public Long getSchedulerConfigId() {
/* 253 */     return this.schedulerConfigId;
/*     */   }
/*     */   
/*     */   public void setSchedulerConfigId(Long schedulerConfigId) {
/* 257 */     this.schedulerConfigId = schedulerConfigId;
/*     */   }
/*     */   
/*     */   @PrePersist
/*     */   public void prePersist() {
/* 262 */     this.end = new Date();
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\lib\common-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\common\fm\model\ReportExportFile.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */