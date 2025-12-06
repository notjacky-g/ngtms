package com.hwacom.ngtms.ao.shared.dto;

import com.google.gwt.user.client.rpc.IsSerializable;
import java.io.Serializable;

public class RoomInfoDTO implements Serializable, IsSerializable {

  private static final long serialVersionUID = -473243763540403451L;

  //機房平面圖編號
  private String id;
  //機房顯示名稱
  private String name;
  //機房道路位置
  private String lineName;
  //機房里程
  private Integer mileage;
  //主機連線狀態  0是連線 1是斷線  預設 -1無狀態
  private int status;
  //保全狀態(0是保全啟動;1是人員逗留;2是非法入侵) 預設-1= 沒狀態
  private int safeStatus;
  //聲光警報器狀態(0是關閉;1是啟動) 預設-1= 沒狀態
  private int alarmStatus;
  //該機房樓層有人臉辨識功能(0是沒有;1是有) 預設=0
  private int lifefacePanel;
  //該機房樓層有臉辯狀態(0是沒有;1是有) 預設=0
  private int lifefaceStatus;

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

  public int getSafeStatus() {
    return safeStatus;
  }

  public void setSafeStatus(int safeStatus) {
    this.safeStatus = safeStatus;
  }

  public int getAlarmStatus() {
    return alarmStatus;
  }

  public void setAlarmStatus(int alarmStatus) {
    this.alarmStatus = alarmStatus;
  }

  public int getLifefacePanel() {
    return lifefacePanel;
  }

  public void setLifefacePanel(int lifefacePanel) {
    this.lifefacePanel = lifefacePanel;
  }

  public int getLifefaceStatus() {
    return lifefaceStatus;
  }

  public void setLifefaceStatus(int lifefaceStatus) {
    this.lifefaceStatus = lifefaceStatus;
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
    RoomInfoDTO other = (RoomInfoDTO) obj;
    if (id == null) {
      if (other.id != null) return false;
    } else if (!id.equals(other.id)) return false;
    return true;
  }
}
