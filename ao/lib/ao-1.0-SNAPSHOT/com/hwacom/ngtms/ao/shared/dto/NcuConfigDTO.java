package com.hwacom.ngtms.ao.shared.dto;

import com.google.gwt.user.client.rpc.IsSerializable;
import java.io.Serializable;

public class NcuConfigDTO implements Serializable, IsSerializable {

  private static final long serialVersionUID = -317311432507897214L;
  //NCU主機編號
  private String id;
  //主機名稱
  private String name;
  //主機道路位置
  private String lineName;
  //主機里程
  private Integer mileage;
  //警告音自動關閉時間預設0 等於不關閉
  private Integer alarmTime;
  //主機連線狀態  0是連線 1是斷線 2是卡機狀態有動作
  private int status;

  private boolean enable;

  private String ip;

  private String port;

  private Double latitude;

  private Double longitude;

  private String dataTimeNow;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getLineName() {
    return lineName;
  }

  public void setLineName(String lineName) {
    this.lineName = lineName;
  }

  public Integer getMileage() {
    return mileage;
  }

  public void setMileage(Integer mileage) {
    this.mileage = mileage;
  }

  public int getStatus() {
    return status;
  }

  public void setStatus(int status) {
    this.status = status;
  }

  public boolean isEnable() {
    return enable;
  }

  public void setEnable(boolean enable) {
    this.enable = enable;
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

  public Double getLatitude() {
    return latitude;
  }

  public void setLatitude(Double latitude) {
    this.latitude = latitude;
  }

  public Double getLongitude() {
    return longitude;
  }

  public void setLongitude(Double longitude) {
    this.longitude = longitude;
  }

  public Integer getAlarmTime() {
    return alarmTime;
  }

  public void setAlarmTime(Integer alarmTime) {
    this.alarmTime = alarmTime;
  }

  public String getDataTimeNow() {
    return dataTimeNow;
  }

  public void setDataTimeNow(String dataTimeNow) {
    this.dataTimeNow = dataTimeNow;
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
    NcuConfigDTO other = (NcuConfigDTO) obj;
    if (id == null) {
      if (other.id != null) return false;
    } else if (!id.equals(other.id)) return false;
    return true;
  }
}
