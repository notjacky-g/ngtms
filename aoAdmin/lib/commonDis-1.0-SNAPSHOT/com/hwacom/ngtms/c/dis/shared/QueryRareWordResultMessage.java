/*
 * © HwaCom Systems Inc. 2015
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.c.dis.shared;

import java.util.List;

public interface QueryRareWordResultMessage {

  int getWidth();

  void setWidth(int width);

  int getHeight();

  void setHeight(int height);

  List<Integer> getTrueIndices();

  void setTrueIndices(List<Integer> trueIndices);

  public class QueryRareWordResult {
    private int width;
    private int height;
    private byte[] pattern;

    public int getWidth() {
      return width;
    }

    public void setWidth(int width) {
      this.width = width;
    }

    public int getHeight() {
      return height;
    }

    public void setHeight(int height) {
      this.height = height;
    }

    public byte[] getPattern() {
      return pattern;
    }

    public void setPattern(byte[] pattern) {
      this.pattern = pattern;
    }
  }
}
