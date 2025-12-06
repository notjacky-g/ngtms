/*
 * © HwaCom Systems Inc. 2013
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.hcce.am.event;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.hwacom.ngtms.hcce.am.event.ActiveSystemDisconnectedEvent.ActiveSystemDisconnectedEventHandler;

public class ActiveSystemDisconnectedEvent extends GwtEvent<ActiveSystemDisconnectedEventHandler> {

  public static final Type<ActiveSystemDisconnectedEventHandler> TYPE =
      new Type<ActiveSystemDisconnectedEventHandler>();

  public ActiveSystemDisconnectedEvent() {}

  @Override
  public Type<ActiveSystemDisconnectedEventHandler> getAssociatedType() {
    return TYPE;
  }

  @Override
  protected void dispatch(ActiveSystemDisconnectedEventHandler handler) {
    handler.onProcess(this);
  }

  public interface ActiveSystemDisconnectedEventHandler extends EventHandler {

    void onProcess(ActiveSystemDisconnectedEvent event);
  }
}
