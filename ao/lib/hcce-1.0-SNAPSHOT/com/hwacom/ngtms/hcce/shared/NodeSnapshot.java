/*
 * © HwaCom Systems Inc. 2013
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.hcce.shared;

import java.io.Serializable;

public class NodeSnapshot implements Serializable {
  private static final long serialVersionUID = 1L;
  private String nodeName;
  private String ipAddress;
  private int port;

  public NodeSnapshot(String nodeName, String ipAddress, int port) {
    this.nodeName = nodeName;
    this.ipAddress = ipAddress;
    this.port = port;
  }

  public String getNodeName() {
    return nodeName;
  }

  public void setNodeName(String nodeName) {
    this.nodeName = nodeName;
  }

  public String getIpAddress() {
    return ipAddress;
  }

  public void setIpAddress(String ipAddress) {
    this.ipAddress = ipAddress;
  }

  public int getPort() {
    return port;
  }

  public void setPort(int port) {
    this.port = port;
  }

  @Override
  public String toString() {
    return "NodeSnapshot [nodeName="
        + nodeName
        + ", ipAddress="
        + ipAddress
        + ", port="
        + port
        + "]";
  }
}
