/*
 * © HwaCom Systems Inc. 2015
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.c.shared;

import com.google.gwt.user.client.rpc.IsSerializable;
import com.hwacom.ngtms.base.i18n.shared.MessageType;

/** @author jeff.lien */
public enum DivisionType implements MessageType, IsSerializable {
  /** 系統交流道 */
  C,
  /** 交流道 */
  I,
  /** 服務區 */
  S,
  /** 收費站 */
  T;

  @Override
  public String getMessageKeyPrefix() {
    return "commonFm.DivisionType";
  }
}
