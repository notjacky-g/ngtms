/*     */ package com.hwacom.ngtms.hcce.nodelog.model;
/*     */ 
/*     */ import com.google.common.base.MoreObjects;
/*     */ import com.google.common.base.MoreObjects.ToStringHelper;
/*     */ import com.hwacom.ngtms.base.annotation.Comment;
/*     */ import java.util.Date;
/*     */ import javax.persistence.Column;
/*     */ 
/*     */ @javax.persistence.Entity
/*     */ public class NodeMonitorLog implements java.io.Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 5837627194336125812L;
/*     */   @javax.persistence.Id
/*     */   private String id;
/*     */   @Comment("主系統主機狀態")
/*     */   private Boolean mainHcStatus;
/*     */   @Comment("備援系統主機狀態")
/*     */   private Boolean backupHcStatus;
/*     */   @Comment("線上資料庫狀態")
/*     */   private Boolean onlineDbStatus;
/*     */   @Comment("備援資料庫狀態")
/*     */   private Boolean backupDbStatus;
/*     */   @Comment("主系統狀態")
/*     */   private Integer mainSystemStatus;
/*     */   
/*     */   public NodeMonitorLog()
/*     */   {
/*  28 */     this.mainHcStatus = Boolean.valueOf(false);
/*     */     
/*     */ 
/*     */ 
/*  32 */     this.backupHcStatus = Boolean.valueOf(false);
/*     */     
/*     */ 
/*     */ 
/*  36 */     this.onlineDbStatus = Boolean.valueOf(false);
/*     */     
/*     */ 
/*     */ 
/*  40 */     this.backupDbStatus = Boolean.valueOf(false);
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  49 */     this.mainSystemStatus = Integer.valueOf(0);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   @Comment("備援系統狀態")
/*  57 */   private Integer backupSystemStatus = Integer.valueOf(0);
/*     */   
/*     */   @Column(nullable=false)
/*     */   @javax.persistence.Temporal(javax.persistence.TemporalType.TIMESTAMP)
/*     */   @Comment("操作時間")
/*     */   private Date updateDate;
/*     */   
/*     */   public String getId()
/*     */   {
/*  66 */     return this.id;
/*     */   }
/*     */   
/*     */   public void setId(String id) {
/*  70 */     this.id = id;
/*     */   }
/*     */   
/*     */   public Boolean getMainHCStatus() {
/*  74 */     return this.mainHcStatus;
/*     */   }
/*     */   
/*     */   public void setMainHCStatus(Boolean mainHCStatus) {
/*  78 */     this.mainHcStatus = mainHCStatus;
/*     */   }
/*     */   
/*     */   public Boolean getBackupHCStatus() {
/*  82 */     return this.backupHcStatus;
/*     */   }
/*     */   
/*     */   public void setBackupHCStatus(Boolean backupHCStatus) {
/*  86 */     this.backupHcStatus = backupHCStatus;
/*     */   }
/*     */   
/*     */   public Boolean getOnlineDBStatus() {
/*  90 */     return this.onlineDbStatus;
/*     */   }
/*     */   
/*     */   public void setOnlineDBStatus(Boolean onlineDBStatus) {
/*  94 */     this.onlineDbStatus = onlineDBStatus;
/*     */   }
/*     */   
/*     */   public Boolean getBackupDBStatus() {
/*  98 */     return this.backupDbStatus;
/*     */   }
/*     */   
/*     */   public void setBackupDBStatus(Boolean backupDBStatus) {
/* 102 */     this.backupDbStatus = backupDBStatus;
/*     */   }
/*     */   
/*     */   public Integer getMainSystemStatus() {
/* 106 */     return this.mainSystemStatus;
/*     */   }
/*     */   
/*     */   public void setMainSystemStatus(Integer mainSystemStatus) {
/* 110 */     this.mainSystemStatus = mainSystemStatus;
/*     */   }
/*     */   
/*     */   public Integer getBackupSystemStatus() {
/* 114 */     return this.backupSystemStatus;
/*     */   }
/*     */   
/*     */   public void setBackupSystemStatus(Integer backupSystemStatus) {
/* 118 */     this.backupSystemStatus = backupSystemStatus;
/*     */   }
/*     */   
/*     */   public Date getUpdateDate() {
/* 122 */     return this.updateDate;
/*     */   }
/*     */   
/*     */   public void setUpdateDate(Date updateDate) {
/* 126 */     this.updateDate = updateDate;
/*     */   }
/*     */   
/*     */   public int hashCode()
/*     */   {
/* 131 */     int prime = 31;
/* 132 */     int result = 1;
/* 133 */     result = 31 * result + (this.id == null ? 0 : this.id.hashCode());
/* 134 */     return result;
/*     */   }
/*     */   
/*     */   public boolean equals(Object obj)
/*     */   {
/* 139 */     if (this == obj) return true;
/* 140 */     if (obj == null) return false;
/* 141 */     if (getClass() != obj.getClass()) return false;
/* 142 */     NodeMonitorLog other = (NodeMonitorLog)obj;
/* 143 */     if (this.id == null) {
/* 144 */       if (other.id != null) return false;
/* 145 */     } else if (!this.id.equals(other.id)) return false;
/* 146 */     return true;
/*     */   }
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
/*     */   public String toString()
/*     */   {
/* 160 */     return MoreObjects.toStringHelper(this).add("id", this.id).add("mainHCStatus", this.mainHcStatus).add("backupHCStatus", this.backupHcStatus).add("onlineDBStatus", this.onlineDbStatus).add("backupDBStatus", this.backupDbStatus).add("mainSystemStatus", this.mainSystemStatus).add("backupSystemStatus", this.backupSystemStatus).add("updateDate", this.updateDate).toString();
/*     */   }
/*     */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\hcce-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\hcce\nodelog\model\NodeMonitorLog.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */