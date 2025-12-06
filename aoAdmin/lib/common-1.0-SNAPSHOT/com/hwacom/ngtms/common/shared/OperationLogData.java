/*
 * © HwaCom Systems Inc. 2019
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.common.shared;

import com.hwacom.ngtms.base.oplog.shared.OperationItem;
import com.hwacom.ngtms.base.oplog.shared.OperationResult;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;

public class OperationLogData implements Serializable {

  private static final long serialVersionUID = 1L;

  private String userId;

  private String cpeIp;

  private String subSystem;

  private OperationItem operationItem;

  private String deviceName;

  private String description;

  private Date operationTime;

  private OperationResult operationResult;

  private String remark;

  private Object[] args;

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public String getCpeIp() {
    return cpeIp;
  }

  public void setCpeIp(String cpeIp) {
    this.cpeIp = cpeIp;
  }

  public String getSubSystem() {
    return subSystem;
  }

  public void setSubSystem(String subSystem) {
    this.subSystem = subSystem;
  }

  public OperationItem getOperationItem() {
    return operationItem;
  }

  public void setOperationItem(OperationItem operationItem) {
    this.operationItem = operationItem;
  }

  public String getDeviceName() {
    return deviceName;
  }

  public void setDeviceName(String deviceName) {
    this.deviceName = deviceName;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Date getOperationTime() {
    return operationTime;
  }

  public void setOperationTime(Date operationTime) {
    this.operationTime = operationTime;
  }

  public OperationResult getOperationResult() {
    return operationResult;
  }

  public void setOperationResult(OperationResult operationResult) {
    this.operationResult = operationResult;
  }

  public String getRemark() {
    return remark;
  }

  public void setRemark(String remark) {
    this.remark = remark;
  }

  public Object[] getArgs() {
    return args;
  }

  public void setArgs(Object[] args) {
    this.args = args;
  }

  @Override
  public String toString() {
    return "OperationLogData [userId="
        + userId
        + ", cpeIp="
        + cpeIp
        + ", subSystem="
        + subSystem
        + ", operationItem="
        + operationItem
        + ", deviceName="
        + deviceName
        + ", opTime="
        + operationTime
        + ", opResult="
        + operationResult
        + ", remark="
        + remark
        + ", descMsgId="
        + description
        + ", args="
        + Arrays.toString(args)
        + "]";
  }
}
