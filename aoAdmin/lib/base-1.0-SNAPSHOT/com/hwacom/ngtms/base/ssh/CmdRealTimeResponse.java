package com.hwacom.ngtms.base.ssh;

public interface CmdRealTimeResponse {
  void stdOut(String paramString);
  
  void stdErr(String paramString);
  
  void onExitStatus(Integer paramInteger);
  
  void onException(String paramString);
}


/* Location:              C:\User\\user\Desktop\lib\base-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\base\ssh\CmdRealTimeResponse.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */