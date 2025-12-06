/*
 * © HwaCom Systems Inc. 2015
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.cam.client.ui.draw;

import com.sencha.gxt.chart.client.draw.Color;
import com.sencha.gxt.chart.client.draw.DrawComponent;
import com.sencha.gxt.chart.client.draw.sprite.RectangleSprite;
import com.sencha.gxt.chart.client.draw.sprite.Sprite;

/**
 * 一個邏輯上的繪圖物件，實際上是一個矩形的 background（{@link RectangleSprite}）以及 {@link Sprite} 的集合。 設計這個 class
 * 的目的，除了提供基礎且共用的 API 之外， 對 caller 而言，只需控制 block 而無需理會 block 上的各個 sprite。
 *
 * <p>反過來說，{@link AbstractBlock} 的實作 class， 必須詳細實作 {@link #doSetWidth()}（{@link #setWidth(double)}
 * 的實際行為，其餘以此類推）、 {@link #isDirty()}、{@link #clearDirtyFlag()} 等 method。
 *
 * @author monty.pan
 */
public abstract class AbstractBlock {
  private DrawComponent canvas;
  private RectangleSprite background;
  private double width;
  private double height;
  private double x;
  private double y;
  /**
   * {@link Sprite} 預設的 zIndex 是 10，所以預設值給 12（11 是 background）， 以確保沒有特別設定 zIndex
   * 的情況下也不會莫名其妙被蓋在下頭看不到。
   */
  private int zIndex = 12;

  private boolean visible = true;
  private boolean widthDirty;
  private boolean heightDirty;
  private boolean xDirty;
  private boolean yDirty;
  private boolean zIndexDirty;
  private boolean visibleDirty;

  //因為考量到有 background 的關係，所以要求一定要給寬度跟高度
  protected AbstractBlock(double width, double height) {
    setWidth(width);
    setHeight(height);
  }

  /**
   * 實作 canvas 移除 block 中所有 {@link Sprite} 的 method。
   *
   * <p><b>注意：</b>在此 method 中不應該呼叫 {@link #render()}。
   *
   * @see #canvasDeleteSprite(Sprite)
   */
  protected abstract void doRemoveFromCanvas();

  /**
   * 實做將 block 中所有 {@link Sprite} 加到 canvas 上的 method。
   *
   * <p><b>注意：</b>在此 method 中不應該呼叫 {@link #render()}。
   *
   * @see #canvasDeleteSprite(Sprite)
   */
  protected abstract void doPutOnCanvas();

  protected abstract void doSetX();

  protected abstract void doSetY();

  protected abstract void doSetWidth();

  protected abstract void doSetHeight();

  protected abstract void doSetZIndex();

  protected abstract void doSetVisible();

  public final DrawComponent getCanvas() {
    return canvas;
  }

  public final void putOnCanvas(DrawComponent canvas) {
    if (canvas == null) {
      throw new IllegalArgumentException("canvas 不能是 null");
    }

    this.canvas = canvas;
    if (background != null) {
      canvasAddSprite(background);
    }

    doPutOnCanvas();

    //藉由 doXXXX() 來強制 sprite 都是正確的狀態
    doSetWidth();
    doSetHeight();
    doSetX();
    doSetY();
    doSetZIndex();
    doSetVisible();

    render();
  }

  /** 從 canvas 上移除。 */
  public final void removeFromCanvas() {
    //因為 canvasDeleteSprite() 有防堵 cavnas == null 的情況
    //所以這裡就不防堵這件事情了
    removeBackground();
    doRemoveFromCanvas();
    this.canvas = null;
  }

  protected final void render() {
    if (canvas == null) {
      return;
    }
    if (!isDirty()) {
      return;
    }
    canvas.redrawSurface();
    clearDirtyFlag();
  }

  /**
   * 提供 {@link #render()} 判斷是否真的要重繪（{@link DrawComponent#redrawSurface()}）的檢查。 實作的 class
   * 視需求可能要覆寫（加入其他 dirty flag 的檢查）此 method。
   *
   * @see #clearDirtyFlag()
   */
  protected boolean isDirty() {
    //懶得替 background 判斷 dirty 了，sprite 本身的機制會處理。
    return widthDirty || heightDirty || xDirty || yDirty || zIndexDirty || visibleDirty;
  }

  /**
   * 在 {@link #render()} 完畢後會透過此 method 清除所有的 dirty flag。 實作的 class 視需求可能要覆寫（清除其他 dirty flag）此
   * method。
   */
  protected void clearDirtyFlag() {
    widthDirty = false;
    heightDirty = false;
    xDirty = false;
    yDirty = false;
    zIndexDirty = false;
    visibleDirty = false;
  }

  protected final void canvasDeleteSprite(Sprite sprite) {
    if (canvas == null) {
      return;
    }
    canvas.getSurface().deleteSprite(sprite);
  }

  protected final void canvasAddSprite(Sprite sprite) {
    if (canvas == null) {
      return;
    }
    canvas.addSprite(sprite);
  }

  public final double getX() {
    return x;
  }

  public void setX(double x) {
    if (Double.compare(this.x, x) == 0) {
      return;
    }
    this.x = x;
    this.xDirty = true;
    if (background != null) {
      background.setX(x);
    }
    doSetX();
    render();
  }

  public final double getY() {
    return y;
  }

  public void setY(double y) {
    if (Double.compare(this.y, y) == 0) {
      return;
    }
    this.y = y;
    this.yDirty = true;
    if (background != null) {
      background.setY(y);
    }
    doSetY();
    render();
  }

  public final double getWidth() {
    return width;
  }

  public void setWidth(double width) {
    if (Double.compare(this.width, width) == 0) {
      return;
    }
    this.width = width;
    this.widthDirty = true;
    if (background != null) {
      background.setWidth(width);
    }
    doSetWidth();
    render();
  }

  public final double getHeight() {
    return height;
  }

  public void setHeight(double height) {
    if (Double.compare(this.height, height) == 0) {
      return;
    }
    this.height = height;
    this.heightDirty = true;
    if (background != null) {
      background.setHeight(height);
    }
    doSetHeight();
    render();
  }

  public final int getZIndex() {
    return zIndex;
  }

  /**
   * {@link #background} 的 z-index 會是 {@link #getZIndex()} - 1， 如果 z-index 小於 1 就不會顯示在畫面上，所以 {@link
   * #setZIndex(int)} 允許的最小值為 2。 *
   *
   * @param zIndex
   */
  public void setZIndex(int zIndex) {
    if (zIndex < 2) {
      throw new IllegalArgumentException("z-index 必須大於 1");
    }
    if (this.zIndex == zIndex) {
      return;
    }
    this.zIndex = zIndex;
    this.zIndexDirty = true;
    if (background != null) {
      background.setZIndex(zIndex - 1);
    }
    doSetZIndex();
    render();
  }

  public final boolean isVisible() {
    return visible;
  }

  public void setVisible(boolean visible) {
    if (this.visible == visible) {
      return;
    }
    this.visible = visible;
    this.visibleDirty = true;
    if (background != null) {
      background.setHidden(!visible);
    }
    doSetVisible();
    render();
  }

  //Refactory follow dirty flag render flow
  public final void setBackgroundColor(Color bgColor) {
    if (background == null) {
      background = new RectangleSprite(width, height, x, y);
      background.setZIndex(zIndex - 1);
      background.setHidden(!visible);
      canvasAddSprite(background);
    }
    background.setFill(bgColor);

    if (canvas != null) {
      canvas.redrawSurface(); //因為懶得設 bgDirty，所以跳過 render() 直接呼叫 redraw()
    }
  }

  public final void removeBackground() {
    if (background == null) {
      return;
    }
    canvasDeleteSprite(background);
    background = null;
  }
}
