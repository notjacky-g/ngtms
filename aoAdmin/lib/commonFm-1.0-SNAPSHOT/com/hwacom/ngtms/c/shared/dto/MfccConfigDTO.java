/*
 * © HwaCom Systems Inc. 2015
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.c.shared.dto;

import com.google.gwt.user.client.rpc.IsSerializable;
import com.hwacom.ngtms.c.shared.MfccStatus;
import java.io.Serializable;

/**
 * MFCC軟體基本建構主檔表
 *
 * @author jeff.lien
 */
public class MfccConfigDTO implements Serializable, IsSerializable {
  private static final long serialVersionUID = 4568908500326080895L;

  /** MFCC軟體編號 */
  private String mfccId;

  /** MFCC軟體名稱 */
  private String mfccName;
  /** 所屬主機編號 */
  private String hostId;
  /** 備註說明 */
  private String memo;
  /** MFCC設備狀態 */
  private MfccStatus status;

  /** 此欄位須跟hcce_group_info裡面的group_name對照 */
  private String groupName;

  public MfccConfigDTO() {}

  public MfccConfigDTO(String mfccId, String hostId) {
    this.mfccId = mfccId;
    this.hostId = hostId;
  }

  public MfccConfigDTO(
      String mfccId, String mfccName, String hostId, String memo, MfccStatus status) {
    this.mfccId = mfccId;
    this.mfccName = mfccName;
    this.hostId = hostId;
    this.memo = memo;
    this.status = status;
  }

  public String getMfccId() {
    return mfccId;
  }

  public void setMfccId(String mfccId) {
    this.mfccId = mfccId;
  }

  public String getMfccName() {
    return mfccName;
  }

  public void setMfccName(String mfccName) {
    this.mfccName = mfccName;
  }

  public String getHostId() {
    return hostId;
  }

  public void setHostId(String hostId) {
    this.hostId = hostId;
  }

  public String getMemo() {
    return this.memo;
  }

  public void setMemo(String memo) {
    this.memo = memo;
  }

  public MfccStatus getStatus() {
    return this.status;
  }

  public void setStatus(MfccStatus status) {
    this.status = status;
  }

  @Override
  public int hashCode() {
    int hash = 0;
    hash += (mfccId != null ? mfccId.hashCode() : 0);
    return hash;
  }

  @Override
  public boolean equals(Object object) {
    // TODO: Warning - this method won't work in the case the id fields are not set
    if (!(object instanceof MfccConfigDTO)) {
      return false;
    }
    MfccConfigDTO other = (MfccConfigDTO) object;
    if ((this.mfccId == null && other.mfccId != null)
        || (this.mfccId != null && !this.mfccId.equals(other.mfccId))) {
      return false;
    }
    return true;
  }

  public String getGroupName() {
    return groupName;
  }

  public void setGroupName(String groupName) {
    this.groupName = groupName;
  }
}
