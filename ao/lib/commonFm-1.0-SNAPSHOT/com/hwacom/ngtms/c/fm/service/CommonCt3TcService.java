package com.hwacom.ngtms.c.fm.service;

import com.hwacom.ngtms.ncc.remote.TcResponse;
import java.util.Calendar;
import java.util.List;
import java.util.function.BiConsumer;

public abstract interface CommonCt3TcService
{
  public abstract void setCt3ResetDevice(List<String> paramList, BiConsumer<String, TcResponse> paramBiConsumer);
  
  public abstract void queryCt3EquipmentNumber(List<String> paramList, int paramInt, BiConsumer<String, TcResponse> paramBiConsumer);
  
  public abstract void setCt3CommRestart(List<String> paramList, BiConsumer<String, TcResponse> paramBiConsumer);
  
  public abstract void queryCt3CommRestartAndTest(List<String> paramList, BiConsumer<String, TcResponse> paramBiConsumer);
  
  public abstract void setCt3Time(List<String> paramList, Calendar paramCalendar, BiConsumer<String, TcResponse> paramBiConsumer);
  
  public abstract void queryCt3Time(List<String> paramList, BiConsumer<String, TcResponse> paramBiConsumer);
  
  public abstract void setCt3CommandSet(List<String> paramList, int paramInt, BiConsumer<String, TcResponse> paramBiConsumer);
  
  public abstract void queryCt3Ver(List<String> paramList, BiConsumer<String, TcResponse> paramBiConsumer);
  
  public abstract void setCt3ReportHwStatusCycle(List<String> paramList, int paramInt, BiConsumer<String, TcResponse> paramBiConsumer);
  
  public abstract void queryCt3ReportHwStatusCycle(List<String> paramList, BiConsumer<String, TcResponse> paramBiConsumer);
  
  public abstract void setCt3DbPassword(List<String> paramList, byte[] paramArrayOfByte, BiConsumer<String, TcResponse> paramBiConsumer);
  
  public abstract void queryCt3DbPassword(List<String> paramList, BiConsumer<String, TcResponse> paramBiConsumer);
  
  public abstract void setCt3LockDb(List<String> paramList, int paramInt, BiConsumer<String, TcResponse> paramBiConsumer);
  
  public abstract void queryCt3LockDb(List<String> paramList, BiConsumer<String, TcResponse> paramBiConsumer);
}


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\commonFm-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\c\fm\service\CommonCt3TcService.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */