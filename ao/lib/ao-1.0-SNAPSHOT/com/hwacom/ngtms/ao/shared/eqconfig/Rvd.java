package com.hwacom.ngtms.ao.shared.eqconfig;

import java.io.Serializable;

public class Rvd implements Serializable {

  private static final long serialVersionUID = 1L;

  private String rvdId;

  public String getRvdId() {
    return rvdId;
  }

  public void setRvdId(String rvdId) {
    this.rvdId = rvdId;
  }
}
