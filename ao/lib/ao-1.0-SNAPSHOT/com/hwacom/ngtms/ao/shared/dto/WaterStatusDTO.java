/*
 * © HwaCom Systems Inc. 2015
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.ao.shared.dto;

import java.io.Serializable;
import java.util.Date;

public class WaterStatusDTO implements Serializable {

  private static final long serialVersionUID = 8635231444983358060L;

  private String id;

  private String displayName;

  private String waterAlarm;

  private Double waterLastHour;

  private Double water24Hour;

  private Double cumulateValue;

  private Double instantaneousValue;

  private Date dataTime;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getDisplayName() {
    return displayName;
  }

  public void setDisplayName(String displayName) {
    this.displayName = displayName;
  }

  public String getWaterAlarm() {
    return waterAlarm;
  }

  public void setWaterAlarm(String waterAlarm) {
    this.waterAlarm = waterAlarm;
  }

  public Double getWaterLastHour() {
    return waterLastHour;
  }

  public void setWaterLastHour(Double waterLastHour) {
    this.waterLastHour = waterLastHour;
  }

  public Double getWater24Hour() {
    return water24Hour;
  }

  public void setWater24Hour(Double water24Hour) {
    this.water24Hour = water24Hour;
  }

  public Double getCumulateValue() {
    return cumulateValue;
  }

  public void setCumulateValue(Double cumulateValue) {
    this.cumulateValue = cumulateValue;
  }

  public Double getInstantaneousValue() {
    return instantaneousValue;
  }

  public void setInstantaneousValue(Double instantaneousValue) {
    this.instantaneousValue = instantaneousValue;
  }

  public Date getDataTime() {
    return dataTime;
  }

  public void setDataTime(Date dataTime) {
    this.dataTime = dataTime;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((id == null) ? 0 : id.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null) return false;
    if (getClass() != obj.getClass()) return false;
    WaterStatusDTO other = (WaterStatusDTO) obj;
    if (id == null) {
      if (other.id != null) return false;
    } else if (!id.equals(other.id)) return false;
    return true;
  }
}
