/*
 * © HwaCom Systems Inc. 2021
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.cam.client.event;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;

public class BackgroundGraphicQueryViewerEvent
    extends GwtEvent<BackgroundGraphicQueryViewerEvent.BackgroundGraphicQueryViewerEventHandler> {

  public static final Type<BackgroundGraphicQueryViewerEventHandler> TYPE =
      new Type<BackgroundGraphicQueryViewerEventHandler>();

  public enum Action {
    QUERY_LIST,
    QUERY_GRAPHIC
  }

  private final Action action;

  public BackgroundGraphicQueryViewerEvent(Action action) {
    this.action = action;
  }

  @Override
  public Type<BackgroundGraphicQueryViewerEventHandler> getAssociatedType() {
    return TYPE;
  }

  @Override
  protected void dispatch(BackgroundGraphicQueryViewerEventHandler handler) {
    if (action == Action.QUERY_LIST) {
      handler.onQueryList(this);
    } else if (action == Action.QUERY_GRAPHIC) {
      handler.onQueryGraphic(this);
    }
  }

  public interface BackgroundGraphicQueryViewerEventHandler extends EventHandler {

    void onQueryList(BackgroundGraphicQueryViewerEvent event);
    void onQueryGraphic(BackgroundGraphicQueryViewerEvent event);
  }
}