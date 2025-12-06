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
import java.util.Map;
import java.util.TreeMap;

public class TopologyInfo implements Serializable {
  private static final long serialVersionUID = 7534857283271049260L;
  private Date startTime;
  private Map<String, TopologyNode> nodeMap;

  @Override
  public String toString() {
    return "TopologyInfo [startTime=" + startTime + ", nodeMap=" + nodeMap + "]";
  }

  public Map<String, TopologyNode> getNodeMap() {
    return nodeMap;
  }

  public void setNodeMap(Map<String, TopologyNode> nodeMap) {
    this.nodeMap = nodeMap;
  }

  public TopologyInfo addTopologyNode(TopologyNodeCfg nodeCfg) {
    if (nodeMap == null) {
      nodeMap = new TreeMap<String, TopologyNode>();
    }
    if (nodeCfg != null) {
      TopologyNode node = new TopologyNode(nodeCfg);
      nodeMap.put(nodeCfg.getNodeName(), node);
    }
    return this;
  }

  public TopologyInfo removeTopologyNode(String nodeName) {
    if (nodeMap == null) {
      return this;
    }
    nodeMap.remove(nodeName);
    return this;
  }

  public Date getStartTime() {
    return startTime;
  }

  public void setStartTime(Date startTime) {
    this.startTime = startTime;
  }
}
