package com.hwacom.ngtms.ccs.shared.dto;

import com.google.gwt.user.client.rpc.IsSerializable;
import java.io.Serializable;

public class CctvConfigDTO implements Serializable, IsSerializable {

  private static final long serialVersionUID = -4609820588904454946L;

  /** 設備編號 */
  private String deviceName;

  /** 低解析度 URL */
  private String lowUrl;

  /** 高解析度 URL */
  private String highUrl;

  /** 外網 URL */
  private String externalUrl;

  public String getDeviceName() {
    return deviceName;
  }

  public void setDeviceName(String deviceName) {
    this.deviceName = deviceName;
  }

  public String getLowUrl() {
    return lowUrl;
  }

  public void setLowUrl(String lowUrl) {
    this.lowUrl = lowUrl;
  }

  public String getHighUrl() {
    return highUrl;
  }

  public void setHighUrl(String highUrl) {
    this.highUrl = highUrl;
  }

  public String getExternalUrl() {
    return externalUrl;
  }

  public void setExternalUrl(String externalUrl) {
    this.externalUrl = externalUrl;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((deviceName == null) ? 0 : deviceName.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null) return false;
    if (getClass() != obj.getClass()) return false;
    CctvConfigDTO other = (CctvConfigDTO) obj;
    if (deviceName == null) {
      if (other.deviceName != null) return false;
    } else if (!deviceName.equals(other.deviceName)) return false;
    return true;
  }

  @Override
  public String toString() {
    return "CctvConfigDTO [deviceName="
        + deviceName
        + ", lowUrl="
        + lowUrl
        + ", highUrl="
        + highUrl
        + ", externalUrl="
        + externalUrl
        + "]";
  }
}
