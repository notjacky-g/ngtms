package com.hwacom.ngtms.hcce.core.message;

import com.hwacom.ngtms.hcce.core.NodeManager;
import com.hwacom.ngtms.hcce.core.exception.FmeOperationException;
import com.hwacom.ngtms.hcce.core.exception.NodeManagerNotReadyException;

public interface NmInternal {
  void stopNode();
  
  void startNode();
  
  void restartNode();
  
  void addFme(String paramString1, String paramString2, String paramString3) throws FmeOperationException, NodeManagerNotReadyException;
  
  void startFme(String paramString) throws NodeManagerNotReadyException, FmeOperationException;
  
  void removeFme(String paramString) throws NodeManagerNotReadyException;
  
  NodeManager.NmState getNmState();
}


/* Location:              C:\User\\user\Desktop\lib\hcce-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\hcce\core\message\NmInternal.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */