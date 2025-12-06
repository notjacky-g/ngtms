/*
 * © HwaCom Systems Inc. 2013
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.base.sms.shared;

import java.io.Serializable;

/** @author brian.cheng */
public class SmsSendRequest implements Serializable {

  private static final long serialVersionUID = -4872817946006940577L;

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
    return "SmsSendRequest [toAddrs=" + toAddrs + ", messages=" + messages + "]";
  }
}
