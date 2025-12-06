/*
 * © HwaCom Systems Inc. 2013
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.hcce.shared;

import java.io.Serializable;

public class HcceEnv implements Serializable {
  private static final long serialVersionUID = 1L;
  private String nodeName;
  private String primaryGroupName;
  private String backupGroupName;
  private String clientGroupName;
  private String currentGroupName;
  private String localIpAddress;
  private boolean inPrimaryGroup;
  private int rmiRegistryPort;
  private int fmeManagementFirstDelay; // unit: sec
  private int fmeManagementInterval; // unit: sec

  public String getNodeName() {
    return nodeName;
  }

  public void setNodeName(String nodeName) {
    this.nodeName = nodeName;
  }

  public String getPrimaryGroupName() {
    return primaryGroupName;
  }

  public void setPrimaryGroupName(String primaryGroupName) {
    this.primaryGroupName = primaryGroupName;
  }

  public String getBackupGroupName() {
    return backupGroupName;
  }

  public void setBackupGroupName(String backupGroupName) {
    this.backupGroupName = backupGroupName;
  }

  public String getClientGroupName() {
    return clientGroupName;
  }

  public void setClientGroupName(String clientGroupName) {
    this.clientGroupName = clientGroupName;
  }

  public boolean isInPrimaryGroup() {
    return inPrimaryGroup;
  }

  public void setInPrimaryGroup(boolean inPrimaryGroup) {
    this.inPrimaryGroup = inPrimaryGroup;
  }

  public String getCurrentGroupName() {
    return currentGroupName;
  }

  public void setCurrentGroupName(String currentGroupName) {
    this.currentGroupName = currentGroupName;
  }

  public int getRmiRegistryPort() {
    return rmiRegistryPort;
  }

  public void setRmiRegistryPort(int rmiRegistryPort) {
    this.rmiRegistryPort = rmiRegistryPort;
  }

  public int getFmeManagementFirstDelay() {
    return fmeManagementFirstDelay;
  }

  public void setFmeManagementFirstDelay(int fmeManagementFirstDelay) {
    this.fmeManagementFirstDelay = fmeManagementFirstDelay;
  }

  public int getFmeManagementInterval() {
    return fmeManagementInterval;
  }

  public void setFmeManagementInterval(int fmeManagementInterval) {
    this.fmeManagementInterval = fmeManagementInterval;
  }

  public String getLocalIpAddress() {
    return localIpAddress;
  }

  public void setLocalIpAddress(String localIpAddress) {
    this.localIpAddress = localIpAddress;
  }

  @Override
  public String toString() {
    return "HcceEnv [nodeName="
        + nodeName
        + ", primaryGroupName="
        + primaryGroupName
        + ", backupGroupName="
        + backupGroupName
        + ", currentGroupName="
        + currentGroupName
        + ", localIpAddress="
        + localIpAddress
        + ", inPrimaryGroup="
        + inPrimaryGroup
        + ", rmiRegistryPort="
        + rmiRegistryPort
        + ", fmeManagementFirstDelay="
        + fmeManagementFirstDelay
        + ", fmeManagementInterval="
        + fmeManagementInterval
        + "]";
  }
}
