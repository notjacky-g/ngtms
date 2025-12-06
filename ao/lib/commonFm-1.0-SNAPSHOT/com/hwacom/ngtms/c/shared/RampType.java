/*
 * © HwaCom Systems Inc. 2013
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.c.shared;

/**
 * 只有當 {@link LocationType} = "R" (匝道) 時才有意義
 *
 * @see {@link com.hwacom.ngtms.c.fm.model.DeviceTcConfig DeviceTcConfig}
 */
public enum RampType {
  /** 入口匝道 */
  I,
  /** 出口匝道 */
  O,
  /** 未定義 */
  U;
}
