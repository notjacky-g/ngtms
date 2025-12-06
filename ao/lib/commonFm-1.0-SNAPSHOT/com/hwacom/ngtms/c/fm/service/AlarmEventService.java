package com.hwacom.ngtms.c.fm.service;

import com.hwacom.ngtms.c.fm.model.AlarmSubTypeConfig;
import com.hwacom.ngtms.c.shared.AlarmMessage;

public abstract interface AlarmEventService
{
  public abstract void onAlarmOccured(AlarmMessage paramAlarmMessage, AlarmSubTypeConfig paramAlarmSubTypeConfig);
  
  public abstract void checkExpirtEventLock();
}


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\commonFm-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\c\fm\service\AlarmEventService.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */