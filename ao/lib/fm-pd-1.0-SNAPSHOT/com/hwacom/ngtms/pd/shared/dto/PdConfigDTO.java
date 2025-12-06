/*
 * © HwaCom Systems Inc. 2015
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.pd.shared.dto;

import com.google.gwt.user.client.rpc.IsSerializable;
import com.hwacom.ngtms.c.shared.Direction;
import java.io.Serializable;

/**
 * PD 組態設定DTO
 *
 * @author brian.cheng
 */
public class PdConfigDTO implements Serializable, IsSerializable {

  private static final long serialVersionUID = -8148178880431978371L;

  /** 設備編號 */
  private String deviceName;

  /** 設備名稱 */
  private String displayName;

  /** 機房編號 */
  private String locationNo;

  /** 機房編號 */
  private String location;

  /** 道路編號 */
  private String lineId;

  /** 道路名稱 */
  private String lineName;

  /** 方向 */
  private Direction direction;

  /** 里程 */
  private Integer milepost;

  /** 路段編號 */
  private String sectionId;

  /** 路段名稱 */
  private String sectionName;

  /** 電錶號 */
  private String meterNo;

  /** 所屬區數 */
  private String area;

  /** 聯絡電話 */
  private String phone;

  /** 網路IP */
  private String ip;

  /** 網路通訊埠 */
  private Integer port;

  /** 經度 */
  private Double longitude;

  /** 緯度 */
  private Double latitude;

  /** 總迴路數 */
  private Integer loopNo;

  /** 備註 */
  private String memo;

  /** 是否啟用 */
  private Boolean enable;

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

  public String getLocationNo() {
    return locationNo;
  }

  public void setLocationNo(String locationNo) {
    this.locationNo = locationNo;
  }

  public String getLineId() {
    return lineId;
  }

  public void setLineId(String lineId) {
    this.lineId = lineId;
  }

  public Direction getDirection() {
    return direction;
  }

  public void setDirection(Direction direction) {
    this.direction = direction;
  }

  public Integer getMilepost() {
    return milepost;
  }

  public void setMilepost(Integer milepost) {
    this.milepost = milepost;
  }

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

  public String getMeterNo() {
    return meterNo;
  }

  public void setMeterNo(String meterNo) {
    this.meterNo = meterNo;
  }

  public String getArea() {
    return area;
  }

  public void setArea(String area) {
    this.area = area;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public String getIp() {
    return ip;
  }

  public void setIp(String ip) {
    this.ip = ip;
  }

  public Integer getPort() {
    return port;
  }

  public void setPort(Integer port) {
    this.port = port;
  }

  public Double getLongitude() {
    return longitude;
  }

  public void setLongitude(Double longitude) {
    this.longitude = longitude;
  }

  public Double getLatitude() {
    return latitude;
  }

  public void setLatitude(Double latitude) {
    this.latitude = latitude;
  }

  public Integer getLoopNo() {
    return loopNo;
  }

  public void setLoopNo(Integer loopNo) {
    this.loopNo = loopNo;
  }

  public String getMemo() {
    return memo;
  }

  public void setMemo(String memo) {
    this.memo = memo;
  }

  public String getLocation() {
    return location;
  }

  public void setLocation(String location) {
    this.location = location;
  }

  public String getLineName() {
    return lineName;
  }

  public void setLineName(String lineName) {
    this.lineName = lineName;
  }

  public Boolean getEnable() {
    return enable;
  }

  public void setEnable(Boolean enable) {
    this.enable = enable;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((deviceName == null) ? 0 : deviceName.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null) return false;
    if (getClass() != obj.getClass()) return false;
    PdConfigDTO other = (PdConfigDTO) obj;
    if (deviceName == null) {
      if (other.deviceName != null) return false;
    } else if (!deviceName.equals(other.deviceName)) return false;
    return true;
  }

  @Override
  public String toString() {
    return "PdConfigDTO [deviceName="
        + deviceName
        + ", displayName="
        + displayName
        + ", locationNo="
        + locationNo
        + ", lineId="
        + lineId
        + ", direction="
        + direction
        + ", milepost="
        + milepost
        + ", sectionId="
        + sectionId
        + ", sectionName="
        + sectionName
        + ", meterNo="
        + meterNo
        + ", area="
        + area
        + ", phone="
        + phone
        + ", ip="
        + ip
        + ", port="
        + port
        + ", longitude="
        + longitude
        + ", latitude="
        + latitude
        + ", loopNo="
        + loopNo
        + ", memo="
        + memo
        + ", location="
        + location
        + ", lineName="
        + lineName
        + ", enable="
        + enable
        + "]";
  }
}
