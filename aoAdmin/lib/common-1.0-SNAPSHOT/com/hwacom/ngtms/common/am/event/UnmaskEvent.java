/*
 * © HwaCom Systems Inc. 2021
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.common.am.event;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;

public class UnmaskEvent
    extends GwtEvent<UnmaskEvent.UnmaskEventHandler> {

  public static final Type<UnmaskEventHandler> TYPE =
      new Type<UnmaskEventHandler>();

  public enum Action {
    UNMASK
  }

  private final Action action;

  public UnmaskEvent(Action action) {
    this.action = action;
  }

  @Override
  public Type<UnmaskEventHandler> getAssociatedType() {
    return TYPE;
  }

  @Override
  protected void dispatch(UnmaskEventHandler handler) {
    if (action == Action.UNMASK) {
      handler.onUnmask(this);
    }
  }

  public interface UnmaskEventHandler extends EventHandler {

    void onUnmask(UnmaskEvent event);
  }
}