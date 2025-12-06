package com.hwacom.ngtms.hcce.core.message;

import com.hwacom.ngtms.hcce.core.exception.ClusterManagerNotReadyException;
import com.hwacom.ngtms.hcce.core.exception.RegisterNodeException;

public interface CmInternal {
  void registerNode(String paramString1, String paramString2) throws RegisterNodeException, ClusterManagerNotReadyException;
}


/* Location:              C:\User\\user\Desktop\lib\hcce-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\hcce\core\message\CmInternal.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */