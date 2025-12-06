/*
 * © HwaCom Systems Inc. 2015
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.c.shared;

import static com.google.common.base.Preconditions.checkArgument;

import java.util.Collection;

public class ContactKey {

  private static final String _Dash = "-";

  private ContactKey() {}
  /**
   * 如果有多個物件會用 "" 來連接<br>
   * <br>
   * <b>注意：{@link java.util.Date Date} 物件請先自行轉換成字串</b>
   */
  public static String get(Object... datas) {
    return asString(_Dash, datas);
  }

  public static String asString(String delimiter, Object... datas) {
    checkArgument(delimiter != null, "Delimiter cannot be null");

    StringBuilder buffer = new StringBuilder();
    for (Object data : datas) buffer.append(delimiter).append(data.toString());

    return buffer.length() == 0 ? buffer.toString() : buffer.substring(delimiter.length());
  }

  public static String asString(String delimiter, Collection<?> datas) {
    checkArgument(delimiter != null, "Delimiter cannot be null");
    checkArgument(datas != null, "Data collection cannot be null");

    StringBuilder buffer = new StringBuilder();
    for (Object data : datas) buffer.append(delimiter).append(data.toString());

    return buffer.length() == 0 ? buffer.toString() : buffer.substring(delimiter.length());
  }
}
