/*
 * © HwaCom Systems Inc. 2015
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.c.shared;

import com.google.gwt.user.client.rpc.IsSerializable;

public enum AlarmContextTable implements IsSerializable {

  /** 一小時旅行時間 */
  ETAG_RECOGNITION_1HOUR,

  /** 雨量事件記錄表 */
  RD_EVENT,
  /** 濃霧事件記錄表 */
  VI_EVENT,
  /** 風力事件記錄表 */
  WD_EVENT,
  /** 坍方事件記錄表 */
  LSS_EVENT,

  /** IID偵測紀錄表 */
  IID_STATE_LOG,

  /** 無 */
  NONE
}
