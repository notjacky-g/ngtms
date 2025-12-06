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
public class SmsSendResponse implements Serializable {
  private static final long serialVersionUID = 7374736127204545319L;

  /** 收件人 */
  private String toAddr;
  /** 訊息代碼， returnCode 定義 參閱 「IMSP SMS 說明文件」 */
  private String returnCode;
  /** 傳送成功的訊息ID */
  private String messageId;
  /** 傳送結果說明 */
  private String description;
  /** 簡訊送到簡訊發送平台時間 */
  private Date sendTime;

  public SmsSendResponse(
      String toAddr, String returnCode, String messageId, String description, Date sendTime) {
    this.toAddr = toAddr;
    this.returnCode = returnCode;
    this.messageId = messageId;
    this.description = description;
    this.sendTime = sendTime;
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

  public String getMessageId() {
    return messageId;
  }

  public void setMessageId(String messageId) {
    this.messageId = messageId;
  }

  public Date getSendTime() {
    return sendTime;
  }

  public void setSendTime(Date sendTime) {
    this.sendTime = sendTime;
  }

  @Override
  public String toString() {
    return "SmsSendResponse [toAddr="
        + toAddr
        + ", returnCode="
        + returnCode
        + ", messageId="
        + messageId
        + ", description="
        + description
        + ", sendTime="
        + sendTime
        + "]";
  }
}
