/*
 * © HwaCom Systems Inc. 2015
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.c.dis.shared;

import com.hwacom.ngtms.base.i18n.shared.MessageType;

/**
 * 面板大小 EX:2X2
 *
 * @author Jeff.Ku
 */
public enum PanelCategory implements MessageType {
  NONE(0, 0, "NONE"), //
  RG_1x4(1, 4, "RG"), //
  RG_1x8(1, 8, "RG"),
  RG_2x2(2, 2, "RG"), //
  RG_2x6(2, 6, "RG"), //
  RG_2x8(2, 8, "RG"), //
  RGB_2x8(2, 8, "RGB"), //
  RG_3x6(3, 6, "RG"), //
  RG_3x8(3, 8, "RG"), //
  RG_4x1(4, 1, "RG"), //
  RGB_4x8(4, 8, "RGB"), //
  RG_5x1(5, 1, "RG"), //
  RG_5x2(5, 2, "RG"), //
  RG_6x2(6, 2, "RG"), //
  RG_8x1(8, 1, "RG"),
  RGB_6x2(6, 2, "RGB"),
  RGB_8x1(8, 1, "RGB"),
  RGB_3x8(3, 8, "RGB"),
  RGB_1x8(1, 8, "RGB"),
  RGB_2x6(2, 6, "RGB"),
  RGB_1x12(1, 12, "RGB"),
  ; //

  private int row;

  private int column;

  private String colorModel;

  private PanelCategory(int row, int column, String colorModel) {
    this.row = row;
    this.column = column;
    this.colorModel = colorModel;
  }

  public int getRow() {
    return row;
  }

  public int getColumn() {
    return column;
  }

  public String getColorModel() {
    return colorModel;
  }

  @Override
  public String getMessageKeyPrefix() {
    return "commonFm.PanelCategory";
  }
}
