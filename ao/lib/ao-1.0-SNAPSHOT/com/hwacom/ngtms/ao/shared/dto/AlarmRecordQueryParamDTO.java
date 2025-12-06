package com.hwacom.ngtms.ao.shared.dto;

import com.google.gwt.user.client.rpc.IsSerializable;
import java.io.Serializable;
import java.util.Date;

public class AlarmRecordQueryParamDTO implements Serializable, IsSerializable {

  private static final long serialVersionUID = -6801836940829681111L;

  private Date startTime;

  private Date endTime;

  private RoomNcuDeviceNameDTO roomDto;

  private AlarmTypeDTO alarmMonitorType;

  private int offset;

  public Date getStartTime() {
    return startTime;
  }

  public void setStartTime(Date startTime) {
    this.startTime = startTime;
  }

  public Date getEndTime() {
    return endTime;
  }

  public void setEndTime(Date endTime) {
    this.endTime = endTime;
  }

  public RoomNcuDeviceNameDTO getRoomDto() {
    return roomDto;
  }

  public void setRoomDto(RoomNcuDeviceNameDTO roomDto) {
    this.roomDto = roomDto;
  }

  public AlarmTypeDTO getAlarmMonitorType() {
    return alarmMonitorType;
  }

  public void setAlarmMonitorType(AlarmTypeDTO alarmMonitorType) {
    this.alarmMonitorType = alarmMonitorType;
  }

  public int getOffset() {
    return offset;
  }

  public void setOffset(int offset) {
    this.offset = offset;
  }

  @Override
  public String toString() {
    return "AlarmRecordQueryParamDTO [startTime="
        + startTime
        + ", endTime="
        + endTime
        + ", roomDto="
        + roomDto
        + ", alarmMonitorType="
        + alarmMonitorType
        + ", offset="
        + offset
        + "]";
  }
}
