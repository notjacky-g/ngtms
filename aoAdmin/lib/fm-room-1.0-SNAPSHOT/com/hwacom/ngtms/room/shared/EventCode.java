/*
 * © HwaCom Systems Inc. 2015
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.room.shared;

import com.google.gwt.user.client.rpc.IsSerializable;

public enum EventCode implements IsSerializable {
  NONE(0),
  ON(1),
  STOP(2),
  OFF(3),
  NUMBER_ERROR(4),
  CARD_NUMBER_DELETE(5),
  CARD_NUMBER_DUPLICATE(6),
  DOOR_OPEN_OUTSIDE(7),
  DOOR_OPEN_PASSWORD(8),
  DOOR_OPEN_F1(9),
  ANTI_DURESS(10),
  TIME_OUT(11),
  DOOR_OPEN(12),
  DOOR_CLOSE(13),
  CARD_NUMBER_ERROR_UNCEASINGLY(14);

  private Integer id;

  EventCode(Integer id) {
    this.id = id;
  }

  public Integer getId() {
    return this.id;
  }
}
