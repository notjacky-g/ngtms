package com.hwacom.ngtms.ao.shared.eqconfig;

import java.io.Serializable;
import java.util.List;

public class EtEqConfig implements Serializable {

  private static final long serialVersionUID = 1L;

  private List<Et> et;

  public List<Et> getEt() {
    return et;
  }

  public void setEt(List<Et> et) {
    this.et = et;
  }
}
