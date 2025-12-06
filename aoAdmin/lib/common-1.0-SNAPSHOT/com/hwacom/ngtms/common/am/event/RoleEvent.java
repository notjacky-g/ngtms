/*
 * © HwaCom Systems Inc. 2021
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.common.am.event;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;

public class RoleEvent
    extends GwtEvent<RoleEvent.RoleEventHandler> {

  public static final Type<RoleEventHandler> TYPE =
      new Type<RoleEventHandler>();

  public enum Action {
    SAVE,
    ADD,
    DELETE
  }

  private final Action action;

  public RoleEvent(Action action) {
    this.action = action;
  }

  @Override
  public Type<RoleEventHandler> getAssociatedType() {
    return TYPE;
  }

  @Override
  protected void dispatch(RoleEventHandler handler) {
    if (action == Action.SAVE) {
      handler.onSave(this);
    } else if (action == Action.ADD) {
      handler.onAdd(this);
    } else if (action == Action.DELETE) {
      handler.onDelete(this);
    }
  }

  public interface RoleEventHandler extends EventHandler {

    void onSave(RoleEvent event);
    void onAdd(RoleEvent event);
    void onDelete(RoleEvent event);
  }
}