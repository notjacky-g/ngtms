/*
 * © HwaCom Systems Inc. 2023
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.pd.am.event;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;

public class DeviceInfoViewerEvent
    extends GwtEvent<DeviceInfoViewerEvent.DeviceInfoViewerEventHandler> {

  public static final Type<DeviceInfoViewerEventHandler> TYPE =
      new Type<DeviceInfoViewerEventHandler>();

  public enum Action {
    ADD_DEVICES
  }

  private final Action action;

  public DeviceInfoViewerEvent(Action action) {
    this.action = action;
  }

  @Override
  public Type<DeviceInfoViewerEventHandler> getAssociatedType() {
    return TYPE;
  }

  @Override
  protected void dispatch(DeviceInfoViewerEventHandler handler) {
    if (action == Action.ADD_DEVICES) {
      handler.onAddDevices(this);
    }
  }

  public interface DeviceInfoViewerEventHandler extends EventHandler {

    void onAddDevices(DeviceInfoViewerEvent event);
  }
}