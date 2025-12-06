/*
 * © HwaCom Systems Inc. 2015
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.c.shared.dto;

import com.google.gwt.user.client.rpc.IsSerializable;
import java.io.Serializable;

public class LaneCountDTO implements Serializable, IsSerializable {

  private static final long serialVersionUID = 1236975731701326095L;

  /** 車道數編號 */
  private String id;

  /** 主線編號 */
  private String lineId;

  private String lineName;

  /** 開始里程 */
  private Integer startMile;

  /** 結束里程 */
  private Integer endMile;

  /** 方向 */
  private String direction;

  /** 車道數 */
  private Integer laneCount;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getLineId() {
    return lineId;
  }

  public void setLineId(String lineId) {
    this.lineId = lineId;
  }

  public Integer getStartMile() {
    return startMile;
  }

  public void setStartMile(Integer startMile) {
    this.startMile = startMile;
  }

  public Integer getEndMile() {
    return endMile;
  }

  public void setEndMile(Integer endMile) {
    this.endMile = endMile;
  }

  public String getDirection() {
    return direction;
  }

  public void setDirection(String direction) {
    this.direction = direction;
  }

  public Integer getLaneCount() {
    return laneCount;
  }

  public void setLaneCount(Integer laneCount) {
    this.laneCount = laneCount;
  }

  public String getLineName() {
    return lineName;
  }

  public void setLineName(String lineName) {
    this.lineName = lineName;
  }

  @Override
  public String toString() {
    return "LaneCountDTO [id="
        + id
        + ", lineId="
        + lineId
        + ", lineName="
        + lineName
        + ", startMile="
        + startMile
        + ", endMile="
        + endMile
        + ", direction="
        + direction
        + ", laneCount="
        + laneCount
        + "]";
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((id == null) ? 0 : id.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null) return false;
    if (getClass() != obj.getClass()) return false;
    LaneCountDTO other = (LaneCountDTO) obj;
    if (id == null) {
      if (other.id != null) return false;
    } else if (!id.equals(other.id)) return false;
    return true;
  }
}
