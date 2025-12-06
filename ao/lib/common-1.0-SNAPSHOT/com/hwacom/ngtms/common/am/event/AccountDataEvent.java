/*
 * © HwaCom Systems Inc. 2021
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.common.am.event;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;

public class AccountDataEvent
    extends GwtEvent<AccountDataEvent.AccountDataEventHandler> {

  public static final Type<AccountDataEventHandler> TYPE =
      new Type<AccountDataEventHandler>();

  public enum Action {
    ROLE_INIT_DATA
  }

  private final Action action;

  public AccountDataEvent(Action action) {
    this.action = action;
  }

  @Override
  public Type<AccountDataEventHandler> getAssociatedType() {
    return TYPE;
  }

  @Override
  protected void dispatch(AccountDataEventHandler handler) {
    if (action == Action.ROLE_INIT_DATA) {
      handler.onRoleInitData(this);
    }
  }

  public interface AccountDataEventHandler extends EventHandler {

    void onRoleInitData(AccountDataEvent event);
  }
}