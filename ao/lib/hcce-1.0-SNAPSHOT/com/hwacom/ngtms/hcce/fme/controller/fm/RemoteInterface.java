package com.hwacom.ngtms.hcce.fme.controller.fm;

import com.hwacom.ngtms.base.oplog.shared.OperationResult;
import com.hwacom.ngtms.hcce.config.model.DynamicConfig;
import java.util.Date;

public abstract interface RemoteInterface
{
  public abstract DynamicConfig getDynaConfig(String paramString);
  
  public abstract void setDynaConfig(String paramString1, String paramString2);
  
  public abstract boolean isInPrimaryGroup();
  
  public abstract void addOpLog(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, Date paramDate, OperationResult paramOperationResult, String paramString6);
  
  public abstract void addOpLog(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, Date paramDate, OperationResult paramOperationResult, String paramString7);
}


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\hcce-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\hcce\fme\controller\fm\RemoteInterface.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */