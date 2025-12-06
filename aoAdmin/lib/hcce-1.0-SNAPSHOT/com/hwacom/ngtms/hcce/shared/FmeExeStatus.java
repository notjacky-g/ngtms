/*
 * © HwaCom Systems Inc. 2013
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.hcce.shared;

import java.io.Serializable;
import java.util.Date;

public class FmeExeStatus implements Serializable {
  private static final long serialVersionUID = 2771350779099234915L;

  private String fmeName;
  private String fmeClassName;
  private String assignedMemberUuid;
  private String assignedNodeName;
  private FmeState state = FmeState.NoNode;
  private String assignedMemberIpAddress;
  private Date startTime;

  @Override
  public String toString() {
    return "FmeExeStatus [fmeName="
        + fmeName
        + ", fmeClassName="
        + fmeClassName
        + ", assignedMemberUuid="
        + assignedMemberUuid
        + ", assignedNodeName="
        + assignedNodeName
        + ", state="
        + state
        + ", assignedMemberIpAddress="
        + assignedMemberIpAddress
        + ", startTime="
        + startTime
        + "]";
  }

  public void resetAssignedNodeInfo() {
    assignedMemberUuid = null;
    assignedNodeName = null;
    assignedMemberIpAddress = null;
  }

  public void setAssignedNodeInfo(String nodeName, String memberUuid, String memberIpAddress) {
    assignedMemberUuid = memberUuid;
    assignedNodeName = nodeName;
    assignedMemberIpAddress = memberIpAddress;
  }
  /** 設定為停止,並設定 ip、member、node name為空 */
  //	public void toStopState() {
  //		setAssignedMemberUuid(null);
  //		setAssignedNodeName(null);
  //		setAssignedMemberIpAddress(null);
  //	}

  /** 設定為啟動狀態, 以及ip、member、node name */
  //	public void toStartState(Member m) {
  //		setAssignedMemberUuid(m.getUuid());
  //		setAssignedMemberIpAddress(HzUtils.getIpAddress(m));
  //		setAssignedNodeName(HcceManageUtils.getNodeNameByMember(m));
  //		setState(FmeState.Starting);
  //	}

  public String getFmeName() {
    return fmeName;
  }

  public void setFmeName(String fmeName) {
    this.fmeName = fmeName;
  }

  public String getAssignedNodeName() {
    return assignedNodeName;
  }

  public void setAssignedNodeName(String assignedNodeName) {
    this.assignedNodeName = assignedNodeName;
  }

  public String getFmeClassName() {
    return fmeClassName;
  }

  public void setFmeClassName(String fmeClassName) {
    this.fmeClassName = fmeClassName;
  }

  public String getAssignedMemberUuid() {
    return assignedMemberUuid;
  }

  public void setAssignedMemberUuid(String assignedMemberUuid) {
    this.assignedMemberUuid = assignedMemberUuid;
  }

  public FmeState getState() {
    return state;
  }

  public void setState(FmeState state) {
    this.state = state;
  }

  public String getAssignedMemberIpAddress() {
    return assignedMemberIpAddress;
  }

  public void setAssignedMemberIpAddress(String assignedMemberIpAddress) {
    this.assignedMemberIpAddress = assignedMemberIpAddress;
  }

  public Date getStartTime() {
    return startTime;
  }

  public void setStartTime(Date startTime) {
    this.startTime = startTime;
  }
}
