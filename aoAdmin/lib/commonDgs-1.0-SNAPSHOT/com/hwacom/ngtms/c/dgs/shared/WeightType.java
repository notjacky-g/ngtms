/*
 * © HwaCom Systems Inc. 2015
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.c.dgs.shared;

import com.hwacom.ngtms.base.i18n.shared.MessageType;
import org.apache.commons.lang.StringUtils;

public enum WeightType implements MessageType {
  Type0000("全無資料"), //
  Type1000("只有VD"), //
  Type0100("只有ETAG"), //
  Type1100("缺ETC和歷史資料"), //
  Type0010("只有ETC"), //
  Type1010("缺ETAG和歷史資料"), //
  Type0110("缺VD和歷史資料"), //
  Type1110("缺歷史資料"), //
  Type0001("只有歷史資料"), //
  Type1001("缺ETAG和ETC"), //
  Type0101("只有ETAG和歷史資料"), //
  Type1101("缺ETC"), //
  Type0011("只有ETC和歷史資料"), //
  Type1011("缺ETAG"), //
  Type0111("缺VD"), //
  Type1111("全部包含"); //

  private String desc;

  private WeightType(String desc) {
    this.desc = desc;
  }

  /**
   * 透過各種旅行時間找出符合的權重類型
   *
   * @param vdValue
   * @param aviValue
   * @param etcValue
   * @param histValue
   * @return
   */
  public static WeightType findType(int vdValue, int aviValue, int etcValue, int histValue) {
    vdValue = vdValue > 0 ? 1000 : 0;
    aviValue = aviValue > 0 ? 100 : 0;
    etcValue = etcValue > 0 ? 10 : 0;
    histValue = histValue > 0 ? 1 : 0;
    int type = vdValue + aviValue + etcValue + histValue;
    String typeStr = StringUtils.leftPad(String.valueOf(type), 4, "0");
    return WeightType.valueOf("Type" + typeStr);
  }

  public String getDesc() {
    return desc;
  }

  @Override
  public String getMessageKeyPrefix() {
    return "dgs.WeightType";
  }
}
