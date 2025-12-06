package com.hwacom.ngtms.c.fm.service;

import com.hwacom.ngtms.ncc.remote.TcResponse;
import java.util.Calendar;
import java.util.List;
import java.util.function.BiConsumer;

public interface CommonCt3TcService {
  void setCt3ResetDevice(List<String> paramList, BiConsumer<String, TcResponse> paramBiConsumer);
  
  void queryCt3EquipmentNumber(List<String> paramList, int paramInt, BiConsumer<String, TcResponse> paramBiConsumer);
  
  void setCt3CommRestart(List<String> paramList, BiConsumer<String, TcResponse> paramBiConsumer);
  
  void queryCt3CommRestartAndTest(List<String> paramList, BiConsumer<String, TcResponse> paramBiConsumer);
  
  void setCt3Time(List<String> paramList, Calendar paramCalendar, BiConsumer<String, TcResponse> paramBiConsumer);
  
  void queryCt3Time(List<String> paramList, BiConsumer<String, TcResponse> paramBiConsumer);
  
  void setCt3CommandSet(List<String> paramList, int paramInt, BiConsumer<String, TcResponse> paramBiConsumer);
  
  void queryCt3Ver(List<String> paramList, BiConsumer<String, TcResponse> paramBiConsumer);
  
  void setCt3ReportHwStatusCycle(List<String> paramList, int paramInt, BiConsumer<String, TcResponse> paramBiConsumer);
  
  void queryCt3ReportHwStatusCycle(List<String> paramList, BiConsumer<String, TcResponse> paramBiConsumer);
  
  void setCt3DbPassword(List<String> paramList, byte[] paramArrayOfbyte, BiConsumer<String, TcResponse> paramBiConsumer);
  
  void queryCt3DbPassword(List<String> paramList, BiConsumer<String, TcResponse> paramBiConsumer);
  
  void setCt3LockDb(List<String> paramList, int paramInt, BiConsumer<String, TcResponse> paramBiConsumer);
  
  void queryCt3LockDb(List<String> paramList, BiConsumer<String, TcResponse> paramBiConsumer);
}


/* Location:              C:\User\\user\Desktop\lib\commonFm-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\c\fm\service\CommonCt3TcService.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */