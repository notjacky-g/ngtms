package com.hwacom.ngtms.c.shared;

import java.util.List;

public interface CommonCt3LockDbMessage {

  String TYPE = "LockDb";

  String getType();

  void setType(String type);

  String getDeviceType();

  void setDeviceType(String deviceType);

  List<String> getDeviceNames();

  void setDeviceNames(List<String> deviceNames);

  Integer getLockDb();

  void setLockDb(Integer lockDb);
}
