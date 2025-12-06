/*
 * © HwaCom Systems Inc. 2021
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.common.am.event;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;

public class UnitViewerEvent
    extends GwtEvent<UnitViewerEvent.UnitViewerEventHandler> {

  public static final Type<UnitViewerEventHandler> TYPE =
      new Type<UnitViewerEventHandler>();

  public enum Action {
    UNIT_ADDED,
    UNIT_REMOVED,
    UNIT_UPDATED
  }

  private final Action action;

  public UnitViewerEvent(Action action) {
    this.action = action;
  }

  @Override
  public Type<UnitViewerEventHandler> getAssociatedType() {
    return TYPE;
  }

  @Override
  protected void dispatch(UnitViewerEventHandler handler) {
    if (action == Action.UNIT_ADDED) {
      handler.onUnitAdded(this);
    } else if (action == Action.UNIT_REMOVED) {
      handler.onUnitRemoved(this);
    } else if (action == Action.UNIT_UPDATED) {
      handler.onUnitUpdated(this);
    }
  }

  public interface UnitViewerEventHandler extends EventHandler {

    void onUnitAdded(UnitViewerEvent event);
    void onUnitRemoved(UnitViewerEvent event);
    void onUnitUpdated(UnitViewerEvent event);
  }
}