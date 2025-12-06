/*
 * © HwaCom Systems Inc. 2013
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.ao.shared.eqconfig;

import java.io.Serializable;

/**
 * 1day_eq_config_data.xml
 *
 * @author jeff.ku
 */
public class EqConfigData implements Serializable {

  private static final long serialVersionUID = -3778876612815439198L;

  private OnedayEqConfigData onedayEqConfigData;

  private String fileName;

  private Integer controlCenterId;

  private String time;

  public OnedayEqConfigData getOnedayEqConfigData() {
    return onedayEqConfigData;
  }

  public void setOnedayEqConfigData(OnedayEqConfigData onedayEqConfigData) {
    this.onedayEqConfigData = onedayEqConfigData;
  }

  public String getFileName() {
    return fileName;
  }

  public void setFileName(String fileName) {
    this.fileName = fileName;
  }

  public Integer getControlCenterId() {
    return controlCenterId;
  }

  public void setControlCenterId(Integer controlCenterId) {
    this.controlCenterId = controlCenterId;
  }

  public String getTime() {
    return time;
  }

  public void setTime(String time) {
    this.time = time;
  }
}
