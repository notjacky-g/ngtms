/*
 * © HwaCom Systems Inc. 2018
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.common.am.view;

import com.sencha.gxt.widget.core.client.Window;

public class BlackWindow extends Window {
  @Override
  public void show() {
    super.show();
    getModalPanel().getElement().getStyle().setOpacity(1.0);
  }
}
