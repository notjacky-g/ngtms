/*
 * © HwaCom Systems Inc. 2021
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.common.am.event;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;

public class PwdRecoveryEvent
    extends GwtEvent<PwdRecoveryEvent.PwdRecoveryEventHandler> {

  public static final Type<PwdRecoveryEventHandler> TYPE =
      new Type<PwdRecoveryEventHandler>();

  public enum Action {
    COMPLETE
  }

  private final Action action;

  public PwdRecoveryEvent(Action action) {
    this.action = action;
  }

  @Override
  public Type<PwdRecoveryEventHandler> getAssociatedType() {
    return TYPE;
  }

  @Override
  protected void dispatch(PwdRecoveryEventHandler handler) {
    if (action == Action.COMPLETE) {
      handler.onComplete(this);
    }
  }

  public interface PwdRecoveryEventHandler extends EventHandler {

    void onComplete(PwdRecoveryEvent event);
  }
}