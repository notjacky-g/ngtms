package com.hwacom.ngtms.room.shared;

import java.io.Serializable;

public class RtuConfig implements Serializable {

  private static final long serialVersionUID = 8336783080090704765L;

  /** 訊號種類 */
  private String signalType;

  /** 單位 */
  private String unit;

  /** 上限值 */
  private Integer upperLimit;

  /** 下限值 */
  private Integer lowerLimit;

  /** 是否發送警報 */
  private boolean isAlarm;

  public String getSignalType() {
    return signalType;
  }

  public void setSignalType(String signalType) {
    this.signalType = signalType;
  }

  public String getUnit() {
    return unit;
  }

  public void setUnit(String unit) {
    this.unit = unit;
  }

  public Integer getUpperLimit() {
    return upperLimit;
  }

  public void setUpperLimit(Integer upperLimit) {
    this.upperLimit = upperLimit;
  }

  public Integer getLowerLimit() {
    return lowerLimit;
  }

  public void setLowerLimit(Integer lowerLimit) {
    this.lowerLimit = lowerLimit;
  }

  public boolean isAlarm() {
    return isAlarm;
  }

  public void setAlarm(boolean isAlarm) {
    this.isAlarm = isAlarm;
  }
}
