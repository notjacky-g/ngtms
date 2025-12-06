/*
 * © HwaCom Systems Inc. 2015
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.pd.shared.dto;

import com.google.gwt.user.client.rpc.IsSerializable;
import com.sencha.gxt.data.shared.TreeStore.TreeNode;
import java.util.ArrayList;
import java.util.List;
/**
 * PD 迴路底下設備組態設定 for treeGird
 *
 * @author huei.yang
 */
public class LoopDeviceConfigDTO implements IsSerializable, TreeNode<LoopDeviceConfigDTO> {

  /** 設備編號 */
  private String deviceName;

  /** 設備編號 */
  private String displayName;

  /** 迴路編號 */
  private Integer loopNo;

  /** 線徑 */
  private Integer diameter;

  /** 備註 */
  private String memo;

  /** 是否為最底層 */
  private Boolean check = false;

  /** 路線id */
  private String lineId;

  /** 路線名稱 */
  private String lineName;

  /** 里程 */
  private Integer mileage;

  private List<LoopDeviceConfigDTO> children = new ArrayList<LoopDeviceConfigDTO>();

  public Integer getLoopNo() {
    return loopNo;
  }

  public void setLoopNo(Integer loopNo) {
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

  public String getDisplayName() {
    return displayName;
  }

  public void setDisplayName(String displayName) {
    this.displayName = displayName;
  }

  public Boolean getCheck() {
    return check;
  }

  public void setCheck(Boolean check) {
    this.check = check;
  }

  public void setChildren(List<LoopDeviceConfigDTO> children) {
    this.children = children;
  }

  public String getLineId() {
    return lineId;
  }

  public void setLineId(String lineId) {
    this.lineId = lineId;
  }

  public String getLineName() {
    return lineName;
  }

  public void setLineName(String lineName) {
    this.lineName = lineName;
  }

  public Integer getMileage() {
    return mileage;
  }

  public void setMileage(Integer mileage) {
    this.mileage = mileage;
  }

  @Override
  public List<LoopDeviceConfigDTO> getChildren() {
    return children;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((deviceName == null) ? 0 : deviceName.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null) return false;
    if (getClass() != obj.getClass()) return false;
    LoopDeviceConfigDTO other = (LoopDeviceConfigDTO) obj;
    if (deviceName == null) {
      if (other.deviceName != null) return false;
    } else if (!deviceName.equals(other.deviceName)) return false;
    return true;
  }

  @Override
  public String toString() {
    return "LoopDeviceConfigDTO [deviceName="
        + deviceName
        + ", displayName="
        + displayName
        + ", loopNo="
        + loopNo
        + ", diameter="
        + diameter
        + ", memo="
        + memo
        + ", check="
        + check
        + ", children="
        + children
        + "]";
  }

  @Override
  public LoopDeviceConfigDTO getData() {
    return null;
  }
}
