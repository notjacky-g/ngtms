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

public class RoadSectionDTO implements Serializable, Comparable<RoadSectionDTO>, IsSerializable {

  private static final long serialVersionUID = 2461850175695441449L;
  /** 路段編號 */
  private String sectionId;
  /** 路段名稱 */
  private String sectionName;
  /** 路線編號（屬於哪一個路線） */
  private String lineid;
  /** 路線名稱（屬於哪一個路線） */
  private String lineName;
  /** 方向（N=北上，S=南下，E=東向，W=西向） */
  private Direction direction;

  private String directionStr;

  @SuppressWarnings("unused")
  private String directionShow;
  /** 開始的分割點編號（系統交流道、交流道、收費站、服務區） */
  private String startDivisionId;

  private String startDivisionName;
  /** 結束的分割點編號（系統交流道、交流道、收費站、服務區） */
  private String endDivisionId;

  private String endDivisionName;
  /** 該路段車道數 */
  private short laneCount;
  /** 最高限速 */
  private Integer maxSpeed;
  /** 最底限速 */
  private Integer minSpeed;
  /** 轄區 (N=北區、C=中區、S=南區) */
  private String areaType;
  /** 主標竿VD */
  private String standardVd;
  /** 次標竿VD1 */
  private String subStandardVd1;
  /** 次標竿VD2 */
  private String subStandardVd2;
  /** 允許手動建立事件 */
  private Boolean manualEnabled;
  /** 反應計畫事件登錄模式 */
  private String eventLogMode;
  /** 反應計畫事件執行模式 */
  private String eventExecMode;
  /** 車道容量(預設:2000,單位:PCU) */
  private Integer sectionFlow;
  /** 自由車速 */
  private Integer freeSpeed;
  /** DDS壅塞路段顯示設定 */
  private Boolean ddsRoadSectionEnabled;

  /** DDS壅塞路段顯示設定群組一 */
  private Boolean ddsRoadSectionGroup1;

  /** DDS壅塞路段顯示設定群組二 */
  private Boolean ddsRoadSectionGroup2;

  /** DDS壅塞路段顯示設定群組三 */
  private Boolean ddsRoadSectionGroup3;

  /** DDS壅塞路段顯示設定群組四 */
  private Boolean ddsRoadSectionGroup4;

  public String getSectionId() {
    return sectionId;
  }

  public void setSectionId(String sectionId) {
    this.sectionId = sectionId;
  }

  public String getSectionName() {
    return sectionName;
  }

  public void setSectionName(String sectionName) {
    this.sectionName = sectionName;
  }

  public String getLineid() {
    return lineid;
  }

  public void setLineid(String lineid) {
    this.lineid = lineid;
  }

  public String getLineName() {
    return lineName;
  }

  public void setLineName(String lineName) {
    this.lineName = lineName;
  }

  public Direction getDirection() {
    return direction;
  }

  public void setDirection(Direction direction) {
    this.direction = direction;
  }

  public String getStartDivisionId() {
    return startDivisionId;
  }

  public void setStartDivisionId(String startDivisionId) {
    this.startDivisionId = startDivisionId;
  }

  public String getEndDivisionId() {
    return endDivisionId;
  }

  public void setEndDivisionId(String endDivisionId) {
    this.endDivisionId = endDivisionId;
  }

  public short getLaneCount() {
    return laneCount;
  }

  public void setLaneCount(short laneCount) {
    this.laneCount = laneCount;
  }

  public Integer getMaxSpeed() {
    return maxSpeed;
  }

  public void setMaxSpeed(Integer maxSpeed) {
    this.maxSpeed = maxSpeed;
  }

  public Integer getMinSpeed() {
    return minSpeed;
  }

  public void setMinSpeed(Integer minSpeed) {
    this.minSpeed = minSpeed;
  }

  public String getStartDivisionName() {
    return startDivisionName;
  }

  public void setStartDivisionName(String startDivisionName) {
    this.startDivisionName = startDivisionName;
  }

  public String getEndDivisionName() {
    return endDivisionName;
  }

  public void setEndDivisionName(String endDivisionName) {
    this.endDivisionName = endDivisionName;
  }

  public String getDirectionStr() {
    return directionStr;
  }

  public void setDirectionStr(String directionStr) {
    this.directionStr = directionStr;
  }

  public String getDirectionShow() {
    return directionShow;
  }

  public void setDirectionShow(String directionShow) {
    this.directionShow = directionShow;
  }

  public String getAreaType() {
    return areaType;
  }

  public void setAreaType(String areaType) {
    this.areaType = areaType;
  }

  public String getStandardVd() {
    return standardVd;
  }

  public void setStandardVd(String standardVd) {
    this.standardVd = standardVd;
  }

  public String getSubStandardVd1() {
    return subStandardVd1;
  }

  public void setSubStandardVd1(String subStandardVd1) {
    this.subStandardVd1 = subStandardVd1;
  }

  public String getSubStandardVd2() {
    return subStandardVd2;
  }

  public void setSubStandardVd2(String subStandardVd2) {
    this.subStandardVd2 = subStandardVd2;
  }

  public Boolean getManualEnabled() {
    return manualEnabled;
  }

  public void setManualEnabled(Boolean manualEnabled) {
    this.manualEnabled = manualEnabled;
  }

  public String getEventLogMode() {
    return eventLogMode;
  }

  public void setEventLogMode(String eventLogMode) {
    this.eventLogMode = eventLogMode;
  }

  public String getEventExecMode() {
    return eventExecMode;
  }

  public void setEventExecMode(String eventExecMode) {
    this.eventExecMode = eventExecMode;
  }

  public Integer getSectionFlow() {
    return sectionFlow;
  }

  public void setSectionFlow(Integer sectionFlow) {
    this.sectionFlow = sectionFlow;
  }

  public Integer getFreeSpeed() {
    return freeSpeed;
  }

  public void setFreeSpeed(Integer freeSpeed) {
    this.freeSpeed = freeSpeed;
  }

  public Boolean getDdsRoadSectionEnabled() {
    return ddsRoadSectionEnabled;
  }

  public void setDdsRoadSectionEnabled(Boolean ddsRoadSectionEnabled) {
    this.ddsRoadSectionEnabled = ddsRoadSectionEnabled;
  }

  public Boolean getDdsRoadSectionGroup1() {
    return ddsRoadSectionGroup1;
  }

  public void setDdsRoadSectionGroup1(Boolean ddsRoadSectionGroup1) {
    this.ddsRoadSectionGroup1 = ddsRoadSectionGroup1;
  }

  public Boolean getDdsRoadSectionGroup2() {
    return ddsRoadSectionGroup2;
  }

  public void setDdsRoadSectionGroup2(Boolean ddsRoadSectionGroup2) {
    this.ddsRoadSectionGroup2 = ddsRoadSectionGroup2;
  }

  public Boolean getDdsRoadSectionGroup3() {
    return ddsRoadSectionGroup3;
  }

  public void setDdsRoadSectionGroup3(Boolean ddsRoadSectionGroup3) {
    this.ddsRoadSectionGroup3 = ddsRoadSectionGroup3;
  }

  public Boolean getDdsRoadSectionGroup4() {
    return ddsRoadSectionGroup4;
  }

  public void setDdsRoadSectionGroup4(Boolean ddsRoadSectionGroup4) {
    this.ddsRoadSectionGroup4 = ddsRoadSectionGroup4;
  }

  @Override
  public String toString() {
    //此 return key 是要給 roadSectionComboBox 用的,不能更動
    return this.sectionId;
  }

  public String toStringInfo() {
    return "RoadSectionBean [sectionId="
        + sectionId
        + ", sectionName="
        + sectionName
        + ", lineid="
        + lineid
        + ", lineName="
        + lineName
        + ", direction="
        + direction
        + ", directionStr="
        + directionStr
        + ", startDivisionId="
        + startDivisionId
        + ", startDivisionName="
        + startDivisionName
        + ", endDivisionId="
        + endDivisionId
        + ", endDivisionName="
        + endDivisionName
        + ", laneCount="
        + laneCount
        + ", maxSpeed="
        + maxSpeed
        + ", minSpeed="
        + minSpeed
        + "]";
  }

  @Override
  public int compareTo(RoadSectionDTO o) {
    if (this.sectionId.compareTo(o.getSectionId()) > 0) return 1;
    if (this.sectionId.compareTo(o.getSectionId()) < 0) return -1;
    return 0;
  }
}
