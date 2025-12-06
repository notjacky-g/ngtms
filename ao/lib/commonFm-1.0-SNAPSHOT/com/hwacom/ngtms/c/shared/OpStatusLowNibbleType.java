/*
 * © HwaCom Systems Inc. 2015
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.c.shared;

/** 通訊協定 0BH op_status low_nibble(bit0~bit3) */
public enum OpStatusLowNibbleType {
  /** 原bitset轉int後之 value 0.設備沒有施作opStatus */
  NONE,
  /** 原bitset轉int後之 value 1，代表"現場偵測" */
  ON_SITE_DETECT,
  /** 原bitset轉int後之 value 2，代表"日照表控制" */
  SUNSHINE_TABLE_CONTROL,
  /** 原bitset轉int後之 value 3，代表"亮度控制表控制" */
  BRIGHTNESS_TABLE_CONTROL,
  /** 原bitset轉int後之 value 4，代表"遙控白天亮度" */
  REMOTE_DAY_BRIGHTNESS,
  /** 原bitset轉int後之 value 5，代表"遙控夜晚亮度" */
  REMOTE_NIGHT_BRIGHTNESS,
  /** 原bitset轉int後之 value 6.代表"遙控晨昏亮度" */
  REMOTE_DAWN_BRIGHTNESS,
  /** 原bitset轉int後之 value 7.代表"遙控深夜亮度" */
  REMOTE_MIDNIGHT_BRIGHTNESS,
  /** 其他不正常狀況 */
  ABNORMAL,
  ;
}
