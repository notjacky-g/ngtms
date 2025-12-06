package com.hwacom.ngtms.hcce.fme.controller.fm;

import com.hwacom.ngtms.hcce.core.exception.FmException;

public interface FmInstance {
  void start() throws FmException;
  
  void stop();
  
  void testStart() throws FmException;
}


/* Location:              C:\User\\user\Desktop\lib\hcce-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\hcce\fme\controller\fm\FmInstance.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */