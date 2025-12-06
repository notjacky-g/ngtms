/*
 * © HwaCom Systems Inc. 2015
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.pd.shared.dto;

import java.io.Serializable;

/**
 * PD 迴路底下設備組態設定
 *
 * @author brian.cheng
 */
public class PdLoopDeviceConfigDTO implements Serializable {

  private static final long serialVersionUID = 5674005695502855080L;

  /** ID */
  private Long id;

  /** PD點設備編號 */
  private String pdDeviceName;

  /** 迴路編號 */
  private String loopNo;

  /** 所屬設備編號 */
  private String deviceName;

  /** 線徑 */
  private Integer diameter;

  /** 備註 */
  private String memo;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getPdDeviceName() {
    return pdDeviceName;
  }

  public void setPdDeviceName(String pdDeviceName) {
    this.pdDeviceName = pdDeviceName;
  }

  public String getLoopNo() {
    return loopNo;
  }

  public void setLoopNo(String loopNo) {
    this.loopNo = loopNo;
  }

  public String getDeviceName() {
    return deviceName;
  }

  public void setDeviceName(String deviceName) {
    this.deviceName = deviceName;
  }

  public Integer getDiameter() {
    return diameter;
  }

  public void setDiameter(Integer diameter) {
    this.diameter = diameter;
  }

  public String getMemo() {
    return memo;
  }

  public void setMemo(String memo) {
    this.memo = memo;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((deviceName == null) ? 0 : deviceName.hashCode());
    result = prime * result + ((diameter == null) ? 0 : diameter.hashCode());
    result = prime * result + ((id == null) ? 0 : id.hashCode());
    result = prime * result + ((loopNo == null) ? 0 : loopNo.hashCode());
    result = prime * result + ((memo == null) ? 0 : memo.hashCode());
    result = prime * result + ((pdDeviceName == null) ? 0 : pdDeviceName.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null) return false;
    if (getClass() != obj.getClass()) return false;
    PdLoopDeviceConfigDTO other = (PdLoopDeviceConfigDTO) obj;
    if (deviceName == null) {
      if (other.deviceName != null) return false;
    } else if (!deviceName.equals(other.deviceName)) return false;
    if (diameter == null) {
      if (other.diameter != null) return false;
    } else if (!diameter.equals(other.diameter)) return false;
    if (id == null) {
      if (other.id != null) return false;
    } else if (!id.equals(other.id)) return false;
    if (loopNo == null) {
      if (other.loopNo != null) return false;
    } else if (!loopNo.equals(other.loopNo)) return false;
    if (memo == null) {
      if (other.memo != null) return false;
    } else if (!memo.equals(other.memo)) return false;
    if (pdDeviceName == null) {
      if (other.pdDeviceName != null) return false;
    } else if (!pdDeviceName.equals(other.pdDeviceName)) return false;
    return true;
  }

  @Override
  public String toString() {
    return "PdLoopDeviceConfig [id="
        + id
        + ", pdDeviceName="
        + pdDeviceName
        + ", loopNo="
        + loopNo
        + ", deviceName="
        + deviceName
        + ", diameter="
        + diameter
        + ", memo="
        + memo
        + "]";
  }
}
