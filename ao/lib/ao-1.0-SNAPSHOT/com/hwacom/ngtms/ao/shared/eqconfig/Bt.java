package com.hwacom.ngtms.ao.shared.eqconfig;

import java.io.Serializable;

public class Bt implements Serializable {

  private static final long serialVersionUID = 1L;

  private String btId;

  public String getBtId() {
    return btId;
  }

  public void setBtId(String btId) {
    this.btId = btId;
  }
}
