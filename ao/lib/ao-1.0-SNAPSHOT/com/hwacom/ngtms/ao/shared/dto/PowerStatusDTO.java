/*
 * © HwaCom Systems Inc. 2015
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.ao.shared.dto;

import java.io.Serializable;
import java.util.Date;

public class PowerStatusDTO implements Serializable {

  private static final long serialVersionUID = 5911735796491257248L;

  private String id;

  private String displayName;

  private String powerAlarm;

  private Double powerLastHour;

  private Double power24Hour;

  private Double rv;

  private Double sv;

  private Double tv;

  private Double av;

  private Double ri;

  private Double si;

  private Double ti;

  private Double ai;

  private Double kw;

  private Double pf;

  private Double kwh;

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

  public String getPowerAlarm() {
    return powerAlarm;
  }

  public void setPowerAlarm(String powerAlarm) {
    this.powerAlarm = powerAlarm;
  }

  public Double getPowerLastHour() {
    return powerLastHour;
  }

  public void setPowerLastHour(Double powerLastHour) {
    this.powerLastHour = powerLastHour;
  }

  public Double getPower24Hour() {
    return power24Hour;
  }

  public void setPower24Hour(Double power24Hour) {
    this.power24Hour = power24Hour;
  }

  public Double getRv() {
    return rv;
  }

  public void setRv(Double rv) {
    this.rv = rv;
  }

  public Double getSv() {
    return sv;
  }

  public void setSv(Double sv) {
    this.sv = sv;
  }

  public Double getTv() {
    return tv;
  }

  public void setTv(Double tv) {
    this.tv = tv;
  }

  public Double getAv() {
    return av;
  }

  public void setAv(Double av) {
    this.av = av;
  }

  public Double getRi() {
    return ri;
  }

  public void setRi(Double ri) {
    this.ri = ri;
  }

  public Double getSi() {
    return si;
  }

  public void setSi(Double si) {
    this.si = si;
  }

  public Double getTi() {
    return ti;
  }

  public void setTi(Double ti) {
    this.ti = ti;
  }

  public Double getAi() {
    return ai;
  }

  public void setAi(Double ai) {
    this.ai = ai;
  }

  public Double getKw() {
    return kw;
  }

  public void setKw(Double kw) {
    this.kw = kw;
  }

  public Double getPf() {
    return pf;
  }

  public void setPf(Double pf) {
    this.pf = pf;
  }

  public Double getKwh() {
    return kwh;
  }

  public void setKwh(Double kwh) {
    this.kwh = kwh;
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
    PowerStatusDTO other = (PowerStatusDTO) obj;
    if (id == null) {
      if (other.id != null) return false;
    } else if (!id.equals(other.id)) return false;
    return true;
  }
}
