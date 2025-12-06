/*
 * © HwaCom Systems Inc. 2013
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.ao.shared.eqconfig;

import java.io.Serializable;

/**
 * * oneday_eq_config_data
 *
 * <p>element tts attributes
 *
 * @author jeff.ku
 */
public class Tts implements Serializable {

  private static final long serialVersionUID = 8460843951176190829L;

  private String eqId;
  private String freewayId;
  private String expresswayId;
  private Integer directionId;
  private Integer milepost;
  private String city1Name;
  private Integer city1Milepost;
  private String city2Name;
  private Integer city2Milepost;
  private String city3Name;
  private Integer city3Milepost;
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

  public void setUniqueId(String uniqueId) {
    this.uniqueId = uniqueId;
  }

  public String getCity1Name() {
    return city1Name;
  }

  public void setCity1Name(String city1Name) {
    this.city1Name = city1Name;
  }

  public Integer getCity1Milepost() {
    return city1Milepost;
  }

  public void setCity1Milepost(Integer city1Milepost) {
    this.city1Milepost = city1Milepost;
  }

  public String getCity2Name() {
    return city2Name;
  }

  public void setCity2Name(String city2Name) {
    this.city2Name = city2Name;
  }

  public Integer getCity2Milepost() {
    return city2Milepost;
  }

  public void setCity2Milepost(Integer city2Milepost) {
    this.city2Milepost = city2Milepost;
  }

  public String getCity3Name() {
    return city3Name;
  }

  public void setCity3Name(String city3Name) {
    this.city3Name = city3Name;
  }

  public Integer getCity3Milepost() {
    return city3Milepost;
  }

  public void setCity3Milepost(Integer city3Milepost) {
    this.city3Milepost = city3Milepost;
  }
}
