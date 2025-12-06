/*
 * © HwaCom Systems Inc. 2013
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.hcce.shared.dto;

import com.google.gwt.user.client.rpc.IsSerializable;
import java.io.Serializable;

public class HcceEnvDTO implements Serializable, IsSerializable {

  private static final long serialVersionUID = 8468419734056593009L;
  private String nodeName;
  private String primaryGroupName;
  private String backupGroupName;
  private String currentGroupName;
  private String localIpAddress;
  private boolean inPrimaryGroup;
  private int rmiRegistryPort;
  private int fmeManagementFirstDelay; // unit: sec
  private int fmeManagementInterval; // unit: sec

  /** @return the nodeName */
  public String getNodeName() {
    return nodeName;
  }
  /** @param nodeName the nodeName to set */
  public void setNodeName(String nodeName) {
    this.nodeName = nodeName;
  }
  /** @return the primaryGroupName */
  public String getPrimaryGroupName() {
    return primaryGroupName;
  }
  /** @param primaryGroupName the primaryGroupName to set */
  public void setPrimaryGroupName(String primaryGroupName) {
    this.primaryGroupName = primaryGroupName;
  }
  /** @return the backupGroupName */
  public String getBackupGroupName() {
    return backupGroupName;
  }
  /** @param backupGroupName the backupGroupName to set */
  public void setBackupGroupName(String backupGroupName) {
    this.backupGroupName = backupGroupName;
  }
  /** @return the currentGroupName */
  public String getCurrentGroupName() {
    return currentGroupName;
  }
  /** @param currentGroupName the currentGroupName to set */
  public void setCurrentGroupName(String currentGroupName) {
    this.currentGroupName = currentGroupName;
  }
  /** @return the inPrimaryGroup */
  public Boolean getInPrimaryGroup() {
    return inPrimaryGroup;
  }
  /** @param inPrimaryGroup the inPrimaryGroup to set */
  public void setInPrimaryGroup(Boolean inPrimaryGroup) {
    this.inPrimaryGroup = inPrimaryGroup;
  }
  /** @return the rmiRegistryPort */
  public int getRmiRegistryPort() {
    return rmiRegistryPort;
  }
  /** @param rmiRegistryPort the rmiRegistryPort to set */
  public void setRmiRegistryPort(int rmiRegistryPort) {
    this.rmiRegistryPort = rmiRegistryPort;
  }
  /** @return the fmeManagementFirstDelay */
  public int getFmeManagementFirstDelay() {
    return fmeManagementFirstDelay;
  }
  /** @param fmeManagementFirstDelay the fmeManagementFirstDelay to set */
  public void setFmeManagementFirstDelay(int fmeManagementFirstDelay) {
    this.fmeManagementFirstDelay = fmeManagementFirstDelay;
  }
  /** @return the fmeManagementInterval */
  public int getFmeManagementInterval() {
    return fmeManagementInterval;
  }
  /** @param fmeManagementInterval the fmeManagementInterval to set */
  public void setFmeManagementInterval(int fmeManagementInterval) {
    this.fmeManagementInterval = fmeManagementInterval;
  }

  public String getLocalIpAddress() {
    return localIpAddress;
  }

  public void setLocalIpAddress(String localIpAddress) {
    this.localIpAddress = localIpAddress;
  }

  public void setInPrimaryGroup(boolean inPrimaryGroup) {
    this.inPrimaryGroup = inPrimaryGroup;
  }
  /* (non-Javadoc)
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((backupGroupName == null) ? 0 : backupGroupName.hashCode());
    result = prime * result + ((currentGroupName == null) ? 0 : currentGroupName.hashCode());
    result = prime * result + ((primaryGroupName == null) ? 0 : primaryGroupName.hashCode());
    return result;
  }
  /* (non-Javadoc)
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null) return false;
    if (getClass() != obj.getClass()) return false;
    HcceEnvDTO other = (HcceEnvDTO) obj;
    if (backupGroupName == null) {
      if (other.backupGroupName != null) return false;
    } else if (!backupGroupName.equals(other.backupGroupName)) return false;
    if (currentGroupName == null) {
      if (other.currentGroupName != null) return false;
    } else if (!currentGroupName.equals(other.currentGroupName)) return false;
    if (primaryGroupName == null) {
      if (other.primaryGroupName != null) return false;
    } else if (!primaryGroupName.equals(other.primaryGroupName)) return false;
    return true;
  }

  @Override
  public String toString() {
    return "HcceEnvDTO [nodeName="
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
