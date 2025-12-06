/*
 * © HwaCom Systems Inc. 2015
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.c.shared;

import java.util.List;

public interface DeviceListAndTypeMessage {

  String TYPE = DeviceListAndTypeMessage.class.getName();

  String getDeviceType();

  void setDeviceType(String deviceType);

  List<String> getDeviceNames();

  void setDeviceNames(List<String> deviceNames);

  public class DeviceListAndTypeMessageImpl implements DeviceListAndTypeMessage {

    private String deviceType;

    private List<String> deviceNames;

    @Override
    public String getDeviceType() {
      return deviceType;
    }

    @Override
    public void setDeviceType(String deviceType) {
      this.deviceType = deviceType;
    }

    @Override
    public List<String> getDeviceNames() {
      return deviceNames;
    }

    @Override
    public void setDeviceNames(List<String> deviceNames) {
      this.deviceNames = deviceNames;
    }
  }
}
