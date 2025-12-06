package com.hwacom.ngtms.c.shared;

import java.util.List;

public interface CommonCt3CommRestartAndTestMessage {

  String TYPE = "CommRestartAndTest";

  String getType();

  void setType(String type);

  String getDeviceType();

  void setDeviceType(String deviceType);

  List<String> getDeviceNames();

  void setDeviceNames(List<String> deviceNames);

  byte[] getHardwareStatus();

  void setHardwareStatus(byte[] hardwareStatus);
}
