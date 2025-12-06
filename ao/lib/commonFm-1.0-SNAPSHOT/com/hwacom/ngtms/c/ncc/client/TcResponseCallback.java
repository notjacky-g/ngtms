package com.hwacom.ngtms.c.ncc.client;

import com.hwacom.ngtms.ncc.remote.TcResponse;

public abstract interface TcResponseCallback
{
  public abstract void onResponse(String paramString1, String paramString2, TcResponse paramTcResponse);
}


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\commonFm-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\c\ncc\client\TcResponseCallback.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */