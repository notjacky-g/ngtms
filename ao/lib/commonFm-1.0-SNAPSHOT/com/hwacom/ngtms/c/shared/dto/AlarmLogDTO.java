/*
 * © HwaCom Systems Inc. 2015
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.c.shared.dto;

import com.google.gwt.user.client.rpc.IsSerializable;
import com.hwacom.ngtms.c.shared.AlarmContextTable;
import com.hwacom.ngtms.c.shared.AlarmSource;
import java.io.Serializable;
import java.util.Date;

public class AlarmLogDTO implements Serializable, IsSerializable {

  private static final long serialVersionUID = -6913589633672317256L;
  private Long id;
  /** 警報來源 */
  private AlarmSource alarmSource;
  /** 警報次類別ID */
  private Integer alarmSubTypeId;

  private String alarmSubType;
  /**
   * 警報會話(Session)編號，相同編號的警報，視為同一警報事件的初始或跨階警報。 警報發送者須確保編號在全系統的唯一性。 建議格式：FME Name + [警報型態] + 序號 或
   * 設備編號。
   */
  private String alarmSessionId;

  private String eventId;

  private Date timestamp;
  /** 設備編號 */
  private String deviceName;
  /** 設備名稱 */
  private String displayName;
  /** 警報發生或變化時間 */
  /** 等級 */
  private Integer degree;
  /** 路線編號 */
  private String lineId;

  private String lineName;
  /** 路段編號 */
  private String sectionId;

  private String sectionName;
  /** 方向 */
  private String direction;
  /** 起點里程數 */
  private Integer startMileage;
  /** 終點里程數 */
  private Integer endMileage;
  /** 是否已處理 */
  private Boolean processed = false;
  /** 是否已結束 */
  private Boolean closed = false;
  /** 是否誤報 */
  private Boolean isFalseAlarm = false;
  /** 是否為反應計畫事件 */
  private Boolean rpsEvent = false;
  /** 警報訊息相關資料 */
  private String alarmContextData;
  /** 詳細資料所在table */
  private AlarmContextTable alarmContextTable;
  /** 詳細資料table key */
  private String alarmContextKey;
  /** @return the id */
  public Long getId() {
    return id;
  }
  /** @param id the id to set */
  public void setId(Long id) {
    this.id = id;
  }
  /** @return the alarmSource */
  public AlarmSource getAlarmSource() {
    return alarmSource;
  }
  /** @param alarmSource the alarmSource to set */
  public void setAlarmSource(AlarmSource alarmSource) {
    this.alarmSource = alarmSource;
  }
  /** @return the alarmSubTypeId */
  public Integer getAlarmSubTypeId() {
    return alarmSubTypeId;
  }
  /** @param alarmSubTypeId the alarmSubTypeId to set */
  public void setAlarmSubTypeId(Integer alarmSubTypeId) {
    this.alarmSubTypeId = alarmSubTypeId;
  }
  /** @return the alarmSessionId */
  public String getAlarmSessionId() {
    return alarmSessionId;
  }
  /** @param alarmSessionId the alarmSessionId to set */
  public void setAlarmSessionId(String alarmSessionId) {
    this.alarmSessionId = alarmSessionId;
  }
  /** @return the timestamp */
  public Date getTimestamp() {
    return timestamp;
  }
  /** @param timestamp the timestamp to set */
  public void setTimestamp(Date timestamp) {
    this.timestamp = timestamp;
  }
  /** @return the deviceName */
  public String getDeviceName() {
    return deviceName;
  }
  /** @param deviceName the deviceName to set */
  public void setDeviceName(String deviceName) {
    this.deviceName = deviceName;
  }

  /** @return the displayName */
  public String getDisplayName() {
    return displayName;
  }
  /** @param displayName the displayName to set */
  public void setDisplayName(String displayName) {
    this.displayName = displayName;
  }
  /** @return the degree */
  public Integer getDegree() {
    return degree;
  }
  /** @param degree the degree to set */
  public void setDegree(Integer degree) {
    this.degree = degree;
  }
  /** @return the lineId */
  public String getLineId() {
    return lineId;
  }
  /** @param lineId the lineId to set */
  public void setLineId(String lineId) {
    this.lineId = lineId;
  }
  /** @return the sectionId */
  public String getSectionId() {
    return sectionId;
  }
  /** @param sectionId the sectionId to set */
  public void setSectionId(String sectionId) {
    this.sectionId = sectionId;
  }
  /** @return the direction */
  public String getDirection() {
    return direction;
  }
  /** @param direction the direction to set */
  public void setDirection(String direction) {
    this.direction = direction;
  }
  /** @return the startMileage */
  public Integer getStartMileage() {
    return startMileage;
  }
  /** @param startMileage the startMileage to set */
  public void setStartMileage(Integer startMileage) {
    this.startMileage = startMileage;
  }
  /** @return the endMileage */
  public Integer getEndMileage() {
    return endMileage;
  }
  /** @param endMileage the endMileage to set */
  public void setEndMileage(Integer endMileage) {
    this.endMileage = endMileage;
  }
  /** @return the processed */
  public Boolean getProcessed() {
    return processed;
  }
  /** @param processed the processed to set */
  public void setProcessed(Boolean processed) {
    this.processed = processed;
  }
  /** @return the closed */
  public Boolean getClosed() {
    return closed;
  }
  /** @param closed the closed to set */
  public void setClosed(Boolean closed) {
    this.closed = closed;
  }
  /** @return the isFalseAlarm */
  public Boolean getIsFalseAlarm() {
    return isFalseAlarm;
  }
  /** @param isFalseAlarm the isFalseAlarm to set */
  public void setIsFalseAlarm(Boolean isFalseAlarm) {
    this.isFalseAlarm = isFalseAlarm;
  }
  /** @return the rpsEvent */
  public Boolean getRpsEvent() {
    return rpsEvent;
  }
  /** @param rpsEvent the rpsEvent to set */
  public void setRpsEvent(Boolean rpsEvent) {
    this.rpsEvent = rpsEvent;
  }
  /** @return the alarmContextData */
  public String getAlarmContextData() {
    return alarmContextData;
  }
  /** @param alarmContextData the alarmContextData to set */
  public void setAlarmContextData(String alarmContextData) {
    this.alarmContextData = alarmContextData;
  }
  /** @return the alarmContextTable */
  public AlarmContextTable getAlarmContextTable() {
    return alarmContextTable;
  }
  /** @param alarmContextTable the alarmContextTable to set */
  public void setAlarmContextTable(AlarmContextTable alarmContextTable) {
    this.alarmContextTable = alarmContextTable;
  }
  /** @return the alarmContextKey */
  public String getAlarmContextKey() {
    return alarmContextKey;
  }
  /** @param alarmContextKey the alarmContextKey to set */
  public void setAlarmContextKey(String alarmContextKey) {
    this.alarmContextKey = alarmContextKey;
  }

  /** @return the alarmSubType */
  public String getAlarmSubType() {
    return alarmSubType;
  }
  /** @param alarmSubType the alarmSubType to set */
  public void setAlarmSubType(String alarmSubType) {
    this.alarmSubType = alarmSubType;
  }

  public String getLineName() {
    return lineName;
  }

  public void setLineName(String lineName) {
    this.lineName = lineName;
  }

  public String getSectionName() {
    return sectionName;
  }

  public void setSectionName(String sectionName) {
    this.sectionName = sectionName;
  }

  public String getEventId() {
    return eventId;
  }

  public void setEventId(String eventId) {
    this.eventId = eventId;
  }

  @Override
  public String toString() {
    return "AlarmLogDTO [id="
        + id
        + ", alarmSource="
        + alarmSource
        + ", alarmSubTypeId="
        + alarmSubTypeId
        + ", alarmSubType="
        + alarmSubType
        + ", alarmSessionId="
        + alarmSessionId
        + ", eventId="
        + eventId
        + ", timestamp="
        + timestamp
        + ", deviceName="
        + deviceName
        + ", displayName="
        + displayName
        + ", degree="
        + degree
        + ", lineId="
        + lineId
        + ", lineName="
        + lineName
        + ", sectionId="
        + sectionId
        + ", sectionName="
        + sectionName
        + ", direction="
        + direction
        + ", startMileage="
        + startMileage
        + ", endMileage="
        + endMileage
        + ", processed="
        + processed
        + ", closed="
        + closed
        + ", isFalseAlarm="
        + isFalseAlarm
        + ", rpsEvent="
        + rpsEvent
        + ", alarmContextData="
        + alarmContextData
        + ", alarmContextTable="
        + alarmContextTable
        + ", alarmContextKey="
        + alarmContextKey
        + "]";
  }
}
