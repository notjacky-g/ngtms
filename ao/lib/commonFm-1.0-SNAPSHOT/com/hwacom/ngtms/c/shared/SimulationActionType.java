/*
 * © HwaCom Systems Inc. 2015
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.c.shared;

/** @author Chris */
public enum SimulationActionType {
  /** 0 : 立即停止模擬輸出 */
  ForceStop(0), //
  /** 1 : 立即啟動模擬輸出 */
  ForceStart(1), //
  /** 2 : 依據本指令設定的時間，停止模擬輸出 */
  StopByTime(2), //
  /** 3 : 依據本指令設定的時間，啟動模擬輸出 */
  StartByTime(3), //
  ; //

  private int protocolValue;

  private SimulationActionType(int protocolValue) {
    this.protocolValue = protocolValue;
  }

  public int getProtocolValue() {
    return this.protocolValue;
  }
}
