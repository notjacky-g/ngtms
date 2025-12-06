/*
 * © HwaCom Systems Inc. 2015
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.c.shared;

import com.google.gwt.user.client.rpc.IsSerializable;
import com.hwacom.ngtms.base.i18n.shared.MessageType;

public enum AreaType implements MessageType, IsSerializable {
  /** 北區 */
  N,
  /** 中區 */
  C,
  /** 南區 */
  S,
  /** 坪林 */
  P,
  /** 全區 */
  A;

  @Override
  public String getMessageKeyPrefix() {
    return "commonFm.AreaType";
  }
}
