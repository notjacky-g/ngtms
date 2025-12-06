package com.hwacom.ngtms.c.dis.fm.websocket;

import com.hwacom.ngtms.ncc.remote.TcResponse;

public interface AmCallback {
  String getMessageId();
  
  void logResult(String paramString, TcResponse paramTcResponse, Object... paramVarArgs);
}


/* Location:              C:\User\\user\Desktop\lib\commonDis-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\c\dis\fm\websocket\AmCallback.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */