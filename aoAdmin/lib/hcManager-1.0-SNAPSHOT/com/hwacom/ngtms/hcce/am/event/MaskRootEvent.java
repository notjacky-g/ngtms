/*
 * © HwaCom Systems Inc. 2013
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.hcce.am.event;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.hwacom.ngtms.hcce.am.event.MaskRootEvent.MaskRootEventHandler;

public class MaskRootEvent extends GwtEvent<MaskRootEventHandler> {

  public static final Type<MaskRootEventHandler> TYPE = new Type<MaskRootEventHandler>();

  public enum Action {
    TO_MASK
  }

  private final Action action;

  public MaskRootEvent(Action action) {
    this.action = action;
  }

  @Override
  public Type<MaskRootEventHandler> getAssociatedType() {
    return TYPE;
  }

  @Override
  protected void dispatch(MaskRootEventHandler handler) {
    if (action == Action.TO_MASK) {
      handler.onMask(this);
    }
  }

  public interface MaskRootEventHandler extends EventHandler {

    void onMask(MaskRootEvent event);
  }
}
