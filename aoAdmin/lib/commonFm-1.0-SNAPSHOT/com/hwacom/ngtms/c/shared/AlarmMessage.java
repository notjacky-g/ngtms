/*
 * © HwaCom Systems Inc. 2015
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.c.shared;

import java.io.Serializable;
import java.util.Date;

/**
 * 攜帶警報相關資訊時，可使用的欄位包含：alarmContextData, alarmContextTable, 及 alarmContextKey。 這些欄位的建議使用方式為： 1.
 * 當警報相關資訊存放在 一個表的 一個 row 或多個 row時，請使用 alarmContextTable + alarmContextKey 攜帶資訊 2. 當警報相關資訊存放在 多個表的
 * 一個 row 或多個 row時，請使用 alarmContextTable + alarmContextKey 攜帶資訊 3. 當警報相關資訊不存放在資料表，或該資料表無法被接收端讀取時，請使用
 * alarmContextData 攜帶資訊
 *
 * @author yhleu
 */
public class AlarmMessage implements Serializable {
  private static final long serialVersionUID = 1L;

  /** 警報來源 */
  private AlarmSource alarmSource;
  /** 警報次類別 */
  private Integer alarmSubType;

  /**
   * 警報會話(Session)編號，相同編號的警報，視為同一警報事件的初始或跨階警報。 警報發送者須確保編號在全系統的唯一性。 建議格式：FME Name + [警報型態] + 序號 或
   * 設備編號。
   */
  private String alarmSessionId;

  /** 警報發生或變化時間 */
  private Date timestamp;

  /** 設備編號 */
  private String deviceName;
  /** 設備名稱 */
  private String displayName;

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

  /**
   * 警報訊息相關資料。資料內容格式由警報發送端與接收端協調。典型的作法有： 1. 是將某一物件 Serialize 為字串， 例如 MCNS-001;通報者;10;1。 2. 直接將 物件轉換為
   * JSON 格式。
   */
  private String alarmContextData;
  /** 記錄詳細資料的 table。 警報發送者應先將警報詳細資料寫入DB後才發送警報。 alarmContextTable 對應到一個或多個資料庫table。 */
  private AlarmContextTable alarmContextTable;

  /** 詳細資料table key。當使用用多個table 時，請使用 key1;key2;key3 格式記錄多個 table key。 */
  private String alarmContextKey;

  /** Mail 內容 */
  private String mailContent;

  /** SMS 內容 */
  private String smsContent;

  public AlarmMessage() {}

  public AlarmMessage(
      AlarmSource alarmSource,
      Integer alarmSubType,
      String alarmSessionId,
      Date timestamp,
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
    this.alarmSource = alarmSource;
    this.alarmSubType = alarmSubType;
    this.alarmSessionId = alarmSessionId;
    this.deviceName = deviceName;
    this.timestamp = timestamp;
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

  public AlarmMessage(
      AlarmSource alarmSource,
      Integer alarmSubType,
      String alarmSessionId,
      Date timestamp,
      String deviceName,
      String displayName,
      Integer degree,
      String lineId,
      String sectionId,
      Direction direction,
      Integer startMileage,
      Integer endMileage,
      String alarmContextData,
      AlarmContextTable alarmContextTable,
      String alarmContextKey) {
    this.alarmSource = alarmSource;
    this.alarmSubType = alarmSubType;
    this.alarmSessionId = alarmSessionId;
    this.deviceName = deviceName;
    this.displayName = displayName;
    this.timestamp = timestamp;
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

  public AlarmMessage(
      AlarmSource alarmSource,
      Integer alarmSubType,
      String alarmSessionId,
      Date timestamp,
      String deviceName,
      String displayName,
      Integer degree,
      String lineId,
      String sectionId,
      Direction direction,
      Integer startMileage,
      Integer endMileage,
      String alarmContextData,
      AlarmContextTable alarmContextTable,
      String alarmContextKey,
      String mailContent,
      String smsContent) {
    this.alarmSource = alarmSource;
    this.alarmSubType = alarmSubType;
    this.alarmSessionId = alarmSessionId;
    this.deviceName = deviceName;
    this.displayName = displayName;
    this.timestamp = timestamp;
    this.degree = degree;
    this.lineId = lineId;
    this.sectionId = sectionId;
    this.direction = direction;
    this.startMileage = startMileage;
    this.endMileage = endMileage;
    this.alarmContextData = alarmContextData;
    this.alarmContextTable = alarmContextTable;
    this.alarmContextKey = alarmContextKey;
    this.mailContent = mailContent;
    this.mailContent = mailContent;
  }

  public AlarmSource getAlarmSource() {
    return alarmSource;
  }

  public void setAlarmSource(AlarmSource alarmSource) {
    this.alarmSource = alarmSource;
  }

  public Integer getAlarmSubType() {
    return alarmSubType;
  }

  public void setAlarmSubType(Integer alarmSubType) {
    this.alarmSubType = alarmSubType;
  }

  public String getAlarmSessionId() {
    return alarmSessionId;
  }

  public void setAlarmSessionId(String alarmSessionId) {
    this.alarmSessionId = alarmSessionId;
  }

  public String getDeviceName() {
    return deviceName;
  }

  public void setDeviceName(String deviceName) {
    this.deviceName = deviceName;
  }

  public String getDisplayName() {
    return displayName;
  }

  public void setDisplayName(String displayName) {
    this.displayName = displayName;
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

  /** @return the mailContent */
  public String getMailContent() {
    return mailContent;
  }

  /** @param mailContent the mailContent to set */
  public void setMailContent(String mailContent) {
    this.mailContent = mailContent;
  }

  /** @return the smsContent */
  public String getSmsContent() {
    return smsContent;
  }

  /** @param smsContent the smsContent to set */
  public void setSmsContext(String smsContent) {
    this.smsContent = smsContent;
  }

  /*
   * (non-Javadoc)
   *
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    return "AlarmMessage [alarmSource="
        + alarmSource
        + ", alarmSubType="
        + alarmSubType
        + ", alarmSessionId="
        + alarmSessionId
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
        + ", mailContent="
        + mailContent
        + ", smsContent="
        + smsContent
        + "]";
  }
}
