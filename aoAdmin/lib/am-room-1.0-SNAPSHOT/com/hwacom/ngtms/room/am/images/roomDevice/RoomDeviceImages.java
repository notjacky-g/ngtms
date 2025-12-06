/*
 * © HwaCom Systems Inc. 2015
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.room.am.images.roomDevice;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import org.vectomatic.dom.svg.ui.SVGResource;

public interface RoomDeviceImages extends ClientBundle {

  RoomDeviceImages INSTANCE = GWT.create(RoomDeviceImages.class);

  //機房設備
  SVGResource gc();

  SVGResource gr();

  SVGResource gt();
}
