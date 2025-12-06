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
 * <p>element lcs attributes
 *
 * @author jeff.ku
 */
public class Lcs implements Serializable {

  private static final long serialVersionUID = 8460843951176190829L;

  private String eqId;
  private String freewayId;
  private String expresswayId;
  private Integer directionId;
  private Integer milepost;
  private Integer lanes;
  private Integer boards;
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

  public Integer getLanes() {
    return lanes;
  }

  public void setLanes(Integer lanes) {
    this.lanes = lanes;
  }

  public Integer getBoards() {
    return boards;
  }

  public void setBoards(Integer boards) {
    this.boards = boards;
  }
}
