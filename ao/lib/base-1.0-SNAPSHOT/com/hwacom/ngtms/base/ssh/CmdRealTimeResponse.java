package com.hwacom.ngtms.base.ssh;

public abstract interface CmdRealTimeResponse
{
  public abstract void stdOut(String paramString);
  
  public abstract void stdErr(String paramString);
  
  public abstract void onExitStatus(Integer paramInteger);
  
  public abstract void onException(String paramString);
}


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\base-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\base\ssh\CmdRealTimeResponse.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */