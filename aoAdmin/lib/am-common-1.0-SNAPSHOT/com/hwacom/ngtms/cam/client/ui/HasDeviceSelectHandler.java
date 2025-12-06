/*
 * © HwaCom Systems Inc. 2015
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.cam.client.ui;

import com.google.gwt.event.shared.HandlerRegistration;
import com.hwacom.ngtms.cam.client.event.DeviceConfigSelectHandler;

public interface HasDeviceSelectHandler {
  public HandlerRegistration addDeviceSelectHandler(DeviceConfigSelectHandler h);
}
