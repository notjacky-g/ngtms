package com.hwacom.ngtms.ao.shared.dto;

import com.google.gwt.user.client.rpc.IsSerializable;
import com.hwacom.ngtms.ao.shared.dto.RoomAnalogTreeDTO.AnalogType;
import java.io.Serializable;

public class AnalogyDataDTO implements Serializable, IsSerializable {

  private static final long serialVersionUID = -2210470802025445856L;

  private String deviceName;

  /** locationName+(subLocationName)+displayName */
  private String locationName;

  private AnalogType analogType;

  public String getDeviceName() {
    return deviceName;
  }

  public void setDeviceName(String deviceName) {
    this.deviceName = deviceName;
  }

  public String getLocationName() {
    return locationName;
  }

  public void setLocationName(String locationName) {
    this.locationName = locationName;
  }

  public AnalogType getAnalogType() {
    return analogType;
  }

  public void setAnalogType(AnalogType analogType) {
    this.analogType = analogType;
  }
}
