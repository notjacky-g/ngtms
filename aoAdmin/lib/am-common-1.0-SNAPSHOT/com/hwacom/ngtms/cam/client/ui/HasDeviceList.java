/*
 * © HwaCom Systems Inc. 2015
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.cam.client.ui;

import com.google.gwt.user.client.ui.IsWidget;
import com.hwacom.ngtms.c.shared.dto.DeviceConfigDTO;
import java.util.List;

public interface HasDeviceList extends IsWidget {
  public void addDevices(List<DeviceConfigDTO> data);

  public List<DeviceConfigDTO> getDeviceList();
}
