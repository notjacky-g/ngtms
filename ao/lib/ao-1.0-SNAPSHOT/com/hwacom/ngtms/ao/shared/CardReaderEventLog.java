package com.hwacom.ngtms.ao.shared;

import java.io.Serializable;

public class CardReaderEventLog implements Serializable {

  private static final long serialVersionUID = -4134429926580124202L;

  private String deviceName;

  private boolean dataValid;

  private String time;

  private Integer cardNumber1;

  private Integer cardNumber2;

  private Integer eventCode;

  public String getDeviceName() {
    return deviceName;
  }

  public void setDeviceName(String deviceName) {
    this.deviceName = deviceName;
  }

  public boolean getDataValid() {
    return dataValid;
  }

  public void setDataValid(boolean dataValid) {
    this.dataValid = dataValid;
  }

  public String getTime() {
    return time;
  }

  public void setTime(String time) {
    this.time = time;
  }

  public Integer getCardNumber1() {
    return cardNumber1;
  }

  public void setCardNumber1(Integer cardNumber1) {
    this.cardNumber1 = cardNumber1;
  }

  public Integer getCardNumber2() {
    return cardNumber2;
  }

  public void setCardNumber2(Integer cardNumber2) {
    this.cardNumber2 = cardNumber2;
  }

  public Integer getEventCode() {
    return eventCode;
  }

  public void setEventCode(Integer eventCode) {
    this.eventCode = eventCode;
  }
}
