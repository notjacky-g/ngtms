package com.hwacom.ngtms.ao.shared.eqconfig;

import java.io.Serializable;
import java.util.List;

public class BtEqConfig implements Serializable {

  private static final long serialVersionUID = 1L;

  private List<Bt> bt;

  public List<Bt> getBt() {
    return bt;
  }

  public void setBt(List<Bt> bt) {
    this.bt = bt;
  }
}
