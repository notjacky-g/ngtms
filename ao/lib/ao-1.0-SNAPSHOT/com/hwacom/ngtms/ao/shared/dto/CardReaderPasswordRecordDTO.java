package com.hwacom.ngtms.ao.shared.dto;

import com.google.gwt.user.client.rpc.IsSerializable;
import com.hwacom.ngtms.ao.shared.CardReaderPasswordType;
import java.io.Serializable;
import java.util.Date;

public class CardReaderPasswordRecordDTO implements Serializable, IsSerializable {

  private static final long serialVersionUID = -1747641155452782116L;

  private String id;

  private String deviceName;

  private String password;

  private Date time;

  private CardReaderPasswordType type;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getDeviceName() {
    return deviceName;
  }

  public void setDeviceName(String deviceName) {
    this.deviceName = deviceName;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public Date getTime() {
    return time;
  }

  public void setTime(Date time) {
    this.time = time;
  }

  public CardReaderPasswordType getType() {
    return type;
  }

  public void setType(CardReaderPasswordType type) {
    this.type = type;
  }
}
