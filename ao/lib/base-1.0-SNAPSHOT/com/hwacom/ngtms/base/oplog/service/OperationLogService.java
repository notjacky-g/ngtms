package com.hwacom.ngtms.base.oplog.service;

import com.hwacom.ngtms.base.oplog.model.OperationLog;
import com.hwacom.ngtms.base.oplog.shared.OperationResult;
import java.util.Date;
import java.util.List;

public abstract interface OperationLogService
{
  public abstract void addOpLog(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, Date paramDate, OperationResult paramOperationResult, String paramString7);
  
  public abstract void addSchOpLog(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, Date paramDate, OperationResult paramOperationResult, String paramString6);
  
  public abstract List<OperationLog> findByOperationTimeBetween(Date paramDate1, Date paramDate2);
  
  public abstract List<OperationLog> findByUserIdAndOperationTimeBetween(String paramString, Date paramDate1, Date paramDate2);
  
  public abstract List<OperationLog> findBySchIdAndOperationTimeBetween(String paramString, Date paramDate1, Date paramDate2);
  
  public abstract OperationLog getOperationLogById(Long paramLong);
}


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\base-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\base\oplog\service\OperationLogService.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */