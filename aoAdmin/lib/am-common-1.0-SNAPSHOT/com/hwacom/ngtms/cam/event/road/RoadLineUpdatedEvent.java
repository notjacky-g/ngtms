/*
 * © HwaCom Systems Inc. 2015
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.cam.event.road;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.hwacom.ngtms.cam.event.road.RoadLineUpdatedEvent.RoadLineUpdatedEventHandler;

public class RoadLineUpdatedEvent extends GwtEvent<RoadLineUpdatedEventHandler> {

  public static final Type<RoadLineUpdatedEventHandler> TYPE =
      new Type<RoadLineUpdatedEventHandler>();

  public enum Action {
    ROADLINE_UPDATED
  }

  private final Action action;

  public RoadLineUpdatedEvent(Action action) {
    this.action = action;
  }

  @Override
  public Type<RoadLineUpdatedEventHandler> getAssociatedType() {
    return TYPE;
  }

  @Override
  protected void dispatch(RoadLineUpdatedEventHandler handler) {
    if (action == Action.ROADLINE_UPDATED) {
      handler.roadLineUpdated(this);
    }
  }

  public interface RoadLineUpdatedEventHandler extends EventHandler {

    void roadLineUpdated(RoadLineUpdatedEvent event);
  }
}
