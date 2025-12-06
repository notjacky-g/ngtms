package com.hwacom.ngtms.node.performance.shared.dto;

import java.io.Serializable;
import java.util.List;

public class GetNodePerformanceLogsResponseDTO implements Serializable {

  private static final long serialVersionUID = 1L;

  private List<NodePerformanceLogDTO> nodePerformanceLogs;

  private int totalLength;

  public List<NodePerformanceLogDTO> getNodePerformanceLogs() {
    return nodePerformanceLogs;
  }

  public void setNodePerformanceLogs(List<NodePerformanceLogDTO> nodePerformanceLogs) {
    this.nodePerformanceLogs = nodePerformanceLogs;
  }

  public int getTotalLength() {
    return totalLength;
  }

  public void setTotalLength(int totalLength) {
    this.totalLength = totalLength;
  }
}
