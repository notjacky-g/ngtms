/*
 * © HwaCom Systems Inc. 2015
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.c.shared;

import com.hwacom.ngtms.alarm.fm.shared.AlarmLogContext;

public class AlarmLogEventContext extends AlarmLogContext {

  private static final long serialVersionUID = 5731054782005476073L;

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

  private String memo;

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

  public String getMemo() {
    return memo;
  }

  public void setMemo(String memo) {
    this.memo = memo;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((deviceName == null) ? 0 : deviceName.hashCode());
    result = prime * result + ((direction == null) ? 0 : direction.hashCode());
    result = prime * result + ((endMileage == null) ? 0 : endMileage.hashCode());
    result = prime * result + ((lineId == null) ? 0 : lineId.hashCode());
    result = prime * result + ((sectionId == null) ? 0 : sectionId.hashCode());
    result = prime * result + ((startMileage == null) ? 0 : startMileage.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null) return false;
    if (getClass() != obj.getClass()) return false;
    AlarmLogEventContext other = (AlarmLogEventContext) obj;
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
    return true;
  }

  @Override
  public String toString() {
    return "CepEventAlarmLogContext [deviceName="
        + deviceName
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
        + ", memo="
        + memo
        + "]";
  }
}
