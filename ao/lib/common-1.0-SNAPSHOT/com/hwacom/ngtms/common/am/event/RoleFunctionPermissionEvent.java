/*
 * © HwaCom Systems Inc. 2021
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.common.am.event;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;

public class RoleFunctionPermissionEvent
    extends GwtEvent<RoleFunctionPermissionEvent.RoleFunctionPermissionEventHandler> {

  public static final Type<RoleFunctionPermissionEventHandler> TYPE =
      new Type<RoleFunctionPermissionEventHandler>();

  public enum Action {
    SAVE
  }

  private final Action action;

  public RoleFunctionPermissionEvent(Action action) {
    this.action = action;
  }

  @Override
  public Type<RoleFunctionPermissionEventHandler> getAssociatedType() {
    return TYPE;
  }

  @Override
  protected void dispatch(RoleFunctionPermissionEventHandler handler) {
    if (action == Action.SAVE) {
      handler.onSave(this);
    }
  }

  public interface RoleFunctionPermissionEventHandler extends EventHandler {

    void onSave(RoleFunctionPermissionEvent event);
  }
}