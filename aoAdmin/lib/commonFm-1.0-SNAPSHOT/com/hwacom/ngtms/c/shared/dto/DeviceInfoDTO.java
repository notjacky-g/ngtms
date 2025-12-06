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
 * 餵給 {@link DeviceInfoView} 的 grid 的 model。 裡頭包含了 {@link DeviceConfigDTO}、{@link MfccConfigDTO}，
 * 以及子系統的 config class。
 *
 * @param <SC> 子系統的 config DTO class，例如 {@link
 *     com.hwacom.ngtms.dgs.weather.am.shared.model.RdConfigDTO}。
 * @author monty.pan
 */
public class DeviceInfoDTO<SC> implements HasDeviceConfig, Serializable, IsSerializable {
  private static final long serialVersionUID = -3982044128414685654L;

  private DeviceConfigDTO deviceConfig;
  private MfccConfigDTO mfccConfig;
  private SC specifyConfig;
  private Boolean connect;

  @Override
  public DeviceConfigDTO getDeviceConfig() {
    return deviceConfig;
  }

  @Override
  public void setDeviceConfig(DeviceConfigDTO deviceConfig) {
    this.deviceConfig = deviceConfig;
  }

  //    public MfccConfig getMfccConfig() {
  //        return mfccConfig;
  //    }
  //
  //    public void setMfccConfig(MfccConfig mfccConfig) {
  //        this.mfccConfig = mfccConfig;
  //    }

  public MfccConfigDTO getMfccConfig() {
    return mfccConfig;
  }

  public void setMfccConfig(MfccConfigDTO mfccConfig) {
    this.mfccConfig = mfccConfig;
  }

  public SC getSpecifyConfig() {
    return specifyConfig;
  }

  public void setSpecifyConfig(SC specifyConfig) {
    this.specifyConfig = specifyConfig;
  }

  public Boolean isConnect() {
    return connect;
  }

  public void setConnect(Boolean connect) {
    this.connect = connect;
  }
}
