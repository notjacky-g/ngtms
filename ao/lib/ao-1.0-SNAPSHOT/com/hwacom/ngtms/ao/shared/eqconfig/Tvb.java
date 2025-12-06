package com.hwacom.ngtms.ao.shared.eqconfig;

import java.io.Serializable;

public class Tvb implements Serializable {

  private static final long serialVersionUID = 1L;

  private String tvbId;

  public String getTvbId() {
    return tvbId;
  }

  public void setTvbId(String tvbId) {
    this.tvbId = tvbId;
  }
}
