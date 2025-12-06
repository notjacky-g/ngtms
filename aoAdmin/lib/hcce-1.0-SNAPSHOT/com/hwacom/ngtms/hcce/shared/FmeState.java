/*
 * © HwaCom Systems Inc. 2013
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.hcce.shared;

public enum FmeState {
  /** 沒有節點執行這個 FME */
  NoNode,
  Starting, //
  Running,
  RuntimeException,
  Stop
}
