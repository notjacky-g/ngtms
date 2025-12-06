package com.hwacom.ngtms.ao.shared.dto;

import com.google.gwt.user.client.rpc.IsSerializable;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class RoomAnalogRecordQueryParamDTO implements Serializable, IsSerializable {

  private static final long serialVersionUID = 4487519914116108661L;

  private List<RoomAnalogTreeDTO> roomAnalogList;

  private Date startTime;

  private Date endTime;

  public List<RoomAnalogTreeDTO> getRoomAnalogList() {
    return roomAnalogList;
  }

  public void setRoomAnalogList(List<RoomAnalogTreeDTO> roomAnalogList) {
    this.roomAnalogList = roomAnalogList;
  }

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
}
