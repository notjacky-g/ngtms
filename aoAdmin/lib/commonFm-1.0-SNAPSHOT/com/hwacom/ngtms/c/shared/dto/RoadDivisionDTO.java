/*
 * © HwaCom Systems Inc. 2015
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.c.shared.dto;

import com.google.gwt.user.client.rpc.IsSerializable;
import com.hwacom.ngtms.c.shared.AreaType;
import com.hwacom.ngtms.c.shared.DivisionType;
import java.io.Serializable;

/** @see {@link com.hwacom.ngtms.c.fm.model.RoadDivision RoadDivision} */
public class RoadDivisionDTO implements Serializable, IsSerializable {

  private static final long serialVersionUID = -552863519325063738L;

  private String divisionId;

  /** 分割點名稱 */
  private String divisionName;

  /** 分割點種類（C=系統交流道、I=交流道、T=收費站、S=服務區） （注意！規範中並無明確說明隧道為分割點的標準之一，所以隧道暫不列入分割點參考！） */
  private DivisionType divisionType;

  /** 里程數（公尺） */
  private Integer mileage;

  /** 路線編號（屬於哪一個道路） */
  private String lineId;

  private String lineName;

  /** 轄區 (N=北區、C=中區、S=南區) */
  private AreaType areaType;
  /** 切割點是否屬於交接路段。 交接路段切割點位於 北區或南區與中區的交界處，交接路段切割點至轄區路段允許輸入反應計畫事件 */
  private Boolean boundary = false;
  /** 轄區內的切割點皆可被選為 旅行時間目的地，轄區外的切割點，其 travelTimeVisible = true 時，可被選為 旅行時間目的地 */
  private Boolean travelTimeVisible = false;

  private Boolean systemInterchange = false;

  public String getDivisionId() {
    return divisionId;
  }

  public void setDivisionId(String divisionId) {
    this.divisionId = divisionId;
  }

  public String getDivisionName() {
    return divisionName;
  }

  public void setDivisionName(String divisionName) {
    this.divisionName = divisionName;
  }

  public DivisionType getDivisionType() {
    return divisionType;
  }

  public void setDivisionType(DivisionType divisionType) {
    this.divisionType = divisionType;
  }

  public Integer getMileage() {
    return mileage;
  }

  public void setMileage(Integer mileage) {
    this.mileage = mileage;
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

  public AreaType getAreaType() {
    return areaType;
  }

  public void setAreaType(AreaType areaType) {
    this.areaType = areaType;
  }

  public Boolean getBoundary() {
    return boundary;
  }

  public void setBoundary(Boolean boundary) {
    this.boundary = boundary;
  }

  public Boolean getTravelTimeVisible() {
    return travelTimeVisible;
  }

  public void setTravelTimeVisible(Boolean travelTimeVisible) {
    this.travelTimeVisible = travelTimeVisible;
  }

  public Boolean isSystemInterchange() {
    return systemInterchange;
  }

  public void setSystemInterchange(Boolean systemInterchange) {
    this.systemInterchange = systemInterchange;
  }
}
