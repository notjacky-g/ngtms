package com.hwacom.ngtms.c.shared;

import java.util.List;

public interface CommonCt3ReportHwStatusCycleMessage {

  String TYPE = "ReportHwStatusCycle";

  String getType();

  void setType(String type);

  String getDeviceType();

  void setDeviceType(String deviceType);

  List<String> getDeviceNames();

  void setDeviceNames(List<String> deviceNames);

  Integer getHardwareCycle();

  void setHardwareCycle(Integer hardwareCycle);
}
