/*
 * © HwaCom Systems Inc. 2021
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.cam.client.event;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;

public class RareWordQueryViewerEvent
    extends GwtEvent<RareWordQueryViewerEvent.RareWordQueryViewerEventHandler> {

  public static final Type<RareWordQueryViewerEventHandler> TYPE =
      new Type<RareWordQueryViewerEventHandler>();

  public enum Action {
    QUERY_LIST,
    QUERY
  }

  private final Action action;

  public RareWordQueryViewerEvent(Action action) {
    this.action = action;
  }

  @Override
  public Type<RareWordQueryViewerEventHandler> getAssociatedType() {
    return TYPE;
  }

  @Override
  protected void dispatch(RareWordQueryViewerEventHandler handler) {
    if (action == Action.QUERY_LIST) {
      handler.onQueryList(this);
    } else if (action == Action.QUERY) {
      handler.onQuery(this);
    }
  }

  public interface RareWordQueryViewerEventHandler extends EventHandler {

    void onQueryList(RareWordQueryViewerEvent event);
    void onQuery(RareWordQueryViewerEvent event);
  }
}