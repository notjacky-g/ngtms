package com.hwacom.ngtms.ao.shared.eqconfig;

import java.io.Serializable;
import java.util.List;

public class QldEqConfig implements Serializable {

  private static final long serialVersionUID = 1L;

  private List<Qld> qld;

  public List<Qld> getQld() {
    return qld;
  }

  public void setQld(List<Qld> qld) {
    this.qld = qld;
  }
}
