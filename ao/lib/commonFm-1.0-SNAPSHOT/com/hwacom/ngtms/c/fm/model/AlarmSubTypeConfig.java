/*     */ package com.hwacom.ngtms.c.fm.model;
/*     */ 
/*     */ import com.hwacom.ngtms.base.annotation.Comment;
/*     */ import com.hwacom.ngtms.c.shared.EventExeMode;
/*     */ import com.hwacom.ngtms.c.shared.EventLogMode;
/*     */ import java.io.Serializable;
/*     */ import javax.persistence.Column;
/*     */ import javax.persistence.Entity;
/*     */ import javax.persistence.EnumType;
/*     */ import javax.persistence.Enumerated;
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
/*     */ public class AlarmSubTypeConfig
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   @Id
/*     */   @Comment("id")
/*     */   private Integer id;
/*     */   @Comment("警報次類別英文名稱")
/*     */   private String alarmSubTypeName;
/*     */   @Comment("警報次類別說明")
/*     */   private String description;
/*     */   @Comment("警報次類別簡稱")
/*     */   @Column(nullable=false)
/*     */   private String shortName;
/*     */   @Comment("所屬警報型態")
/*     */   private String alarmType;
/*     */   @Comment("警報圖示檔案名稱 ")
/*     */   private String icon;
/*     */   @Comment("警報聲音檔案名稱 ")
/*     */   private String voice;
/*     */   @Comment("警報前景顏色 RRGGBB.")
/*  54 */   private String alarmFcolor = "000000";
/*     */   
/*     */ 
/*     */   @Comment("警報前景顏色 RRGGBB.")
/*  58 */   private String alarmBcolor = "FFFFFF";
/*     */   
/*     */ 
/*     */ 
/*     */   @Comment("警報嚴重程度")
/*  63 */   private Integer severity = Integer.valueOf(0);
/*     */   
/*     */ 
/*     */   @Comment("是否顯示警報")
/*  67 */   private Boolean displayEnabled = Boolean.valueOf(true);
/*     */   
/*     */ 
/*     */   @Comment("是否閃爍警報")
/*  71 */   private Boolean flashEnabled = Boolean.valueOf(false);
/*     */   
/*     */ 
/*     */   @Comment("反應計畫事件登錄模式，只有在 rspEvent = true 才有效 ")
/*     */   @Enumerated(EnumType.STRING)
/*     */   private EventLogMode eventLogMode;
/*     */   
/*     */ 
/*     */   @Comment("反應計畫 事件反應內容執行模式，只有在 isEvent = true 才有效 ")
/*     */   @Enumerated(EnumType.STRING)
/*     */   private EventExeMode eventExeMode;
/*     */   
/*     */ 
/*     */   @Comment("是否允許手動登錄反應計畫事件")
/*  85 */   private Boolean manualEnabled = Boolean.valueOf(false);
/*     */   
/*     */ 
/*     */   @Comment("是否啟用反應計畫事件鎖定 (CCTV)")
/*  89 */   private Boolean eventLockEnabled = Boolean.valueOf(false);
/*     */   
/*     */ 
/*     */   @Comment("事件鎖定預設時間(分)")
/*     */   private Integer eventLockTime;
/*     */   
/*     */   @Comment("事件前景顏色 RRGGBB.")
/*  96 */   private String eventFcolor = "000000";
/*     */   
/*     */ 
/*     */   @Comment("事件前景顏色 RRGGBB.")
/* 100 */   private String eventBcolor = "FFFFFF";
/*     */   
/*     */ 
/*     */ 
/*     */   @Comment("事故圖示編碼(0代表不使用)")
/* 105 */   private Integer incidentIconId = Integer.valueOf(0);
/*     */   
/*     */ 
/*     */   @Comment("事故背景圖編碼(0代表不使用)")
/* 109 */   private Integer incidentBkgId = Integer.valueOf(0);
/*     */   
/*     */ 
/*     */   @Comment("是否需要提醒事件尚未結束，只應用於手動輸入事件情況")
/* 113 */   private Boolean remindEnabled = Boolean.valueOf(false);
/*     */   
/*     */ 
/*     */   @Comment("首次提醒時間，單位: 分鐘 ")
/*     */   private Integer firstRemindTime;
/*     */   
/*     */ 
/*     */   @Comment("第二次提醒時間，單位: 分鐘 ")
/*     */   private Integer secondRemindTime;
/*     */   
/*     */ 
/*     */   @Comment("第三次及其之後間隔提醒時間，單位: 分鐘")
/*     */   private Integer thirdRemindTime;
/*     */   
/*     */ 
/*     */   @Comment("是否加入跑馬燈顯示(前提必需是反應計畫事件型態才能設定) ")
/*     */   private Boolean isMarquee;
/*     */   
/*     */ 
/*     */   @Comment("嚴重程式(跑馬燈)(前提必需是反應計畫事件型態才能設定) ")
/*     */   private Integer marqueeSeverity;
/*     */   
/*     */ 
/*     */   @Comment("是否要寄 Mail")
/* 137 */   private Boolean sendMail = Boolean.valueOf(false);
/*     */   
/*     */ 
/*     */   @Comment("寄 Mail 的角色(用 , 隔開)")
/*     */   private String mailRoles;
/*     */   
/*     */ 
/*     */   @Comment("寄 Mail 的使用者(用 , 隔開)")
/*     */   private String mailUsers;
/*     */   
/*     */ 
/*     */   @Comment("寄 Mail 的其他使用者(用 , 隔開)")
/*     */   private String mailOtherUsers;
/*     */   
/*     */ 
/*     */   @Comment("是否要寄簡訊")
/* 153 */   private Boolean sendSms = Boolean.valueOf(false);
/*     */   
/*     */ 
/*     */   @Comment("寄簡訊的角色(用 , 隔開)")
/*     */   private String smsRoles;
/*     */   
/*     */ 
/*     */   @Comment("寄簡訊的使用者(用 , 隔開)")
/*     */   private String smsUsers;
/*     */   
/*     */ 
/*     */   @Comment("寄簡訊的其他使用者(用 , 隔開)")
/*     */   private String smsOtherUsers;
/*     */   
/*     */ 
/*     */   @Comment("是否加入瀏覽器跑馬燈顯示")
/*     */   private Boolean isBrowserMarquee;
/*     */   
/*     */   @Comment("發出告警時額外的告警訊息, 如\"斷線\", 預設為空字串")
/* 172 */   private String alarmMessage = "";
/*     */   
/*     */ 
/*     */   @Comment("告警恢復正常時(degree = 0)，是否需要發 mail 或簡訊 ")
/* 176 */   private Boolean backNormalAlarm = Boolean.TRUE;
/*     */   
/*     */   public Integer getId()
/*     */   {
/* 180 */     return this.id;
/*     */   }
/*     */   
/*     */   public void setId(Integer id) {
/* 184 */     this.id = id;
/*     */   }
/*     */   
/*     */   public String getAlarmSubTypeName() {
/* 188 */     return this.alarmSubTypeName;
/*     */   }
/*     */   
/*     */   public void setAlarmSubTypeName(String alarmSubTypeName) {
/* 192 */     this.alarmSubTypeName = alarmSubTypeName;
/*     */   }
/*     */   
/*     */   public String getDescription() {
/* 196 */     return this.description;
/*     */   }
/*     */   
/*     */   public void setDescription(String description) {
/* 200 */     this.description = description;
/*     */   }
/*     */   
/*     */   public String getShortName() {
/* 204 */     return this.shortName;
/*     */   }
/*     */   
/*     */   public void setShortName(String shortName) {
/* 208 */     this.shortName = shortName;
/*     */   }
/*     */   
/*     */   public String getAlarmType() {
/* 212 */     return this.alarmType;
/*     */   }
/*     */   
/*     */   public void setAlarmType(String alarmType) {
/* 216 */     this.alarmType = alarmType;
/*     */   }
/*     */   
/*     */   public String getIcon() {
/* 220 */     return this.icon;
/*     */   }
/*     */   
/*     */   public void setIcon(String icon) {
/* 224 */     this.icon = icon;
/*     */   }
/*     */   
/*     */   public String getVoice() {
/* 228 */     return this.voice;
/*     */   }
/*     */   
/*     */   public void setVoice(String voice) {
/* 232 */     this.voice = voice;
/*     */   }
/*     */   
/*     */   public String getAlarmFcolor() {
/* 236 */     return this.alarmFcolor;
/*     */   }
/*     */   
/*     */   public void setAlarmFcolor(String alarmFcolor) {
/* 240 */     this.alarmFcolor = alarmFcolor;
/*     */   }
/*     */   
/*     */   public String getAlarmBcolor() {
/* 244 */     return this.alarmBcolor;
/*     */   }
/*     */   
/*     */   public void setAlarmBcolor(String alarmBcolor) {
/* 248 */     this.alarmBcolor = alarmBcolor;
/*     */   }
/*     */   
/*     */   public Integer getSeverity() {
/* 252 */     return this.severity;
/*     */   }
/*     */   
/*     */   public void setSeverity(Integer severity) {
/* 256 */     this.severity = severity;
/*     */   }
/*     */   
/*     */   public Boolean getDisplayEnabled() {
/* 260 */     return this.displayEnabled;
/*     */   }
/*     */   
/*     */   public void setDisplayEnabled(Boolean displayEnabled) {
/* 264 */     this.displayEnabled = displayEnabled;
/*     */   }
/*     */   
/*     */   public Boolean getFlashEnabled() {
/* 268 */     return this.flashEnabled;
/*     */   }
/*     */   
/*     */   public void setFlashEnabled(Boolean flashEnabled) {
/* 272 */     this.flashEnabled = flashEnabled;
/*     */   }
/*     */   
/*     */   public EventLogMode getEventLogMode() {
/* 276 */     return this.eventLogMode;
/*     */   }
/*     */   
/*     */   public void setEventLogMode(EventLogMode eventLogMode) {
/* 280 */     this.eventLogMode = eventLogMode;
/*     */   }
/*     */   
/*     */   public EventExeMode getEventExeMode() {
/* 284 */     return this.eventExeMode;
/*     */   }
/*     */   
/*     */   public void setEventExeMode(EventExeMode eventExeMode) {
/* 288 */     this.eventExeMode = eventExeMode;
/*     */   }
/*     */   
/*     */   public Boolean getManualEnabled() {
/* 292 */     return this.manualEnabled;
/*     */   }
/*     */   
/*     */   public void setManualEnabled(Boolean manualEnabled) {
/* 296 */     this.manualEnabled = manualEnabled;
/*     */   }
/*     */   
/*     */   public Boolean getEventLockEnabled() {
/* 300 */     return this.eventLockEnabled;
/*     */   }
/*     */   
/*     */   public void setEventLockEnabled(Boolean eventLockEnabled) {
/* 304 */     this.eventLockEnabled = eventLockEnabled;
/*     */   }
/*     */   
/*     */   public String getEventFcolor() {
/* 308 */     return this.eventFcolor;
/*     */   }
/*     */   
/*     */   public void setEventFcolor(String eventFcolor) {
/* 312 */     this.eventFcolor = eventFcolor;
/*     */   }
/*     */   
/*     */   public String getEventBcolor() {
/* 316 */     return this.eventBcolor;
/*     */   }
/*     */   
/*     */   public void setEventBcolor(String eventBcolor) {
/* 320 */     this.eventBcolor = eventBcolor;
/*     */   }
/*     */   
/*     */   public Integer getIncidentIconId() {
/* 324 */     return this.incidentIconId;
/*     */   }
/*     */   
/*     */   public void setIncidentIconId(Integer incidentIconId) {
/* 328 */     this.incidentIconId = incidentIconId;
/*     */   }
/*     */   
/*     */   public Integer getIncidentBkgId() {
/* 332 */     return this.incidentBkgId;
/*     */   }
/*     */   
/*     */   public void setIncidentBkgId(Integer incidentBkgId) {
/* 336 */     this.incidentBkgId = incidentBkgId;
/*     */   }
/*     */   
/*     */   public Boolean getRemindEnabled() {
/* 340 */     return this.remindEnabled;
/*     */   }
/*     */   
/*     */   public void setRemindEnabled(Boolean remindEnabled) {
/* 344 */     this.remindEnabled = remindEnabled;
/*     */   }
/*     */   
/*     */   public Integer getFirstRemindTime() {
/* 348 */     return this.firstRemindTime;
/*     */   }
/*     */   
/*     */   public void setFirstRemindTime(Integer firstRemindTime) {
/* 352 */     this.firstRemindTime = firstRemindTime;
/*     */   }
/*     */   
/*     */   public Integer getSecondRemindTime() {
/* 356 */     return this.secondRemindTime;
/*     */   }
/*     */   
/*     */   public void setSecondRemindTime(Integer secondRemindTime) {
/* 360 */     this.secondRemindTime = secondRemindTime;
/*     */   }
/*     */   
/*     */   public Integer getThirdRemindTime() {
/* 364 */     return this.thirdRemindTime;
/*     */   }
/*     */   
/*     */   public void setThirdRemindTime(Integer thirdRemindTime) {
/* 368 */     this.thirdRemindTime = thirdRemindTime;
/*     */   }
/*     */   
/*     */   public Boolean getIsBrowserMarquee() {
/* 372 */     return this.isBrowserMarquee;
/*     */   }
/*     */   
/*     */   public void setIsBrowserMarquee(Boolean isBrowserMarquee) {
/* 376 */     this.isBrowserMarquee = isBrowserMarquee;
/*     */   }
/*     */   
/*     */   public Boolean getIsMarquee() {
/* 380 */     return this.isMarquee;
/*     */   }
/*     */   
/*     */   public void setIsMarquee(Boolean isMarquee) {
/* 384 */     this.isMarquee = isMarquee;
/*     */   }
/*     */   
/*     */   public Integer getMarqueeSeverity() {
/* 388 */     return this.marqueeSeverity;
/*     */   }
/*     */   
/*     */   public void setMarqueeSeverity(Integer marqueeSeverity) {
/* 392 */     this.marqueeSeverity = marqueeSeverity;
/*     */   }
/*     */   
/*     */   public Boolean getSendMail()
/*     */   {
/* 397 */     return this.sendMail;
/*     */   }
/*     */   
/*     */   public void setSendMail(Boolean sendMail) {
/* 401 */     this.sendMail = sendMail;
/*     */   }
/*     */   
/*     */   public String getMailRoles() {
/* 405 */     return this.mailRoles;
/*     */   }
/*     */   
/*     */   public void setMailRoles(String mailRoles) {
/* 409 */     this.mailRoles = mailRoles;
/*     */   }
/*     */   
/*     */   public String getMailUsers() {
/* 413 */     return this.mailUsers;
/*     */   }
/*     */   
/*     */   public void setMailUsers(String mailUsers) {
/* 417 */     this.mailUsers = mailUsers;
/*     */   }
/*     */   
/*     */   public String getMailOtherUsers() {
/* 421 */     return this.mailOtherUsers;
/*     */   }
/*     */   
/*     */   public void setMailOtherUsers(String mailOtherUsers) {
/* 425 */     this.mailOtherUsers = mailOtherUsers;
/*     */   }
/*     */   
/*     */   public Boolean getSendSms() {
/* 429 */     return this.sendSms;
/*     */   }
/*     */   
/*     */   public void setSendSms(Boolean sendSms) {
/* 433 */     this.sendSms = sendSms;
/*     */   }
/*     */   
/*     */   public String getSmsRoles() {
/* 437 */     return this.smsRoles;
/*     */   }
/*     */   
/*     */   public void setSmsRoles(String smsRoles) {
/* 441 */     this.smsRoles = smsRoles;
/*     */   }
/*     */   
/*     */   public String getSmsUsers() {
/* 445 */     return this.smsUsers;
/*     */   }
/*     */   
/*     */   public void setSmsUsers(String smsUsers) {
/* 449 */     this.smsUsers = smsUsers;
/*     */   }
/*     */   
/*     */   public String getSmsOtherUsers() {
/* 453 */     return this.smsOtherUsers;
/*     */   }
/*     */   
/*     */   public void setSmsOtherUsers(String smsOtherUsers) {
/* 457 */     this.smsOtherUsers = smsOtherUsers;
/*     */   }
/*     */   
/*     */   public String getAlarmMessage() {
/* 461 */     return this.alarmMessage;
/*     */   }
/*     */   
/*     */   public void setAlarmMessage(String alarmMessage) {
/* 465 */     this.alarmMessage = alarmMessage;
/*     */   }
/*     */   
/*     */   public Boolean getBackNormalAlarm() {
/* 469 */     return this.backNormalAlarm;
/*     */   }
/*     */   
/*     */   public void setBackNormalAlarm(Boolean backNormalAlarm) {
/* 473 */     this.backNormalAlarm = backNormalAlarm;
/*     */   }
/*     */   
/*     */   public Integer getEventLockTime() {
/* 477 */     return this.eventLockTime;
/*     */   }
/*     */   
/*     */   public void setEventLockTime(Integer eventLockTime) {
/* 481 */     this.eventLockTime = eventLockTime;
/*     */   }
/*     */   
/*     */   public int hashCode()
/*     */   {
/* 486 */     int prime = 31;
/* 487 */     int result = 1;
/* 488 */     result = 31 * result + (this.id == null ? 0 : this.id.hashCode());
/* 489 */     return result;
/*     */   }
/*     */   
/*     */   public boolean equals(Object obj)
/*     */   {
/* 494 */     if (this == obj) return true;
/* 495 */     if (obj == null) return false;
/* 496 */     if (getClass() != obj.getClass()) return false;
/* 497 */     AlarmSubTypeConfig other = (AlarmSubTypeConfig)obj;
/* 498 */     if (this.id == null) {
/* 499 */       if (other.id != null) return false;
/* 500 */     } else if (!this.id.equals(other.id)) return false;
/* 501 */     return true;
/*     */   }
/*     */   
/*     */   public String toString()
/*     */   {
/* 506 */     return "AlarmSubTypeConfig [id=" + this.id + ", alarmSubTypeName=" + this.alarmSubTypeName + ", description=" + this.description + ", shortName=" + this.shortName + ", alarmType=" + this.alarmType + ", icon=" + this.icon + ", voice=" + this.voice + ", alarmFcolor=" + this.alarmFcolor + ", alarmBcolor=" + this.alarmBcolor + ", severity=" + this.severity + ", displayEnabled=" + this.displayEnabled + ", flashEnabled=" + this.flashEnabled + ", eventLogMode=" + this.eventLogMode + ", eventExeMode=" + this.eventExeMode + ", manualEnabled=" + this.manualEnabled + ", eventLockEnabled=" + this.eventLockEnabled + ", eventFcolor=" + this.eventFcolor + ", eventBcolor=" + this.eventBcolor + ", incidentIconId=" + this.incidentIconId + ", incidentBkgId=" + this.incidentBkgId + ", remindEnabled=" + this.remindEnabled + ", firstRemindTime=" + this.firstRemindTime + ", secondRemindTime=" + this.secondRemindTime + ", thirdRemindTime=" + this.thirdRemindTime + ", isMarquee=" + this.isMarquee + ", marqueeSeverity=" + this.marqueeSeverity + ", sendMail=" + this.sendMail + ", mailRoles=" + this.mailRoles + ", mailUsers=" + this.mailUsers + ", mailOtherUsers=" + this.mailOtherUsers + ", sendSms=" + this.sendSms + ", smsRoles=" + this.smsRoles + ", smsUsers=" + this.smsUsers + ", smsOtherUsers=" + this.smsOtherUsers + ", isBrowserMarquee=" + this.isBrowserMarquee + ", alarmMessage=" + this.alarmMessage + ", backNormalAlarm=" + this.backNormalAlarm + ", eventLockTime=" + this.eventLockTime + "]";
/*     */   }
/*     */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\commonFm-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\c\fm\model\AlarmSubTypeConfig.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */