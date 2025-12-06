/*
 * © HwaCom Systems Inc. 2013
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.base.sms.shared;

import java.io.Serializable;
import java.util.Date;

/** @author yhleu */
public class SmsQueryResponse implements Serializable {
  private static final long serialVersionUID = 667320759359623286L;

  /** 收件人 */
  private String toAddr;
  /** 訊息代碼 , returnCode 定義 參閱 「IMSP SMS 說明文件」 */
  private String returnCode;
  /** 傳送時間 */
  private Date doneTime;
  /** 傳送結果說明 */
  private String description;

  public SmsQueryResponse(String toAddr, String returnCode, Date doneTime, String description) {
    this.toAddr = toAddr;
    this.returnCode = returnCode;
    this.doneTime = doneTime;
    this.description = description;
  }

  public String getToAddr() {
    return toAddr;
  }

  public void setToAddr(String toAddr) {
    this.toAddr = toAddr;
  }

  public String getReturnCode() {
    return returnCode;
  }

  public void setReturnCode(String returnCode) {
    this.returnCode = returnCode;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Date getDoneTime() {
    return doneTime;
  }

  public void setDoneTime(Date doneTime) {
    this.doneTime = doneTime;
  }

  @Override
  public String toString() {
    return "SmsQueryResponse [toAddr="
        + toAddr
        + ", returnCode="
        + returnCode
        + ", doneTime="
        + doneTime
        + ", description="
        + description
        + "]";
  }
}
