/*
 * © HwaCom Systems Inc. 2015
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.c.dis.shared;

import com.google.gwt.user.client.rpc.IsSerializable;
import java.io.Serializable;

public enum ColorModel implements Serializable, IsSerializable {
  RED("#FF0000", "rgb(255, 0, 0)"),
  GREEN("#008000", "rgb(0, 128, 0)"),
  YELLOW("#FFFF00", "rgb(255, 255, 0)"),
  BLACK("#000000", "rgb(0, 0, 0)");

  private String hex;

  private String rgb;

  ColorModel(String hex, String rgb) {
    this.hex = hex;
    this.rgb = rgb;
  }

  public String getHex() {
    return hex;
  }

  public String getRgb() {
    return rgb;
  }

  public static ColorModel evaluate(String color, ColorModel defaultColor) {
    for (ColorModel each : values()) {
      if (each.getHex().equals(color) || each.getRgb().equals(color)) {
        return each;
      }
    }

    return defaultColor;
  }
}
