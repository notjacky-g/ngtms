/*
 * © HwaCom Systems Inc. 2023
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.pd.am.event;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;

public class PdConfigSettingViewerEvent
    extends GwtEvent<PdConfigSettingViewerEvent.PdConfigSettingViewerEventHandler> {

  public static final Type<PdConfigSettingViewerEventHandler> TYPE =
      new Type<PdConfigSettingViewerEventHandler>();

  public enum Action {
    UPDATE
  }

  private final Action action;

  public PdConfigSettingViewerEvent(Action action) {
    this.action = action;
  }

  @Override
  public Type<PdConfigSettingViewerEventHandler> getAssociatedType() {
    return TYPE;
  }

  @Override
  protected void dispatch(PdConfigSettingViewerEventHandler handler) {
    if (action == Action.UPDATE) {
      handler.onUpdate(this);
    }
  }

  public interface PdConfigSettingViewerEventHandler extends EventHandler {

    void onUpdate(PdConfigSettingViewerEvent event);
  }
}