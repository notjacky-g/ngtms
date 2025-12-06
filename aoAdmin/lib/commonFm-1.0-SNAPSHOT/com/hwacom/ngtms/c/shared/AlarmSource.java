/*
 * © HwaCom Systems Inc. 2015
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.c.shared;

import com.google.gwt.user.client.rpc.IsSerializable;

public enum AlarmSource implements IsSerializable {
  DETECTOR(2),
  AUTO_INCIDENT_DETECTION(7),
  MCNS(12);

  private Integer id;

  AlarmSource(Integer id) {
    this.id = id;
  }

  public Integer getId() {
    return this.id;
  }
}
