package com.hwacom.ngtms.ao.shared.dto;

import com.google.gwt.user.client.rpc.IsSerializable;
import java.io.Serializable;
import java.util.List;

public class RoomDeviceParamDTO implements Serializable, IsSerializable {

  private static final long serialVersionUID = 7221138863334304932L;

  private String selectedSubLocationId;

  private List<String> deviceNameList;

  public String getSelectedSubLocationId() {
    return selectedSubLocationId;
  }

  public void setSelectedSubLocationId(String selectedSubLocationId) {
    this.selectedSubLocationId = selectedSubLocationId;
  }

  public List<String> getDeviceNameList() {
    return deviceNameList;
  }

  public void setDeviceNameList(List<String> deviceNameList) {
    this.deviceNameList = deviceNameList;
  }
}
