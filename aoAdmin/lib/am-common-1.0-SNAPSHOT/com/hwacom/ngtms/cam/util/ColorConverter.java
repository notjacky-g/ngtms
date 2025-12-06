/*
 * © HwaCom Systems Inc. 2015
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.cam.util;

import com.hwacom.ngtms.c.dis.shared.RGColorModel;
import com.sencha.gxt.chart.client.draw.Color;
import com.sencha.gxt.chart.client.draw.RGB;

public class ColorConverter {
  public static Color from(RGColorModel colorModel) {
    if (RGColorModel.GREEN == colorModel) {
      return RGB.GREEN;
    } else if (RGColorModel.RED == colorModel) {
      return RGB.RED;
    } else if (RGColorModel.YELLOW == colorModel) {
      return RGB.YELLOW;
    } else {
      return RGB.BLACK;
    }
  }
}
