package com.hwacom.ngtms.hcce.fme.controller.fm;

import com.hwacom.ngtms.base.oplog.shared.OperationResult;
import com.hwacom.ngtms.hcce.config.model.DynamicConfig;
import java.util.Date;

public interface RemoteInterface {
  DynamicConfig getDynaConfig(String paramString);
  
  void setDynaConfig(String paramString1, String paramString2);
  
  boolean isInPrimaryGroup();
  
  void addOpLog(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, Date paramDate, OperationResult paramOperationResult, String paramString6);
  
  void addOpLog(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, Date paramDate, OperationResult paramOperationResult, String paramString7);
}


/* Location:              C:\User\\user\Desktop\lib\hcce-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\hcce\fme\controller\fm\RemoteInterface.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */