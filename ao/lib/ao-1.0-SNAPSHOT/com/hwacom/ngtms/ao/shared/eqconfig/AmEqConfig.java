package com.hwacom.ngtms.ao.shared.eqconfig;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class AmEqConfig implements Serializable {

  private static final long serialVersionUID = 1L;

  List<Am> am = new ArrayList<>();

  public List<Am> getAm() {
    return am;
  }

  public void setAm(List<Am> am) {
    this.am = am;
  }
}
