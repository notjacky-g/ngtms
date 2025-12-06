/*
 * © HwaCom Systems Inc. 2015
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.c.shared.dto;

import com.google.gwt.user.client.rpc.IsSerializable;
import com.hwacom.ngtms.c.shared.HasDeviceConfig;
import java.io.Serializable;

/**
 * 餵給 {@link DeviceStatusView} 中 grid 的 model。 裡頭包含了 {@link DeviceConfigDTO}、{@link
 * EmsDeviceStatusDTO}。
 *
 * @author monty.pan
 */
public class DeviceStatusDTO implements HasDeviceConfig, Serializable, IsSerializable {
  private DeviceConfigDTO deviceConfig;
  private EmsDeviceStatusDTO emsDeviceStatus;

  @Override
  public DeviceConfigDTO getDeviceConfig() {
    return deviceConfig;
  }

  @Override
  public void setDeviceConfig(DeviceConfigDTO deviceConfig) {
    this.deviceConfig = deviceConfig;
  }

  public EmsDeviceStatusDTO getEmsDeviceStatus() {
    return emsDeviceStatus;
  }

  public void setEmsDeviceStatus(EmsDeviceStatusDTO emsDeviceStatus) {
    this.emsDeviceStatus = emsDeviceStatus;
  }
}
