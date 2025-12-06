package com.hwacom.ngtms.c.ncc.client;

import com.hwacom.ngtms.hcce.fme.controller.fm.RemoteInterface;
import com.hwacom.ngtms.ncc.remote.TcResponse;

public abstract interface NccEnabledRemote
  extends RemoteInterface
{
  public abstract TcResponse sendTcRequest(String paramString, Object paramObject);
  
  public abstract void sendAsyncRequest(String paramString, Object paramObject, TcResponseCallback paramTcResponseCallback);
  
  public abstract void sendAsyncRequest(String paramString1, Object paramObject, TcResponseCallback paramTcResponseCallback, String paramString2);
  
  public abstract void sendAsyncRequest(String paramString1, Object paramObject, TcResponseCallback paramTcResponseCallback, String paramString2, long paramLong);
  
  public abstract void sendAsyncMultipleRequests(String paramString, Object[] paramArrayOfObject, TcResponseCallback paramTcResponseCallback);
  
  public abstract void sendAsyncMultipleRequests(String paramString1, Object[] paramArrayOfObject, TcResponseCallback paramTcResponseCallback, String paramString2);
  
  public abstract void sendAsyncMultipleRequests(String paramString1, Object[] paramArrayOfObject, TcResponseCallback paramTcResponseCallback, String paramString2, long paramLong);
  
  public abstract void sendAsyncRequest2MulipleTc(String[] paramArrayOfString, Object paramObject, TcResponseCallback paramTcResponseCallback);
  
  public abstract void sendAsyncRequest2MulipleTc(String[] paramArrayOfString, Object paramObject, TcResponseCallback paramTcResponseCallback, String paramString);
  
  public abstract void sendAsyncRequest2MulipleTc(String[] paramArrayOfString, Object paramObject, TcResponseCallback paramTcResponseCallback, String paramString, long paramLong);
  
  public abstract boolean resetTcConnection(String paramString);
}


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\commonFm-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\c\ncc\client\NccEnabledRemote.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */