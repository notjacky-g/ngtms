package com.hwacom.ngtms.ao.shared.eqconfig;

import java.io.Serializable;
import java.util.List;

public class TvbEqConfig implements Serializable {

  private static final long serialVersionUID = 1L;

  private List<Tvb> tvb;

  public List<Tvb> getTvb() {
    return tvb;
  }

  public void setTvb(List<Tvb> tvb) {
    this.tvb = tvb;
  }
}
