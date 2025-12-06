/*
 * © HwaCom Systems Inc. 2015
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.c.shared.dto;

import com.google.gwt.user.client.rpc.IsSerializable;
import com.hwacom.ngtms.c.shared.AreaType;
import java.io.Serializable;

/** @author brian.cheng */
public class BranchConfigDTO implements Serializable, IsSerializable {
  private static final long serialVersionUID = 1096033713500819184L;

  private String id;

  /** 工務段名稱 */
  private String branchName;

  /** 快速道ID */
  private String expresswayId;

  /** 國道ID */
  private String lineId;

  /** 轄區起始公里數 */
  private Integer startMileage;

  /** 分區 */
  private AreaType areaType;

  /** 轄區結束公里數 */
  private Integer endMileage;

  /** 是否為全部選項 */
  private Boolean all = Boolean.FALSE;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getBranchName() {
    return branchName;
  }

  public void setBranchName(String branchName) {
    this.branchName = branchName;
  }

  public String getExpresswayId() {
    return expresswayId;
  }

  public void setExpresswayId(String expresswayId) {
    this.expresswayId = expresswayId;
  }

  public String getLineId() {
    return lineId;
  }

  public void setLineId(String lineId) {
    this.lineId = lineId;
  }

  public Integer getStartMileage() {
    return startMileage;
  }

  public void setStartMileage(Integer startMileage) {
    this.startMileage = startMileage;
  }

  public AreaType getAreaType() {
    return areaType;
  }

  public void setAreaType(AreaType areaType) {
    this.areaType = areaType;
  }

  public Integer getEndMileage() {
    return endMileage;
  }

  public void setEndMileage(Integer endMileage) {
    this.endMileage = endMileage;
  }

  public Boolean getAll() {
    return all;
  }

  public void setAll(Boolean all) {
    this.all = all;
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
    BranchConfigDTO other = (BranchConfigDTO) obj;
    if (id == null) {
      if (other.id != null) return false;
    } else if (!id.equals(other.id)) return false;
    return true;
  }
}
