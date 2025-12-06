/*
 * © HwaCom Systems Inc. 2015
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.c.shared;

public enum CarType {
  MOTER,
  SMALL_CAR,
  BIG_CAR,
  CONNECT_CAR;

  public static CarType getEtagCarType(String s) {
    switch (s) {
      case "00H":
        return SMALL_CAR;
      case "01H":
        return BIG_CAR;
      case "02H":
        return CONNECT_CAR;
    }
    return null;
  }
}
