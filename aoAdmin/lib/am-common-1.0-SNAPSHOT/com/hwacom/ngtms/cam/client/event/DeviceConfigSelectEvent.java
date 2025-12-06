/*
 * © HwaCom Systems Inc. 2015
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.cam.client.event;

import com.google.gwt.event.shared.GwtEvent;
import com.hwacom.ngtms.c.shared.dto.DeviceConfigDTO;
import java.util.ArrayList;

public class DeviceConfigSelectEvent extends GwtEvent<DeviceConfigSelectHandler> {
  public static final Type<DeviceConfigSelectHandler> TYPE = new Type<DeviceConfigSelectHandler>();
  private ArrayList<DeviceConfigDTO> data;

  public DeviceConfigSelectEvent(ArrayList<DeviceConfigDTO> data) {
    this.data = data;
  }

  public ArrayList<DeviceConfigDTO> getData() {
    return data;
  }

  @Override
  public Type<DeviceConfigSelectHandler> getAssociatedType() {
    return TYPE;
  }

  @Override
  protected void dispatch(DeviceConfigSelectHandler handler) {
    handler.onDeviceConfigSelect(this);
  }
}
