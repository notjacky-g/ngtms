/*
 * © HwaCom Systems Inc. 2015
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.cam.client.event;

import com.google.gwt.event.shared.GwtEvent;
import java.util.Date;

public class ChartItemSelectionEvent extends GwtEvent<ChartItemSelectionHandler> {
  public static final Type<ChartItemSelectionHandler> TYPE = new Type<ChartItemSelectionHandler>();
  private Integer deviceIdx;
  private Date date;

  public ChartItemSelectionEvent(Integer deviceIdx, Date date) {
    this.deviceIdx = deviceIdx;
    this.date = date;
  }

  public Integer getDeviceIdx() {
    return deviceIdx;
  }

  public Date getDate() {
    return date;
  }

  @Override
  public Type<ChartItemSelectionHandler> getAssociatedType() {
    return TYPE;
  }

  @Override
  protected void dispatch(ChartItemSelectionHandler handler) {
    handler.onChartItemSelection(this);
  }
}
