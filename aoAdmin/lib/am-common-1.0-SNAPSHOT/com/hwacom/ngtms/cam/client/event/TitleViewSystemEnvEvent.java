/*
 * © HwaCom Systems Inc. 2015
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.cam.client.event;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.hwacom.ngtms.cam.client.event.TitleViewSystemEnvEvent.TitleViewSystemEnvEventHandler;

public class TitleViewSystemEnvEvent extends GwtEvent<TitleViewSystemEnvEventHandler> {

  public static final Type<TitleViewSystemEnvEventHandler> TYPE =
      new Type<TitleViewSystemEnvEventHandler>();

  public TitleViewSystemEnvEvent() {}

  @Override
  public Type<TitleViewSystemEnvEventHandler> getAssociatedType() {
    return TYPE;
  }

  @Override
  protected void dispatch(TitleViewSystemEnvEventHandler handler) {
    handler.onMaskFired(this);
  }

  public interface TitleViewSystemEnvEventHandler extends EventHandler {

    void onMaskFired(TitleViewSystemEnvEvent event);
  }
}
