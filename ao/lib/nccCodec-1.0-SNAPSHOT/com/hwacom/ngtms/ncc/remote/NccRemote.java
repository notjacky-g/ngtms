package com.hwacom.ngtms.ncc.remote;

public abstract interface NccRemote
{
  public abstract TcResponse sendTcRequest(String paramString, Object paramObject);
  
  public abstract void sendAsyncRequest(String paramString1, Object paramObject, NccCallback paramNccCallback, String paramString2);
  
  public abstract void sendAsyncMultipleRequests(String paramString1, Object[] paramArrayOfObject, NccCallback paramNccCallback, String paramString2);
  
  public abstract void sendAsyncRequest2MulipleTc(String[] paramArrayOfString, Object paramObject, NccCallback paramNccCallback, String paramString);
  
  public abstract boolean isManagedTc(String paramString);
  
  public abstract long addProbe(String paramString, TcProbeCallback paramTcProbeCallback);
  
  public abstract void removeProbe(long paramLong);
  
  public abstract boolean resetTcConnection(String paramString);
}


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\nccCodec-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ncc\remote\NccRemote.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */