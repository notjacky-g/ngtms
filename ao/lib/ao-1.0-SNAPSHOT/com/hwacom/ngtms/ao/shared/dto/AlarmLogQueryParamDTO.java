package com.hwacom.ngtms.ao.shared.dto;

import com.google.gwt.user.client.rpc.IsSerializable;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class AlarmLogQueryParamDTO implements Serializable, IsSerializable {

  private static final long serialVersionUID = 1L;

  private Date startTime;

  private Date endTime;

  private String alarmTypeId;

  private String alarmSubTypeId;

  private List<String> deviceNames;

  private Boolean allDevices = false;

  private boolean check0Event;

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

  public String getAlarmTypeId() {
    return alarmTypeId;
  }

  public void setAlarmTypeId(String alarmTypeId) {
    this.alarmTypeId = alarmTypeId;
  }

  public String getAlarmSubTypeId() {
    return alarmSubTypeId;
  }

  public void setAlarmSubTypeId(String alarmSubTypeId) {
    this.alarmSubTypeId = alarmSubTypeId;
  }

  public List<String> getDeviceNames() {
    return deviceNames;
  }

  public void setDeviceNames(List<String> deviceNames) {
    this.deviceNames = deviceNames;
  }

  public Boolean getAllDevices() {
    return allDevices;
  }

  public void setAllDevices(Boolean allDevices) {
    this.allDevices = allDevices;
  }

  public boolean isCheck0Event() {
    return check0Event;
  }

  public void setCheck0Event(boolean check0Event) {
    this.check0Event = check0Event;
  }

  @Override
  public String toString() {
    return "AlarmLogQueryParamDTO [startTime="
        + startTime
        + ", endTime="
        + endTime
        + ", alarmTypeId="
        + alarmTypeId
        + ", alarmSubTypeId="
        + alarmSubTypeId
        + ", deviceNames="
        + deviceNames
        + ", allDevices="
        + allDevices
        + ", check0Event="
        + check0Event
        + "]";
  }
}
