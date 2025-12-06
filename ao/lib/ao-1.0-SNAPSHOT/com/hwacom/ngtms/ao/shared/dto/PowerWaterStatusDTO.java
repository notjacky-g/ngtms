/*
 * © HwaCom Systems Inc. 2015
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.ao.shared.dto;

import java.io.Serializable;
import java.util.List;

public class PowerWaterStatusDTO implements Serializable {

  private static final long serialVersionUID = -8798373661311667794L;

  private List<PowerStatusDTO> powerStatusList;

  private List<WaterStatusDTO> waterStatusList;

  public List<PowerStatusDTO> getPowerStatusList() {
    return powerStatusList;
  }

  public void setPowerStatusList(List<PowerStatusDTO> powerStatusList) {
    this.powerStatusList = powerStatusList;
  }

  public List<WaterStatusDTO> getWaterStatusList() {
    return waterStatusList;
  }

  public void setWaterStatusList(List<WaterStatusDTO> waterStatusList) {
    this.waterStatusList = waterStatusList;
  }
}
