/*
 * © HwaCom Systems Inc. 2015
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.c.shared;

/** 通訊協定 0BH op_status hi_nibble(bit4~bit7) */
public enum OpStatusHiNibbleType {
  /** 原bitset轉int後之 value 0.設備沒有施作opStatus */
  NONE,
  /** 原bitset轉int後之 value 1，代表"故障" */
  MALFUNCTIONAL,
  /** 原bitset轉int後之 value 2，代表"顯示中(白天亮度)" */
  DAY_BRIGHTNESS,
  /** 原bitset轉int後之 value 3，代表"熄滅中" */
  OFF,
  /** 原bitset轉int後之 value 4，代表"顯示中(晨昏亮度)" */
  DAWN_BRIGHTNESS,
  /** 原bitset轉int後之 value 5，代表"顯示中(晚上亮度)" */
  NIGHT_BRIGHTNESS,
  /** 原bitset轉int後之 value 6,代表"顯示中(深夜亮度)" */
  MIDNIGHT_BRIGHTNESS,
  /** 其他不正常狀況 */
  ABNORMAL,
  ;
}
