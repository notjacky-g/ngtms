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

public class DragItemDeselectEvent extends GwtEvent<DragItemDeselectHandler> {
  public static final Type<DragItemDeselectHandler> TYPE = new Type<DragItemDeselectHandler>();
  private ArrayList<DragItem> data;

  public DragItemDeselectEvent(ArrayList<DragItem> data) {
    this.data = data;
  }

  public ArrayList<DragItem> getData() {
    return data;
  }

  @Override
  public Type<DragItemDeselectHandler> getAssociatedType() {
    return TYPE;
  }

  @Override
  protected void dispatch(DragItemDeselectHandler handler) {
    handler.onDragItemDeselect(this);
  }
}
