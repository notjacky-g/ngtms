/*
 * © HwaCom Systems Inc. 2015
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.cam.view.home;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Widget;
import com.hwacom.ngtms.common.shared.dto.FunctionPermissionDTO;
import com.sencha.gxt.widget.core.client.Composite;
import com.sencha.gxt.widget.core.client.container.HasLayout;
import com.sencha.gxt.widget.core.client.container.ResizeContainer;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;

/**
 * 提供一個圖在上、文字在下的 widget。
 *
 * <p>預設大小為 161*161、 亦可自行指定圖片大小（{@link #setImageWidth(int)}、 {@link #setImageHeight(int)}）。
 *
 * @author monty.pan
 */
public class ImageItem extends Composite implements HasLayout {
  private static ImgTxtButtonUiBinder uiBinder = GWT.create(ImgTxtButtonUiBinder.class);

  interface ImgTxtButtonUiBinder extends UiBinder<Widget, ImageItem> {}

  private static int CURRENT_IMAGE_WIDTH = 161;
  private static int CURRENT_IMAGE_HEIGHT = 161;

  @UiField ResizeContainer root;
  @UiField Image currentImage;

  private ImageResource image;
  private ImageResource overImage;
  private int width;
  private int height;
  private FunctionPermissionDTO functionPermissionDTO;

  private final SimpleEventBus eventBus = new SimpleEventBus();

  public ImageItem(FunctionPermissionDTO functionPermissionDTO) {
    this(CURRENT_IMAGE_WIDTH, CURRENT_IMAGE_HEIGHT, functionPermissionDTO);
  }

  public ImageItem(int w, int h, FunctionPermissionDTO functionPermissionDTO) {
    this.functionPermissionDTO = functionPermissionDTO;
    initWidget(uiBinder.createAndBindUi(this));
    setPixelSize(w, h);
    // currentImage.addLoadHandler(new LoadHandler() {
    //
    // @Override
    // public void onLoad(LoadEvent event) {
    // // TODO Auto-generated method stub
    //
    // }
    // });

    // 以 VerticalLayoutContainer 的作法，第一個參數是傳 getContainerTarget()
    // 但那是 protected 的，所以就直接以實際 method getElement() 代替
    DOM.sinkEvents(root.getElement(), Event.ONCLICK | Event.ONMOUSEOUT | Event.ONMOUSEOVER);
  }

  @Override
  public void onBrowserEvent(Event event) {
    super.onBrowserEvent(event);

    switch (event.getTypeInt()) {
      case Event.ONCLICK:
        GWT.log("functionPermissionDTO =>" + functionPermissionDTO.toString());
        Window.open(GWT.getHostPageBaseURL() + functionPermissionDTO.getUrlMapping(), "_blank", "");
        //eventBus.fireEvent(new SelectEvent());
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

  public void setImage(ImageResource image, ImageResource overImage) {
    this.image = image;
    this.overImage = overImage;
    currentImage.setWidth(CURRENT_IMAGE_WIDTH + "px");
    currentImage.setHeight(CURRENT_IMAGE_HEIGHT + "px");
    currentImage.setResource(image);
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
