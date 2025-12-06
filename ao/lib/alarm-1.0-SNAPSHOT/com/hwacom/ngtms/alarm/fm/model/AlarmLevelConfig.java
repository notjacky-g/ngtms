/*     */ package com.hwacom.ngtms.alarm.fm.model;
/*     */ 
/*     */ import com.hwacom.ngtms.base.annotation.Comment;
/*     */ import java.io.Serializable;
/*     */ import javax.persistence.Column;
/*     */ import javax.persistence.Entity;
/*     */ import javax.persistence.Id;
/*     */ import javax.persistence.Lob;
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
/*     */ public class AlarmLevelConfig
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 4063362705632849926L;
/*     */   @Id
/*     */   @Comment("UUID")
/*     */   private String id;
/*     */   @Column(nullable=false)
/*     */   @Comment("警報類型")
/*     */   private String alarmSubType;
/*     */   @Column(nullable=false)
/*     */   @Comment("警報級別")
/*     */   private Integer alarmLevel;
/*     */   @Column(nullable=false)
/*     */   @Comment("警報閾值")
/*     */   private Double alarmThreshold;
/*     */   @Lob
/*     */   @Comment("通知對象")
/*     */   private String notifyTarget;
/*     */   @Column(nullable=false)
/*     */   @Comment("是否抑制")
/*     */   private boolean suppress;
/*     */   @Comment("客製的通知")
/*     */   private Boolean customNotification;
/*     */   @Comment("是否記錄告警時間")
/*     */   private Boolean recordTime;
/*     */   
/*     */   public String getId()
/*     */   {
/*  52 */     return this.id;
/*     */   }
/*     */   
/*     */   public void setId(String id) {
/*  56 */     this.id = id;
/*     */   }
/*     */   
/*     */   public String getAlarmSubType() {
/*  60 */     return this.alarmSubType;
/*     */   }
/*     */   
/*     */   public void setAlarmSubType(String alarmSubType) {
/*  64 */     this.alarmSubType = alarmSubType;
/*     */   }
/*     */   
/*     */   public Integer getAlarmLevel() {
/*  68 */     return this.alarmLevel;
/*     */   }
/*     */   
/*     */   public void setAlarmLevel(Integer alarmLevel) {
/*  72 */     this.alarmLevel = alarmLevel;
/*     */   }
/*     */   
/*     */   public Double getAlarmThreshold() {
/*  76 */     return this.alarmThreshold;
/*     */   }
/*     */   
/*     */   public void setAlarmThreshold(Double alarmThreshold) {
/*  80 */     this.alarmThreshold = alarmThreshold;
/*     */   }
/*     */   
/*     */   public String getNotifyTarget() {
/*  84 */     return this.notifyTarget;
/*     */   }
/*     */   
/*     */   public void setNotifyTarget(String notifyTarget) {
/*  88 */     this.notifyTarget = notifyTarget;
/*     */   }
/*     */   
/*     */   public boolean isSuppress() {
/*  92 */     return this.suppress;
/*     */   }
/*     */   
/*     */   public void setSuppress(boolean suppress) {
/*  96 */     this.suppress = suppress;
/*     */   }
/*     */   
/*     */   public Boolean getCustomNotification() {
/* 100 */     return this.customNotification;
/*     */   }
/*     */   
/*     */   public void setCustomNotification(Boolean customNotification) {
/* 104 */     this.customNotification = customNotification;
/*     */   }
/*     */   
/*     */   public Boolean getRecordTime() {
/* 108 */     return this.recordTime;
/*     */   }
/*     */   
/*     */   public void setRecordTime(Boolean recordTime) {
/* 112 */     this.recordTime = recordTime;
/*     */   }
/*     */   
/*     */   public int hashCode()
/*     */   {
/* 117 */     int prime = 31;
/* 118 */     int result = 1;
/* 119 */     result = 31 * result + (this.id == null ? 0 : this.id.hashCode());
/* 120 */     return result;
/*     */   }
/*     */   
/*     */   public boolean equals(Object obj)
/*     */   {
/* 125 */     if (this == obj) return true;
/* 126 */     if (obj == null) return false;
/* 127 */     if (getClass() != obj.getClass()) return false;
/* 128 */     AlarmLevelConfig other = (AlarmLevelConfig)obj;
/* 129 */     if (this.id == null) {
/* 130 */       if (other.id != null) return false;
/* 131 */     } else if (!this.id.equals(other.id)) return false;
/* 132 */     return true;
/*     */   }
/*     */   
/*     */   public String toString()
/*     */   {
/* 137 */     return "AlarmLevelConfig [id=" + this.id + ", alarmSubType=" + this.alarmSubType + ", alarmLevel=" + this.alarmLevel + ", alarmThreshold=" + this.alarmThreshold + ", notifyTarget=" + this.notifyTarget + ", suppress=" + this.suppress + ", recordTime=" + this.recordTime + "]";
/*     */   }
/*     */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\alarm-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\alarm\fm\model\AlarmLevelConfig.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */