/*
 * © HwaCom Systems Inc. 2013
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.common.am;

import com.google.gwt.core.client.GWT;
import com.hwacom.ngtms.common.am.view.AccountViewer;
import com.hwacom.ngtms.common.am.view.Messages;

public class AccountEP extends AmEntryPoint {

  public static final Messages messages = GWT.create(Messages.class);

  public AccountEP() {
    super(messages.windowTitle());
  }

  @Override
  protected void allServicesReady() {
    init(new AccountViewer());
  }
}
