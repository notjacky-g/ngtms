package com.hwacom.ngtms.ao.shared.dto;

import java.io.Serializable;

public class AlarmSendMessageDTO implements Serializable {

  private static final long serialVersionUID = -3584647803743355038L;

  private String toAddrs;

  private String messages;

  public String getToAddrs() {
    return toAddrs;
  }

  public void setToAddrs(String toAddrs) {
    this.toAddrs = toAddrs;
  }

  public String getMessages() {
    return messages;
  }

  public void setMessages(String messages) {
    this.messages = messages;
  }

  @Override
  public String toString() {
    return "AlarmSendMessageDTO [toAddrs=" + toAddrs + ", messages=" + messages + "]";
  }
}
