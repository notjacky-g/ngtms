/*
 * © HwaCom Systems Inc. 2019
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.common.shared.dto;

import com.google.gwt.user.client.rpc.IsSerializable;
import java.io.Serializable;

public class UserBasicInfoDTO implements Serializable, IsSerializable {

  private static final long serialVersionUID = 1L;

  private String login;

  private String oriPassword;

  private String pwd1;

  private String name;

  private String description;

  /** 手機 */
  private String mobile;

  /** mail */
  private String mail;

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((login == null) ? 0 : login.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null) return false;
    if (getClass() != obj.getClass()) return false;
    UserBasicInfoDTO other = (UserBasicInfoDTO) obj;
    if (login == null) {
      if (other.login != null) return false;
    } else if (!login.equals(other.login)) return false;
    return true;
  }

  public String getLogin() {
    return login;
  }

  public void setLogin(String login) {
    this.login = login;
  }

  public String getOriPassword() {
    return oriPassword;
  }

  public void setOriPassword(String oriPassword) {
    this.oriPassword = oriPassword;
  }

  public String getPwd1() {
    return pwd1;
  }

  public void setPwd1(String pwd1) {
    this.pwd1 = pwd1;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getMobile() {
    return mobile;
  }

  public void setMobile(String mobile) {
    this.mobile = mobile;
  }

  public String getMail() {
    return mail;
  }

  public void setMail(String mail) {
    this.mail = mail;
  }

  @Override
  public String toString() {
    return "UserBasicInfo [login="
        + login
        + ", password="
        + pwd1
        + ", name="
        + name
        + ", description="
        + description
        + ", mobile="
        + mobile
        + ", mail="
        + mail
        + "]";
  }
}
