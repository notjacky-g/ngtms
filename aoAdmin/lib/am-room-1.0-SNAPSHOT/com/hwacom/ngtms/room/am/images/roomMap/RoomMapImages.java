/*
 * © HwaCom Systems Inc. 2015
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.room.am.images.roomMap;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import org.vectomatic.dom.svg.ui.SVGResource;

public interface RoomMapImages extends ClientBundle {

  RoomMapImages INSTANCE = GWT.create(RoomMapImages.class);

  //機房背景地圖
  SVGResource northHoleRoom1F();

  SVGResource southHoleRoom1F();

  SVGResource anZhenCenterRoom2F();
}
