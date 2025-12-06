/*
 * © HwaCom Systems Inc. 2015
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.ao.shared;

import com.hwacom.ngtms.c.shared.Direction;
import java.io.Serializable;
import java.util.Date;

public class AlarmLogData implements Serializable {

  private static final long serialVersionUID = 5750802516599568155L;

  /** 設備編號 */
  private String deviceName;

  /** 通報來源 */
  private Long notifySource;

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

  private String blockLaneMark;

  private String alarmSubType;

  private Date timestamp;

  private Integer degree;

  public String getDeviceName() {
    return deviceName;
  }

  public void setDeviceName(String deviceName) {
    this.deviceName = deviceName;
  }

  public Long getNotifySource() {
    return notifySource;
  }

  public void setNotifySource(Long notifySource) {
    this.notifySource = notifySource;
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

  public String getBlockLaneMark() {
    return blockLaneMark;
  }

  public void setBlockLaneMark(String blockLaneMark) {
    this.blockLaneMark = blockLaneMark;
  }

  public String getAlarmSubType() {
    return alarmSubType;
  }

  public void setAlarmSubType(String alarmSubType) {
    this.alarmSubType = alarmSubType;
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

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((alarmSubType == null) ? 0 : alarmSubType.hashCode());
    result = prime * result + ((degree == null) ? 0 : degree.hashCode());
    result = prime * result + ((deviceName == null) ? 0 : deviceName.hashCode());
    result = prime * result + ((direction == null) ? 0 : direction.hashCode());
    result = prime * result + ((endMileage == null) ? 0 : endMileage.hashCode());
    result = prime * result + ((lineId == null) ? 0 : lineId.hashCode());
    result = prime * result + ((sectionId == null) ? 0 : sectionId.hashCode());
    result = prime * result + ((startMileage == null) ? 0 : startMileage.hashCode());
    result = prime * result + ((timestamp == null) ? 0 : timestamp.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null) return false;
    if (getClass() != obj.getClass()) return false;
    AlarmLogData other = (AlarmLogData) obj;
    if (alarmSubType == null) {
      if (other.alarmSubType != null) return false;
    } else if (!alarmSubType.equals(other.alarmSubType)) return false;
    if (degree == null) {
      if (other.degree != null) return false;
    } else if (!degree.equals(other.degree)) return false;
    if (deviceName == null) {
      if (other.deviceName != null) return false;
    } else if (!deviceName.equals(other.deviceName)) return false;
    if (direction != other.direction) return false;
    if (endMileage == null) {
      if (other.endMileage != null) return false;
    } else if (!endMileage.equals(other.endMileage)) return false;
    if (lineId == null) {
      if (other.lineId != null) return false;
    } else if (!lineId.equals(other.lineId)) return false;
    if (sectionId == null) {
      if (other.sectionId != null) return false;
    } else if (!sectionId.equals(other.sectionId)) return false;
    if (startMileage == null) {
      if (other.startMileage != null) return false;
    } else if (!startMileage.equals(other.startMileage)) return false;
    if (timestamp == null) {
      if (other.timestamp != null) return false;
    } else if (!timestamp.equals(other.timestamp)) return false;
    return true;
  }
}
