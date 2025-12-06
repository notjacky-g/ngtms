package com.hwacom.ngtms.c.fm.service;

import com.hwacom.ngtms.c.fm.model.DeviceType;

public abstract interface DeviceTypeService
{
  public abstract DeviceType getAvi();
  
  public abstract DeviceType getCctv();
  
  public abstract DeviceType getCctvDetect();
  
  public abstract DeviceType getCctvIid();
  
  public abstract DeviceType getCms();
  
  public abstract DeviceType getCmsRst();
  
  public abstract DeviceType getCsls();
  
  public abstract DeviceType getEt();
  
  public abstract DeviceType getEtc();
  
  public abstract DeviceType getEtag();
  
  public abstract DeviceType getIid();
  
  public abstract DeviceType getPd();
  
  public abstract DeviceType getRd();
  
  public abstract DeviceType getRgs();
  
  public abstract DeviceType getRms();
  
  public abstract DeviceType getScm();
  
  public abstract DeviceType getScs();
  
  public abstract DeviceType getSig();
  
  public abstract DeviceType getTem();
  
  public abstract DeviceType getTts();
  
  public abstract DeviceType getVd();
  
  public abstract DeviceType getVi();
  
  public abstract DeviceType getWd();
  
  public abstract DeviceType getWis();
  
  public abstract DeviceType getDeviceType(String paramString);
}


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\commonFm-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\c\fm\service\DeviceTypeService.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */