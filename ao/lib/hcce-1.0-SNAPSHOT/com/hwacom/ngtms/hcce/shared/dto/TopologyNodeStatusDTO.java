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

public class TopologyNodeStatusDTO implements Serializable, IsSerializable {

  private static final long serialVersionUID = 3409611494572106747L;
  private String groupName;
  private String nodeName;
  private boolean coordinator;
  private boolean registered;
  private Date registerTime;
  private Date unRegisterTime;
  private String serviceTime;
  private String lastRegIpAddress; // 最後一次註冊的 IP 位址
  private String operation;
  private String mapOperation;

  public String getKey() {
    return this.groupName + this.nodeName;
  }

  /** @return the groupName */
  public String getGroupName() {
    return groupName;
  }

  /** @param groupName the groupName to set */
  public void setGroupName(String groupName) {
    this.groupName = groupName;
  }

  /** @return the nodeName */
  public String getNodeName() {
    return nodeName;
  }

  /** @param nodeName the nodeName to set */
  public void setNodeName(String nodeName) {
    this.nodeName = nodeName;
  }

  /** @return the coordinator */
  public boolean isCoordinator() {
    return coordinator;
  }

  /** @param coordinator the coordinator to set */
  public void setCoordinator(boolean coordinator) {
    this.coordinator = coordinator;
  }

  /** @return the registered */
  public boolean isRegistered() {
    return registered;
  }

  /** @param registered the registered to set */
  public void setRegistered(boolean registered) {
    this.registered = registered;
  }

  /** @return the registerTime */
  public Date getRegisterTime() {
    if (registerTime != null) return new Date(registerTime.getTime());
    else return null;
  }

  /** @param registerTime the registerTime to set */
  public void setRegisterTime(Date registerTime) {
    if (registerTime != null) this.registerTime = new Date(registerTime.getTime());
    else this.registerTime = null;
  }

  /** @return the serviceTime */
  public String getServiceTime() {
    return serviceTime;
  }

  /** @param serviceTime the serviceTime to set */
  public void setServiceTime(String serviceTime) {
    this.serviceTime = serviceTime;
  }

  /** @return the lastRegIpAddress */
  public String getLastRegIpAddress() {
    return lastRegIpAddress;
  }

  /** @param lastRegIpAddress the lastRegIpAddress to set */
  public void setLastRegIpAddress(String lastRegIpAddress) {
    this.lastRegIpAddress = lastRegIpAddress;
  }

  /** @return the operation */
  public String getOperation() {
    return operation;
  }

  /** @param operation the operation to set */
  public void setOperation(String operation) {
    this.operation = operation;
  }

  /** @return the operation */
  public String getMapOperation() {
    return mapOperation;
  }

  /** @param operation the operation to set */
  public void setMapOperation(String mapOperation) {
    this.mapOperation = mapOperation;
  }

  /** @return the unRegisterTime */
  public Date getUnRegisterTime() {
    if (unRegisterTime != null) return new Date(unRegisterTime.getTime());
    else return null;
  }

  /** @param unRegisterTime the unRegisterTime to set */
  public void setUnRegisterTime(Date unRegisterTime) {
    if (unRegisterTime != null) this.unRegisterTime = new Date(unRegisterTime.getTime());
    else this.unRegisterTime = null;
  }

  /*
   * (non-Javadoc)
   *
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("TopologyNodeStatusDTO [groupName=");
    builder.append(groupName);
    builder.append(", nodeName=");
    builder.append(nodeName);
    builder.append(", coordinator=");
    builder.append(coordinator);
    builder.append(", registered=");
    builder.append(registered);
    builder.append(", registerTime=");
    builder.append(registerTime);
    builder.append(", unRegisterTime=");
    builder.append(unRegisterTime);
    builder.append(", serviceTime=");
    builder.append(serviceTime);
    builder.append(", lastRegIpAddress=");
    builder.append(lastRegIpAddress);
    builder.append(", operation=");
    builder.append(operation);
    builder.append("]");
    return builder.toString();
  }
}
