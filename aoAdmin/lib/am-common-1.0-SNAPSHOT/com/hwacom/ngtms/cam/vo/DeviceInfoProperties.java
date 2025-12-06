/*
 * © HwaCom Systems Inc. 2015
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.cam.vo;

import com.google.gwt.editor.client.Editor.Path;
import com.hwacom.ngtms.c.shared.dto.DeviceInfoDTO;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;

/**
 * {@link DeviceInfoDTO} 共用的 property access。
 *
 * @param <SC> 子系統的 config DTO class
 * @author monty.pan
 */
public interface DeviceInfoProperties<SC> extends PropertyAccess<DeviceInfoDTO<SC>> {
  @Path("deviceConfig.deviceName")
  ModelKeyProvider<DeviceInfoDTO<SC>> id();

  // ==== DeviceConfig 區 ==== //
  @Path("deviceConfig.deviceName")
  ValueProvider<DeviceInfoDTO<SC>, String> deviceName();

  @Path("deviceConfig.displayName")
  ValueProvider<DeviceInfoDTO<SC>, String> displayName();

  @Path("deviceConfig.deviceType")
  ValueProvider<DeviceInfoDTO<SC>, String> deviceType();

  @Path("deviceConfig.ip")
  ValueProvider<DeviceInfoDTO<SC>, String> ip();

  @Path("deviceConfig.port")
  ValueProvider<DeviceInfoDTO<SC>, String> port();
  // ======== //
}
