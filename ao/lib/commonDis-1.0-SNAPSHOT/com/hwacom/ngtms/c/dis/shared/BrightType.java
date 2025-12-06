/*
 * © HwaCom Systems Inc. 2015
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.c.dis.shared;

/** 亮度控制(1:偵測器控制,2:日照表控制,3:白天,4:黃昏,5:夜晚,6:深夜,預設為2)。 */
public enum BrightType {

  /** 0:未定義，EnumType.ordinal save用順序 */
  None(0),

  /** 1:偵測器控制 */
  SensorControl(1),

  /** 2:日照表控制 */
  SunshineTableControl(2),

  /** 3:白天 */
  Daytime(3),

  /** 4:黃昏 */
  Dusk(4),

  /** 5:夜晚 */
  Night(5),

  /** 6:深夜 */
  LateAtNight(6);

  private int protocolValue;

  BrightType(int protocolValue) {
    this.protocolValue = protocolValue;
  }

  public int getProtocolValue() {
    return this.protocolValue;
  }

  public static BrightType getEnum(int value) {
    for (BrightType v : values()) if (v.getProtocolValue() == value) return v;
    throw new IllegalArgumentException();
  }
}
