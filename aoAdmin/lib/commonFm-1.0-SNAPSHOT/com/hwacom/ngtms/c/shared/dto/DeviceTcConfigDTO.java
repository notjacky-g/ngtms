package com.hwacom.ngtms.c.shared.dto;

import com.hwacom.ngtms.c.shared.Direction;
import com.hwacom.ngtms.c.shared.RampType;
import com.hwacom.ngtms.c.shared.TcProtocolType;
import java.util.Date;

public class DeviceTcConfigDTO extends DeviceConfigDTO {

  private static final long serialVersionUID = 1L;

  private Integer milepost;

  private String mfccId;

  private String defaultMfccId;

  private RampType rampType;

  private TcProtocolType protocolType;

  private Date versionDate;

  private String lineId;

  private Direction direction;

  private String sectionId;

  private String location;

  /** 是否顯示於 EMS 中，若非"電腦主機設備"，請設定為 false，否則設為 true */
  private Boolean showInEms;

  public Integer getMilepost() {
    return milepost;
  }

  public void setMilepost(Integer milepost) {
    this.milepost = milepost;
  }

  public String getMfccId() {
    return mfccId;
  }

  public void setMfccId(String mfccId) {
    this.mfccId = mfccId;
  }

  public String getDefaultMfccId() {
    return defaultMfccId;
  }

  public void setDefaultMfccId(String defaultMfccId) {
    this.defaultMfccId = defaultMfccId;
  }

  public RampType getRampType() {
    return rampType;
  }

  public void setRampType(RampType rampType) {
    this.rampType = rampType;
  }

  public TcProtocolType getProtocolType() {
    return protocolType;
  }

  public void setProtocolType(TcProtocolType protocolType) {
    this.protocolType = protocolType;
  }

  public Date getVersionDate() {
    return versionDate;
  }

  public void setVersionDate(Date versionDate) {
    this.versionDate = versionDate;
  }

  public String getLineId() {
    return lineId;
  }

  public void setLineId(String lineId) {
    this.lineId = lineId;
  }

  public Direction getDirection() {
    return direction;
  }

  public void setDirection(Direction direction) {
    this.direction = direction;
  }

  public String getSectionId() {
    return sectionId;
  }

  public void setSectionId(String sectionId) {
    this.sectionId = sectionId;
  }

  public <T extends Enum<T>> T getLocation(Class<T> t) {
    return Enum.valueOf(t, location);
  }

  public String getLocation() {
    return location;
  }

  public void setLocation(String location) {
    this.location = location;
  }

  public Boolean getShowInEms() {
    return showInEms;
  }

  public void setShowInEms(Boolean showInEms) {
    this.showInEms = showInEms;
  }
}
