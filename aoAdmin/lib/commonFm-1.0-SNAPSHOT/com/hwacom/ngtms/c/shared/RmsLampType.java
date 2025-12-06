package com.hwacom.ngtms.c.shared;

public enum RmsLampType {
  GREEN(0),
  YELLOW(1),
  RED(2),
  POLICE(3),
  VERTICAL_GREEN(4),
  VERTICAL_YELLOW(5),
  VERTICAL_RED(6),
  COUNT_DOWN(7),
  ;

  private int bit;

  RmsLampType(int bit) {
    this.bit = bit;
  }

  public int getBit() {
    return bit;
  }
}
