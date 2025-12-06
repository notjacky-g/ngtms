/*     */ package com.hwacom.ngtms.c.fm.model;
/*     */ 
/*     */ import com.hwacom.ngtms.base.annotation.Comment;
/*     */ import java.io.Serializable;
/*     */ import javax.persistence.Column;
/*     */ import javax.persistence.Entity;
/*     */ import javax.persistence.Id;
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
/*     */ public class AlarmTypeConfig
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   @Id
/*     */   @Comment("警報型態 ")
/*     */   private String alarmType;
/*     */   @Comment("警報種類說明 ")
/*     */   @Column(nullable = false)
/*     */   private String description;
/*     */   @Comment("是否屬也是 反應計畫 事件型態 ")
/*     */   @Column(nullable = false)
/*  32 */   private Boolean rpsEvent = Boolean.valueOf(false);
/*     */ 
/*     */   
/*     */   @Comment("嚴重程度 ")
/*     */   @Column(nullable = false)
/*  37 */   private Integer severity = Integer.valueOf(0);
/*     */   
/*     */   @Comment("對應到 反應計畫產生軟體內的 邏輯名稱 （規則庫內的某個規則名稱）")
/*     */   private String rspLogicType;
/*     */ 
/*     */   
/*     */   public String getAlarmType() {
/*  44 */     return this.alarmType;
/*     */   }
/*     */   
/*     */   public void setAlarmType(String alarmType) {
/*  48 */     this.alarmType = alarmType;
/*     */   }
/*     */   
/*     */   public String getDescription() {
/*  52 */     return this.description;
/*     */   }
/*     */   
/*     */   public void setDescription(String description) {
/*  56 */     this.description = description;
/*     */   }
/*     */   
/*     */   public Boolean getRpsEvent() {
/*  60 */     return this.rpsEvent;
/*     */   }
/*     */   
/*     */   public void setRpsEvent(Boolean rpsEvent) {
/*  64 */     this.rpsEvent = rpsEvent;
/*     */   }
/*     */   
/*     */   public Integer getSeverity() {
/*  68 */     return this.severity;
/*     */   }
/*     */   
/*     */   public void setSeverity(Integer severity) {
/*  72 */     this.severity = severity;
/*     */   }
/*     */   
/*     */   public String getRspLogicType() {
/*  76 */     return this.rspLogicType;
/*     */   }
/*     */   
/*     */   public void setRspLogicType(String rspLogicType) {
/*  80 */     this.rspLogicType = rspLogicType;
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/*  85 */     int prime = 31;
/*  86 */     int result = 1;
/*  87 */     result = 31 * result + ((this.alarmType == null) ? 0 : this.alarmType.hashCode());
/*  88 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*  93 */     if (this == obj) return true; 
/*  94 */     if (obj == null) return false; 
/*  95 */     if (getClass() != obj.getClass()) return false; 
/*  96 */     AlarmTypeConfig other = (AlarmTypeConfig)obj;
/*  97 */     if (this.alarmType == null)
/*  98 */     { if (other.alarmType != null) return false;  }
/*  99 */     else if (!this.alarmType.equals(other.alarmType)) { return false; }
/* 100 */      return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 105 */     return "AlarmTypeConfig [alarmType=" + this.alarmType + ", description=" + this.description + ", rpsEvent=" + this.rpsEvent + ", severity=" + this.severity + ", rspLogicType=" + this.rspLogicType + "]";
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\lib\commonFm-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\c\fm\model\AlarmTypeConfig.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */