/*
 * © HwaCom Systems Inc. 2013
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.hcce.am.view;

import com.google.gwt.user.client.Timer;
import com.sencha.gxt.widget.core.client.Component;

public class MessageTimerMask {

  private Timer timer;

  public MessageTimerMask(final Component componet, String message, int time) {
    timer =
        new Timer() {
          public void run() {
            componet.unmask();
          }
        };
    timer.schedule(time);
    componet.mask(message);
  }

  public void cancel() {
    if (timer != null) {
      timer.cancel();
    }
  }
}
