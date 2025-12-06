package com.hwacom.ngtms.ao.shared.eqconfig;

import java.io.Serializable;
import java.util.List;

public class SdhEqConfig implements Serializable {

  private static final long serialVersionUID = 1L;

  private List<Sdh> sdh;

  public List<Sdh> getSdh() {
    return sdh;
  }

  public void setSdh(List<Sdh> sdh) {
    this.sdh = sdh;
  }
}
