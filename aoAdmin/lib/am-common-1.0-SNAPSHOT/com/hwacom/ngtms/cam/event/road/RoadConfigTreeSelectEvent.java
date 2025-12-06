/*
 * © HwaCom Systems Inc. 2015
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.cam.event.road;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.hwacom.ngtms.cam.event.road.RoadConfigTreeSelectEvent.RoadConfigTreeSelectEventHandler;

public class RoadConfigTreeSelectEvent extends GwtEvent<RoadConfigTreeSelectEventHandler> {

  public static final Type<RoadConfigTreeSelectEventHandler> TYPE =
      new Type<RoadConfigTreeSelectEventHandler>();

  public enum Action {
    SELECT_ONE_ITEM
  }

  private final Action action;

  public RoadConfigTreeSelectEvent(Action action) {
    this.action = action;
  }

  @Override
  public Type<RoadConfigTreeSelectEventHandler> getAssociatedType() {
    return TYPE;
  }

  @Override
  protected void dispatch(RoadConfigTreeSelectEventHandler handler) {
    if (action == Action.SELECT_ONE_ITEM) {
      handler.selectOneItem(this);
    }
  }

  public interface RoadConfigTreeSelectEventHandler extends EventHandler {

    void selectOneItem(RoadConfigTreeSelectEvent event);
  }
}
