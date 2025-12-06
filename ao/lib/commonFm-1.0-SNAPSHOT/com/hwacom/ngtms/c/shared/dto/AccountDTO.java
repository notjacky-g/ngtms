/*
 * © HwaCom Systems Inc. 2015
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.c.shared.dto;

import com.google.gwt.user.client.rpc.IsSerializable;
import java.io.Serializable;
import java.util.Date;

public class AccountDTO implements Serializable, IsSerializable {

  private static final long serialVersionUID = 745236221429401622L;
  private String id;
  private String name;
  private String password;
  private Date updateTime;
  private Boolean enable;
  private Boolean isEmmAccount;
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

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public Date getUpdateTime() {
    return updateTime;
  }

  public void setUpdateTime(Date updateTime) {
    this.updateTime = updateTime;
  }

  public Boolean getEnable() {
    return enable;
  }

  public void setEnable(Boolean enable) {
    this.enable = enable;
  }

  public Boolean getIsEmmAccount() {
    return isEmmAccount;
  }

  public void setIsEmmAccount(Boolean isEmmAccount) {
    this.isEmmAccount = isEmmAccount;
  }

  public String getMemo() {
    return memo;
  }

  public void setMemo(String memo) {
    this.memo = memo;
  }

  @Override
  public String toString() {
    return "AccountDTO [id="
        + id
        + ", name="
        + name
        + ", password="
        + password
        + ", updateTime="
        + updateTime
        + ", enable="
        + enable
        + ", isEmmAccount="
        + isEmmAccount
        + ", memo="
        + memo
        + "]";
  }
}
