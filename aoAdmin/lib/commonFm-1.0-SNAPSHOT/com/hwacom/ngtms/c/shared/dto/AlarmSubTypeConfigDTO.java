/*
 * © HwaCom Systems Inc. 2015
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.c.shared.dto;

import com.google.gwt.user.client.rpc.IsSerializable;
import com.hwacom.ngtms.c.shared.EventExeMode;
import com.hwacom.ngtms.c.shared.EventLogMode;
import java.io.Serializable;

/** @author yhleu */
public class AlarmSubTypeConfigDTO implements Serializable, IsSerializable {
  private static final long serialVersionUID = 1L;

  private Integer id;

  /** 警報次類別英文名稱，如:Accident */
  private String alarmSubTypeName;
  /** 警報次類別說明 */
  private String description;
  /** 警報次類別簡稱 */
  private String shortName;
  /** 所屬警報型態 */
  private String alarmType;
  /** 所屬警報型態名稱 */
  private String alarmTypeDescription;
  /** 警報圖示檔案名稱 */
  private String icon;
  /** 警報聲音檔案名稱 */
  private String voice;
  /** 警報前景顏色 RRGGBB. */
  private String alarmFcolor;
  /** 警報前景顏色 RRGGBB. */
  private String alarmBcolor;
  /** 警報嚴重程度 */
  private Integer severity;
  /** 是否顯示警報 */
  private Boolean displayEnabled;
  /** 是否閃爍警報 */
  private Boolean flashEnabled;
  /** 反應計畫事件登錄模式，只有在 isEvent = true 才有效 */
  private EventLogMode eventLogMode;
  /** 反應計畫 事件反應內容執行模式，只有在 isEvent = true 才有效 */
  private EventExeMode eventExeMode;
  /** 是否允許手動登錄反應計畫事件 */
  private Boolean manualEnabled;
  /** 是否啟用反應計畫事件鎖定 */
  private Boolean eventLockEnabled;
  /** 事件前景顏色 RRGGBB. */
  private String eventFcolor;
  /** 事件前景顏色 RRGGBB. */
  private String eventBcolor;
  /** 事故圖示編碼(0代表不使用) */
  private Integer incidentIconId;
  /** 事故背景圖編碼(0代表不使用) */
  private Integer incidentBkgId;
  /** 是否需要提醒事件尚未結束，只應用於手動輸入事件情況 */
  private Boolean remindEnabled;
  /** 首次提醒時間，單位: 分鐘 */
  private Integer firstRemindTime;
  /** 第二次提醒時間，單位: 分鐘 */
  private Integer secondRemindTime;
  /** 第三次及其之後間隔提醒時間，單位: 分鐘 */
  private Integer thirdRemindTime;
  /** 是否加入跑馬燈顯示(前提必需是反應計畫事件型態才能設定) */
  private Boolean isMarquee;
  /** 嚴重程式(跑馬燈)(前提必需是反應計畫事件型態才能設定) */
  private Integer marqueeSeverity;
  /** 是否要寄 Mail */
  private Boolean sendMail = false;
  /** 寄 Mail 的角色(用 ',' 隔開) */
  private String mailRoles;
  /** 寄 Mail 的使用者(用 ',' 隔開) */
  private String mailUsers;
  /** 寄 Mail 的其他使用者(用 ',' 隔開) */
  private String mailOtherUsers;
  /** 是否要寄簡訊 */
  private Boolean sendSms = false;
  /** 寄簡訊的角色(用 ',' 隔開) */
  private String smsRoles;
  /** 寄簡訊的使用者(用 ',' 隔開) */
  private String smsUsers;
  /** 寄簡訊的其他使用者(用 ',' 隔開) */
  private String smsOtherUsers;
  /** 是否加入瀏覽器跑馬燈顯示 */
  private Boolean isBrowserMarquee;
  /** 發出告警時額外的告警訊息, 如"斷線", 預設為空字串 */
  private String alarmMessage = "";
  /** 告警恢復正常時(degree = 0)，是否需要發 mail 或簡訊 */
  private Boolean backNormalAlarm = Boolean.TRUE;
  /** 事件鎖定預設時間(分) */
  private Integer eventLockTime;

  /** 所有alarmSubType，前端才會用到此欄位 */
  private Boolean all = false;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getAlarmSubTypeName() {
    return alarmSubTypeName;
  }

  public void setAlarmSubTypeName(String alarmSubTypeName) {
    this.alarmSubTypeName = alarmSubTypeName;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getShortName() {
    return shortName;
  }

  public void setShortName(String shortName) {
    this.shortName = shortName;
  }

  public String getAlarmType() {
    return alarmType;
  }

  public void setAlarmType(String alarmType) {
    this.alarmType = alarmType;
  }

  public String getIcon() {
    return icon;
  }

  public void setIcon(String icon) {
    this.icon = icon;
  }

  public String getVoice() {
    return voice;
  }

  public void setVoice(String voice) {
    this.voice = voice;
  }

  public String getAlarmFcolor() {
    return alarmFcolor;
  }

  public void setAlarmFcolor(String alarmFcolor) {
    this.alarmFcolor = alarmFcolor;
  }

  public String getAlarmBcolor() {
    return alarmBcolor;
  }

  public void setAlarmBcolor(String alarmBcolor) {
    this.alarmBcolor = alarmBcolor;
  }

  public Integer getSeverity() {
    return severity;
  }

  public void setSeverity(Integer severity) {
    this.severity = severity;
  }

  public Boolean getDisplayEnabled() {
    return displayEnabled;
  }

  public void setDisplayEnabled(Boolean displayEnabled) {
    this.displayEnabled = displayEnabled;
  }

  public Boolean getFlashEnabled() {
    return flashEnabled;
  }

  public void setFlashEnabled(Boolean flashEnabled) {
    this.flashEnabled = flashEnabled;
  }

  public EventLogMode getEventLogMode() {
    return eventLogMode;
  }

  public void setEventLogMode(EventLogMode eventLogMode) {
    this.eventLogMode = eventLogMode;
  }

  public EventExeMode getEventExeMode() {
    return eventExeMode;
  }

  public void setEventExeMode(EventExeMode eventExeMode) {
    this.eventExeMode = eventExeMode;
  }

  public Boolean getManualEnabled() {
    return manualEnabled;
  }

  public void setManualEnabled(Boolean manualEnabled) {
    this.manualEnabled = manualEnabled;
  }

  public Boolean getEventLockEnabled() {
    return eventLockEnabled;
  }

  public void setEventLockEnabled(Boolean eventLockEnabled) {
    this.eventLockEnabled = eventLockEnabled;
  }

  public String getEventFcolor() {
    return eventFcolor;
  }

  public void setEventFcolor(String eventFcolor) {
    this.eventFcolor = eventFcolor;
  }

  public String getEventBcolor() {
    return eventBcolor;
  }

  public void setEventBcolor(String eventBcolor) {
    this.eventBcolor = eventBcolor;
  }

  public Integer getIncidentIconId() {
    return incidentIconId;
  }

  public void setIncidentIconId(Integer incidentIconId) {
    this.incidentIconId = incidentIconId;
  }

  public Integer getIncidentBkgId() {
    return incidentBkgId;
  }

  public void setIncidentBkgId(Integer incidentBkgId) {
    this.incidentBkgId = incidentBkgId;
  }

  public Boolean getRemindEnabled() {
    return remindEnabled;
  }

  public void setRemindEnabled(Boolean remindEnabled) {
    this.remindEnabled = remindEnabled;
  }

  public Integer getFirstRemindTime() {
    return firstRemindTime;
  }

  public void setFirstRemindTime(Integer firstRemindTime) {
    this.firstRemindTime = firstRemindTime;
  }

  public Integer getSecondRemindTime() {
    return secondRemindTime;
  }

  public void setSecondRemindTime(Integer secondRemindTime) {
    this.secondRemindTime = secondRemindTime;
  }

  public Integer getThirdRemindTime() {
    return thirdRemindTime;
  }

  public void setThirdRemindTime(Integer thirdRemindTime) {
    this.thirdRemindTime = thirdRemindTime;
  }

  public String getAlarmTypeDescription() {
    return alarmTypeDescription;
  }

  public void setAlarmTypeDescription(String alarmTypeDescription) {
    this.alarmTypeDescription = alarmTypeDescription;
  }

  /** @return the isMarquee */
  public Boolean getIsMarquee() {
    return isMarquee;
  }
  /** @param isMarquee the isMarquee to set */
  public void setIsMarquee(Boolean isMarquee) {
    this.isMarquee = isMarquee;
  }
  /** @return the marqueeSeverity */
  public Integer getMarqueeSeverity() {
    return marqueeSeverity;
  }
  /** @param marqueeSeverity the marqueeSeverity to set */
  public void setMarqueeSeverity(Integer marqueeSeverity) {
    this.marqueeSeverity = marqueeSeverity;
  }
  /** @return the sendMail */
  public Boolean getSendMail() {
    return sendMail;
  }
  /** @param sendMail the sendMail to set */
  public void setSendMail(Boolean sendMail) {
    this.sendMail = sendMail;
  }
  /** @return the mailRoles */
  public String getMailRoles() {
    return mailRoles;
  }
  /** @param mailRoles the mailRoles to set */
  public void setMailRoles(String mailRoles) {
    this.mailRoles = mailRoles;
  }
  /** @return the mailUsers */
  public String getMailUsers() {
    return mailUsers;
  }
  /** @param mailUsers the mailUsers to set */
  public void setMailUsers(String mailUsers) {
    this.mailUsers = mailUsers;
  }
  /** @return the mailOtherUsers */
  public String getMailOtherUsers() {
    return mailOtherUsers;
  }
  /** @param mailOtherUsers the mailOtherUsers to set */
  public void setMailOtherUsers(String mailOtherUsers) {
    this.mailOtherUsers = mailOtherUsers;
  }
  /** @return the sendSms */
  public Boolean getSendSms() {
    return sendSms;
  }
  /** @param sendSms the sendSms to set */
  public void setSendSms(Boolean sendSms) {
    this.sendSms = sendSms;
  }
  /** @return the smsRoles */
  public String getSmsRoles() {
    return smsRoles;
  }
  /** @param smsRoles the smsRoles to set */
  public void setSmsRoles(String smsRoles) {
    this.smsRoles = smsRoles;
  }
  /** @return the smsUsers */
  public String getSmsUsers() {
    return smsUsers;
  }
  /** @param smsUsers the smsUsers to set */
  public void setSmsUsers(String smsUsers) {
    this.smsUsers = smsUsers;
  }
  /** @return the smsOtherUsers */
  public String getSmsOtherUsers() {
    return smsOtherUsers;
  }
  /** @param smsOtherUsers the smsOtherUsers to set */
  public void setSmsOtherUsers(String smsOtherUsers) {
    this.smsOtherUsers = smsOtherUsers;
  }

  public Boolean getIsBrowserMarquee() {
    return isBrowserMarquee;
  }

  public void setIsBrowserMarquee(Boolean isBrowserMarquee) {
    this.isBrowserMarquee = isBrowserMarquee;
  }

  public String getAlarmMessage() {
    return alarmMessage;
  }

  public void setAlarmMessage(String alarmMessage) {
    this.alarmMessage = alarmMessage;
  }

  public Boolean getBackNormalAlarm() {
    return backNormalAlarm;
  }

  public void setBackNormalAlarm(Boolean backNormalAlarm) {
    this.backNormalAlarm = backNormalAlarm;
  }

  public Integer getEventLockTime() {
    return eventLockTime;
  }

  public void setEventLockTime(Integer eventLockTime) {
    this.eventLockTime = eventLockTime;
  }

  public Boolean getAll() {
    return all;
  }

  public void setAll(Boolean all) {
    this.all = all;
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
    AlarmSubTypeConfigDTO other = (AlarmSubTypeConfigDTO) obj;
    if (id == null) {
      if (other.id != null) return false;
    } else if (!id.equals(other.id)) return false;
    return true;
  }

  @Override
  public String toString() {
    return "AlarmSubTypeConfigDTO [id="
        + id
        + ", alarmSubTypeName="
        + alarmSubTypeName
        + ", description="
        + description
        + ", shortName="
        + shortName
        + ", alarmType="
        + alarmType
        + ", alarmTypeDescription="
        + alarmTypeDescription
        + ", icon="
        + icon
        + ", voice="
        + voice
        + ", alarmFcolor="
        + alarmFcolor
        + ", alarmBcolor="
        + alarmBcolor
        + ", severity="
        + severity
        + ", displayEnabled="
        + displayEnabled
        + ", flashEnabled="
        + flashEnabled
        + ", eventLogMode="
        + eventLogMode
        + ", eventExeMode="
        + eventExeMode
        + ", manualEnabled="
        + manualEnabled
        + ", eventLockEnabled="
        + eventLockEnabled
        + ", eventFcolor="
        + eventFcolor
        + ", eventBcolor="
        + eventBcolor
        + ", incidentIconId="
        + incidentIconId
        + ", incidentBkgId="
        + incidentBkgId
        + ", remindEnabled="
        + remindEnabled
        + ", firstRemindTime="
        + firstRemindTime
        + ", secondRemindTime="
        + secondRemindTime
        + ", thirdRemindTime="
        + thirdRemindTime
        + ", isMarquee="
        + isMarquee
        + ", marqueeSeverity="
        + marqueeSeverity
        + ", sendMail="
        + sendMail
        + ", mailRoles="
        + mailRoles
        + ", mailUsers="
        + mailUsers
        + ", mailOtherUsers="
        + mailOtherUsers
        + ", sendSms="
        + sendSms
        + ", smsRoles="
        + smsRoles
        + ", smsUsers="
        + smsUsers
        + ", smsOtherUsers="
        + smsOtherUsers
        + ", isBrowserMarquee="
        + isBrowserMarquee
        + ", alarmMessage="
        + alarmMessage
        + ", backNormalAlarm="
        + backNormalAlarm
        + ", eventLockTime="
        + eventLockTime
        + "]";
  }
}
