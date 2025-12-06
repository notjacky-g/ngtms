/*
 * © HwaCom Systems Inc. 2013
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.hcce.am.event;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.hwacom.ngtms.hcce.am.event.FmEvent.FmEventHandler;
import com.hwacom.ngtms.hcce.shared.dto.TopologyGroup;

public class FmEvent extends GwtEvent<FmEventHandler> {

  public static final Type<FmEventHandler> TYPE = new Type<FmEventHandler>();
  private TopologyGroup topologyGroup;

  public enum Action {
    /** fme reset */
    FME_RESET
  }

  private final Action action;

  public FmEvent(Action action, TopologyGroup topologyGroup) {
    this.action = action;
    this.topologyGroup = topologyGroup;
  }

  @Override
  public Type<FmEventHandler> getAssociatedType() {
    return TYPE;
  }

  @Override
  protected void dispatch(FmEventHandler handler) {
    if (action == Action.FME_RESET) {
      handler.onResetFme(this);
    }
  }

  /** @return the topologyGroup */
  public TopologyGroup getTopologyGroup() {
    return topologyGroup;
  }

  public interface FmEventHandler extends EventHandler {

    /**
     * fire if action equals {@code Action.FME_RESET}
     *
     * @param event
     * @see FmEvent.Action
     */
    void onResetFme(FmEvent event);
  }
}
