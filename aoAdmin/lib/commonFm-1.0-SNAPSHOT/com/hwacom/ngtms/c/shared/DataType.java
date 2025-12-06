/*
 * © HwaCom Systems Inc. 2015
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.c.shared;

/**
 * 資料真實性
 *
 * @author Chris
 */
public enum DataType {
  /** 模擬 */
  Simulation,
  /** 真實 */
  Real;

  /**
   * 透過response code取得資料型態<br>
   * 00H|01H|02H| 為 DataType.Real<br>
   * 04H|05H|14H|15H|F4H|F5H 為 DataType.Simulation<br>
   *
   * @param responseType
   * @return
   */
  public static DataType getDateType(int responseType) {

    switch (responseType) {
      case 0:
        return DataType.Real;
      case 1:
        return DataType.Real;
      case 2:
        return DataType.Real;
      default:
        // 04H|05H|14H|15H|F4H|F5H 為模擬
        return DataType.Simulation;
    }
  }
}
