package com.hwacom.ngtms.base.oplog.service;

import com.hwacom.ngtms.base.oplog.model.OperationLog;
import com.hwacom.ngtms.base.oplog.shared.OperationResult;
import java.util.Date;
import java.util.List;

public interface OperationLogService {
  void addOpLog(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, Date paramDate, OperationResult paramOperationResult, String paramString7);
  
  void addSchOpLog(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, Date paramDate, OperationResult paramOperationResult, String paramString6);
  
  List<OperationLog> findByOperationTimeBetween(Date paramDate1, Date paramDate2);
  
  List<OperationLog> findByUserIdAndOperationTimeBetween(String paramString, Date paramDate1, Date paramDate2);
  
  List<OperationLog> findBySchIdAndOperationTimeBetween(String paramString, Date paramDate1, Date paramDate2);
  
  OperationLog getOperationLogById(Long paramLong);
}


/* Location:              C:\User\\user\Desktop\lib\base-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\base\oplog\service\OperationLogService.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */