package com.hwacom.ngtms.ao.shared.eqconfig;

import java.io.Serializable;

public class Sdh implements Serializable {

  private static final long serialVersionUID = 1L;

  private String sdhId;

  public String getSdhId() {
    return sdhId;
  }

  public void setSdhId(String sdhId) {
    this.sdhId = sdhId;
  }
}
