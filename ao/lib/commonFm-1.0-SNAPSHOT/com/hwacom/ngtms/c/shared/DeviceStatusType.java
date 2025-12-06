/*
 * © HwaCom Systems Inc. 2015
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.c.shared;

import com.google.gwt.user.client.rpc.IsSerializable;

public enum DeviceStatusType implements IsSerializable {
  /** 無 */
  NONE,
  /** 連線 */
  ONLINE,
  /** 斷線 */
  OFFLINE,
  /** 停用 */
  SUSPEND
}
