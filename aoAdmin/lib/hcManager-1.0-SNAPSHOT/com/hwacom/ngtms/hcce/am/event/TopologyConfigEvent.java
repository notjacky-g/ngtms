/*
 * © HwaCom Systems Inc. 2013
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.hcce.am.event;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.hwacom.ngtms.hcce.am.event.TopologyConfigEvent.TopologyConfigEventHandler;
import com.hwacom.ngtms.hcce.shared.dto.TopologyGroup;

/** @author bryan.lin */
public class TopologyConfigEvent extends GwtEvent<TopologyConfigEventHandler> {

  public static final Type<TopologyConfigEventHandler> TYPE =
      new Type<TopologyConfigEventHandler>();
  private TopologyGroup topologyGroup;

  public enum Action {
    /** save */
    SAVE,
    /** add */
    ADD,
    /** delete */
    DELETE
  }

  private final Action action;

  public TopologyConfigEvent(Action action, TopologyGroup topologyGroup) {
    this.action = action;
    this.topologyGroup = topologyGroup;
  }

  @Override
  public Type<TopologyConfigEventHandler> getAssociatedType() {
    return TYPE;
  }

  @Override
  protected void dispatch(TopologyConfigEventHandler handler) {
    if (action == Action.SAVE) {
      handler.onSave(this);
    } else if (action == Action.ADD) {
      handler.onAdd(this);
    } else if (action == Action.DELETE) {
      handler.onDelete(this);
    }
  }

  /** @return the topologyGroup */
  public TopologyGroup getTopologyGroup() {
    return topologyGroup;
  }

  public interface TopologyConfigEventHandler extends EventHandler {

    /**
     * fire if action equals {@code Action.SAVE}
     *
     * @param event
     * @see TopologyConfigEvent.Action
     */
    void onSave(TopologyConfigEvent event);

    /**
     * fire if action equals {@code Action.ADD}
     *
     * @param event
     * @see TopologyConfigEvent.Action
     */
    void onAdd(TopologyConfigEvent event);

    /**
     * fire if action equals {@code Action.DELETE}
     *
     * @param event
     * @see TopologyConfigEvent.Action
     */
    void onDelete(TopologyConfigEvent event);
  }
}
