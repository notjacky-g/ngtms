package com.hwacom.ngtms.c.ncc.client;

import com.hwacom.ngtms.hcce.fme.controller.fm.RemoteInterface;
import com.hwacom.ngtms.ncc.remote.TcResponse;

public interface NccEnabledRemote extends RemoteInterface {
  TcResponse sendTcRequest(String paramString, Object paramObject);
  
  void sendAsyncRequest(String paramString, Object paramObject, TcResponseCallback paramTcResponseCallback);
  
  void sendAsyncRequest(String paramString1, Object paramObject, TcResponseCallback paramTcResponseCallback, String paramString2);
  
  void sendAsyncRequest(String paramString1, Object paramObject, TcResponseCallback paramTcResponseCallback, String paramString2, long paramLong);
  
  void sendAsyncMultipleRequests(String paramString, Object[] paramArrayOfObject, TcResponseCallback paramTcResponseCallback);
  
  void sendAsyncMultipleRequests(String paramString1, Object[] paramArrayOfObject, TcResponseCallback paramTcResponseCallback, String paramString2);
  
  void sendAsyncMultipleRequests(String paramString1, Object[] paramArrayOfObject, TcResponseCallback paramTcResponseCallback, String paramString2, long paramLong);
  
  void sendAsyncRequest2MulipleTc(String[] paramArrayOfString, Object paramObject, TcResponseCallback paramTcResponseCallback);
  
  void sendAsyncRequest2MulipleTc(String[] paramArrayOfString, Object paramObject, TcResponseCallback paramTcResponseCallback, String paramString);
  
  void sendAsyncRequest2MulipleTc(String[] paramArrayOfString, Object paramObject, TcResponseCallback paramTcResponseCallback, String paramString, long paramLong);
  
  boolean resetTcConnection(String paramString);
}


/* Location:              C:\User\\user\Desktop\lib\commonFm-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\c\ncc\client\NccEnabledRemote.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */