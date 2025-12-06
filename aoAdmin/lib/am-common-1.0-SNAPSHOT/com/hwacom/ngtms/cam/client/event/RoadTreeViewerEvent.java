/*
 * © HwaCom Systems Inc. 2015
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.cam.client.event;

import com.google.gwt.event.shared.GwtEvent;

/** An event which indicate RoadTreeViewer actions occurred in widgets */
public class RoadTreeViewerEvent extends GwtEvent<RoadTreeViewerEventHandler> {

  public static final Type<RoadTreeViewerEventHandler> TYPE =
      new Type<RoadTreeViewerEventHandler>();

  /** The specific event action to triggered */
  public enum Action {
    SELECT_SELECTEDDEVICE,
    REMOTE_ALLDEVICE,
    READY
  }

  private final Action action;

  public RoadTreeViewerEvent(Action action) {
    this.action = action;
  }

  public Action getAction() {
    return action;
  }

  @Override
  public Type<RoadTreeViewerEventHandler> getAssociatedType() {
    return TYPE;
  }

  @Override
  protected void dispatch(RoadTreeViewerEventHandler handler) {
    if (action == Action.SELECT_SELECTEDDEVICE) {
      handler.onSelectSelectedDevice(this);
    } else if (action == Action.REMOTE_ALLDEVICE) {
      handler.onRemoveAllDevice(this);
    } else if (action == Action.READY) {
      handler.onReady(this);
    }
  }
}
