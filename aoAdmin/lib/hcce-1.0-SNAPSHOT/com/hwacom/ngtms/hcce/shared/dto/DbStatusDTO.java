/*
 * © HwaCom Systems Inc. 2020
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.hcce.shared.dto;

import com.google.gwt.user.client.rpc.IsSerializable;
import java.io.Serializable;

public class DbStatusDTO implements Serializable, IsSerializable {

  private static final long serialVersionUID = -8320687569235123003L;
  private boolean wsrepConnected;
  private boolean wsrepLocalStateComment;
  private boolean wsrepReady;
  private String descriptoin;

  public boolean isOnline() {
    return wsrepConnected && wsrepLocalStateComment && wsrepReady;
  }

  public boolean isWsrepConnected() {
    return wsrepConnected;
  }

  public void setWsrepConnected(boolean wsrepConnected) {
    this.wsrepConnected = wsrepConnected;
  }

  public boolean isWsrepLocalStateComment() {
    return wsrepLocalStateComment;
  }

  public void setWsrepLocalStateComment(boolean wsrepLocalStateComment) {
    this.wsrepLocalStateComment = wsrepLocalStateComment;
  }

  public boolean isWsrepReady() {
    return wsrepReady;
  }

  public void setWsrepReady(boolean wsrepReady) {
    this.wsrepReady = wsrepReady;
  }

  public String getDescriptoin() {
    return descriptoin;
  }

  public void setDescriptoin(String descriptoin) {
    this.descriptoin = descriptoin;
  }
}
