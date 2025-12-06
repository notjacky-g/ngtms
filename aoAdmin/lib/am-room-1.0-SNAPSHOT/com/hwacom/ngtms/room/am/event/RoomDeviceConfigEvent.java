/*
 * © HwaCom Systems Inc. 2021
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.room.am.event;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;

public class RoomDeviceConfigEvent
    extends GwtEvent<RoomDeviceConfigEvent.RoomDeviceConfigEventHandler> {

  public static final Type<RoomDeviceConfigEventHandler> TYPE =
      new Type<RoomDeviceConfigEventHandler>();

  public enum Action {
    CLICK,
    ANALOG_RECORD
  }

  private final Action action;

  public RoomDeviceConfigEvent(Action action) {
    this.action = action;
  }

  @Override
  public Type<RoomDeviceConfigEventHandler> getAssociatedType() {
    return TYPE;
  }

  @Override
  protected void dispatch(RoomDeviceConfigEventHandler handler) {
    if (action == Action.CLICK) {
      handler.onClick(this);
    } else if (action == Action.ANALOG_RECORD) {
      handler.onAnalogRecord(this);
    }
  }

  public interface RoomDeviceConfigEventHandler extends EventHandler {

    void onClick(RoomDeviceConfigEvent event);
    void onAnalogRecord(RoomDeviceConfigEvent event);
  }
}