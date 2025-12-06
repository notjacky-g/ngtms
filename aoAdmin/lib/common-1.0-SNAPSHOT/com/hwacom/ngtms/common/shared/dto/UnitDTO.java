/*
 * © HwaCom Systems Inc. 2013
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.common.shared.dto;

import java.io.Serializable;

public class UnitDTO implements Serializable {

  private static final long serialVersionUID = 1L;

  /** 名稱 */
  private String name;

  /** 全名 */
  private String fullName;

  /** 負責人 */
  private String manInCharge;

  /** 地址 */
  private String address;

  /** 電子郵件 */
  private String email;

  /** 電話 */
  private String phone;

  /** 傳真 */
  private String fax;

  /** 統一編號 */
  private String taxId;

  /** 備註 */
  private String description;

  /** 可否審核帳號 */
  private Boolean haveAccountApprove;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getFullName() {
    return fullName;
  }

  public void setFullName(String fullName) {
    this.fullName = fullName;
  }

  public String getManInCharge() {
    return manInCharge;
  }

  public void setManInCharge(String manInCharge) {
    this.manInCharge = manInCharge;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public String getFax() {
    return fax;
  }

  public void setFax(String fax) {
    this.fax = fax;
  }

  public String getTaxId() {
    return taxId;
  }

  public void setTaxId(String taxId) {
    this.taxId = taxId;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Boolean getHaveAccountApprove() {
    return haveAccountApprove;
  }

  public void setHaveAccountApprove(Boolean haveAccountApprove) {
    this.haveAccountApprove = haveAccountApprove;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((name == null) ? 0 : name.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null) return false;
    if (getClass() != obj.getClass()) return false;
    UnitDTO other = (UnitDTO) obj;
    if (name == null) {
      if (other.name != null) return false;
    } else if (!name.equals(other.name)) return false;
    return true;
  }
}
