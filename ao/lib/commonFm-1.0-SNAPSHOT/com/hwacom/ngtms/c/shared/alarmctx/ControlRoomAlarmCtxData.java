/*
 * © HwaCom Systems Inc. 2015
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.c.shared.alarmctx;

import com.hwacom.ngtms.c.shared.AlarmContextData;

/**
 * 參照 機房設備交換之 XML 轉換對應的 AlarmCtxData
 *
 * @author brian.cheng
 */
public class ControlRoomAlarmCtxData implements AlarmContextData {
  private String alarmStatus;
  private String placeName;
  private String placeNameDetail;
  private String name;
  private String groupName;
  private String normalContent;
  private String alarmContent;

  @Override
  public String toString() {
    return "ControlRoomAlarmCtxData{"
        + ", alarmStatus="
        + alarmStatus
        + ", placeName="
        + placeName
        + ", placeNameDetail="
        + placeNameDetail
        + ", name="
        + name
        + ", groupName="
        + groupName
        + ", normalContent="
        + normalContent
        + ", alarmContent="
        + alarmContent
        + '}';
  }

  @Override
  public void decode(String strData) {
    if (strData != null) {
      String[] parts = strData.split(";");
      if ((parts.length > 0) && (parts[0].length() > 0)) {
        alarmStatus = parts[0];
      }
      if ((parts.length > 1) && (parts[1].length() > 0)) {
        placeName = parts[1];
      }
      if ((parts.length > 2) && (parts[2].length() > 0)) {
        placeNameDetail = parts[2];
      }
      if ((parts.length > 3) && (parts[3].length() > 0)) {
        name = parts[3];
      }
      if ((parts.length > 4) && (parts[4].length() > 0)) {
        groupName = parts[4];
      }
      if ((parts.length > 5) && (parts[5].length() > 0)) {
        normalContent = parts[5];
      }
      if ((parts.length > 6) && (parts[6].length() > 0)) {
        alarmContent = parts[6];
      }
    }
  }

  @Override
  public String encode() {
    StringBuilder sb = new StringBuilder();
    if (alarmStatus != null) sb.append(alarmStatus);
    sb.append(';');
    if (placeName != null) sb.append(placeName);
    sb.append(';');
    if (placeNameDetail != null) sb.append(placeNameDetail);
    sb.append(';');
    if (name != null) sb.append(name);
    sb.append(';');
    if (groupName != null) sb.append(groupName);
    sb.append(';');
    if (normalContent != null) sb.append(normalContent);
    sb.append(';');
    if (alarmContent != null) sb.append(alarmContent);
    sb.append(';');
    return sb.toString();
  }

  public String getAlarmStatus() {
    return alarmStatus;
  }

  public void setAlarmStatus(String alarmStatus) {
    this.alarmStatus = alarmStatus;
  }

  public String getPlaceName() {
    return placeName;
  }

  public void setPlaceName(String placeName) {
    this.placeName = placeName;
  }

  public String getPlaceNameDetail() {
    return placeNameDetail;
  }

  public void setPlaceNameDetail(String placeNameDetail) {
    this.placeNameDetail = placeNameDetail;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getGroupName() {
    return groupName;
  }

  public void setGroupName(String groupName) {
    this.groupName = groupName;
  }

  public String getNormalContent() {
    return normalContent;
  }

  public void setNormalContent(String normalContent) {
    this.normalContent = normalContent;
  }

  public String getAlarmContent() {
    return alarmContent;
  }

  public void setAlarmContent(String alarmContent) {
    this.alarmContent = alarmContent;
  }
}
