/*
 * © HwaCom Systems Inc. 2013
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.hcce.shared;

import com.hwacom.ngtms.hcce.topology.model.TopologyNodeCfg;
import java.io.Serializable;
import java.util.Date;

public class TopologyNode implements Serializable {
  private static final long serialVersionUID = 2L;
  private String memberUuid;
  // Hazelcast 3.2 後 DataSerializable 不再繼承 Serializable，導致 TopologyNode 無法被放入 IMap
  // 這裡將 member 變為 transient，serialize 時只存 member UUID，當要取 member 時再把他找出來
  //private  Member member;
  private boolean coordinator;
  private boolean registered;
  private Date registerTime;
  private Date unregisterTime;
  private TopologyNodeCfg nodeCfg;
  private String lastRegIpAddress; // 最後一次註冊的 IP 位址
  private int webContainerPort;

  public TopologyNode(TopologyNodeCfg nodeCfg) {
    this.nodeCfg = nodeCfg;
  }

  public boolean isCoordinator() {
    return coordinator;
  }

  public void setCoordinator(boolean coordinator) {
    this.coordinator = coordinator;
  }

  public boolean isRegistered() {
    return registered;
  }

  public void setRegistered(boolean registered) {
    this.registered = registered;
  }

  public TopologyNodeCfg getNodeCfg() {
    return nodeCfg;
  }

  public void setNodeCfg(TopologyNodeCfg nodeCfg) {
    this.nodeCfg = nodeCfg;
  }

  public String getMemberUuid() {
    return memberUuid;
  }

  public void setMemberUuid(String memberUuid) {
    this.memberUuid = memberUuid;
  }

  public String getLastRegIpAddress() {
    return lastRegIpAddress;
  }

  public void setLastRegIpAddress(String lastRegIpAddress) {
    this.lastRegIpAddress = lastRegIpAddress;
  }

  public int getWebContainerPort() {
    return webContainerPort;
  }

  public void setWebContainerPort(int webContainerPort) {
    this.webContainerPort = webContainerPort;
  }

  public Date getUnregisterTime() {
    return unregisterTime;
  }

  public void setUnregisterTime(Date unregisterTime) {
    this.unregisterTime = unregisterTime;
  }

  /**
   * 上次註冊或解除註冊的時間
   *
   * @return
   */
  public Date getRegisterTime() {
    return registerTime;
  }

  /**
   * 設定註冊或解除註冊的時間
   *
   * @param registerTime
   */
  public void setRegisterTime(Date registerTime) {
    this.registerTime = registerTime;
  }

  @Override
  public String toString() {
    return "TopologyNode [memberUuid="
        + memberUuid
        + ", coordinator="
        + coordinator
        + ", registered="
        + registered
        + ", registerTime="
        + registerTime
        + ", unregisterTime="
        + unregisterTime
        + ", nodeCfg="
        + nodeCfg
        + ", lastRegIpAddress="
        + lastRegIpAddress
        + ", webContainerPort="
        + webContainerPort
        + "]";
  }
}
