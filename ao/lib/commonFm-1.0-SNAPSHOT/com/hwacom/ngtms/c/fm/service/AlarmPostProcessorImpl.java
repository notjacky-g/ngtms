package com.hwacom.ngtms.c.fm.service;

import com.hwacom.ngtms.alarm.fm.model.AlarmLog;
import com.hwacom.ngtms.alarm.fm.service.AlarmPostProcessor;
import com.hwacom.ngtms.alarm.fm.shared.AlarmMessage;
import java.util.List;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile({"dev"})
public class AlarmPostProcessorImpl
  implements AlarmPostProcessor
{
  public void notify(List<AlarmMessage> alarmLogs) {}
  
  public void process(List<AlarmLog> alarmLogs) {}
}


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\commonFm-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\c\fm\service\AlarmPostProcessorImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */