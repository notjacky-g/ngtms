/*
 * © HwaCom Systems Inc. 2013
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.hcce.shared.dto;

import com.google.gwt.user.client.rpc.IsSerializable;
import java.io.Serializable;

public class TopologyNodeCfgVO implements Serializable, IsSerializable {

  private static final long serialVersionUID = 5171880691715770174L;

  private String groupName;

  private String nodeName;

  private String description;

  @Override
  public int hashCode() {
    int hash = 0;
    hash += (nodeName != null ? nodeName.hashCode() : 0);
    return hash;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null) return false;
    if (getClass() != obj.getClass()) return false;
    TopologyNodeCfgVO other = (TopologyNodeCfgVO) obj;
    if (groupName == null) {
      if (other.groupName != null) return false;
    } else if (!groupName.equals(other.groupName)) return false;
    if (nodeName == null) {
      if (other.nodeName != null) return false;
    } else if (!nodeName.equals(other.nodeName)) return false;
    return true;
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

  /** @return the description */
  public String getDescription() {
    return description;
  }

  /** @param description the description to set */
  public void setDescription(String description) {
    this.description = description;
  }

  public String getKey() {
    return this.groupName + this.nodeName;
  }

  /* (non-Javadoc)
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("TopologyNodeCfgDTO [groupName=");
    builder.append(groupName);
    builder.append(", nodeName=");
    builder.append(nodeName);
    builder.append(", description=");
    builder.append(description);
    builder.append("]");
    return builder.toString();
  }
}
