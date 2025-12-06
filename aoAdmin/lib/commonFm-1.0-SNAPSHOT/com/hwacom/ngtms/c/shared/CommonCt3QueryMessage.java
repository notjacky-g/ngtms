package com.hwacom.ngtms.c.shared;

public interface CommonCt3QueryMessage extends QueryMessage {

  String getDeviceType();

  void setDeviceType(String deviceType);

  Integer getEquipmentNo();

  void setEquipmentNo(Integer equipmentNo);
}
