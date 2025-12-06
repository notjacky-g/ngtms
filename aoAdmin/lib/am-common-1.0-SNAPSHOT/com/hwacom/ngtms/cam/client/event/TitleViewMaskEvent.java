/*
 * © HwaCom Systems Inc. 2015
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.cam.client.event;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.hwacom.ngtms.cam.client.event.TitleViewMaskEvent.TitleViewMaskEventHandler;

public class TitleViewMaskEvent extends GwtEvent<TitleViewMaskEventHandler> {

  public static final Type<TitleViewMaskEventHandler> TYPE = new Type<TitleViewMaskEventHandler>();

  public TitleViewMaskEvent() {}

  @Override
  public Type<TitleViewMaskEventHandler> getAssociatedType() {
    return TYPE;
  }

  @Override
  protected void dispatch(TitleViewMaskEventHandler handler) {
    handler.onMaskFired(this);
  }

  public interface TitleViewMaskEventHandler extends EventHandler {

    void onMaskFired(TitleViewMaskEvent event);
  }
}
