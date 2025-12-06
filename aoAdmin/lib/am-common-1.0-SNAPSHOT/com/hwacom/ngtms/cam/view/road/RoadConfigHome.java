/*
 * © HwaCom Systems Inc. 2015
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.cam.view.road;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.widget.core.client.Composite;

public class RoadConfigHome extends Composite {

  private static RoadConfigHomeUiBinder uiBinder = GWT.create(RoadConfigHomeUiBinder.class);

  interface RoadConfigHomeUiBinder extends UiBinder<Widget, RoadConfigHome> {}

  public RoadConfigHome() {
    initWidget(uiBinder.createAndBindUi(this));
  }
}
