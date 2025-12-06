package com.hwacom.ngtms.ao.shared.eqconfig;

import java.io.Serializable;
import java.util.List;

public class PdEqConfig implements Serializable {

  private static final long serialVersionUID = 1L;

  private List<Pd> pd;

  public List<Pd> getPd() {
    return pd;
  }

  public void setPd(List<Pd> pd) {
    this.pd = pd;
  }
}
