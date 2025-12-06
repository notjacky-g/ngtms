/*
 * © HwaCom Systems Inc. 2015
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.c.shared;

import java.util.List;

public interface DeviceListMessage {

  String TYPE = DeviceListMessage.class.getName();

  String getType();

  void setType(String type);

  List<String> getDeviceNames();

  void setDeviceNames(List<String> deviceNames);

  public class DeviceListMessageImpl implements DeviceListMessage {

    private String type;

    private List<String> deviceNames;

    @Override
    public String getType() {
      return type;
    }

    @Override
    public void setType(String type) {
      this.type = type;
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
