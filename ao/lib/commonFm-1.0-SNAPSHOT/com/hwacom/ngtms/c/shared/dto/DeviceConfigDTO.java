/*
 * © HwaCom Systems Inc. 2015
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.c.shared.dto;

import com.google.gwt.user.client.rpc.IsSerializable;
import com.hwacom.ngtms.c.shared.DeviceStatusType;
import com.hwacom.ngtms.c.shared.Direction;
import com.hwacom.ngtms.c.shared.DragItem;
import com.hwacom.ngtms.c.shared.RampType;
import com.hwacom.ngtms.c.shared.TcProtocolType;
import java.io.Serializable;

public class DeviceConfigDTO extends DragItem
    implements Serializable, Comparable<DeviceConfigDTO>, IsSerializable {
  private static final long serialVersionUID = -7936235437467409603L;
  /** 設備名稱（EX. vd001） */
  private String deviceName;
  /** UI 顯示可修改的device name */
  private String displayName;
  /** ENABLE（Y = ENABLE、N=DISABLE） 0=Y 1=N */
  private Boolean enable;
  /** 設備種類（EX. VD、VI、CMS） */
  private String deviceType;
  /** 里程數（公尺） */
  private Integer milepost;
  /** 網路ＩＰ */
  private String ip;
  /** 網路通訊埠 */
  private String port;
  /** MFCC軟體編號 */
  private String mfccId;
  /** 預設的MFCC軟體編號 */
  private String defaultMfccId;
  /** 通訊協定版本 */
  private TcProtocolType protocolType;
  /** 工程標別 */
  private String project;
  /** 路線編號（屬於哪一個國道） */
  private String lineId;
  /** 路線名稱 */
  private String lineName;
  /** 路段編號 */
  private String sectionId;
  /** F=高速公路，H=快速道路，T=隧道，R=匝道，P=保留 (程度表的key)S=服務區，L=地方道路, 或主機設備所屬設備位置 */
  private String location;
  /** I=入口匝道，O=出口匝道，U=未定義 */
  private RampType locationR;
  /** 方向（N=北上，S=南下，E=東向，W=西向） */
  private Direction direction;
  /** gps 經度 */
  private Double longitude;
  /** gps 緯度 */
  private Double latitude;
  /** 備註 */
  private String memo;
  /** 是否顯示於 EMS 中，若非"電腦主機設備"，請設定為 false，否則設為 true */
  private Boolean showInEms;
  /** 無 NONE, 連線 ONLINE,斷線 OFFLINE,停用 SUSPEND */
  private DeviceStatusType status = DeviceStatusType.NONE;

  public DeviceConfigDTO() {}

  public String getDeviceName() {
    return deviceName;
  }

  public void setDeviceName(String deviceName) {
    this.deviceName = deviceName;
    this.uuid = deviceName;
  }

  public Boolean getEnable() {
    return enable;
  }

  public void setEnable(Boolean enable) {
    this.enable = enable;
  }

  public String getDeviceType() {
    return deviceType;
  }

  public void setDeviceType(String deviceType) {
    this.deviceType = deviceType;
  }

  public Integer getMilepost() {
    return milepost;
  }

  public void setMilepost(Integer milepost) {
    this.milepost = milepost;
  }

  public String getIp() {
    return ip;
  }

  public void setIp(String ip) {
    this.ip = ip;
  }

  public String getPort() {
    return port;
  }

  public void setPort(String port) {
    this.port = port;
  }

  public String getMfccId() {
    return mfccId;
  }

  public void setMfccId(String mfccId) {
    this.mfccId = mfccId;
  }

  /** @return the defaultMfccId */
  public String getDefaultMfccId() {
    return defaultMfccId;
  }

  /** @param defaultMfccId the defaultMfccId to set */
  public void setDefaultMfccId(String defaultMfccId) {
    this.defaultMfccId = defaultMfccId;
  }

  public String getProject() {
    return project;
  }

  public void setProject(String project) {
    this.project = project;
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

  public String getSectionId() {
    return sectionId;
  }

  public void setSectionId(String sectionId) {
    this.sectionId = sectionId;
  }

  public String getLocation() {
    return location;
  }

  public void setLocation(String location) {
    this.location = location;
  }

  public RampType getLocationR() {
    return locationR;
  }

  public void setLocationR(RampType locationR) {
    this.locationR = locationR;
  }

  public Direction getDirection() {
    return direction;
  }

  public void setDirection(Direction direction) {
    this.direction = direction;
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

  public String getDisplayName() {
    return displayName;
  }

  public void setDisplayName(String displayName) {
    this.displayName = displayName;
    this.selectedDisplayName = displayName;
  }

  public Boolean getShowInEms() {
    return showInEms;
  }

  public void setShowInEms(Boolean showInEms) {
    this.showInEms = showInEms;
  }

  public TcProtocolType getProtocolType() {
    return protocolType;
  }

  public void setProtocolType(TcProtocolType protocolType) {
    this.protocolType = protocolType;
  }

  @Override
  public int compareTo(DeviceConfigDTO o) {
    return this.deviceName.compareTo(o.deviceName);
  }

  /** @return the memo */
  public String getMemo() {
    return memo;
  }

  /** @param memo the memo to set */
  public void setMemo(String memo) {
    this.memo = memo;
  }

  /** @return the status */
  public DeviceStatusType getStatus() {
    return status;
  }

  /** @param status the status to set */
  public void setStatus(DeviceStatusType status) {
    this.status = status;
  }

  /* (non-Javadoc)
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    return "DeviceConfigDTO [deviceName="
        + deviceName
        + ", displayName="
        + displayName
        + ", enable="
        + enable
        + ", deviceType="
        + deviceType
        + ", milepost="
        + milepost
        + ", ip="
        + ip
        + ", port="
        + port
        + ", mfccId="
        + mfccId
        + ", protocolType="
        + protocolType
        + ", defaultMfccId="
        + defaultMfccId
        + ", project="
        + project
        + ", lineId="
        + lineId
        + ", sectionId="
        + sectionId
        + ", location="
        + location
        + ", locationR="
        + locationR
        + ", direction="
        + direction
        + ", longitude="
        + longitude
        + ", latitude="
        + latitude
        + ", memo="
        + memo
        + ", status="
        + status
        + "]";
  }

  /* (non-Javadoc)
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((deviceName == null) ? 0 : deviceName.hashCode());
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
    DeviceConfigDTO other = (DeviceConfigDTO) obj;
    if (deviceName == null) {
      if (other.deviceName != null) return false;
    } else if (!deviceName.equals(other.deviceName)) return false;
    return true;
  }
}
