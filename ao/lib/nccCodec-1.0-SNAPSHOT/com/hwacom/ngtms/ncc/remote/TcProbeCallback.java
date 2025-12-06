package com.hwacom.ngtms.ncc.remote;

public abstract interface TcProbeCallback
{
  public abstract void onMessage(String paramString, long paramLong, boolean paramBoolean, TcMessage paramTcMessage);
  
  public abstract void onError(String paramString1, long paramLong, boolean paramBoolean, TcMessage paramTcMessage, String paramString2);
  
  public abstract void onStatusChanged(String paramString, long paramLong, TcConnectionStatus paramTcConnectionStatus);
}


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\nccCodec-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ncc\remote\TcProbeCallback.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */