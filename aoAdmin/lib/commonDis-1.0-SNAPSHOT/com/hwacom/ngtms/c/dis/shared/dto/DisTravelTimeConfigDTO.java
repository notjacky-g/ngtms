/*
 * © HwaCom Systems Inc. 2015
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.c.dis.shared.dto;

import com.google.gwt.user.client.rpc.IsSerializable;
import com.hwacom.ngtms.c.shared.dto.RoadDivisionDTO;
import java.io.Serializable;
import java.util.List;

public class DisTravelTimeConfigDTO implements Serializable, IsSerializable {

  private static final long serialVersionUID = 529066442390753623L;

  private String id;

  private String deviceName;

  private String displayName;

  private int boardId;

  private boolean iconDisplay;

  private int maxTravelTime;

  private int minTravelTime;

  private int delayTime;

  private List<RoadDivisionDTO> roadDivisionList;

  private Boolean enable;

  private Boolean showMaxTravelTime;

  private Integer additionalTravelTime;

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
    DisTravelTimeConfigDTO other = (DisTravelTimeConfigDTO) obj;
    if (id == null) {
      if (other.id != null) return false;
    } else if (!id.equals(other.id)) return false;
    return true;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getDeviceName() {
    return deviceName;
  }

  public void setDeviceName(String deviceName) {
    this.deviceName = deviceName;
  }

  public String getDisplayName() {
    return displayName;
  }

  public void setDisplayName(String displayName) {
    this.displayName = displayName;
  }

  public int getBoardId() {
    return boardId;
  }

  public void setBoardId(int boardId) {
    this.boardId = boardId;
  }

  public boolean isIconDisplay() {
    return iconDisplay;
  }

  public void setIconDisplay(boolean iconDisplay) {
    this.iconDisplay = iconDisplay;
  }

  public int getMaxTravelTime() {
    return maxTravelTime;
  }

  public void setMaxTravelTime(int maxTravelTime) {
    this.maxTravelTime = maxTravelTime;
  }

  public int getMinTravelTime() {
    return minTravelTime;
  }

  public void setMinTravelTime(int minTravelTime) {
    this.minTravelTime = minTravelTime;
  }

  public int getDelayTime() {
    return delayTime;
  }

  public void setDelayTime(int delayTime) {
    this.delayTime = delayTime;
  }

  public List<RoadDivisionDTO> getRoadDivisionList() {
    return roadDivisionList;
  }

  public void setRoadDivisionList(List<RoadDivisionDTO> roadDivisionList) {
    this.roadDivisionList = roadDivisionList;
  }

  public Boolean isEnable() {
    return enable;
  }

  public void setEnable(Boolean enable) {
    this.enable = enable;
  }

  public Boolean getShowMaxTravelTime() {
    return showMaxTravelTime;
  }

  public void setShowMaxTravelTime(Boolean showMaxTravelTime) {
    this.showMaxTravelTime = showMaxTravelTime;
  }

  public Integer getAdditionalTravelTime() {
    return additionalTravelTime;
  }

  public void setAdditionalTravelTime(Integer additionalTravelTime) {
    this.additionalTravelTime = additionalTravelTime;
  }
}
