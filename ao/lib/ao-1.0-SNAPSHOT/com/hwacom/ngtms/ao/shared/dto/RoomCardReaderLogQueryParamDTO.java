package com.hwacom.ngtms.ao.shared.dto;

import com.google.gwt.user.client.rpc.IsSerializable;
import java.io.Serializable;
import java.util.Date;

public class RoomCardReaderLogQueryParamDTO implements Serializable, IsSerializable {

  private static final long serialVersionUID = -7691723549076386716L;

  private Date startTime;

  private Date endTime;

  private String ncnDeviceName;

  private String location;

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

  public String getNcnDeviceName() {
    return ncnDeviceName;
  }

  public void setNcnDeviceName(String ncnDeviceName) {
    this.ncnDeviceName = ncnDeviceName;
  }

  public String getLocation() {
    return location;
  }

  public void setLocation(String location) {
    this.location = location;
  }

  @Override
  public String toString() {
    return "RoomCardReaderLogQueryParamDTO [startTime="
        + startTime
        + ", endTime="
        + endTime
        + ", ncnDeviceName="
        + ncnDeviceName
        + ", location="
        + location
        + "]";
  }
}
