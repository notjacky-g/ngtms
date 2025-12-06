/*
 * © HwaCom Systems Inc. 2013
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.hcce.shared.dto;

import com.google.common.base.MoreObjects;
import com.google.gwt.user.client.rpc.IsSerializable;
import java.io.Serializable;
import java.util.Date;

public class NodeMonitorLogDTO implements Serializable, IsSerializable {

  private static final long serialVersionUID = -2663287126591777310L;
  private String id;

  /** 主系統主機狀態 true : 正常 false : 故障 */
  private Boolean mainHCStatus = false;

  /** 備援系統主機狀態 true : 正常 false : 故障 */
  private Boolean backupHCStatus = false;

  /** 線上資料庫狀態 true : 正常 false : 故障 */
  private Boolean onlineDBStatus = false;

  /** 備援資料庫狀態 true : 正常 false : 故障 */
  private Boolean backupDBStatus = false;

  /*
   * 主系統狀態
   * 0 : 正常; mainHCStatus = true and onlineDBStatus = true and backupDBStatus = true
   * 1 : 故障; mainHCStatus = false or onlineDBStatus = false
   * 2 : 異常; mainHCStatus = true and onlineDBStatus = true and backupDBStatus = false
   */
  private Integer mainSystemStatus = 1;

  /*
   * 備援系統狀態
   * 0 : 正常; backupHCStatus = true and backupDBStatus = true
   * 1 : 故障; backupHCStatus = false or backupDBStatus = false
   */
  private Integer backupSystemStatus = 1;

  /** 操作時間 */
  private Date updateDate;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public Boolean getMainHCStatus() {
    return mainHCStatus;
  }

  public void setMainHCStatus(Boolean mainHCStatus) {
    this.mainHCStatus = mainHCStatus;
  }

  public Boolean getBackupHCStatus() {
    return backupHCStatus;
  }

  public void setBackupHCStatus(Boolean backupHCStatus) {
    this.backupHCStatus = backupHCStatus;
  }

  public Boolean getOnlineDBStatus() {
    return onlineDBStatus;
  }

  public void setOnlineDBStatus(Boolean onlineDBStatus) {
    this.onlineDBStatus = onlineDBStatus;
  }

  public Boolean getBackupDBStatus() {
    return backupDBStatus;
  }

  public void setBackupDBStatus(Boolean backupDBStatus) {
    this.backupDBStatus = backupDBStatus;
  }

  public Integer getMainSystemStatus() {
    return mainSystemStatus;
  }

  public void setMainSystemStatus(Integer mainSystemStatus) {
    this.mainSystemStatus = mainSystemStatus;
  }

  public Integer getBackupSystemStatus() {
    return backupSystemStatus;
  }

  public void setBackupSystemStatus(Integer backupSystemStatus) {
    this.backupSystemStatus = backupSystemStatus;
  }

  public Date getUpdateDate() {
    return updateDate;
  }

  public void setUpdateDate(Date updateDate) {
    this.updateDate = updateDate;
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
    NodeMonitorLogDTO other = (NodeMonitorLogDTO) obj;
    if (id == null) {
      if (other.id != null) return false;
    } else if (!id.equals(other.id)) return false;
    return true;
  }

  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this)
        .add("id", id)
        .add("mainHCStatus", mainHCStatus)
        .add("backupHCStatus", backupHCStatus)
        .add("onlineDBStatus", onlineDBStatus)
        .add("backupDBStatus", backupDBStatus)
        .add("mainSystemStatus", mainSystemStatus)
        .add("backupSystemStatus", backupSystemStatus)
        .add("updateDate", updateDate)
        .toString();
  }
}
