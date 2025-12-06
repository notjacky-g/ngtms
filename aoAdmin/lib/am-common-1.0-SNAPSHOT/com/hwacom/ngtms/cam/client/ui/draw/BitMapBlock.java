/*
 * © HwaCom Systems Inc. 2015
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.cam.client.ui.draw;

import com.sencha.gxt.chart.client.draw.Color;
import com.sencha.gxt.chart.client.draw.sprite.RectangleSprite;
import java.util.ArrayList;
import java.util.List;

/**
 * 提供一個將 {@link Color} list 轉換成（視覺意義上的）點陣圖形的 block。
 *
 * @author monty.pan
 */
public class BitMapBlock extends AbstractBlock {
  //這是假設 BitMapBlock 直接覆蓋在原來的區塊上，所以要給一個比較大的值。
  public static final int DEFAULT_ZINDEX = 10001;
  private int row;
  private int column;
  private double wUnit;
  private double hUnit;
  private double gridSize;
  private Color gridColor = Color.NONE;
  private boolean matrixDirty;
  private boolean gridDirty;
  private ArrayList<RectangleSprite> bitSprites = new ArrayList<>();
  private ArrayList<RectangleSprite> gridSprites = new ArrayList<>();

  public BitMapBlock(double width, double height) {
    this(width, height, 0, 0);
  }

  public BitMapBlock(double width, double height, double x, double y) {
    super(width, height);
    setX(x);
    setY(y);
    setZIndex(DEFAULT_ZINDEX);
  }

  /**
   * 設定每個 bit 的 border。當 color 為 {@link Color#NONE} 或是 size 為 0，就等同於移除 border。
   *
   * @param color
   * @param size
   */
  public void setBorder(Color color, double size) {
    if (size < 0) {
      throw new IllegalArgumentException("grid size (" + size + ") 必須大於 0");
    }

    if (gridColor == color && Double.compare(gridSize, size) == 0) {
      return;
    }

    gridColor = color;
    gridSize = size;

    if (row == 0 || column == 0) {
      return;
    }

    //過了上面的 if，setColorList() 會保證下面的迴圈能正常執行
    for (int index = 0; index <= row + column + 1; index++) {
      gridSprites.get(index).setFill(color);
    }

    resetSprite();

    gridDirty = true;
    render();
  }

  /**
   * list 轉成二維陣列的邏輯為「由左到右、然後由上而下」。
   *
   * @param columnSize bit map 的寬度（aka：x 軸有幾個點）
   * @param rowSize bit map 的高度（aka：y 軸有幾個點）
   * @param colorList
   */
  // 因為 row / column 參數重設定，所以 grid 也在這裡重新設定。
  public void setColorList(List<Color> colorList, int columnSize, int rowSize) {
    if (columnSize < 1) {
      throw new IllegalArgumentException("column (" + columnSize + ") 不可小於 1");
    }
    if (rowSize < 1) {
      throw new IllegalArgumentException("row (" + rowSize + ") 不可小於 1");
    }
    if (colorList.size() != columnSize * rowSize) {
      throw new IllegalArgumentException(
          "color 數量 ("
              + colorList.size()
              + ") != column ("
              + columnSize
              + ") * row ("
              + rowSize
              + ")");
    }

    row = rowSize;
    column = columnSize;

    matrixDirty = true;
    doRemoveFromCanvas();
    bitSprites.clear();
    gridSprites.clear();

    for (int yIndex = 0; yIndex < row; yIndex++) {
      for (int xIndex = 0; xIndex < column; xIndex++) {
        RectangleSprite bit = new RectangleSprite();
        bit.setFill(colorList.get(xIndex + yIndex * column));
        bit.setZIndex(getZIndex());
        bit.setHidden(!isVisible());
        bitSprites.add(bit);
      }
    }

    //不管有沒有要顯示 border，把需要的 sprite 先加了再說
    for (int index = 0; index <= row + column + 1; index++) {
      RectangleSprite grid = new RectangleSprite();
      grid.setZIndex(getZIndex() + 1);
      grid.setHidden(!isVisible());
      grid.setFill(gridColor);
      gridSprites.add(grid);
    }

    resetSprite();
    doPutOnCanvas();
    render();
  }

  /** 重新計算所有 sprite 的位置與大小 */
  private void resetSprite() {
    //setColorList() 有檢查 size 與 column / row 的關係，所以只要判斷 row / column 就夠了
    //不能用 bitSprites 的原因是如果 caller 是源自於 constructor，bitSprites 還沒 init 會是 null
    if (row == 0 || column == 0) {
      return;
    }

    wUnit = getWidth() * 1.0 / column;
    hUnit = getHeight() * 1.0 / row;

    for (int yIndex = 0; yIndex < row; yIndex++) {
      for (int xIndex = 0; xIndex < column; xIndex++) {
        RectangleSprite bit = bitSprites.get(xIndex + yIndex * column);
        bit.setWidth(wUnit);
        bit.setHeight(hUnit);
        bit.setX(getX() + xIndex * wUnit);
        bit.setY(getY() + yIndex * hUnit);
      }
    }

    // ==== grid 部份 ==== //
    // 根本沒有大小或是沒有顏色，就跳過
    if (gridSprites.size() == 0 || gridColor == Color.NONE) {
      return;
    }

    for (int yIndex = 0; yIndex <= row; yIndex++) {
      RectangleSprite grid = gridSprites.get(yIndex);
      grid.setWidth(getWidth());
      grid.setHeight(gridSize * 2.0);
      grid.setX(getX());
      grid.setY(getY() + hUnit * yIndex - gridSize);
    }
    //頭尾重新校正
    RectangleSprite top = gridSprites.get(0);
    top.setY(getY());
    top.setHeight(gridSize);
    RectangleSprite bottom = gridSprites.get(row);
    bottom.setHeight(gridSize);

    for (int xIndex = 0; xIndex <= column; xIndex++) {
      RectangleSprite grid = gridSprites.get(row + 1 + xIndex);
      grid.setWidth(gridSize * 2.0);
      grid.setHeight(getHeight());
      grid.setX(getX() + wUnit * xIndex - gridSize);
      grid.setY(getY());
    }
    //左右重新校正
    RectangleSprite left = gridSprites.get(row + 1);
    left.setX(getX());
    left.setWidth(gridSize);
    RectangleSprite right = gridSprites.get(row + column + 1);
    right.setWidth(gridSize);
    // ======== //
  }

  @Override
  protected void doSetX() {
    resetSprite();
  }

  @Override
  public void doSetY() {
    resetSprite();
  }

  @Override
  protected void doSetWidth() {
    resetSprite();
  }

  @Override
  protected void doSetHeight() {
    resetSprite();
  }

  @Override
  protected void doSetZIndex() {
    for (RectangleSprite bit : bitSprites) {
      bit.setZIndex(getZIndex());
    }
    for (RectangleSprite grid : gridSprites) {
      grid.setZIndex(getZIndex());
    }
  }

  @Override
  protected void doSetVisible() {
    for (RectangleSprite bit : bitSprites) {
      bit.setHidden(!isVisible());
    }
    for (RectangleSprite grid : gridSprites) {
      grid.setHidden(!isVisible());
    }
  }

  @Override
  protected void doRemoveFromCanvas() {
    for (RectangleSprite bit : bitSprites) {
      canvasDeleteSprite(bit);
    }
    for (RectangleSprite grid : gridSprites) {
      canvasDeleteSprite(grid);
    }
  }

  @Override
  protected void doPutOnCanvas() {
    for (RectangleSprite bit : bitSprites) {
      canvasAddSprite(bit);
    }
    for (RectangleSprite grid : gridSprites) {
      canvasAddSprite(grid);
    }
  }

  @Override
  protected boolean isDirty() {
    return matrixDirty || gridDirty || super.isDirty();
  }

  @Override
  protected void clearDirtyFlag() {
    super.clearDirtyFlag();
    matrixDirty = false;
    gridDirty = false;
  }
}
