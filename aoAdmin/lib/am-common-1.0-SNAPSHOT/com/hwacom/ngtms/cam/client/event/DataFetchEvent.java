/*
 * © HwaCom Systems Inc. 2015
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.cam.client.event;

import com.google.gwt.event.shared.GwtEvent;
import com.hwacom.ngtms.cam.vo.DeviceDataType;

public class DataFetchEvent extends GwtEvent<DataFetchHandler> {
  public static final Type<DataFetchHandler> TYPE = new Type<DataFetchHandler>();
  private DeviceDataType type;

  public DataFetchEvent(DeviceDataType type) {
    this.type = type;
  }

  public DeviceDataType getType() {
    return type;
  }

  @Override
  public Type<DataFetchHandler> getAssociatedType() {
    return TYPE;
  }

  @Override
  protected void dispatch(DataFetchHandler handler) {
    handler.onDataFetchEvent(this);
  }
}
