package com.hwacom.ngtms.ao.shared.dto;

import java.io.Serializable;
import java.util.Date;

public class RoomStaffPeopleDTO implements Serializable {

  private static final long serialVersionUID = -4093090817274921310L;

  /** 刷進人員卡號 */
  private String id;

  /** 刷進人員 名稱 */
  private String name;

  /** 刷進人員 電話 */
  private String cellPhone;

  /** 刷進人員時間 */
  private Date inTime;

  /** 刷進人員備註(可填入廠商 或 單位) */
  private String memo;

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

  public String getCellPhone() {
    return cellPhone;
  }

  public void setCellPhone(String cellPhone) {
    this.cellPhone = cellPhone;
  }

  public Date getInTime() {
    return inTime;
  }

  public void setInTime(Date inTime) {
    this.inTime = inTime;
  }

  public String getMemo() {
    return memo;
  }

  public void setMemo(String memo) {
    this.memo = memo;
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
    RoomStaffPeopleDTO other = (RoomStaffPeopleDTO) obj;
    if (id == null) {
      if (other.id != null) return false;
    } else if (!id.equals(other.id)) return false;
    return true;
  }
}
