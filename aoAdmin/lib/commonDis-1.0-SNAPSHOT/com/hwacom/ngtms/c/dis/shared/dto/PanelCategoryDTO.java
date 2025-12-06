/*
 * © HwaCom Systems Inc. 2015
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.c.dis.shared.dto;

import com.google.gwt.user.client.rpc.IsSerializable;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public enum PanelCategoryDTO implements Serializable, IsSerializable {
  NONE(0, 0, Orientation.HORIZONTAL, false, false),
  RG_1x4(1, 4, Orientation.HORIZONTAL, false, false),
  RG_1x8(1, 8, Orientation.HORIZONTAL, false, false),
  RG_2x2(2, 2, Orientation.HORIZONTAL, false, true),
  RG_2x6(2, 6, Orientation.HORIZONTAL, false, true),
  RG_2x8(2, 8, Orientation.HORIZONTAL, false, true),
  RGB_2x8(2, 8, Orientation.HORIZONTAL, true, true),
  RG_3x6(3, 6, Orientation.HORIZONTAL, false, true),
  RG_3x8(3, 8, Orientation.HORIZONTAL, false, true),
  RG_4x1(4, 1, Orientation.VERTICAL, false, false),
  RGB_4x8(4, 8, Orientation.HORIZONTAL, true, true),
  RG_5x2(5, 2, Orientation.VERTICAL, false, true),
  RG_5x1(5, 1, Orientation.VERTICAL, false, false),
  RG_6x2(6, 2, Orientation.VERTICAL, false, true),
  RG_8x1(8, 1, Orientation.VERTICAL, false, false),
  RGB_6x2(6, 2, Orientation.VERTICAL, true, true),
  RGB_8x1(8, 1, Orientation.VERTICAL, true, false),
  RGB_3x8(3, 8, Orientation.HORIZONTAL, true, true),
  RGB_1x8(1, 8, Orientation.HORIZONTAL, true, false),
  RGB_2x6(2, 6, Orientation.HORIZONTAL, true, true),
  RGB_1x12(1, 12, Orientation.HORIZONTAL, false, false);

  private int row;

  private int column;

  private Orientation orientation;

  private boolean graphicInsertable;

  private boolean supportBreakLine;

  PanelCategoryDTO(
      int row,
      int column,
      Orientation orientation,
      boolean graphicInsertable,
      boolean supportBreakLine) {
    this.row = row;
    this.column = column;
    this.orientation = orientation;
    this.graphicInsertable = graphicInsertable;
    this.supportBreakLine = supportBreakLine;
  }

  public int getRow() {
    return row;
  }

  public int getColumn() {
    return column;
  }

  public Orientation getOrientation() {
    return orientation;
  }

  public boolean isGraphicInsertable() {
    return graphicInsertable;
  }

  public boolean isSupportBreakLine() {
    return supportBreakLine;
  }

  public String toText() {
    return row + "x" + column;
  }

  public static List<PanelCategoryDTO> getValuesUserConcerned() {
    List<PanelCategoryDTO> result = new ArrayList<PanelCategoryDTO>();
    for (PanelCategoryDTO each : PanelCategoryDTO.values()) {
      if (each == NONE || each == RGB_4x8 || each == RG_5x1 || each == RG_2x2) {
        continue;
      }
      result.add(each);
    }
    return result;
  }

  public enum Orientation {
    HORIZONTAL,
    VERTICAL
  }
}
