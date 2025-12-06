/*
 * © HwaCom Systems Inc. 2021
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.cam.client.event;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;

public class TitleViewEvent
    extends GwtEvent<TitleViewEvent.TitleViewEventHandler> {

  public static final Type<TitleViewEventHandler> TYPE =
      new Type<TitleViewEventHandler>();

  public enum Action {
    REFRESH_ROLE
  }

  private final Action action;

  public TitleViewEvent(Action action) {
    this.action = action;
  }

  @Override
  public Type<TitleViewEventHandler> getAssociatedType() {
    return TYPE;
  }

  @Override
  protected void dispatch(TitleViewEventHandler handler) {
    if (action == Action.REFRESH_ROLE) {
      handler.onRefreshRole(this);
    }
  }

  public interface TitleViewEventHandler extends EventHandler {

    void onRefreshRole(TitleViewEvent event);
  }
}