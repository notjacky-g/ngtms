/*
 * © HwaCom Systems Inc. 2021
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.common.am.event;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;

public class UserEvent
    extends GwtEvent<UserEvent.UserEventHandler> {

  public static final Type<UserEventHandler> TYPE =
      new Type<UserEventHandler>();

  public enum Action {
    SAVE,
    ADD,
    DELETE
  }

  private final Action action;

  public UserEvent(Action action) {
    this.action = action;
  }

  @Override
  public Type<UserEventHandler> getAssociatedType() {
    return TYPE;
  }

  @Override
  protected void dispatch(UserEventHandler handler) {
    if (action == Action.SAVE) {
      handler.onSave(this);
    } else if (action == Action.ADD) {
      handler.onAdd(this);
    } else if (action == Action.DELETE) {
      handler.onDelete(this);
    }
  }

  public interface UserEventHandler extends EventHandler {

    void onSave(UserEvent event);
    void onAdd(UserEvent event);
    void onDelete(UserEvent event);
  }
}