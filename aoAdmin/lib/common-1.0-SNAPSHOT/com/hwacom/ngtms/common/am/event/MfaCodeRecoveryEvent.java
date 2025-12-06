/*
 * © HwaCom Systems Inc. 2021
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.common.am.event;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;

public class MfaCodeRecoveryEvent
    extends GwtEvent<MfaCodeRecoveryEvent.MfaCodeRecoveryEventHandler> {

  public static final Type<MfaCodeRecoveryEventHandler> TYPE =
      new Type<MfaCodeRecoveryEventHandler>();

  public enum Action {
    COMPLETE
  }

  private final Action action;

  public MfaCodeRecoveryEvent(Action action) {
    this.action = action;
  }

  @Override
  public Type<MfaCodeRecoveryEventHandler> getAssociatedType() {
    return TYPE;
  }

  @Override
  protected void dispatch(MfaCodeRecoveryEventHandler handler) {
    if (action == Action.COMPLETE) {
      handler.onComplete(this);
    }
  }

  public interface MfaCodeRecoveryEventHandler extends EventHandler {

    void onComplete(MfaCodeRecoveryEvent event);
  }
}