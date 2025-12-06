/*
 * © HwaCom Systems Inc. 2015
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.cam.vo;

import com.hwacom.ngtms.c.shared.HasDeviceConfig;
import com.hwacom.ngtms.c.shared.dto.DeviceConfigDTO;

public class SpecifyConfigVO<SC> implements HasDeviceConfig {
  private DeviceConfigDTO deviceConfig;
  private SC specifyConfig;
  private String commResult;

  @Override
  public DeviceConfigDTO getDeviceConfig() {
    return deviceConfig;
  }

  @Override
  public void setDeviceConfig(DeviceConfigDTO deviceConfig) {
    this.deviceConfig = deviceConfig;
  }

  public SC getSpecifyConfig() {
    return specifyConfig;
  }

  public void setSpecifyConfig(SC specifyConfig) {
    this.specifyConfig = specifyConfig;
  }

  public String getCommResult() {
    return commResult;
  }

  public void setCommResult(String commResult) {
    this.commResult = commResult;
  }
}
