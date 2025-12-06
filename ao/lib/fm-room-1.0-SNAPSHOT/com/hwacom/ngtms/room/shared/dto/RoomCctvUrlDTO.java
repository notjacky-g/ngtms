package com.hwacom.ngtms.room.shared.dto;

import java.io.Serializable;

public class RoomCctvUrlDTO implements Serializable {

  private static final long serialVersionUID = 1L;

  private String deviceName;

  private String displayName;

  private String url;

  public String getDeviceName() {
    return deviceName;
  }

  public void setDeviceName(String deviceName) {
    this.deviceName = deviceName;
  }

  public String getDisplayName() {
    return displayName;
  }

  public void setDisplayName(String displayName) {
    this.displayName = displayName;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }
}
