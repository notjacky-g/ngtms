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

public enum OpStatusLowNibble implements MessageType {
  /** 0.設備沒有施作opStatus */
  NONE(0),
  /** 1.現場偵測 */
  ON_SITE_DETECT(1),
  /** 2.日照表控制 */
  SUNSHINE_TABLE_CONTROL(2),
  /** 3.亮度控制表控制 */
  BRIGHTNESS_TABLE_CONTROL(3),
  /** 4.遙控白天亮度 */
  REMOTE_DAY_BRIGHTNESS(4),
  /** 5.遙控夜晚亮度 */
  REMOTE_NIGHT_BRIGHTNESS(5),
  /** 6.遙控晨昏亮度 */
  REMOTE_DAWN_BRIGHTNESS(6),
  /** 7.遙控深夜亮度 */
  REMOTE_MIDNIGHT_BRIGHTNESS(7),
  /** TC的回傳值不在合理值範圍內 */
  ABNORMAL(8),
  ;

  private static final ImmutableMap<Integer, OpStatusLowNibble> VALUE_MAPPING;

  static {
    Map<Integer, OpStatusLowNibble> tempMap = Maps.newHashMap();
    for (OpStatusLowNibble lowNibble : OpStatusLowNibble.values())
      tempMap.put(lowNibble.getValue(), lowNibble);

    VALUE_MAPPING = ImmutableMap.copyOf(tempMap);
  }

  public static OpStatusLowNibble valueOf(int value) {
    return VALUE_MAPPING.get(value);
  }

  private Integer value;

  OpStatusLowNibble(Integer value) {
    this.value = value;
  }

  public Integer getValue() {
    return this.value;
  }

  @Override
  public String getMessageKeyPrefix() {
    return "commonFm.OpStatusLowNibble";
  }
}
