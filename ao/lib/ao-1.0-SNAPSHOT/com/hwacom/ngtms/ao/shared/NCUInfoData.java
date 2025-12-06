package com.hwacom.ngtms.ao.shared;

import java.io.Serializable;

public class NCUInfoData implements Serializable {

  private static final long serialVersionUID = -4924418245810824156L;

  private String verType;

  private String nVer;

  private String sVer;

  private String bate;

  private String year;

  private String month;

  private String date;

  private String carCount;

  private String recordCount;

  public String getVerType() {
    return verType;
  }

  public void setVerType(String verType) {
    this.verType = verType;
  }

  public String getnVer() {
    return nVer;
  }

  public void setnVer(String nVer) {
    this.nVer = nVer;
  }

  public String getsVer() {
    return sVer;
  }

  public void setsVer(String sVer) {
    this.sVer = sVer;
  }

  public String getBate() {
    return bate;
  }

  public void setBate(String bate) {
    this.bate = bate;
  }

  public String getYear() {
    return year;
  }

  public void setYear(String year) {
    this.year = year;
  }

  public String getMonth() {
    return month;
  }

  public void setMonth(String month) {
    this.month = month;
  }

  public String getDate() {
    return date;
  }

  public void setDate(String date) {
    this.date = date;
  }

  public String getCarCount() {
    return carCount;
  }

  public void setCarCount(String carCount) {
    this.carCount = carCount;
  }

  public String getRecordCount() {
    return recordCount;
  }

  public void setRecordCount(String recordCount) {
    this.recordCount = recordCount;
  }
}
