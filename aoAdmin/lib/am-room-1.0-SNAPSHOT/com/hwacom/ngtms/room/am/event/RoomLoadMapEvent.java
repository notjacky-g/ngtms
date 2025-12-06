/*
 * © HwaCom Systems Inc. 2021
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.room.am.event;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;

public class RoomLoadMapEvent
    extends GwtEvent<RoomLoadMapEvent.RoomLoadMapEventHandler> {

  public static final Type<RoomLoadMapEventHandler> TYPE =
      new Type<RoomLoadMapEventHandler>();

  public enum Action {
    LOAD
  }

  private final Action action;

  public RoomLoadMapEvent(Action action) {
    this.action = action;
  }

  @Override
  public Type<RoomLoadMapEventHandler> getAssociatedType() {
    return TYPE;
  }

  @Override
  protected void dispatch(RoomLoadMapEventHandler handler) {
    if (action == Action.LOAD) {
      handler.onLoad(this);
    }
  }

  public interface RoomLoadMapEventHandler extends EventHandler {

    void onLoad(RoomLoadMapEvent event);
  }
}