package com.hwacom.ngtms.c.fm.service;

import com.hwacom.ngtms.c.fm.model.AlarmSubTypeConfig;
import com.hwacom.ngtms.c.shared.AlarmMessage;

public interface AlarmEventService {
  void onAlarmOccured(AlarmMessage paramAlarmMessage, AlarmSubTypeConfig paramAlarmSubTypeConfig);
  
  void checkExpirtEventLock();
}


/* Location:              C:\User\\user\Desktop\lib\commonFm-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\c\fm\service\AlarmEventService.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */