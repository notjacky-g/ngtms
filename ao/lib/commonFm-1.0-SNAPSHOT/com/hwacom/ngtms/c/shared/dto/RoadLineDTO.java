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

public class RoadLineDTO implements Serializable, Comparable<RoadLineDTO>, IsSerializable {
  private static final long serialVersionUID = 3754889905775534664L;

  /** 路線編號（有國道和快道） */
  private String lineId;
  /** 路線名稱 */
  private String lineName;

  private Direction direction;
  /** 開始里程（公尺） */
  private Integer startMileage;
  /** 結束里程（公尺） */
  private Integer endMileage;
  /** 備註 */
  private String memo;
  /** ENABLE（Y = ENABLE、N=DISABLE） */
  private Boolean enable;
  /** 國道圖示編碼(1-255，0代表不使用) 提供給需要顯示路段圖示的界面使用，EX:旅行時間設定 */
  private Integer ggCodeId;

  public RoadLineDTO() {}

  public RoadLineDTO(String lineId, String lineName) {
    this.lineId = lineId;
    this.lineName = lineName;
  }

  public String getKey() {
    return lineId;
  }

  public String getLineId() {
    return lineId;
  }

  public void setLineId(String lineId) {
    this.lineId = lineId;
  }

  public String getLineName() {
    return lineName;
  }

  public void setLineName(String lineName) {
    this.lineName = lineName;
  }

  public Integer getStartMileage() {
    return startMileage;
  }

  public void setStartMileage(Integer startMileage) {
    this.startMileage = startMileage;
  }

  public Integer getEndMileage() {
    return endMileage;
  }

  public void setEndMileage(Integer endMileage) {
    this.endMileage = endMileage;
  }

  public String getMemo() {
    return memo;
  }

  public void setMemo(String memo) {
    this.memo = memo;
  }

  public Boolean isEnable() {
    return enable;
  }

  public void setEnable(Boolean enable) {
    this.enable = enable;
  }

  public Direction getDirection() {
    return direction;
  }

  public void setDirection(Direction direction) {
    this.direction = direction;
  }

  public String getStartMileageShow() {
    return String.valueOf(((double) startMileage.intValue() / 1000));
  }

  public String getEndMileageShow() {
    return String.valueOf(((double) endMileage.intValue() / 1000));
  }

  public Integer getGgCodeId() {
    return ggCodeId;
  }

  public void setGgCodeId(Integer ggCodeId) {
    this.ggCodeId = ggCodeId;
  }

  @Override
  public int compareTo(RoadLineDTO o) {
    if (this.lineId.compareTo(o.getLineId()) > 0) return 1;
    if (this.lineId.compareTo(o.getLineId()) < 0) return -1;
    return 0;
  }
}
