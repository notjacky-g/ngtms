package com.hwacom.ngtms.hcce.fme.controller.fm;

import com.hwacom.ngtms.hcce.core.exception.FmException;

public abstract interface FmInstance
{
  public abstract void start()
    throws FmException;
  
  public abstract void stop();
  
  public abstract void testStart()
    throws FmException;
}


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\hcce-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\hcce\fme\controller\fm\FmInstance.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */