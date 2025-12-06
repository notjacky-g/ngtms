/*
 * © HwaCom Systems Inc. 2015
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.c.shared;

public enum WebSocketCloseReason {
  FINISHED(4000),
  FAILURE(4001);

  private int code;

  WebSocketCloseReason(int code) {
    this.code = code;
  }

  public int getCode() {
    return code;
  }
}
