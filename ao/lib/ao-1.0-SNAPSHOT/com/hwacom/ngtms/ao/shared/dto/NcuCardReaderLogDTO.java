package com.hwacom.ngtms.ao.shared.dto;

import com.google.gwt.user.client.rpc.IsSerializable;
import java.io.Serializable;
import java.util.Date;

public class NcuCardReaderLogDTO implements Serializable, IsSerializable {

  private static final long serialVersionUID = -4527951082846533057L;

  private String id;

  private String ncuId;

  //機房名稱
  private String location;

  private String deviceId;

  private String displayName;

  private String cardNumber;

  private Date dataTime;

  private String userName;

  private String userCompany;

  private String status;

  private String result;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getNcuId() {
    return ncuId;
  }

  public void setNcuId(String ncuId) {
    this.ncuId = ncuId;
  }

  public String getLocation() {
    return location;
  }

  public void setLocation(String location) {
    this.location = location;
  }

  public String getDeviceId() {
    return deviceId;
  }

  public void setDeviceId(String deviceId) {
    this.deviceId = deviceId;
  }

  public String getDisplayName() {
    return displayName;
  }

  public void setDisplayName(String displayName) {
    this.displayName = displayName;
  }

  public String getCardNumber() {
    return cardNumber;
  }

  public void setCardNumber(String cardNumber) {
    this.cardNumber = cardNumber;
  }

  public Date getDataTime() {
    return dataTime;
  }

  public void setDataTime(Date dataTime) {
    this.dataTime = dataTime;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public String getUserCompany() {
    return userCompany;
  }

  public void setUserCompany(String userCompany) {
    this.userCompany = userCompany;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getResult() {
    return result;
  }

  public void setResult(String result) {
    this.result = result;
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
    NcuCardReaderLogDTO other = (NcuCardReaderLogDTO) obj;
    if (id == null) {
      if (other.id != null) return false;
    } else if (!id.equals(other.id)) return false;
    return true;
  }

  @Override
  public String toString() {
    return "NcuCardReaderLogDTO [id="
        + id
        + ", ncuId="
        + ncuId
        + ", location="
        + location
        + ", deviceId="
        + deviceId
        + ", displayName="
        + displayName
        + ", cardNumber="
        + cardNumber
        + ", dataTime="
        + dataTime
        + ", userName="
        + userName
        + ", userCompany="
        + userCompany
        + ", status="
        + status
        + ", result="
        + result
        + "]";
  }
}
