/*
 * © HwaCom Systems Inc. 2015
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.c.shared;

/** @author Chris */
public enum TransmissionMode {

  /** 0: 被動輪詢回報模式(Polling) */
  PASSIVE, //

  /** 1: 主動回報模式，依設定之傳輸週期主動定時回報。 */
  ACTIVE //
;
}
