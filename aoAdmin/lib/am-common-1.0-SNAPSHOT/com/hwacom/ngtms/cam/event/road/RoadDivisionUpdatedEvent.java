/*
 * © HwaCom Systems Inc. 2015
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.cam.event.road;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.hwacom.ngtms.cam.event.road.RoadDivisionUpdatedEvent.RoadDivisionUpdatedEventHandler;

public class RoadDivisionUpdatedEvent extends GwtEvent<RoadDivisionUpdatedEventHandler> {

  public static final Type<RoadDivisionUpdatedEventHandler> TYPE =
      new Type<RoadDivisionUpdatedEventHandler>();

  public enum Action {
    ROADDIVISION_UPDATED
  }

  private final Action action;

  public RoadDivisionUpdatedEvent(Action action) {
    this.action = action;
  }

  @Override
  public Type<RoadDivisionUpdatedEventHandler> getAssociatedType() {
    return TYPE;
  }

  @Override
  protected void dispatch(RoadDivisionUpdatedEventHandler handler) {
    if (action == Action.ROADDIVISION_UPDATED) {
      handler.roadDivisionUpdated(this);
    }
  }

  public interface RoadDivisionUpdatedEventHandler extends EventHandler {

    void roadDivisionUpdated(RoadDivisionUpdatedEvent event);
  }
}
