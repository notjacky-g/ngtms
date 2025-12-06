package com.hwacom.ngtms.c.dis.fm.websocket;

import com.hwacom.ngtms.ncc.remote.TcResponse;

public abstract interface AmCallback
{
  public abstract String getMessageId();
  
  public abstract void logResult(String paramString, TcResponse paramTcResponse, Object... paramVarArgs);
}


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\commonDis-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\c\dis\fm\websocket\AmCallback.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */