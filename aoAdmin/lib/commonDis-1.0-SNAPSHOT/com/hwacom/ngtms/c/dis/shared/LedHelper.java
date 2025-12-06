/*
 * © HwaCom Systems Inc. 2015
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.c.dis.shared;

import static com.hwacom.ngtms.c.dis.shared.LedStatus.DEFAULT_LED_MODULE;
import static com.hwacom.ngtms.c.dis.shared.LedStatus.LED_BITSET_COUNT;

import java.awt.Point;

/** @author johnson */
public class LedHelper {
  /**
   * 換算面板寬度含幾個LED版單位
   *
   * @param ledBitSetCount 一單位LED版所含燈泡數 預設為LED_BITSET_COUNT
   * @param ledModule 一個字體所需要燈泡數 預設為 DEFAULT_LED_MODULE
   * @param PanelCategory
   * @return
   */
  public static Point calculateLedAxis(
      PanelCategory panelCategory, int ledBitSetCount, int ledModule) {

    if (ledModule <= 0) {
      ledModule = DEFAULT_LED_MODULE;
    }

    if (ledBitSetCount <= 0) {
      ledBitSetCount = LED_BITSET_COUNT;
    }

    //ex: RG_2x8 row2 , column 8
    int yAxis = panelCategory.getRow();
    int xAxis = panelCategory.getColumn();

    int bitLedCountX = (xAxis * ledModule); // 實際bitLed數目=面板寬*一個字所需要燈泡數
    int bitLedCounY = (yAxis * ledModule); // 實際bitLed數目=面板高*一個字所需要燈泡數

    int ledWidth = (bitLedCountX / ledBitSetCount); // 換算成LED版

    int ledHeight = (bitLedCounY / ledBitSetCount); // 換算成LED版

    return new Point(ledWidth, ledHeight);
  }
}
