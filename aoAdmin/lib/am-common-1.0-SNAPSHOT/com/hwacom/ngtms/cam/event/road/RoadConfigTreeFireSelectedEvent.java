/*
 * © HwaCom Systems Inc. 2015
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.cam.event.road;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.hwacom.ngtms.cam.event.road.RoadConfigTreeFireSelectedEvent.RoadConfigTreeFireSelectedEventHandler;

public class RoadConfigTreeFireSelectedEvent
    extends GwtEvent<RoadConfigTreeFireSelectedEventHandler> {

  public static final Type<RoadConfigTreeFireSelectedEventHandler> TYPE =
      new Type<RoadConfigTreeFireSelectedEventHandler>();

  public enum Action {
    SELECT_ONE_ITEM
  }

  private final Action action;

  public RoadConfigTreeFireSelectedEvent(Action action) {
    this.action = action;
  }

  @Override
  public Type<RoadConfigTreeFireSelectedEventHandler> getAssociatedType() {
    return TYPE;
  }

  @Override
  protected void dispatch(RoadConfigTreeFireSelectedEventHandler handler) {
    if (action == Action.SELECT_ONE_ITEM) {
      handler.selectOneItem(this);
    }
  }

  public interface RoadConfigTreeFireSelectedEventHandler extends EventHandler {

    void selectOneItem(RoadConfigTreeFireSelectedEvent event);
  }
}
