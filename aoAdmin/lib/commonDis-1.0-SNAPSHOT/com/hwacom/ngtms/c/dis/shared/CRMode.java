/*
 * © HwaCom Systems Inc. 2015
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.c.dis.shared;

/**
 * 斷行模式
 *
 * @author Jeff.Ku
 */
public enum CRMode {

  /** 0：NO CR */
  NONE(""),
  /** 1：CR */
  CR("\r"),
  /** 2：CRLF */
  CRLF("\r\n");

  private String lineBreak;

  CRMode(String lineBreak) {
    this.lineBreak = lineBreak;
  }

  public String getLineBreak() {
    return lineBreak;
  }
}
