package com.hwacom.ngtms.alarm.fm.service;

import com.hwacom.ngtms.alarm.fm.model.AlarmLog;
import com.hwacom.ngtms.alarm.fm.shared.AlarmMessage;
import java.util.List;

public abstract interface AlarmPostProcessor
{
  public abstract void process(List<AlarmLog> paramList);
  
  public abstract void notify(List<AlarmMessage> paramList);
}


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\alarm-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\alarm\fm\service\AlarmPostProcessor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */