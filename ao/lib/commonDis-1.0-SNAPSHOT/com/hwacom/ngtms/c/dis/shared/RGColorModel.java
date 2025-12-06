/*
 * © HwaCom Systems Inc. 2015
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.c.dis.shared;

import com.google.common.base.Splitter;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 三色LED顯示色彩
 *
 * @author Jeff.Ku
 */
public enum RGColorModel {

  /** 0：熄滅 */
  OFF("000000", 0),
  /** 1：綠色 */
  GREEN("008000", 1),
  /** 2：紅色 */
  RED("FF0000", 2),
  /** 3：黃色 */
  YELLOW("FFFF00", 3);

  private static final ImmutableMap<Integer, RGColorModel> VALUE_MAPPING;

  static {
    Map<Integer, RGColorModel> tempMap = Maps.newHashMap();
    for (RGColorModel model : RGColorModel.values()) tempMap.put(model.getProtocolValue(), model);

    VALUE_MAPPING = ImmutableMap.copyOf(tempMap);
  }

  public static RGColorModel valueOf(int protocolValue) {
    return VALUE_MAPPING.get(protocolValue);
  }

  /**
   * 根據紅綠兩個BIT取得colorModel的值
   *
   * @param red
   * @param green
   * @return RGColorModel
   */
  public static RGColorModel valueOf(boolean red, boolean green) {
    if (red && green) {
      return RGColorModel.YELLOW;
    } else if (red) {
      return RGColorModel.RED;
    } else if (green) {
      return RGColorModel.GREEN;
    }
    return RGColorModel.OFF;
  }

  private String hex;

  private int protocolValue;

  RGColorModel(String hex, int protocolValue) {
    this.hex = hex;
    this.protocolValue = protocolValue;
  }

  public String getHex() {
    return hex;
  }

  public int getProtocolValue() {
    return this.protocolValue;
  }

  public static String rgColor2String(List<RGColorModel> foregroundColorModel, String delimiter) {
    StringBuffer sb = new StringBuffer();
    for (RGColorModel fc : foregroundColorModel) {
      sb.append(fc).append(delimiter);
    }

    return sb.toString();
  }

  public static List<RGColorModel> string2RGColor(String color, String delimiter) {

    List<RGColorModel> backgroundColorModel = new ArrayList<>();
    if (color != null && !color.isEmpty()) {
      Splitter splitter = Splitter.on(delimiter);
      Iterable<String> temp = splitter.omitEmptyStrings().split(color);
      for (String s : temp) {
        if (s.equals("null")) {
          s = "OFF";
        }
        backgroundColorModel.add(RGColorModel.valueOf(s));
      }
    }
    return backgroundColorModel;
  }
}
