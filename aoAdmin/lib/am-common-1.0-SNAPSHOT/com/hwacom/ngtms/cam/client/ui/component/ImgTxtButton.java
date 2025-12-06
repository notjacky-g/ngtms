/*
 * © HwaCom Systems Inc. 2015
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.cam.client.ui.component;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.LoadEvent;
import com.google.gwt.event.dom.client.LoadHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.widget.core.client.Composite;
import com.sencha.gxt.widget.core.client.container.HasLayout;
import com.sencha.gxt.widget.core.client.container.ResizeContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer.VerticalLayoutData;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.toolbar.LabelToolItem;

/**
 * 提供一個圖在上、文字在下的 widget。
 *
 * <p>預設大小為 300*300、文字部份的高度為 30（可透過 {@link #setTextHeight(double)} 設定）、 亦可自行指定圖片大小（{@link
 * #setImageWidth(int)}、{@link #setImageHeight(int)}）。
 *
 * @author monty.pan
 */
public class ImgTxtButton extends Composite implements HasLayout {
  private static ImgTxtButtonUiBinder uiBinder = GWT.create(ImgTxtButtonUiBinder.class);

  interface ImgTxtButtonUiBinder extends UiBinder<Widget, ImgTxtButton> {}

  @UiField ResizeContainer root;
  @UiField Image currentImage;
  @UiField LabelToolItem txt;
  @UiField VerticalLayoutData textLD;

  private ImageResource image;
  private ImageResource overImage;
  private int width;
  private int height;

  private final SimpleEventBus eventBus = new SimpleEventBus();

  public ImgTxtButton() {
    this(300, 300);
  }

  public ImgTxtButton(int w, int h) {
    initWidget(uiBinder.createAndBindUi(this));
    setPixelSize(w, h);
    currentImage.addLoadHandler(
        new LoadHandler() {

          @Override
          public void onLoad(LoadEvent event) {
            // TODO Auto-generated method stub

          }
        });

    //以 VerticalLayoutContainer 的作法，第一個參數是傳 getContainerTarget()
    //但那是 protected 的，所以就直接以實際 method getElement() 代替
    DOM.sinkEvents(root.getElement(), Event.ONCLICK | Event.ONMOUSEOUT | Event.ONMOUSEOVER);
  }

  @Override
  public void onBrowserEvent(Event event) {
    super.onBrowserEvent(event);

    switch (event.getTypeInt()) {
      case Event.ONCLICK:
        eventBus.fireEvent(new SelectEvent());
        return;
      case Event.ONMOUSEOUT:
        currentImage.setResource(image);
        return;
      case Event.ONMOUSEOVER:
        if (overImage != null) {
          currentImage.setResource(overImage);
        }
        return;
    }
  }

  public HandlerRegistration addSelectHandler(SelectHandler h) {
    return eventBus.addHandler(SelectEvent.getType(), h);
  }

  //	public void setImage(String url) {
  //		currentImage.setUrl(url);
  //	}
  //
  //	public void setImage(ImageResource resource) {
  //		currentImage.setResource(resource);
  //	}
  //
  //	public void setImageWidth(int width) {
  //		currentImage.setWidth(width + "px");
  //	}
  //
  //	public void setImageHeight(int height) {
  //		currentImage.setHeight(height + "px");
  //	}

  //TODO 字串過長的防堵
  public void setText(String text) {
    txt.setLabel(text);
  }

  /**
   * 注意：圖片區域的高度會等於整體高度減去 text 區塊的高度。
   *
   * @param height 參見 {@link VerticalLayoutData#setHeight(double)}。
   */
  public void setTextHeight(double height) {
    textLD.setHeight(height);
  }

  @Override
  public void forceLayout() {
    root.forceLayout();
  }

  @Override
  public boolean isLayoutRunning() {
    return root.isLayoutRunning();
  }

  @Override
  public boolean isOrWasLayoutRunning() {
    return root.isOrWasLayoutRunning();
  }

  /** @return the image */
  public ImageResource getImage() {
    return image;
  }

  /** @param image the image to set */
  public void setImage(ImageResource image) {
    this.image = image;
    currentImage.setResource(image);
  }

  /** @return the overImage */
  public ImageResource getOverImage() {
    return overImage;
  }

  /** @param overImage the overImage to set */
  public void setOverImage(ImageResource overImage) {
    this.overImage = overImage;
  }

  /** @return the width */
  public int getImageWidth() {
    return width;
  }

  /** @param width the width to set */
  public void setImageWidth(int width) {
    this.width = width;
    currentImage.setWidth(width + "px");
  }

  /** @return the height */
  public int getImageHeight() {
    return height;
  }

  /** @param height the height to set */
  public void setImageHeight(int height) {
    this.height = height;
    currentImage.setHeight(height + "px");
  }
}
