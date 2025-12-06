/*
 * © HwaCom Systems Inc. 2021
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.common.shared.dto;

public class ChangePwdDTO {

  private String currentPwd;

  private String newPwd;

  public ChangePwdDTO() {}

  public ChangePwdDTO(String currentPwd, String newPwd) {
    this.currentPwd = currentPwd;
    this.newPwd = newPwd;
  }

  public String getCurrentPwd() {
    return currentPwd;
  }

  public void setCurrentPwd(String currentPwd) {
    this.currentPwd = currentPwd;
  }

  public String getNewPwd() {
    return newPwd;
  }

  public void setNewPwd(String newPwd) {
    this.newPwd = newPwd;
  }
}
