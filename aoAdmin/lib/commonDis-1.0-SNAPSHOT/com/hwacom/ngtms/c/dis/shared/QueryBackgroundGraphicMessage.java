/*
 * © HwaCom Systems Inc. 2015
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.c.dis.shared;

public interface QueryBackgroundGraphicMessage {

  String TYPE = QueryBackgroundGraphicMessage.class.getName();

  String getType();

  void setType(String type);

  String getDeviceName();

  void setDeviceName(String deviceName);

  int getCodeId();

  void setCodeId(int codeId);
}
