/*
 * © HwaCom Systems Inc. 2015
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.c.shared.dto;

import com.google.gwt.user.client.rpc.IsSerializable;
import java.io.Serializable;

public class OperationLogDTO implements IsSerializable, Serializable {

  private static final long serialVersionUID = 428203291790687083L;

  private Long id;

  /** 使用者 ID */
  private String userId;

  /** 操作者終端設備IP */
  private String cpeIp;

  /** 排程作業ID */
  private String schId;

  /** 子系統名稱 */
  private String subSysName;

  /** 操作項目（本欄位目前暫不使用） */
  private String operationItem;

  /** 操作說明 */
  private String description;

  /** 操作設備 */
  private String deviceName;

  private String displayName;

  /** 操作時間 */
  private String operationTime;

  private Long operationTimeLong;

  private String operationTimeShort;

  /** 操作結果 */
  private String operationResult;

  /** 備註 */
  private String remark;

  public OperationLogDTO() {}

  public OperationLogDTO(
      String userId,
      String cpeIp,
      String schId,
      String subSysName,
      String operationItem,
      String description,
      String deviceName,
      String operationTime,
      String operationResult,
      String remark,
      String operationTimeShort,
      long operationTimeLong) {
    this.userId = userId;
    this.cpeIp = cpeIp;
    this.schId = schId;
    this.subSysName = subSysName;
    this.operationItem = operationItem;
    this.description = description;
    this.deviceName = deviceName;
    this.operationTime = operationTime;
    this.operationResult = operationResult;
    this.remark = remark;
    this.operationTimeShort = operationTimeShort;
    this.operationTimeLong = operationTimeLong;
  }

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

  public String getSchId() {
    return schId;
  }

  public void setSchId(String schId) {
    this.schId = schId;
  }

  public String getSubSysName() {
    return subSysName;
  }

  public void setSubSysName(String subSysName) {
    this.subSysName = subSysName;
  }

  public String getOperationItem() {
    return operationItem;
  }

  public void setOperationItem(String operationItem) {
    this.operationItem = operationItem;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getDeviceName() {
    return deviceName;
  }

  public void setDeviceName(String deviceName) {
    this.deviceName = deviceName;
  }

  public String getDisplayName() {
    return displayName;
  }

  public void setDisplayName(String displayName) {
    this.displayName = displayName;
  }

  public String getOperationTimeShort() {
    return this.operationTimeShort;
  }

  public void setOperationTimeShort(String operationTimeShort) {
    this.operationTimeShort = operationTimeShort;
  }

  /** @return the operationTime */
  public String getOperationTime() {
    return operationTime;
  }

  public void setOperationTime(String operationTime) {
    this.operationTime = operationTime;
  }

  public String getOperationResult() {
    return operationResult;
  }

  public void setOperationResult(String operationResult) {
    this.operationResult = operationResult;
  }

  public String getRemark() {
    return remark;
  }

  public void setRemark(String remark) {
    this.remark = remark;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  /** @return the operationTimeLong */
  public Long getOperationTimeLong() {
    return operationTimeLong;
  }

  /** @param operationTimeLong the operationTimeLong to set */
  public void setOperationTimeLong(Long operationTimeLong) {
    this.operationTimeLong = operationTimeLong;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((id == null) ? 0 : id.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null) return false;
    if (getClass() != obj.getClass()) return false;
    OperationLogDTO other = (OperationLogDTO) obj;
    if (id == null) {
      if (other.id != null) return false;
    } else if (!id.equals(other.id)) return false;
    return true;
  }

  /* (non-Javadoc)
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    return "OperationLogDTO [id="
        + id
        + ", userId="
        + userId
        + ", cpeIp="
        + cpeIp
        + ", schId="
        + schId
        + ", subSysName="
        + subSysName
        + ", operationItem="
        + operationItem
        + ", description="
        + description
        + ", deviceName="
        + deviceName
        + ", displayName="
        + displayName
        + ", operationTime="
        + operationTime
        + ", operationTimeLong="
        + operationTimeLong
        + ", operationTimeShort="
        + operationTimeShort
        + ", operationResult="
        + operationResult
        + ", remark="
        + remark
        + "]";
  }
}
