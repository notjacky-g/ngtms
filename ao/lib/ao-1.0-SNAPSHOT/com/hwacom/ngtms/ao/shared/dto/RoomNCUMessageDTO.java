package com.hwacom.ngtms.ao.shared.dto;

import com.google.gwt.user.client.rpc.IsSerializable;
import java.io.Serializable;

public class RoomNCUMessageDTO implements Serializable, IsSerializable {

  private static final long serialVersionUID = -1601562364826162023L;

  String ncuMessage;

  public String getNcuMessage() {
    return ncuMessage;
  }

  public void setNcuMessage(String ncuMessage) {
    this.ncuMessage = ncuMessage;
  }
}
