package com.hwacom.ngtms.ao.shared.eqconfig;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class RvdEqConfig implements Serializable {

  private static final long serialVersionUID = 1L;

  List<Rvd> rvd = new ArrayList<>();

  public List<Rvd> getRvd() {
    return rvd;
  }

  public void setRvd(List<Rvd> rvd) {
    this.rvd = rvd;
  }
}
