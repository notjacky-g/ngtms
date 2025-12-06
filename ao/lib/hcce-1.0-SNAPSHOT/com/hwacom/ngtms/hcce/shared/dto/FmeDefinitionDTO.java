/*
 * © HwaCom Systems Inc. 2013
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.hcce.shared.dto;

import com.google.gwt.user.client.rpc.IsSerializable;
import java.io.Serializable;
import java.util.Date;

public class FmeDefinitionDTO implements Serializable, IsSerializable {

  private static final long serialVersionUID = -1603508220985136182L;
  private String groupName;
  private String fmeName;
  private String className;
  private String nodePriority;
  private boolean enable;
  private String fmeStatus;
  private String nowNode;
  private String description;
  private String params;
  private String assignedMemberUuid;
  private String assignedNodeName;
  private String assignedMemberIpAddress;
  private Date startTime;
  private String operation;
  private String userId;

  public String getKey() {
    return groupName + fmeName;
  }

  /** @return the groupName */
  public String getGroupName() {
    return groupName;
  }

  /** @param groupName the groupName to set */
  public void setGroupName(String groupName) {
    this.groupName = groupName;
  }

  /** @return the fmeName */
  public String getFmeName() {
    return fmeName;
  }

  /** @param fmeName the fmeName to set */
  public void setFmeName(String fmeName) {
    this.fmeName = fmeName;
  }

  /** @return the className */
  public String getClassName() {
    return className;
  }

  /** @param className the className to set */
  public void setClassName(String className) {
    this.className = className;
  }

  /** @return the nodePriority */
  public String getNodePriority() {
    return nodePriority;
  }

  /** @param nodePriority the nodePriority to set */
  public void setNodePriority(String nodePriority) {
    this.nodePriority = nodePriority;
  }

  /** @return the enable */
  public boolean isEnable() {
    return enable;
  }

  /** @param enable the enable to set */
  public void setEnable(boolean enable) {
    this.enable = enable;
  }

  /** @return the fmeStatus */
  public String getFmeStatus() {
    return fmeStatus;
  }

  /** @param fmeStatus the fmeStatus to set */
  public void setFmeStatus(String fmeStatus) {
    this.fmeStatus = fmeStatus;
  }

  /** @return the nowNode */
  public String getNowNode() {
    return nowNode;
  }

  /** @param nowNode the nowNode to set */
  public void setNowNode(String nowNode) {
    this.nowNode = nowNode;
  }

  /** @return the description */
  public String getDescription() {
    return description;
  }

  /** @param description the description to set */
  public void setDescription(String description) {
    this.description = description;
  }

  /** @return the params */
  public String getParams() {
    return params;
  }

  /** @param params the params to set */
  public void setParams(String params) {
    this.params = params;
  }

  /** @return the assignedMemberUuid */
  public String getAssignedMemberUuid() {
    return assignedMemberUuid;
  }

  /** @param assignedMemberUuid the assignedMemberUuid to set */
  public void setAssignedMemberUuid(String assignedMemberUuid) {
    this.assignedMemberUuid = assignedMemberUuid;
  }

  /** @return the assignedNodeName */
  public String getAssignedNodeName() {
    return assignedNodeName;
  }

  /** @param assignedNodeName the assignedNodeName to set */
  public void setAssignedNodeName(String assignedNodeName) {
    this.assignedNodeName = assignedNodeName;
  }

  /** @return the assignedMemberIpAddress */
  public String getAssignedMemberIpAddress() {
    return assignedMemberIpAddress;
  }

  /** @param assignedMemberIpAddress the assignedMemberIpAddress to set */
  public void setAssignedMemberIpAddress(String assignedMemberIpAddress) {
    this.assignedMemberIpAddress = assignedMemberIpAddress;
  }

  /** @return the startTime */
  public Date getStartTime() {
    if (startTime != null) return new Date(startTime.getTime());
    else return null;
  }

  /** @param startTime the startTime to set */
  public void setStartTime(Date startTime) {
    if (startTime != null) this.startTime = new Date(startTime.getTime());
    else this.startTime = null;
  }

  /** @return the operation */
  public String getOperation() {
    return operation;
  }

  /** @param operation the operation to set */
  public void setOperation(String operation) {
    this.operation = operation;
  }

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  /*
   * (non-Javadoc)
   *
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("FmeDefinitionDTO [groupName=");
    builder.append(groupName);
    builder.append(", fmeName=");
    builder.append(fmeName);
    builder.append(", className=");
    builder.append(className);
    builder.append(", nodePriority=");
    builder.append(nodePriority);
    builder.append(", enable=");
    builder.append(enable);
    builder.append(", fmeStatus=");
    builder.append(fmeStatus);
    builder.append(", nowNode=");
    builder.append(nowNode);
    builder.append(", description=");
    builder.append(description);
    builder.append(", params=");
    builder.append(params);
    builder.append(", assignedMemberUuid=");
    builder.append(assignedMemberUuid);
    builder.append(", assignedNodeName=");
    builder.append(assignedNodeName);
    builder.append(", assignedMemberIpAddress=");
    builder.append(assignedMemberIpAddress);
    builder.append(", startTime=");
    builder.append(startTime);
    builder.append(", operation=");
    builder.append(operation);
    builder.append(", issueVersion=");
    builder.append("]");
    return builder.toString();
  }

  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((fmeName == null) ? 0 : fmeName.hashCode());
    result = prime * result + ((groupName == null) ? 0 : groupName.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null) return false;
    if (getClass() != obj.getClass()) return false;
    FmeDefinitionDTO other = (FmeDefinitionDTO) obj;
    if (fmeName == null) {
      if (other.fmeName != null) return false;
    } else if (!fmeName.equals(other.fmeName)) return false;
    if (groupName == null) {
      if (other.groupName != null) return false;
    } else if (!groupName.equals(other.groupName)) return false;
    return true;
  }
}
