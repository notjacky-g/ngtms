/*     */ package com.hwacom.ngtms.alarm.fm.model;
/*     */ 
/*     */ import com.hwacom.ngtms.alarm.fm.shared.AlarmClassification;
/*     */ import com.hwacom.ngtms.alarm.shared.AlarmState;
/*     */ import com.hwacom.ngtms.base.annotation.Comment;
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
/*     */ import javax.persistence.Lob;
/*     */ import javax.persistence.Table;
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
/*     */ @Table(indexes = {@Index(columnList = "timestamp")})
/*     */ public class AlarmLog
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 3940660122570185882L;
/*     */   @Id
/*     */   @Comment("UUID")
/*     */   @GeneratedValue(strategy = GenerationType.IDENTITY)
/*     */   private Long id;
/*     */   @Comment("警報分類")
/*     */   private AlarmClassification alarmClassification;
/*     */   @Comment("警報類別")
/*     */   @Column(nullable = false)
/*     */   private String alarmSubType;
/*     */   @Comment("警報會話編號")
/*     */   private String alarmSessionId;
/*     */   @Comment("警報發生或變化時間 ")
/*     */   @Column(nullable = false)
/*     */   private Date timestamp;
/*     */   @Comment("等級")
/*     */   private Integer degree;
/*     */   @Comment("詳細資料")
/*     */   @Lob
/*     */   private String contextData;
/*     */   @Comment("設備名稱")
/*     */   private String deviceName;
/*     */   @Comment("告警訊息")
/*     */   private String message;
/*     */   @Comment("告警狀態")
/*     */   @Column(nullable = false)
/*     */   @Enumerated(EnumType.STRING)
/*     */   private AlarmState alarmState;
/*     */   @Comment("處理狀態")
/*     */   private String note;
/*     */   @Comment("操控者")
/*     */   private String operator;
/*     */   @Comment("處理優先順序")
/*     */   private String priority;
/*     */   
/*     */   public Long getId() {
/*  97 */     return this.id;
/*     */   }
/*     */   
/*     */   public void setId(Long id) {
/* 101 */     this.id = id;
/*     */   }
/*     */   
/*     */   public AlarmClassification getAlarmClassification() {
/* 105 */     return this.alarmClassification;
/*     */   }
/*     */   
/*     */   public void setAlarmClassification(AlarmClassification alarmClassification) {
/* 109 */     this.alarmClassification = alarmClassification;
/*     */   }
/*     */   
/*     */   public String getAlarmSubType() {
/* 113 */     return this.alarmSubType;
/*     */   }
/*     */   
/*     */   public void setAlarmSubType(String alarmSubType) {
/* 117 */     this.alarmSubType = alarmSubType;
/*     */   }
/*     */   
/*     */   public String getAlarmSessionId() {
/* 121 */     return this.alarmSessionId;
/*     */   }
/*     */   
/*     */   public void setAlarmSessionId(String alarmSessionId) {
/* 125 */     this.alarmSessionId = alarmSessionId;
/*     */   }
/*     */   
/*     */   public Date getTimestamp() {
/* 129 */     return this.timestamp;
/*     */   }
/*     */   
/*     */   public void setTimestamp(Date timestamp) {
/* 133 */     this.timestamp = timestamp;
/*     */   }
/*     */   
/*     */   public Integer getDegree() {
/* 137 */     return this.degree;
/*     */   }
/*     */   
/*     */   public void setDegree(Integer degree) {
/* 141 */     this.degree = degree;
/*     */   }
/*     */   
/*     */   public String getContextData() {
/* 145 */     return this.contextData;
/*     */   }
/*     */   
/*     */   public void setContextData(String contextData) {
/* 149 */     this.contextData = contextData;
/*     */   }
/*     */   
/*     */   public String getDeviceName() {
/* 153 */     return this.deviceName;
/*     */   }
/*     */   
/*     */   public void setDeviceName(String deviceName) {
/* 157 */     this.deviceName = deviceName;
/*     */   }
/*     */   
/*     */   public String getMessage() {
/* 161 */     return this.message;
/*     */   }
/*     */   
/*     */   public void setMessage(String message) {
/* 165 */     this.message = message;
/*     */   }
/*     */   
/*     */   public AlarmState getAlarmState() {
/* 169 */     return this.alarmState;
/*     */   }
/*     */   
/*     */   public void setAlarmState(AlarmState alarmState) {
/* 173 */     this.alarmState = alarmState;
/*     */   }
/*     */   
/*     */   public String getNote() {
/* 177 */     return this.note;
/*     */   }
/*     */   
/*     */   public void setNote(String note) {
/* 181 */     this.note = note;
/*     */   }
/*     */   
/*     */   public String getOperator() {
/* 185 */     return this.operator;
/*     */   }
/*     */   
/*     */   public void setOperator(String operator) {
/* 189 */     this.operator = operator;
/*     */   }
/*     */   
/*     */   public String getPriority() {
/* 193 */     return this.priority;
/*     */   }
/*     */   
/*     */   public void setPriority(String priority) {
/* 197 */     this.priority = priority;
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 202 */     int prime = 31;
/* 203 */     int result = 1;
/* 204 */     result = 31 * result + ((this.id == null) ? 0 : this.id.hashCode());
/* 205 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/* 210 */     if (this == obj) return true; 
/* 211 */     if (obj == null) return false; 
/* 212 */     if (getClass() != obj.getClass()) return false; 
/* 213 */     AlarmLog other = (AlarmLog)obj;
/* 214 */     if (this.id == null)
/* 215 */     { if (other.id != null) return false;  }
/* 216 */     else if (!this.id.equals(other.id)) { return false; }
/* 217 */      return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 222 */     return "AlarmLog [id=" + this.id + ", alarmClassification=" + this.alarmClassification + ", alarmSubType=" + this.alarmSubType + ", alarmSessionId=" + this.alarmSessionId + ", timestamp=" + this.timestamp + ", degree=" + this.degree + ", contextData=" + this.contextData + ", deviceName=" + this.deviceName + ", message=" + this.message + ", alarmState=" + this.alarmState + "]";
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\lib\alarm-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\alarm\fm\model\AlarmLog.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */