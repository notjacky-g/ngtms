/*
 * © HwaCom Systems Inc. 2013
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.hcce.am.event;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.hwacom.ngtms.hcce.am.event.SystemEnvEvent.SystemEnvEventHandler;
import com.hwacom.ngtms.hcce.shared.dto.TopologyGroup;

public class SystemEnvEvent extends GwtEvent<SystemEnvEventHandler> {

  public static final Type<SystemEnvEventHandler> TYPE = new Type<SystemEnvEventHandler>();
  private TopologyGroup topologyGroup;

  public enum Action {
    /** initData */
    INIT_DATA,
    UPDATE_DATA
  }

  private final Action action;

  public SystemEnvEvent(Action action, TopologyGroup topologyGroup) {
    this.topologyGroup = topologyGroup;
    this.action = action;
  }

  @Override
  public Type<SystemEnvEventHandler> getAssociatedType() {
    return TYPE;
  }

  @Override
  protected void dispatch(SystemEnvEventHandler handler) {
    if (action == Action.INIT_DATA) {
      handler.onInitEnvData(this);
    } else if (action == Action.UPDATE_DATA) {
      handler.onUpdateEnvData(this);
    }
  }

  /** @return the topologyGroup */
  public TopologyGroup getTopologyGroup() {
    return topologyGroup;
  }

  public interface SystemEnvEventHandler extends EventHandler {

    /**
     * fire if action equals {@code Action.SAVE}
     *
     * @param event
     * @see SystemEnvEvent.Action
     */
    void onUpdateEnvData(SystemEnvEvent event);
    /**
     * fire if action equals {@code Action.SAVE}
     *
     * @param event
     * @see SystemEnvEvent.Action
     */
    void onInitEnvData(SystemEnvEvent event);
  }
}
