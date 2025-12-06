/*
 * © HwaCom Systems Inc. 2015
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.cam.client.event;

import com.google.gwt.event.shared.GwtEvent;

/** An event which indicate RoadTreeViewer actions occurred in widgets */
public class RoadTreeViewerMaskEvent extends GwtEvent<RoadTreeViewerMaskEventHandler> {

  public static final Type<RoadTreeViewerMaskEventHandler> TYPE =
      new Type<RoadTreeViewerMaskEventHandler>();

  /** The specific event action to triggered */
  public enum Action {
    MASK_DEVICES,
    UNMASK_DEVICES,
  }

  private final Action action;

  public RoadTreeViewerMaskEvent(Action action) {
    this.action = action;
  }

  public Action getAction() {
    return action;
  }

  @Override
  public Type<RoadTreeViewerMaskEventHandler> getAssociatedType() {
    return TYPE;
  }

  @Override
  protected void dispatch(RoadTreeViewerMaskEventHandler handler) {
    if (action == Action.MASK_DEVICES) {
      handler.onMaskDevices(this);
    } else if (action == Action.UNMASK_DEVICES) {
      handler.onUnmaskDevices(this);
    }
  }
}
