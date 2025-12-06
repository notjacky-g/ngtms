/*
 * © HwaCom Systems Inc. 2015
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.c.shared;

import com.hwacom.ngtms.c.fm.model.DeviceTcConfig;
import java.util.Comparator;

public class DeviceMilepostComparator implements Comparator<DeviceTcConfig> {

  private CompareOrder order;

  public DeviceMilepostComparator(CompareOrder order) {
    this.order = order;
  }

  @Override
  public int compare(DeviceTcConfig o1, DeviceTcConfig o2) {
    int result = 0;
    if (o1.getMilepost() > o2.getMilepost()) {
      result = 1;
    } else if (o1.getMilepost() < o2.getMilepost()) {
      result = -1;
    }
    if (order == CompareOrder.DESC) {
      result = -result;
    }

    return result;
  }

  public enum CompareOrder {
    ASC,
    DESC;
  }
}
