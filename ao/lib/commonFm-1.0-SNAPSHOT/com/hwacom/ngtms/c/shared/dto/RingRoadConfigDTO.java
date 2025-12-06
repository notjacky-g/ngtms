/*
 * © HwaCom Systems Inc. 2015
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.c.shared.dto;

import com.google.gwt.user.client.rpc.IsSerializable;
import com.hwacom.ngtms.c.shared.Direction;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class RingRoadConfigDTO implements Serializable, IsSerializable {
  /** */
  private static final long serialVersionUID = -4388728961736219119L;

  /** startDivisionId + "-" + startDirection+ "-" + startDivisionId + "-" + endDirection; */
  private String id;

  /** 所屬出口分割點 */
  private String startDivisionId;

  private String startDivisionName;

  /** 所屬入口分割點 */
  private String endDivisionId;

  private String endDivisionName;

  /** 出口路線 */
  private String startLineId;

  private String startLineName;

  /** 出口方向 */
  private Direction startDirection;

  /** 出口路線里程數 */
  private Integer startMileage;

  /** 出口描述 */
  private String startDescription;

  /** 入口路線 */
  private String endLineId;

  private String endLineName;

  /** 入口方向 */
  private Direction endDirection;

  /** 入口路線里程數 */
  private Integer endMileage;

  /** 入口描述 */
  private String endDescription;

  /** 出口前VD (環道起始端前VD) */
  private String exitVd;

  private String exitVdName;

  /** 環道VD */
  private List<RingRoadVdDTO> RingRoadVds = new ArrayList<>();

  private String ringRoadVdStr;

  /** 環道長度 */
  private Integer length;

  /** 速限 */
  private Integer speedLimit = 100;

  /** 預設旅行時間 */
  private Integer freeTravelTime;

  /** 對應etag區段 ，可不設定 */
  private String etagSectionId;

  private String ringRoadDesc;

  /** @return the id */
  public String getId() {
    return id;
  }

  /** @param id the id to set */
  public void setId(String id) {
    this.id = id;
  }

  /** @return the startDivisionId */
  public String getStartDivisionId() {
    return startDivisionId;
  }

  /** @param startDivisionId the startDivisionId to set */
  public void setStartDivisionId(String startDivisionId) {
    this.startDivisionId = startDivisionId;
  }

  /** @return the startDivisionName */
  public String getStartDivisionName() {
    return startDivisionName;
  }

  /** @param startDivisionName the startDivisionName to set */
  public void setStartDivisionName(String startDivisionName) {
    this.startDivisionName = startDivisionName;
  }

  /** @return the endDivisionId */
  public String getEndDivisionId() {
    return endDivisionId;
  }

  /** @param endDivisionId the endDivisionId to set */
  public void setEndDivisionId(String endDivisionId) {
    this.endDivisionId = endDivisionId;
  }

  /** @return the endDivisionName */
  public String getEndDivisionName() {
    return endDivisionName;
  }

  /** @param endDivisionName the endDivisionName to set */
  public void setEndDivisionName(String endDivisionName) {
    this.endDivisionName = endDivisionName;
  }

  /** @return the startLineId */
  public String getStartLineId() {
    return startLineId;
  }

  /** @param startLineId the startLineId to set */
  public void setStartLineId(String startLineId) {
    this.startLineId = startLineId;
  }

  /** @return the startDirectionStr */
  public Direction getStartDirection() {
    return startDirection;
  }

  /** @param startDirection the startDirectionStr to set */
  public void setStartDirection(Direction startDirection) {
    this.startDirection = startDirection;
  }

  /** @return the startMileage */
  public Integer getStartMileage() {
    return startMileage;
  }

  /** @param startMileage the startMileage to set */
  public void setStartMileage(Integer startMileage) {
    this.startMileage = startMileage;
  }

  /** @return the startDescription */
  public String getStartDescription() {
    return startDescription;
  }

  /** @param startDescription the startDescription to set */
  public void setStartDescription(String startDescription) {
    this.startDescription = startDescription;
  }

  /** @return the endLineId */
  public String getEndLineId() {
    return endLineId;
  }

  /** @param endLineId the endLineId to set */
  public void setEndLineId(String endLineId) {
    this.endLineId = endLineId;
  }

  /** @return the endDirectionStr */
  public Direction getEndDirection() {
    return endDirection;
  }

  /** @param endDirection the endDirectionStr to set */
  public void setEndDirection(Direction endDirection) {
    this.endDirection = endDirection;
  }

  /** @return the endMileage */
  public Integer getEndMileage() {
    return endMileage;
  }

  /** @param endMileage the endMileage to set */
  public void setEndMileage(Integer endMileage) {
    this.endMileage = endMileage;
  }

  /** @return the endDescription */
  public String getEndDescription() {
    return endDescription;
  }

  /** @param endDescription the endDescription to set */
  public void setEndDescription(String endDescription) {
    this.endDescription = endDescription;
  }

  /** @return the exitVd */
  public String getExitVd() {
    return exitVd;
  }

  /** @param exitVd the exitVd to set */
  public void setExitVd(String exitVd) {
    this.exitVd = exitVd;
  }

  public String getExitVdName() {
    return exitVdName;
  }

  public void setExitVdName(String exitVdName) {
    this.exitVdName = exitVdName;
  }

  /** @return the ringRoadVds */
  public List<RingRoadVdDTO> getRingRoadVds() {
    return RingRoadVds;
  }

  /** @param ringRoadVds the ringRoadVds to set */
  public void setRingRoadVds(List<RingRoadVdDTO> ringRoadVds) {
    RingRoadVds = ringRoadVds;
  }

  public String getRingRoadVdStr() {
    if (RingRoadVds == null) return "";
    StringBuilder tempStr = new StringBuilder();
    for (RingRoadVdDTO dto : RingRoadVds) {
      tempStr.append(dto.getVd() + " : " + dto.getWeigth() + "; ");
    }
    return tempStr.toString();
  }

  /** @return the startLineName */
  public String getStartLineName() {
    return startLineName;
  }

  /** @param startLineName the startLineName to set */
  public void setStartLineName(String startLineName) {
    this.startLineName = startLineName;
  }

  /** @return the endLineName */
  public String getEndLineName() {
    return endLineName;
  }

  /** @param endLineName the endLineName to set */
  public void setEndLineName(String endLineName) {
    this.endLineName = endLineName;
  }

  /** @return the length */
  public Integer getLength() {
    return length;
  }

  /** @param length the length to set */
  public void setLength(Integer length) {
    this.length = length;
  }

  /** @return the speedLimit */
  public Integer getSpeedLimit() {
    return speedLimit;
  }

  /** @param speedLimit the speedLimit to set */
  public void setSpeedLimit(Integer speedLimit) {
    this.speedLimit = speedLimit;
  }

  /** @return the freeTravelTime */
  public Integer getFreeTravelTime() {
    return freeTravelTime;
  }

  /** @param freeTravelTime the freeTravelTime to set */
  public void setFreeTravelTime(Integer freeTravelTime) {
    this.freeTravelTime = freeTravelTime;
  }

  /** @return the etagSectionId */
  public String getEtagSectionId() {
    return etagSectionId;
  }

  /** @param etagSectionId the etagSectionId to set */
  public void setEtagSectionId(String etagSectionId) {
    this.etagSectionId = etagSectionId;
  }

  public String getRingRoadDesc() {
    return ringRoadDesc;
  }

  public void setRingRoadDesc(String ringRoadDesc) {
    this.ringRoadDesc = ringRoadDesc;
  }

  public void setRingRoadVdStr(String ringRoadVdStr) {
    this.ringRoadVdStr = ringRoadVdStr;
  }

  public String getStartDirectionStr() {
    return startDirection.name();
  }

  public String getEndDirectionStr() {
    return endDirection.name();
  }

  /** @return the serialversionuid */
  public static long getSerialversionuid() {
    return serialVersionUID;
  }

  @Override
  public String toString() {
    return "RingRoadConfigDTO [id="
        + id
        + ", startDivisionId="
        + startDivisionId
        + ", startDivisionName="
        + startDivisionName
        + ", endDivisionId="
        + endDivisionId
        + ", endDivisionName="
        + endDivisionName
        + ", startLineId="
        + startLineId
        + ", startLineName="
        + startLineName
        + ", startDirectionStr="
        + startDirection
        + ", startMileage="
        + startMileage
        + ", startDescription="
        + startDescription
        + ", endLineId="
        + endLineId
        + ", endLineName="
        + endLineName
        + ", endDirectionStr="
        + endDirection
        + ", endMileage="
        + endMileage
        + ", endDescription="
        + endDescription
        + ", exitVd="
        + exitVd
        + ", exitVdName="
        + exitVdName
        + ", RingRoadVds="
        + RingRoadVds
        + ", ringRoadVdStr="
        + ringRoadVdStr
        + ", length="
        + length
        + ", speedLimit="
        + speedLimit
        + ", freeTravelTime="
        + freeTravelTime
        + ", etagSectionId="
        + etagSectionId
        + "]";
  }

  /* (non-Javadoc)
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((id == null) ? 0 : id.hashCode());
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
    RingRoadConfigDTO other = (RingRoadConfigDTO) obj;
    if (id == null) {
      if (other.id != null) return false;
    } else if (!id.equals(other.id)) return false;
    return true;
  }
}
