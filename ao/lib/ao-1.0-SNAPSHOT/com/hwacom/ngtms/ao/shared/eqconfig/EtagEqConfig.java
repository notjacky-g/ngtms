package com.hwacom.ngtms.ao.shared.eqconfig;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class EtagEqConfig implements Serializable {

  private static final long serialVersionUID = 7919699373508252739L;

  private List<EtagDataRow> etagDataList = new ArrayList<>();

  public List<EtagDataRow> getEtagDataList() {
    return etagDataList;
  }

  public void setEtagDataList(List<EtagDataRow> etagDataList) {
    this.etagDataList = etagDataList;
  }
}
