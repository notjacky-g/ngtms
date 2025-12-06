/*
 * © HwaCom Systems Inc. 2021
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.cam.client.event;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;

public class BackgroundGraphicEditViewerEvent
    extends GwtEvent<BackgroundGraphicEditViewerEvent.BackgroundGraphicEditViewerEventHandler> {

  public static final Type<BackgroundGraphicEditViewerEventHandler> TYPE =
      new Type<BackgroundGraphicEditViewerEventHandler>();

  public enum Action {
    DEVICE_CHANGED,
    SELECT_GRAPHIC,
    DOWNLOAD_ALL,
    DOWNLOAD,
    CREATE_GRAPHIC,
    MODIFY_GRAPHIC,
    REMOVE_GRAPHIC
  }

  private final Action action;

  public BackgroundGraphicEditViewerEvent(Action action) {
    this.action = action;
  }

  @Override
  public Type<BackgroundGraphicEditViewerEventHandler> getAssociatedType() {
    return TYPE;
  }

  @Override
  protected void dispatch(BackgroundGraphicEditViewerEventHandler handler) {
    if (action == Action.DEVICE_CHANGED) {
      handler.onDeviceChanged(this);
    } else if (action == Action.SELECT_GRAPHIC) {
      handler.onSelectGraphic(this);
    } else if (action == Action.DOWNLOAD_ALL) {
      handler.onDownloadAll(this);
    } else if (action == Action.DOWNLOAD) {
      handler.onDownload(this);
    } else if (action == Action.CREATE_GRAPHIC) {
      handler.onCreateGraphic(this);
    } else if (action == Action.MODIFY_GRAPHIC) {
      handler.onModifyGraphic(this);
    } else if (action == Action.REMOVE_GRAPHIC) {
      handler.onRemoveGraphic(this);
    }
  }

  public interface BackgroundGraphicEditViewerEventHandler extends EventHandler {

    void onDeviceChanged(BackgroundGraphicEditViewerEvent event);
    void onSelectGraphic(BackgroundGraphicEditViewerEvent event);
    void onDownloadAll(BackgroundGraphicEditViewerEvent event);
    void onDownload(BackgroundGraphicEditViewerEvent event);
    void onCreateGraphic(BackgroundGraphicEditViewerEvent event);
    void onModifyGraphic(BackgroundGraphicEditViewerEvent event);
    void onRemoveGraphic(BackgroundGraphicEditViewerEvent event);
  }
}