/*
 * © HwaCom Systems Inc. 2015
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.cam.client.event;

import com.google.gwt.event.shared.GwtEvent;
import com.hwacom.ngtms.cam.client.ui.RoadTreeViewer;
import com.hwacom.ngtms.cam.client.ui.RoadTreeViewer.SelectType;

/**
 * 如果 {@link #type} 是 {@link SelectType#DIRECTION}， 請使用 {@link
 * RoadTreeViewer#parseRoadlineId(String)} 取得 roadLine 的 id。
 *
 * @author monty.pan
 */
public class RoadTreeSelectEvent extends GwtEvent<RoadTreeSelectHandler> {
  public static final Type<RoadTreeSelectHandler> TYPE = new Type<RoadTreeSelectHandler>();
  private SelectType type;
  private String id;

  public RoadTreeSelectEvent(SelectType type, String id) {
    this.type = type;
    this.id = id;
  }

  public SelectType getType() {
    return type;
  }

  public String getId() {
    return id;
  }

  @Override
  public Type<RoadTreeSelectHandler> getAssociatedType() {
    return TYPE;
  }

  @Override
  protected void dispatch(RoadTreeSelectHandler handler) {
    handler.onRoadTreeSelect(this);
  }
}
