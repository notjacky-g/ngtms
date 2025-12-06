/*
 * © HwaCom Systems Inc. 2021
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.common.am.event;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;

public class UserViewerEvent
    extends GwtEvent<UserViewerEvent.UserViewerEventHandler> {

  public static final Type<UserViewerEventHandler> TYPE =
      new Type<UserViewerEventHandler>();

  public enum Action {
    GRID_READY
  }

  private final Action action;

  public UserViewerEvent(Action action) {
    this.action = action;
  }

  @Override
  public Type<UserViewerEventHandler> getAssociatedType() {
    return TYPE;
  }

  @Override
  protected void dispatch(UserViewerEventHandler handler) {
    if (action == Action.GRID_READY) {
      handler.onGridReady(this);
    }
  }

  public interface UserViewerEventHandler extends EventHandler {

    void onGridReady(UserViewerEvent event);
  }
}