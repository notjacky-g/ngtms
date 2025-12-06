package com.hwacom.ngtms.ao.shared.eqconfig;

import java.io.Serializable;

public class Et implements Serializable {

  private static final long serialVersionUID = -5664129674336723237L;

  private String eqId;
  private String freewayId;
  private String expresswayId;
  private Integer directionId;
  private Integer milepost;
  private String longitude;
  private String latitude;
  private String uniqueId;

  public String getEqId() {
    return eqId;
  }

  public void setEqId(String eqId) {
    this.eqId = eqId;
  }

  public String getFreewayId() {
    return freewayId;
  }

  public void setFreewayId(String freewayId) {
    this.freewayId = freewayId;
  }

  public String getExpresswayId() {
    return expresswayId;
  }

  public void setExpresswayId(String expresswayId) {
    this.expresswayId = expresswayId;
  }

  public Integer getDirectionId() {
    return directionId;
  }

  public void setDirectionId(Integer directionId) {
    this.directionId = directionId;
  }

  public Integer getMilepost() {
    return milepost;
  }

  public void setMilepost(Integer milepost) {
    this.milepost = milepost;
  }

  public String getLongitude() {
    return longitude;
  }

  public void setLongitude(String longitude) {
    this.longitude = longitude;
  }

  public String getLatitude() {
    return latitude;
  }

  public void setLatitude(String latitude) {
    this.latitude = latitude;
  }

  public String getUniqueId() {
    return uniqueId;
  }
}
