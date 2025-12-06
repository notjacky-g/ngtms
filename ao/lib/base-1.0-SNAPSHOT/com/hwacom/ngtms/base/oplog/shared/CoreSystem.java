/*
 * © HwaCom Systems Inc. 2013
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.base.oplog.shared;

import com.google.gwt.user.client.rpc.IsSerializable;
import com.hwacom.ngtms.base.i18n.shared.MessageType;

public enum CoreSystem implements MessageType, IsSerializable {
  /** 中央電腦叢集系統 */
  HCCE("#HCCE"),
  ;

  private String defaultUserId;

  CoreSystem(String defaultUserId) {
    this.defaultUserId = defaultUserId;
  }

  public String getDefaultUserId() {
    return defaultUserId;
  }

  @Override
  public String getMessageKeyPrefix() {
    return "base.SubSystem";
  }
}
