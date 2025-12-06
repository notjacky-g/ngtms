/*
 * © HwaCom Systems Inc. 2013
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.common.shared.dto;

import com.google.gwt.user.client.rpc.IsSerializable;
import java.io.Serializable;

/**
 * Rest 傳送相關參數
 *
 * @author brian.cheng
 */
public class AccountParametersDTO implements Serializable, IsSerializable {
  private static final long serialVersionUID = -5470277099822775127L;

  private UserDTO userDTO;
  private RoleDTO roleDTO;
  private FunctionPermissionDTO functionPermissionDTO;
  private String user;
  private String password;
  private UserDTO dto;
  private UnitDTO unitDTO;

  public UserDTO getUserDTO() {
    return userDTO;
  }

  public void setUserDTO(UserDTO userDTO) {
    this.userDTO = userDTO;
  }

  public RoleDTO getRoleDTO() {
    return roleDTO;
  }

  public void setRoleDTO(RoleDTO roleDTO) {
    this.roleDTO = roleDTO;
  }

  public FunctionPermissionDTO getFunctionPermissionDTO() {
    return functionPermissionDTO;
  }

  public void setFunctionPermissionDTO(FunctionPermissionDTO functionPermissionDTO) {
    this.functionPermissionDTO = functionPermissionDTO;
  }

  public String getUser() {
    return user;
  }

  public void setUser(String user) {
    this.user = user;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public UserDTO getDto() {
    return dto;
  }

  public void setDto(UserDTO dto) {
    this.dto = dto;
  }

  public UnitDTO getUnitDTO() {
    return unitDTO;
  }

  public void setUnitDTO(UnitDTO unitDTO) {
    this.unitDTO = unitDTO;
  }
}
