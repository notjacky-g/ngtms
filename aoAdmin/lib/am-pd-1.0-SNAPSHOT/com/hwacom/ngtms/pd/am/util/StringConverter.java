/*
 * © HwaCom Systems Inc. 2015
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.pd.am.util;

import com.google.gwt.i18n.client.NumberFormat;
import com.hwacom.ngtms.c.shared.Direction;

/** @author huei.yang */
public class StringConverter {

  public static String getFormattedDirection(Direction direction) {
    if (direction == null) {
      return "";
    }
    switch (direction) {
      case E:
        return "東";
      case N:
        return "北";
      case S:
        return "南";
      case W:
        return "西";
      case EW:
        return "東西";
      case NS:
        return "南北";
      default:
        return direction.toString();
    }
  }

  public static String getFormattedMileage(Integer mileage) {
    if (mileage == null) {
      return "";
    }
    return mileage / 1000 + "k+" + NumberFormat.getFormat("000").format(mileage % 1000);
  }
}
