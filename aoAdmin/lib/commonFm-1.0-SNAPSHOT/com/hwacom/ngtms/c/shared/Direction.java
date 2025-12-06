/*
 * © HwaCom Systems Inc. 2013
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.c.shared;

import com.google.gwt.user.client.rpc.IsSerializable;
import com.hwacom.ngtms.base.i18n.shared.MessageType;

public enum Direction implements MessageType, IsSerializable {
  /** 北向 */
  N,
  /** 東向 */
  E,
  /** 南向 */
  S,
  /** 西向 */
  W,
  /** 東北向 */
  EN,
  /** 東南向 */
  ES,
  /** 東西向 */
  EW,
  /** 南北向 */
  NS,
  /** 西北向 */
  WN,
  /** 西南向 */
  WS;

  @Override
  public String getMessageKeyPrefix() {
    return "base.Direction";
  }
}
