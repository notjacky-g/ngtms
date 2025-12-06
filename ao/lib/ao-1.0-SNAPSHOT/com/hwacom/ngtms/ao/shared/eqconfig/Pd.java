package com.hwacom.ngtms.ao.shared.eqconfig;

import java.io.Serializable;

public class Pd implements Serializable {

  private static final long serialVersionUID = 1L;

  private String pdId;

  public String getPdId() {
    return pdId;
  }

  public void setPdId(String pdId) {
    this.pdId = pdId;
  }
}
