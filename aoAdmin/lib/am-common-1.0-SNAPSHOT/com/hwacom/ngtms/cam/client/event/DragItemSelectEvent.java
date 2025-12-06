/*
 * © HwaCom Systems Inc. 2015
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.cam.client.event;

import com.google.gwt.event.shared.GwtEvent;
import com.hwacom.ngtms.c.shared.DragItem;
import java.util.ArrayList;

public class DragItemSelectEvent extends GwtEvent<DragItemSelectHandler> {
  public static final Type<DragItemSelectHandler> TYPE = new Type<DragItemSelectHandler>();
  private ArrayList<DragItem> data;

  public DragItemSelectEvent(ArrayList<DragItem> data) {
    this.data = data;
  }

  public ArrayList<DragItem> getData() {
    return data;
  }

  @Override
  public Type<DragItemSelectHandler> getAssociatedType() {
    return TYPE;
  }

  @Override
  protected void dispatch(DragItemSelectHandler handler) {
    handler.onDragItemSelect(this);
  }
}
