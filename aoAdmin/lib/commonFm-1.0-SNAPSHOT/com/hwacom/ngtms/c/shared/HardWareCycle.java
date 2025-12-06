/*
 * © HwaCom Systems Inc. 2015
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.c.shared;

/** @author Chris */
public enum HardWareCycle {

  /** 0 : 停止傳輸(預設值) 但當硬體狀態有變化時，立即主動回報(以0AH回報) */
  STOP, //

  /** 1 : 5秒 */
  FIVESECONDS,
  /** 2 : 10秒 */
  TENSECONDS,
  /** 3 : 20秒 */
  TWENTYSECONDS,
  /** 4 : 1分鐘 */
  ONEMIN,
  /** 5 : 5分鐘 */
  FIVEMIN;
}
