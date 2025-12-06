/*
 * © HwaCom Systems Inc. 2015
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.ao.shared.dto;

import com.google.gwt.user.client.rpc.IsSerializable;
import java.io.Serializable;

public class LifeFaceSessionDTO implements Serializable, IsSerializable {

  private static final long serialVersionUID = -7616634635543062296L;

  private String serverTime;

  private String sessionId;

  private String ip;
  //辨識成功
  private String identifyTrue;
  //辨識失敗
  private String identifyFalse;

  public String getServerTime() {
    return serverTime;
  }

  public void setServerTime(String serverTime) {
    this.serverTime = serverTime;
  }

  public String getSessionId() {
    return sessionId;
  }

  public void setSessionId(String sessionId) {
    this.sessionId = sessionId;
  }

  public String getIp() {
    return ip;
  }

  public void setIp(String ip) {
    this.ip = ip;
  }

  public String getIdentifyTrue() {
    return identifyTrue;
  }

  public void setIdentifyTrue(String identifyTrue) {
    this.identifyTrue = identifyTrue;
  }

  public String getIdentifyFalse() {
    return identifyFalse;
  }

  public void setIdentifyFalse(String identifyFalse) {
    this.identifyFalse = identifyFalse;
  }
}
