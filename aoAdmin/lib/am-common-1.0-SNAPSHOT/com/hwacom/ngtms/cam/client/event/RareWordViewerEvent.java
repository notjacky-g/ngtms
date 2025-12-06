/*
 * © HwaCom Systems Inc. 2021
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.cam.client.event;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;

public class RareWordViewerEvent
    extends GwtEvent<RareWordViewerEvent.RareWordViewerEventHandler> {

  public static final Type<RareWordViewerEventHandler> TYPE =
      new Type<RareWordViewerEventHandler>();

  public enum Action {
    DOWNLOAD
  }

  private final Action action;

  public RareWordViewerEvent(Action action) {
    this.action = action;
  }

  @Override
  public Type<RareWordViewerEventHandler> getAssociatedType() {
    return TYPE;
  }

  @Override
  protected void dispatch(RareWordViewerEventHandler handler) {
    if (action == Action.DOWNLOAD) {
      handler.onDownload(this);
    }
  }

  public interface RareWordViewerEventHandler extends EventHandler {

    void onDownload(RareWordViewerEvent event);
  }
}