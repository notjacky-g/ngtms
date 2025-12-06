package com.hwacom.ngtms.ao.shared;

import java.io.Serializable;

public class NCUCardData implements Serializable {

  private static final long serialVersionUID = -7994936909832480217L;

  /* 目前主機預設 長度10碼**/
  private String cCardNo;

  private String cStartDate;

  private String cEndDate;
  /* 是否進出門禁的門  0000代表不允許 FFFF代表允許**/
  private String accessControlDoor;

  public String getcCardNo() {
    return cCardNo;
  }

  public void setcCardNo(String cCardNo) {
    this.cCardNo = cCardNo;
  }

  public String getcStartDate() {
    return cStartDate;
  }

  public void setcStartDate(String cStartDate) {
    this.cStartDate = cStartDate;
  }

  public String getcEndDate() {
    return cEndDate;
  }

  public void setcEndDate(String cEndDate) {
    this.cEndDate = cEndDate;
  }

  public String getAccessControlDoor() {
    return accessControlDoor;
  }

  public void setAccessControlDoor(String accessControlDoor) {
    this.accessControlDoor = accessControlDoor;
  }
}
