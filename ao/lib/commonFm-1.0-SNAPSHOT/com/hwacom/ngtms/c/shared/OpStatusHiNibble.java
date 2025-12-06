/*
 * © HwaCom Systems Inc. 2013
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.c.shared;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.hwacom.ngtms.base.i18n.shared.MessageType;
import java.util.Map;

public enum OpStatusHiNibble implements MessageType {
  /** 0.設備沒有施作opStatus */
  NONE(0),
  /** 1.故障 */
  MALFUNCTIONAL(1),
  /** 2.顯示中(白天亮度) */
  DAY_BRIGHTNESS(2),
  /** 3.熄滅中 */
  OFF(3),
  /** 4.顯示中(晨昏亮度) */
  DAWN_BRIGHTNESS(4),
  /** 5.顯示中(晚上亮度) */
  NIGHT_BRIGHTNESS(5),
  /** 6.顯示中(深夜亮度) */
  MIDNIGHT_BRIGHTNESS(6),
  /** TC的回傳值不在合理值範圍內 */
  ABNORMAL(7);

  private static final ImmutableMap<Integer, OpStatusHiNibble> VALUE_MAPPING;

  static {
    Map<Integer, OpStatusHiNibble> tempMap = Maps.newHashMap();
    for (OpStatusHiNibble hiNibble : OpStatusHiNibble.values())
      tempMap.put(hiNibble.getValue(), hiNibble);

    VALUE_MAPPING = ImmutableMap.copyOf(tempMap);
  }

  public static OpStatusHiNibble valueOf(int value) {
    return VALUE_MAPPING.get(value);
  }

  private Integer value;

  OpStatusHiNibble(Integer value) {
    this.value = value;
  }

  public Integer getValue() {
    return this.value;
  }

  @Override
  public String getMessageKeyPrefix() {
    return "commonFm.OpStatusHiNibble";
  }
}
