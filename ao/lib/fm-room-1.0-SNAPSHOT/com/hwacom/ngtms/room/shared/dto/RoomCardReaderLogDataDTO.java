package com.hwacom.ngtms.room.shared.dto;

import com.hwacom.ngtms.room.shared.EventCode;
import java.io.Serializable;
import java.util.Date;

public class RoomCardReaderLogDataDTO implements Serializable {

  private static final long serialVersionUID = -4295396132936843912L;

  private Long id;

  private String deviceName;

  private String displayName;

  private Boolean dataValid;

  private Date time;

  private String cardNumber;

  private EventCode eventCode;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
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

  public Boolean getDataValid() {
    return dataValid;
  }

  public void setDataValid(Boolean dataValid) {
    this.dataValid = dataValid;
  }

  public Date getTime() {
    return time;
  }

  public void setTime(Date time) {
    this.time = time;
  }

  public String getCardNumber() {
    return cardNumber;
  }

  public void setCardNumber(String cardNumber) {
    this.cardNumber = cardNumber;
  }

  public EventCode getEventCode() {
    return eventCode;
  }

  public void setEventCode(EventCode eventCode) {
    this.eventCode = eventCode;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((id == null) ? 0 : id.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null) return false;
    if (getClass() != obj.getClass()) return false;
    RoomCardReaderLogDataDTO other = (RoomCardReaderLogDataDTO) obj;
    if (id == null) {
      if (other.id != null) return false;
    } else if (!id.equals(other.id)) return false;
    return true;
  }

  @Override
  public String toString() {
    return "RoomCardReaderLogDataDTO [id="
        + id
        + ", deviceName="
        + deviceName
        + ", displayName="
        + displayName
        + ", dataValid="
        + dataValid
        + ", time="
        + time
        + ", cardNumber="
        + cardNumber
        + ", eventCode="
        + eventCode
        + "]";
  }
}
