/*
 * © HwaCom Systems Inc. 2013
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.node.performance.shared.dto;

import com.google.gwt.user.client.rpc.IsSerializable;
import java.io.Serializable;
import java.util.Date;
import java.util.Map;

public class NodePerformanceLogDTO implements Serializable, IsSerializable {

  private static final long serialVersionUID = -6286510950378481936L;
  private Long id;
  private String groupName;
  private String nodeName;
  /** CPU每分鐘平均負載 */
  private Double cpuLoadAverage;

  private Double memUsedPercent;

  private String diskUsageJson;

  private Map<String, Double> diskUsage;

  private Date recordTime;

  /** @return the id */
  public Long getId() {
    return id;
  }

  /** @param id the id to set */
  public void setId(Long id) {
    this.id = id;
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

  /** @return the cpuLoadAverage */
  public Double getCpuLoadAverage() {
    return cpuLoadAverage;
  }

  /** @param cpuLoadAverage the cpuLoadAverage to set */
  public void setCpuLoadAverage(Double cpuLoadAverage) {
    this.cpuLoadAverage = cpuLoadAverage;
  }

  public Double getCpuLoadAverageShow() {
    return cpuLoadAverage;
  }

  /** @return the memUsedPercent */
  public Double getMemUsedPercent() {
    return memUsedPercent;
  }

  /** @param memUsedPercent the memUsedPercent to set */
  public void setMemUsedPercent(Double memUsedPercent) {
    this.memUsedPercent = memUsedPercent;
  }

  public String getDiskUsageJson() {
    return diskUsageJson;
  }

  public void setDiskUsageJson(String diskUsageJson) {
    this.diskUsageJson = diskUsageJson;
  }

  public Map<String, Double> getDiskUsage() {
    return diskUsage;
  }

  public void setDiskUsage(Map<String, Double> diskUsage) {
    this.diskUsage = diskUsage;
  }

  /** @return the recordTime */
  public Date getRecordTime() {
    if (recordTime != null) return new Date(recordTime.getTime());
    else return null;
  }

  /** @param recordTime the recordTime to set */
  public void setRecordTime(Date recordTime) {
    if (recordTime != null) this.recordTime = new Date(recordTime.getTime());
    else this.recordTime = null;
  }

  /*
   * (non-Javadoc)
   *
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("NodePerformanceLogDTO [id=");
    builder.append(id);
    builder.append(", groupName=");
    builder.append(groupName);
    builder.append(", nodeName=");
    builder.append(nodeName);
    builder.append(", cpuLoadAverage=");
    builder.append(cpuLoadAverage);
    builder.append(", memUsedPercent=");
    builder.append(memUsedPercent);
    builder.append(", diskUsageJson=");
    builder.append(diskUsageJson);
    builder.append(", diskUsage=");
    builder.append(diskUsage);
    builder.append(", recordTime=");
    builder.append(recordTime);
    builder.append("]");
    return builder.toString();
  }
}
