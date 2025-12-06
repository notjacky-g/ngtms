/*
 * © HwaCom Systems Inc. 2015
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.cam.client.ui.draw;

import com.sencha.gxt.chart.client.draw.Color;
import com.sencha.gxt.chart.client.draw.RGB;
import com.sencha.gxt.chart.client.draw.engine.Canvas2d;
import com.sencha.gxt.chart.client.draw.engine.SVG;
import com.sencha.gxt.chart.client.draw.engine.VML;
import com.sencha.gxt.chart.client.draw.sprite.TextSprite;
import java.util.ArrayList;

/**
 * 俗稱「文字方塊」。在 layout 上做了幾點特化 / 限制：
 *
 * <ul>
 *   <li>ASCII 的 '0' 到 'z' 會轉換成全形（加上 {@link #ASCII_UNICODE_DIFF}）
 *   <li>文字的 X 軸是以分散對齊的方式排列
 *   <li>文字的 Y 軸是以垂直置中的方式排列
 * </ul>
 *
 * <p>{@link TextSprite} 在 {@link SVG} 上會出現文字的 Y 值不如（人類認知）預期， 因此程式用實驗結果（而非學理依據）做出調整， 目前僅在下列環境 /
 * 條件下，通過小樣本數的人類判斷：
 *
 * <ul>
 *   <li>僅測試 {@link SVG}，沒有測試 {@link VML}、{@link Canvas2d}
 *   <li>字體大小介於 25～125
 *   <li>除了字體大小之外，沒有作其他字型設定
 *   <li>Chrome 35、FireFox 30（Windows 7 64bit）
 * </ul>
 *
 * <p>TODO 目前還沒有實作的功能：
 *
 * <ul>
 *   <li>不能全部預設為全形字
 *   <li>setWidth()、setHeight()：只能在 constructor 指定
 *   <li>setMinGap()：只能在 constructor 指定
 *   <li>setFontSize()：只能在 constructor 指定
 * </ul>
 *
 * @author monty.pan
 */
public class TextBlock extends AbstractBlock {
  public static final int MAGIC_Y_COEFFICIENT = 5;
  public static final int ASCII_UNICODE_DIFF = 65248;

  private int fontSize;
  private double minGap;
  private String text;
  private boolean textDirty;
  private Color textColor = RGB.BLACK;
  private int magicYOffset;

  private ArrayList<TextSprite> textSprites = new ArrayList<TextSprite>();

  /** 建立只有一個 character 的 TextBlock，bbox 的長度跟寬度為 fontSize。 */
  public static TextBlock genCharBlock(double x, double y, int fontSize) {
    return new TextBlock(x, y, fontSize, fontSize, fontSize, 0);
  }

  //XXX 沒有遵循 (w, h, x, y) 的慣例... [飛踢]
  public TextBlock(double x, double y, double width, double height, int fontSize, double minGap) {
    super(width, height);
    setX(x);
    setY(y);
    this.fontSize = fontSize;
    this.magicYOffset = fontSize / MAGIC_Y_COEFFICIENT;
    this.minGap = minGap;
  }

  public TextBlock(double x, double y, double width, double height, int fontSize) {
    this(x, y, width, height, fontSize, 0);
  }

  public void setText(String text) {
    if (text.length() * (minGap + fontSize) + minGap > getWidth()) {
      throw new UnsupportedOperationException("字串長度大於可容納寬度");
    }

    doRemoveFromCanvas();
    textSprites.clear();

    this.text = text;

    double realY = calTextY();
    for (int i = 0; i < text.length(); i++) {
      //把 0～z 的字元改為全形
      char c =
          text.charAt(i) >= '0' && text.charAt(i) <= 'z'
              ? (char) (text.charAt(i) + ASCII_UNICODE_DIFF)
              : text.charAt(i);
      TextSprite ts = new TextSprite("" + c);
      textSprites.add(ts);
      ts.setFontSize(fontSize);
      ts.setY(realY);
      ts.setX(getX() + calTextX(i));
      ts.setZIndex(getZIndex());
      ts.setFill(textColor);
    }

    doPutOnCanvas();

    textDirty = true;
    render();
  }

  public void setTextColor(Color color) {
    textColor = color;
    for (TextSprite ts : textSprites) {
      ts.setFill(color);
    }
    textDirty = true;
    render();
  }

  @Override
  protected void doRemoveFromCanvas() {
    for (TextSprite ts : textSprites) {
      canvasDeleteSprite(ts);
    }
  }

  @Override
  protected void doPutOnCanvas() {
    for (TextSprite ts : textSprites) {
      canvasAddSprite(ts);
    }
  }

  @Override
  protected void doSetX() {
    for (int i = 0; i < textSprites.size(); i++) {
      textSprites.get(i).setX(getX() + calTextX(i));
    }
  }

  @Override
  protected void doSetY() {
    double realY = calTextY();
    for (TextSprite ts : textSprites) {
      ts.setY(realY);
    }
  }

  //還不能設定高跟寬，所以實作內容先留白
  @Override
  protected void doSetWidth() {}

  @Override
  protected void doSetHeight() {}

  @Override
  protected void doSetZIndex() {
    for (TextSprite ts : textSprites) {
      ts.setZIndex(getZIndex());
    }
  }

  @Override
  protected void doSetVisible() {
    for (TextSprite ts : textSprites) {
      ts.setHidden(!isVisible());
    }
  }

  @Override
  protected boolean isDirty() {
    return textDirty || super.isDirty();
  }

  @Override
  protected void clearDirtyFlag() {
    super.clearDirtyFlag();
    textDirty = false;
  }

  /**
   * 計算每個字的 X 軸。如果只有一個字就直接置中，不然就依照 width 跟 minGap 與字數平均分配。
   *
   * @param index 第幾個字（起始為 0）
   * @return
   */
  private double calTextX(int index) {
    if (text.length() == 1) {
      return (getWidth() - fontSize) / 2.0;
    }

    return minGap
        +
        //分子：寬度減去頭尾的 gap、還有每個字要佔用的大小。
        //就會得到各個字之間的間隔大小
        (getWidth() - minGap * 2 - fontSize * text.length()) / (text.length() - 1) * index
        + fontSize * index;
  }

  private double calTextY() {
    return getY() - magicYOffset + ((getHeight() - fontSize) / 2);
  }
}
