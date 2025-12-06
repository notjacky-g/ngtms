/*
 * © HwaCom Systems Inc. 2015
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.room.shared.dto;

import com.hwacom.ngtms.room.shared.EventCode;
import java.io.Serializable;
import java.util.Date;

public class RoomCardReaderLogDTO implements Serializable {

  private static final long serialVersionUID = -6247372423639904061L;

  //設備編號
  private String deviceName;

  private Date time;

  private EventCode eventCode;

  private String description;

  private Integer status;

  public String getDeviceName() {
    return deviceName;
  }

  public void setDeviceName(String deviceName) {
    this.deviceName = deviceName;
  }

  public Date getTime() {
    return time;
  }

  public void setTime(Date time) {
    this.time = time;
  }

  public EventCode getEventCode() {
    return eventCode;
  }

  public void setEventCode(EventCode eventCode) {
    this.eventCode = eventCode;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Integer getStatus() {
    return status;
  }

  public void setStatus(Integer status) {
    this.status = status;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((deviceName == null) ? 0 : deviceName.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null) return false;
    if (getClass() != obj.getClass()) return false;
    RoomCardReaderLogDTO other = (RoomCardReaderLogDTO) obj;
    if (deviceName == null) {
      if (other.deviceName != null) return false;
    } else if (!deviceName.equals(other.deviceName)) return false;
    return true;
  }

  @Override
  public String toString() {
    return "RoomCardReaderLogDTO [deviceName="
        + deviceName
        + ", time="
        + time
        + ", eventCode="
        + eventCode
        + ", description="
        + description
        + ", status="
        + status
        + "]";
  }
}
