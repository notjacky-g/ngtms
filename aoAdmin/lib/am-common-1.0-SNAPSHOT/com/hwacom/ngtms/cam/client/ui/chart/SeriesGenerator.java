/*
 * © HwaCom Systems Inc. 2015
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.cam.client.ui.chart;

import com.sencha.gxt.chart.client.chart.series.LineSeries;
import com.sencha.gxt.chart.client.chart.series.Primitives;
import com.sencha.gxt.chart.client.draw.RGB;
import com.sencha.gxt.chart.client.draw.sprite.Sprite;

/** 提供一定數量內不重複樣式的 Series。會先改變顏色、可用顏色用完才改變 marker。 因此超過可用顏色數量 * 可用 marker 數量，就會開始重複。 */
public class SeriesGenerator {
  private static final double RADIUS = 4.0;
  private static final int MARKER_AMOUNT = 3;

  private static final RGB[] COLOR = {
    new RGB(194, 0, 36), new RGB(240, 165, 10), new RGB(32, 68, 186),
  };

  private static int index = 0;

  //Sprite 因為還會作 setFill() 的關係，所以必須重複 new instance
  private static Sprite genSprite() {
    //增減時記得修改 SPRITE_AMOUNT
    switch (index / MARKER_AMOUNT) {
      case 0:
        return Primitives.square(0, 0, RADIUS);
      case 1:
        return Primitives.circle(0, 0, RADIUS);
      case 2:
        return Primitives.diamond(0, 0, RADIUS);
      default:
        return Primitives.square(0, 0, RADIUS);
    }
  }

  /**
   * 透過次序來固定線條變化的規律
   *
   * @param inputIdx 次序
   * @return LineSeries 線條的設定資料
   */
  public static <T> LineSeries<T> gen(int inputIdx) {
    index = inputIdx;
    LineSeries<T> result = new LineSeries<>();
    RGB color = COLOR[index % COLOR.length];
    result.setStroke(color);
    result.setShowMarkers(true);
    Sprite marker = genSprite();
    marker.setFill(color);
    result.setMarkerConfig(marker);

    if (index == COLOR.length * MARKER_AMOUNT) {
      index = index % (COLOR.length * MARKER_AMOUNT);
    }
    return result;
  }
}
