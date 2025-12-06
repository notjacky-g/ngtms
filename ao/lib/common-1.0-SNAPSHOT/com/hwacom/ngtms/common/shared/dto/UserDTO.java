/*
 * © HwaCom Systems Inc. 2013
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.common.shared.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.google.gwt.user.client.rpc.IsSerializable;
import com.hwacom.ngtms.common.shared.DateJsonSerializer;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class UserDTO implements Serializable, IsSerializable {

  private static final long serialVersionUID = -796433570935939702L;

  private UserBasicInfoDTO basicInfo = new UserBasicInfoDTO();

  private Boolean enable;

  @JsonSerialize(using = DateJsonSerializer.class)
  private Date startTime;

  @JsonSerialize(using = DateJsonSerializer.class)
  private Date endTime;

  private Boolean checkExpired;

  private Date updateTime;

  private Boolean loggedIn;

  private String sessionId;

  private Boolean isSso;

  private Set<RoleDTO> roles = new HashSet<RoleDTO>();

  /** 服務單位 */
  private Set<UnitDTO> units = new HashSet<UnitDTO>();

  private Date lockTime;

  private Integer loginFailureCount;

  private Date lastPwdChangeTime;

  public UserDTO() {}

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((basicInfo == null) ? 0 : basicInfo.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null) return false;
    if (getClass() != obj.getClass()) return false;
    UserDTO other = (UserDTO) obj;
    if (basicInfo == null) {
      if (other.basicInfo != null) return false;
    } else if (!basicInfo.equals(other.basicInfo)) return false;
    return true;
  }

  public UserBasicInfoDTO getBasicInfo() {
    return basicInfo;
  }

  public void setBasicInfo(UserBasicInfoDTO basicInfo) {
    this.basicInfo = basicInfo;
  }

  public String getLogin() {
    return basicInfo.getLogin();
  }

  /** @param login the login to set */
  public void setLogin(String login) {
    basicInfo.setLogin(login);
  }

  /** @return the password */
  public String getPwd1() {
    return basicInfo.getPwd1();
  }

  /** @param pwd1 the password to set */
  public void setPwd1(String pwd1) {
    basicInfo.setPwd1(pwd1);
  }

  /** @return the name */
  public String getName() {
    return basicInfo.getName();
  }

  /** @param name the name to set */
  public void setName(String name) {
    basicInfo.setName(name);
  }

  /** @return the description */
  public String getDescription() {
    return basicInfo.getDescription();
  }

  /** @param description the description to set */
  public void setDescription(String description) {
    basicInfo.setDescription(description);
  }

  /** @return the startTime */
  public Date getStartTime() {
    return startTime;
  }

  /** @param startTime the startTime to set */
  public void setStartTime(Date startTime) {
    this.startTime = startTime;
  }

  /** @return the endTime */
  public Date getEndTime() {
    return endTime;
  }

  /** @param endTime the endTime to set */
  public void setEndTime(Date endTime) {
    this.endTime = endTime;
  }

  /** @return the checkExpired */
  public Boolean getCheckExpired() {
    return checkExpired;
  }

  /** @param checkExpired the checkExpired to set */
  public void setCheckExpired(Boolean checkExpired) {
    this.checkExpired = checkExpired;
  }

  /** @return the updateTime */
  public Date getUpdateTime() {
    return updateTime;
  }

  /** @param updateTime the updateTime to set */
  public void setUpdateTime(Date updateTime) {
    this.updateTime = updateTime;
  }

  public Set<RoleDTO> getRoles() {
    return roles;
  }

  public void setRoles(Set<RoleDTO> roles) {
    this.roles = roles;
  }

  public boolean addRole(RoleDTO role) {
    return roles.add(role);
  }

  public String roleStr() {
    String result = "";
    if (roles != null) {
      for (RoleDTO role : roles) {
        result = result + role.getName() + "　";
      }
    }
    return result;
  }

  public Set<UnitDTO> getUnits() {
    return units;
  }

  public void setUnits(Set<UnitDTO> units) {
    this.units = units;
  }

  public boolean addUnit(UnitDTO unit) {
    return units.add(unit);
  }

  public String unitStr() {
    String result = "";
    if (roles != null) {
      for (UnitDTO unit : units) {
        result = result + unit.getName() + "　";
      }
    }
    return result;
  }

  /** @return the enable */
  public Boolean getEnable() {
    return enable;
  }

  /** @param enable the enable to set */
  public void setEnable(Boolean enable) {
    this.enable = enable;
  }

  /** @return the loggedIn */
  public Boolean getLoggedIn() {
    return loggedIn;
  }

  /** @param loggedIn the loggedIn to set */
  public void setLoggedIn(Boolean loggedIn) {
    this.loggedIn = loggedIn;
  }

  /** @return the sessionId */
  public String getSessionId() {
    return sessionId;
  }

  /** @param sessionId the sessionId to set */
  public void setSessionId(String sessionId) {
    this.sessionId = sessionId;
  }

  /** @return the isSso */
  public Boolean getIsSso() {
    return isSso;
  }

  /** @param isSso the isSso to set */
  public void setIsSso(Boolean isSso) {
    this.isSso = isSso;
  }

  /** @return the mobile */
  public String getMobile() {
    return basicInfo.getMobile();
  }

  /** @param mobile the mobile to set */
  public void setMobile(String mobile) {
    basicInfo.setMobile(mobile);
  }

  /** @return the mail */
  public String getMail() {
    return basicInfo.getMail();
  }

  /** @param mail the mail to set */
  public void setMail(String mail) {
    basicInfo.setMail(mail);
  }

  public Date getLockTime() {
    return lockTime;
  }

  public void setLockTime(Date lockTime) {
    this.lockTime = lockTime;
  }

  public Integer getLoginFailureCount() {
    return loginFailureCount;
  }

  public void setLoginFailureCount(Integer loginFailureCount) {
    this.loginFailureCount = loginFailureCount;
  }

  public Date getLastPwdChangeTime() {
    return lastPwdChangeTime;
  }

  public void setLastPwdChangeTime(Date lastPwdChangeTime) {
    this.lastPwdChangeTime = lastPwdChangeTime;
  }

  @Override
  public String toString() {
    return "UserDTO [basicInfo="
        + basicInfo
        + ", enable="
        + enable
        + ", startTime="
        + startTime
        + ", endTime="
        + endTime
        + ", checkExpired="
        + checkExpired
        + ", updateTime="
        + updateTime
        + ", loggedIn="
        + loggedIn
        + ", sessionId="
        + sessionId
        + ", isSso="
        + isSso
        + ", roles="
        + roles
        + ", units="
        + units
        + "]";
  }

  public String toInfoString() {
    StringBuilder roleStr = new StringBuilder();
    for (RoleDTO role : roles) {
      roleStr.append(role.getName());
    }
    StringBuilder unitStr = new StringBuilder();
    for (UnitDTO unit : units) {
      unitStr.append(unit.getName());
    }
    return "UserDTO [basicInfo="
        + basicInfo
        + ", enable="
        + enable
        + ", startTime="
        + startTime
        + ", endTime="
        + endTime
        + ", checkExpired="
        + checkExpired
        + ", updateTime="
        + updateTime
        + ", loggedIn="
        + loggedIn
        + ", sessionId="
        + sessionId
        + ", isSso="
        + isSso
        + ", units="
        + unitStr.toString()
        + ", roles="
        + roleStr.toString()
        + "]";
  }
}
