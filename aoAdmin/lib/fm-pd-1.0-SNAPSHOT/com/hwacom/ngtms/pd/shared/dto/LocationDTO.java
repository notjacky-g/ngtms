package com.hwacom.ngtms.pd.shared.dto;

import com.google.gwt.user.client.rpc.IsSerializable;
import java.io.Serializable;

public class LocationDTO implements Serializable, IsSerializable {

  /** */
  private static final long serialVersionUID = 1L;

  private Integer id;

  private String locName;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getLocName() {
    return locName;
  }

  public void setLocName(String locName) {
    this.locName = locName;
  }
}
