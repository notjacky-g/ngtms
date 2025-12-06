/*
 * © HwaCom Systems Inc. 2015
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.c.shared;

import java.io.Serializable;
import java.util.Date;

/** 接收到的警報若有需要轉為事件並切傳送給CEP模組進行反應計畫處理,則透過EventMessage將資料放入IQeue讓RPS收取 */
public class EventMessage implements Serializable {
  private static final long serialVersionUID = 1L;
  /** 警報ID */
  private Long alarmId;
  /** 警報來源 */
  private AlarmSource alarmSource;
  /** 警報次類別 */
  private Integer alarmSubType;
  /**
   * 警報會話(Session)編號，相同編號的警報，視為同一警報事件的初始或跨階警報。 警報發送者須確保編號在全系統的唯一性。 建議格式：FME Name + [警報型態] + 序號 或
   * 設備編號。
   */
  private String alarmSessionId;
  /** 事件發生時間 */
  private Date incTime;

  /** 事件通知時間 */
  private Date notifyTime;

  /** 設備編號 */
  private String deviceName;
  /** 警報等級，0 為關閉 */
  private Integer degree;
  /** 路線編號 */
  private String lineId;
  /** 路段編號 */
  private String sectionId;
  /** 方向 */
  private Direction direction;

  /** 起點里程數 */
  private Integer startMileage;
  /** 終點里程數 */
  private Integer endMileage;
  /** 警報訊息相關資料。資料內容格式由警報發送端與接收端協調。典型的作法是將某一物件 Serialize 為字串， 例如 Mcns */
  private String alarmContextData;
  /** 記錄詳細資料的 table。 警報發送者應先將警報詳細資料寫入DB後才發送警報。 alarmContextTable 對應到一個或多個資料庫table。 */
  private AlarmContextTable alarmContextTable;

  /** 詳細資料table key。當使用用多個table 時，請使用 key1;key2;key3 格式記錄多個 table key。 */
  private String alarmContextKey;

  public EventMessage() {}

  public EventMessage(
      Long alarmId,
      AlarmSource alarmSource,
      Integer alarmSubType,
      String alarmSessionId,
      Date incTime,
      Date notifyTime,
      String deviceName,
      Integer degree,
      String lineId,
      String sectionId,
      Direction direction,
      Integer startMileage,
      Integer endMileage,
      String alarmContextData,
      AlarmContextTable alarmContextTable,
      String alarmContextKey) {
    this.alarmId = alarmId;
    this.alarmSource = alarmSource;
    this.alarmSubType = alarmSubType;
    this.alarmSessionId = alarmSessionId;
    this.incTime = incTime;
    this.notifyTime = notifyTime;
    this.deviceName = deviceName;
    this.degree = degree;
    this.lineId = lineId;
    this.sectionId = sectionId;
    this.direction = direction;
    this.startMileage = startMileage;
    this.endMileage = endMileage;
    this.alarmContextData = alarmContextData;
    this.alarmContextTable = alarmContextTable;
    this.alarmContextKey = alarmContextKey;
  }

  public Long getAlarmId() {
    return alarmId;
  }

  public void setAlarmId(Long alarmId) {
    this.alarmId = alarmId;
  }

  public Integer getAlarmSubType() {
    return alarmSubType;
  }

  public void setAlarmSubType(Integer alarmSubType) {
    this.alarmSubType = alarmSubType;
  }

  public Date getIncTime() {
    return incTime;
  }

  public void setIncTime(Date incTime) {
    this.incTime = incTime;
  }

  public Date getNotifyTime() {
    return notifyTime;
  }

  public void setNotifyTime(Date notifyTime) {
    this.notifyTime = notifyTime;
  }

  public AlarmSource getAlarmSource() {
    return alarmSource;
  }

  public void setAlarmSource(AlarmSource alarmSource) {
    this.alarmSource = alarmSource;
  }

  public String getDeviceName() {
    return deviceName;
  }

  public void setDeviceName(String deviceName) {
    this.deviceName = deviceName;
  }

  public Integer getDegree() {
    return degree;
  }

  public void setDegree(Integer degree) {
    this.degree = degree;
  }

  public Integer getStartMileage() {
    return startMileage;
  }

  public void setStartMileage(Integer startMileage) {
    this.startMileage = startMileage;
  }

  public Integer getEndMileage() {
    return endMileage;
  }

  public void setEndMileage(Integer endMileage) {
    this.endMileage = endMileage;
  }

  public String getLineId() {
    return lineId;
  }

  public void setLineId(String lineId) {
    this.lineId = lineId;
  }

  public String getSectionId() {
    return sectionId;
  }

  public void setSectionId(String sectionId) {
    this.sectionId = sectionId;
  }

  public Direction getDirection() {
    return direction;
  }

  public void setDirection(Direction direction) {
    this.direction = direction;
  }

  public String getAlarmSessionId() {
    return alarmSessionId;
  }

  public void setAlarmSessionId(String alarmSessionId) {
    this.alarmSessionId = alarmSessionId;
  }

  public String getAlarmContextData() {
    return alarmContextData;
  }

  public void setAlarmContextData(String alarmContextData) {
    this.alarmContextData = alarmContextData;
  }

  public AlarmContextTable getAlarmContextTable() {
    return alarmContextTable;
  }

  public void setAlarmContextTable(AlarmContextTable alarmContextTable) {
    this.alarmContextTable = alarmContextTable;
  }

  public String getAlarmContextKey() {
    return alarmContextKey;
  }

  public void setAlarmContextKey(String alarmContextKey) {
    this.alarmContextKey = alarmContextKey;
  }

  @Override
  public String toString() {
    return "RpsEventMessage [alarmId="
        + alarmId
        + ", alarmSource="
        + alarmSource
        + ", alarmSubType="
        + alarmSubType
        + ", alarmSessionId="
        + alarmSessionId
        + ", incTime="
        + incTime
        + ", notifyTime="
        + notifyTime
        + ", deviceName="
        + deviceName
        + ", degree="
        + degree
        + ", lineId="
        + lineId
        + ", sectionId="
        + sectionId
        + ", direction="
        + direction
        + ", startMileage="
        + startMileage
        + ", endMileage="
        + endMileage
        + ", alarmContextData="
        + alarmContextData
        + ", alarmContextTable="
        + alarmContextTable
        + ", alarmContextKey="
        + alarmContextKey
        + "]";
  }
}
