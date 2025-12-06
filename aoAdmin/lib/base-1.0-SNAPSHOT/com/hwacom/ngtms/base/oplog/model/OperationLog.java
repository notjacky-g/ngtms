/*     */ package com.hwacom.ngtms.base.oplog.model;
/*     */ 
/*     */ import com.hwacom.ngtms.base.annotation.Comment;
/*     */ import com.hwacom.ngtms.base.oplog.shared.OperationResult;
/*     */ import java.io.Serializable;
/*     */ import java.util.Date;
/*     */ import javax.persistence.Column;
/*     */ import javax.persistence.Entity;
/*     */ import javax.persistence.EnumType;
/*     */ import javax.persistence.Enumerated;
/*     */ import javax.persistence.GeneratedValue;
/*     */ import javax.persistence.GenerationType;
/*     */ import javax.persistence.Id;
/*     */ import javax.persistence.Index;
/*     */ import javax.persistence.Table;
/*     */ import javax.persistence.Temporal;
/*     */ import javax.persistence.TemporalType;
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
/*     */ @Table(indexes = {@Index(columnList = "user_id"), @Index(columnList = "sch_id"), @Index(columnList = "operation_time"), @Index(columnList = "sub_sys_name"), @Index(columnList = "device_name")})
/*     */ public class OperationLog
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 7679716087385828505L;
/*     */   @Id
/*     */   @GeneratedValue(strategy = GenerationType.IDENTITY)
/*     */   private Long id;
/*     */   @Column(name = "user_id")
/*     */   @Comment("使用者 ID")
/*     */   private String userId;
/*     */   @Comment("操作者終端設備IP")
/*     */   private String cpeIp;
/*     */   @Column(name = "sch_id")
/*     */   @Comment("排程作業ID")
/*     */   private String schId;
/*     */   @Column(name = "sub_sys_name")
/*     */   @Comment("子系統名稱")
/*     */   private String subSysName;
/*     */   @Comment("操作項目（本欄位目前暫不使用）")
/*     */   private String operationItem;
/*     */   @Column(length = 2000, nullable = false)
/*     */   @Comment("操作說明")
/*     */   private String description;
/*     */   @Column(name = "device_name")
/*     */   @Comment("操作設備")
/*     */   private String deviceName;
/*     */   @Column(nullable = false, name = "operation_time")
/*     */   @Temporal(TemporalType.TIMESTAMP)
/*     */   @Comment("操作時間")
/*     */   private Date operationTime;
/*     */   @Column(nullable = false)
/*     */   @Enumerated(EnumType.STRING)
/*     */   @Comment("操作結果")
/*     */   private OperationResult operationResult;
/*     */   @Column(length = 1000)
/*     */   @Comment("備註")
/*     */   private String remark;
/*     */   
/*     */   public OperationLog() {}
/*     */   
/*     */   public OperationLog(String userId, String cpeIp, String schId, String subSysName, String operationItem, String description, String deviceName, Date operationTime, OperationResult operationResult, String remark) {
/* 105 */     this.userId = userId;
/* 106 */     this.cpeIp = cpeIp;
/* 107 */     this.schId = schId;
/* 108 */     this.subSysName = subSysName;
/* 109 */     this.operationItem = operationItem;
/* 110 */     this.description = description;
/* 111 */     this.deviceName = deviceName;
/* 112 */     this.operationTime = operationTime;
/* 113 */     this.operationResult = operationResult;
/* 114 */     this.remark = remark;
/*     */   }
/*     */   
/*     */   public String getUserId() {
/* 118 */     return this.userId;
/*     */   }
/*     */   
/*     */   public void setUserId(String userId) {
/* 122 */     this.userId = userId;
/*     */   }
/*     */   
/*     */   public String getCpeIp() {
/* 126 */     return this.cpeIp;
/*     */   }
/*     */   
/*     */   public void setCpeIp(String cpeIp) {
/* 130 */     this.cpeIp = cpeIp;
/*     */   }
/*     */   
/*     */   public String getSchId() {
/* 134 */     return this.schId;
/*     */   }
/*     */   
/*     */   public void setSchId(String schId) {
/* 138 */     this.schId = schId;
/*     */   }
/*     */   
/*     */   public String getSubSysName() {
/* 142 */     return this.subSysName;
/*     */   }
/*     */   
/*     */   public void setSubSysName(String subSysName) {
/* 146 */     this.subSysName = subSysName;
/*     */   }
/*     */   
/*     */   public String getOperationItem() {
/* 150 */     return this.operationItem;
/*     */   }
/*     */   
/*     */   public void setOperationItem(String operationItem) {
/* 154 */     this.operationItem = operationItem;
/*     */   }
/*     */   
/*     */   public String getDescription() {
/* 158 */     return this.description;
/*     */   }
/*     */   
/*     */   public void setDescription(String description) {
/* 162 */     this.description = description;
/* 163 */     if (this.description != null) {
/* 164 */       this.description.replace('\r', ' ').replace('\n', ' ');
/* 165 */       this.description.replaceAll("\r\n", "");
/*     */     } 
/*     */   }
/*     */   
/*     */   public String getDeviceName() {
/* 170 */     return this.deviceName;
/*     */   }
/*     */   
/*     */   public void setDeviceName(String deviceName) {
/* 174 */     this.deviceName = deviceName;
/*     */   }
/*     */   
/*     */   public Date getOperationTime() {
/* 178 */     return this.operationTime;
/*     */   }
/*     */   
/*     */   public void setOperationTime(Date operationTime) {
/* 182 */     this.operationTime = operationTime;
/*     */   }
/*     */   
/*     */   public OperationResult getOperationResult() {
/* 186 */     return this.operationResult;
/*     */   }
/*     */   
/*     */   public void setOperationResult(OperationResult operationResult) {
/* 190 */     this.operationResult = operationResult;
/*     */   }
/*     */   
/*     */   public String getRemark() {
/* 194 */     return this.remark;
/*     */   }
/*     */   
/*     */   public void setRemark(String remark) {
/* 198 */     this.remark = remark;
/* 199 */     if (this.remark != null) {
/* 200 */       this.remark.replace('\r', ' ').replace('\n', ' ');
/* 201 */       this.remark.replaceAll("\r\n", "");
/*     */     } 
/*     */   }
/*     */   
/*     */   public Long getId() {
/* 206 */     return this.id;
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 211 */     int prime = 31;
/* 212 */     int result = 1;
/* 213 */     result = 31 * result + ((this.id == null) ? 0 : this.id.hashCode());
/* 214 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/* 219 */     if (this == obj) return true; 
/* 220 */     if (obj == null) return false; 
/* 221 */     if (getClass() != obj.getClass()) return false; 
/* 222 */     OperationLog other = (OperationLog)obj;
/* 223 */     if (this.id == null)
/* 224 */     { if (other.id != null) return false;  }
/* 225 */     else if (!this.id.equals(other.id)) { return false; }
/* 226 */      return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 231 */     return "OperationLog [id=" + this.id + ", userId=" + this.userId + ", cpeIp=" + this.cpeIp + ", schId=" + this.schId + ", subSysName=" + this.subSysName + ", operationItem=" + this.operationItem + ", description=" + this.description + ", deviceName=" + this.deviceName + ", operationTime=" + this.operationTime + ", operationResult=" + this.operationResult + ", remark=" + this.remark + "]";
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\lib\base-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\base\oplog\model\OperationLog.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */