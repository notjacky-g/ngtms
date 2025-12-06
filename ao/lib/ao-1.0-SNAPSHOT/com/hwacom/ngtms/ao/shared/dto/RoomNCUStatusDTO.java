package com.hwacom.ngtms.ao.shared.dto;

import com.google.gwt.user.client.rpc.IsSerializable;
import java.io.Serializable;
import java.util.Set;

public class RoomNCUStatusDTO implements Serializable, IsSerializable {

  private static final long serialVersionUID = -317311432507897214L;
  //主機編號
  private String id;
  //機房名稱
  private String roomName;
  //機房分機號碼 (如果未提供就填"-")
  private String phoneNumber;
  //保全狀態(0是保全啟動;1是人員逗留;2是非法入侵) 預設-1= 沒狀態
  private int preservationStatus;
  //逗留人員名單(當保全狀態為1的時候 查詢該名單)
  private Set<RoomStaffPeopleDTO> staffpeople;
  //警報音狀態 (0是正常(關閉);1是開啟)  預設-1= 沒狀態
  private int alarmVideoStatus;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getRoomName() {
    return roomName;
  }

  public void setRoomName(String roomName) {
    this.roomName = roomName;
  }

  public String getPhoneNumber() {
    return phoneNumber;
  }

  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

  public int getPreservationStatus() {
    return preservationStatus;
  }

  public void setPreservationStatus(int preservationStatus) {
    this.preservationStatus = preservationStatus;
  }

  public Set<RoomStaffPeopleDTO> getStaffpeople() {
    return staffpeople;
  }

  public void setStaffpeople(Set<RoomStaffPeopleDTO> staffpeople) {
    this.staffpeople = staffpeople;
  }

  public int getAlarmVideoStatus() {
    return alarmVideoStatus;
  }

  public void setAlarmVideoStatus(int alarmVideoStatus) {
    this.alarmVideoStatus = alarmVideoStatus;
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
    RoomNCUStatusDTO other = (RoomNCUStatusDTO) obj;
    if (id == null) {
      if (other.id != null) return false;
    } else if (!id.equals(other.id)) return false;
    return true;
  }
}
