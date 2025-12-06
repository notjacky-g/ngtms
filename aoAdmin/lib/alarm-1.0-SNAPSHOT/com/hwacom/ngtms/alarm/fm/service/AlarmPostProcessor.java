package com.hwacom.ngtms.alarm.fm.service;

import com.hwacom.ngtms.alarm.fm.model.AlarmLog;
import com.hwacom.ngtms.alarm.fm.shared.AlarmMessage;
import java.util.List;

public interface AlarmPostProcessor {
  void process(List<AlarmLog> paramList);
  
  void notify(List<AlarmMessage> paramList);
}


/* Location:              C:\User\\user\Desktop\lib\alarm-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\alarm\fm\service\AlarmPostProcessor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */