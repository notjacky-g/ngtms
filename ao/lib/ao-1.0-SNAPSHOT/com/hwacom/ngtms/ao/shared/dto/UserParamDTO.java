package com.hwacom.ngtms.ao.shared.dto;

import java.io.Serializable;

public class UserParamDTO implements Serializable {

  private static final long serialVersionUID = 7163078341304993893L;

  private String username;

  private String password;

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }
}
