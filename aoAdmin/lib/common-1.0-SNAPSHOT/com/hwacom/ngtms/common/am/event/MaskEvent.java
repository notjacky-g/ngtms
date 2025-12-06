/*
 * © HwaCom Systems Inc. 2021
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.common.am.event;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;

public class MaskEvent
    extends GwtEvent<MaskEvent.MaskEventHandler> {

  public static final Type<MaskEventHandler> TYPE =
      new Type<MaskEventHandler>();

  public enum Action {
    MASK
  }

  private final Action action;

  public MaskEvent(Action action) {
    this.action = action;
  }

  @Override
  public Type<MaskEventHandler> getAssociatedType() {
    return TYPE;
  }

  @Override
  protected void dispatch(MaskEventHandler handler) {
    if (action == Action.MASK) {
      handler.onMask(this);
    }
  }

  public interface MaskEventHandler extends EventHandler {

    void onMask(MaskEvent event);
  }
}