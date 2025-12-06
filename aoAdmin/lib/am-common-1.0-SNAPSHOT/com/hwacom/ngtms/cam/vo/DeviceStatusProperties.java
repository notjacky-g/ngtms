/*
 * © HwaCom Systems Inc. 2015
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.cam.vo;

import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.Editor.Path;
import com.hwacom.ngtms.c.shared.dto.DeviceStatusDTO;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;

public interface DeviceStatusProperties extends PropertyAccess<DeviceStatusDTO> {
  public DeviceStatusProperties INSTANCE = GWT.create(DeviceStatusProperties.class);

  @Path("deviceConfig.deviceName")
  ModelKeyProvider<DeviceStatusDTO> id();

  // ==== DeviceConfig 區 ==== //
  @Path("deviceConfig.deviceName")
  ValueProvider<DeviceStatusDTO, String> deviceName();

  @Path("deviceConfig.displayName")
  ValueProvider<DeviceStatusDTO, String> displayName();

  @Path("deviceConfig.project")
  ValueProvider<DeviceStatusDTO, String> project();

  @Path("deviceConfig.ip")
  ValueProvider<DeviceStatusDTO, String> ip();

  @Path("deviceConfig.port")
  ValueProvider<DeviceStatusDTO, String> port();

  @Path("deviceConfig.location")
  ValueProvider<DeviceStatusDTO, String> location();
  // ======== //

  //EmsDeviceStatus 沒一個能直接顯示 raw data... ＝＝"
}
