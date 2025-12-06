/*
 * © HwaCom Systems Inc. 2013
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.hcce.am.event;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.hwacom.ngtms.hcce.am.event.StandbySystemDisconnectedEvent.StandbySystemDisconnectedEventHandler;

public class StandbySystemDisconnectedEvent
    extends GwtEvent<StandbySystemDisconnectedEventHandler> {

  public static final Type<StandbySystemDisconnectedEventHandler> TYPE =
      new Type<StandbySystemDisconnectedEventHandler>();

  public StandbySystemDisconnectedEvent() {}

  @Override
  public Type<StandbySystemDisconnectedEventHandler> getAssociatedType() {
    return TYPE;
  }

  @Override
  protected void dispatch(StandbySystemDisconnectedEventHandler handler) {
    handler.onProcess(this);
  }

  public interface StandbySystemDisconnectedEventHandler extends EventHandler {

    void onProcess(StandbySystemDisconnectedEvent event);
  }
}
