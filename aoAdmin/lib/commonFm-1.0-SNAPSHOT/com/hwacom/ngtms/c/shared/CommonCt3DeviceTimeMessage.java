package com.hwacom.ngtms.c.shared;

import java.util.List;

public interface CommonCt3DeviceTimeMessage {

  String TYPE = "DeviceTime";

  String getType();

  void setType(String type);

  String getDeviceType();

  void setDeviceType(String deviceType);

  List<String> getDeviceNames();

  void setDeviceNames(List<String> deviceNames);

  Integer getYear();

  void setYear(Integer year);

  Integer getMonth();

  void setMonth(Integer month);

  Integer getDay();

  void setDay(Integer day);

  Integer getWeek();

  void setWeek(Integer week);

  Integer getHour();

  void setHour(Integer hour);

  Integer getMin();

  void setMin(Integer min);

  Integer getSec();

  void setSec(Integer sec);

  public class CommonCt3DeviceTimeMessageImp implements CommonCt3DeviceTimeMessage {

    private String type;

    private String deviceType;

    private List<String> deviceNames;

    private Integer year;

    private Integer month;

    private Integer day;

    private Integer week;

    private Integer hour;

    private Integer min;

    private Integer sec;

    @Override
    public String getType() {
      return type;
    }

    @Override
    public void setType(String type) {
      this.type = type;
    }

    @Override
    public String getDeviceType() {
      return deviceType;
    }

    @Override
    public void setDeviceType(String deviceType) {
      this.deviceType = deviceType;
    }

    @Override
    public List<String> getDeviceNames() {
      return deviceNames;
    }

    @Override
    public void setDeviceNames(List<String> deviceNames) {
      this.deviceNames = deviceNames;
    }

    @Override
    public Integer getYear() {
      return year;
    }

    @Override
    public void setYear(Integer year) {
      this.year = year;
    }

    @Override
    public Integer getMonth() {
      return month;
    }

    @Override
    public void setMonth(Integer month) {
      this.month = month;
    }

    @Override
    public Integer getDay() {
      return day;
    }

    @Override
    public void setDay(Integer day) {
      this.day = day;
    }

    @Override
    public Integer getWeek() {
      return week;
    }

    @Override
    public void setWeek(Integer week) {
      this.week = week;
    }

    @Override
    public Integer getHour() {
      return hour;
    }

    @Override
    public void setHour(Integer hour) {
      this.hour = hour;
    }

    @Override
    public Integer getMin() {
      return min;
    }

    @Override
    public void setMin(Integer min) {
      this.min = min;
    }

    @Override
    public Integer getSec() {
      return sec;
    }

    @Override
    public void setSec(Integer sec) {
      this.sec = sec;
    }
  }
}
