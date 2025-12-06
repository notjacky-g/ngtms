/*
 * © HwaCom Systems Inc. 2013
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.hcce.am.event;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.hwacom.ngtms.hcce.am.event.SwitchDrEvent.SwitchDrEventHandler;

public class SwitchDrEvent extends GwtEvent<SwitchDrEventHandler> {

  public static final Type<SwitchDrEventHandler> TYPE = new Type<SwitchDrEventHandler>();

  public enum Action {
    PRIMARY_SYSTEM,
    DR_SYSTEM,
    PRIMARY_START_CLUSTER,
    PRIMARY_STOP_CLUSTER,
    DR_START_CLUSTER,
    DR_STOP_CLUSTER
  }

  private final Action action;

  public SwitchDrEvent(Action action) {
    this.action = action;
  }

  @Override
  public Type<SwitchDrEventHandler> getAssociatedType() {
    return TYPE;
  }

  @Override
  protected void dispatch(SwitchDrEventHandler handler) {
    if (action == Action.PRIMARY_SYSTEM) {
      handler.primarySystem(this);
    } else if (action == Action.DR_SYSTEM) {
      handler.drSystem(this);
    } else if (action == Action.PRIMARY_START_CLUSTER) {
      handler.primaryStartCluster(this);
    } else if (action == Action.PRIMARY_STOP_CLUSTER) {
      handler.primaryStopCluster(this);
    } else if (action == Action.DR_START_CLUSTER) {
      handler.drStartCluster(this);
    } else if (action == Action.DR_STOP_CLUSTER) {
      handler.drStopCluster(this);
    }
  }

  public interface SwitchDrEventHandler extends EventHandler {

    /**
     * fire if action equals {@code Action.PRIMARY_SYSTEM}
     *
     * @param event
     * @see SwitchDrEvent.Action
     */
    void primarySystem(SwitchDrEvent event);

    /**
     * fire if action equals {@code Action.DR_SYSTEM}
     *
     * @param event
     * @see SwitchDrEvent.Action
     */
    void drSystem(SwitchDrEvent event);

    /**
     * fire if action equals {@code Action.PRIMARY_START_CLUSTER}
     *
     * @param event
     * @see SwitchDrEvent.Action
     */
    void primaryStartCluster(SwitchDrEvent event);

    /**
     * fire if action equals {@code Action.PRIMARY_STOP_CLUSTER}
     *
     * @param event
     * @see SwitchDrEvent.Action
     */
    void primaryStopCluster(SwitchDrEvent event);

    /**
     * fire if action equals {@code Action.DR_START_CLUSTER}
     *
     * @param event
     * @see SwitchDrEvent.Action
     */
    void drStartCluster(SwitchDrEvent event);

    /**
     * fire if action equals {@code Action.DR_STOP_CLUSETER}
     *
     * @param event
     * @see SwitchDrEvent.Action
     */
    void drStopCluster(SwitchDrEvent event);
  }
}
