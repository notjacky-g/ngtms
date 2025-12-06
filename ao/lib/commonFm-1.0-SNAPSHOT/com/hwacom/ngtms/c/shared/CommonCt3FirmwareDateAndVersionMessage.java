package com.hwacom.ngtms.c.shared;

import java.util.List;

public interface CommonCt3FirmwareDateAndVersionMessage {

  String TYPE = "FirmwareDateAndVersion";

  String getType();

  void setType(String type);

  String getDeviceType();

  void setDeviceType(String deviceType);

  List<String> getDeviceNames();

  void setDeviceNames(List<String> deviceNames);

  Integer getCommandSet();

  void setCommandSet(Integer commandSet);

  Integer getYear();

  void setYear(Integer year);

  Integer getMonth();

  void setMonth(Integer month);

  Integer getDay();

  void setDay(Integer day);

  Integer getCompanyId();

  void setCompanyId(Integer companyId);

  Integer getVersion();

  void setVersion(Integer version);

  public class CommonCt3FirmwareDateAndVersionMessageImp
      implements CommonCt3FirmwareDateAndVersionMessage {

    private String type;

    String deviceType;

    List<String> deviceNames;

    Integer commandSet;

    Integer year;

    Integer month;

    Integer day;

    Integer companyId;

    Integer version;

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
    public Integer getCommandSet() {
      return commandSet;
    }

    @Override
    public void setCommandSet(Integer commandSet) {
      this.commandSet = commandSet;
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
    public Integer getCompanyId() {
      return companyId;
    }

    @Override
    public void setCompanyId(Integer companyId) {
      this.companyId = companyId;
    }

    @Override
    public Integer getVersion() {
      return version;
    }

    @Override
    public void setVersion(Integer version) {
      this.version = version;
    }
  }
}
