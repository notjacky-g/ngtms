/*
 * © HwaCom Systems Inc. 2015
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.alarm.fm.shared.dto;

import com.hwacom.ngtms.alarm.fm.shared.AlarmClassification;
import com.hwacom.ngtms.alarm.shared.AlarmState;
import com.hwacom.ngtms.base.annotation.Comment;
import java.io.Serializable;
import java.util.Date;

/** @author BrianCheng */
public class AlarmLogDTO implements Serializable {

  private static final long serialVersionUID = -5063683999964139553L;

  private Long id;

  /** 警報分類 */
  private AlarmClassification alarmClassification;

  /** 警報類別 */
  private String alarmSubType;

  /** 警報會話(Session)編號，相同編號的警報，視為同一警報事件的初始或跨階警報。 警報發送者須確保編號在全系統的唯一性。 */
  private String alarmSessionId;

  /** 警報發生或變化時間 */
  private Date timestamp;

  /** 等級 */
  private Integer degree;

  /** 詳細資料 */
  private String contextData;

  /** 設備名稱 */
  private String deviceName;

  /** 告警訊息 */
  private String message;

  /** 告警狀態 */
  private AlarmState alarmState;

  /** 處理狀態 */
  @Comment("處理狀態")
  private String note;

  /** 操控者 */
  @Comment("操控者")
  private String operator;

  /** 處理優先順序 */
  @Comment("處理優先順序")
  private String priority;

  public AlarmLogDTO() {}

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public AlarmClassification getAlarmClassification() {
    return alarmClassification;
  }

  public void setAlarmClassification(AlarmClassification alarmClassification) {
    this.alarmClassification = alarmClassification;
  }

  public String getAlarmSubType() {
    return alarmSubType;
  }

  public void setAlarmSubType(String alarmSubType) {
    this.alarmSubType = alarmSubType;
  }

  public String getAlarmSessionId() {
    return alarmSessionId;
  }

  public void setAlarmSessionId(String alarmSessionId) {
    this.alarmSessionId = alarmSessionId;
  }

  public Date getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(Date timestamp) {
    this.timestamp = timestamp;
  }

  public Integer getDegree() {
    return degree;
  }

  public void setDegree(Integer degree) {
    this.degree = degree;
  }

  public String getContextData() {
    return contextData;
  }

  public void setContextData(String contextData) {
    this.contextData = contextData;
  }

  public String getDeviceName() {
    return deviceName;
  }

  public void setDeviceName(String deviceName) {
    this.deviceName = deviceName;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public AlarmState getAlarmState() {
    return alarmState;
  }

  public void setAlarmState(AlarmState alarmState) {
    this.alarmState = alarmState;
  }

  public String getNote() {
    return note;
  }

  public void setNote(String note) {
    this.note = note;
  }

  public String getOperator() {
    return operator;
  }

  public void setOperator(String operator) {
    this.operator = operator;
  }

  public String getPriority() {
    return priority;
  }

  public void setPriority(String priority) {
    this.priority = priority;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((alarmState == null) ? 0 : alarmState.hashCode());
    result = prime * result + ((alarmSubType == null) ? 0 : alarmSubType.hashCode());
    result = prime * result + ((degree == null) ? 0 : degree.hashCode());
    result = prime * result + ((deviceName == null) ? 0 : deviceName.hashCode());
    result = prime * result + ((id == null) ? 0 : id.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null) return false;
    if (getClass() != obj.getClass()) return false;
    AlarmLogDTO other = (AlarmLogDTO) obj;
    if (alarmState != other.alarmState) return false;
    if (alarmSubType == null) {
      if (other.alarmSubType != null) return false;
    } else if (!alarmSubType.equals(other.alarmSubType)) return false;
    if (degree == null) {
      if (other.degree != null) return false;
    } else if (!degree.equals(other.degree)) return false;
    if (deviceName == null) {
      if (other.deviceName != null) return false;
    } else if (!deviceName.equals(other.deviceName)) return false;
    if (id == null) {
      if (other.id != null) return false;
    } else if (!id.equals(other.id)) return false;
    return true;
  }

  @Override
  public String toString() {
    return "AlarmLogDTO [id="
        + id
        + ", alarmClassification="
        + alarmClassification
        + ", alarmSubType="
        + alarmSubType
        + ", alarmSessionId="
        + alarmSessionId
        + ", timestamp="
        + timestamp
        + ", degree="
        + degree
        + ", contextData="
        + contextData
        + ", deviceName="
        + deviceName
        + ", message="
        + message
        + ", alarmState="
        + alarmState
        + ", note="
        + note
        + ", operator="
        + operator
        + ", priority="
        + priority
        + "]";
  }
}
