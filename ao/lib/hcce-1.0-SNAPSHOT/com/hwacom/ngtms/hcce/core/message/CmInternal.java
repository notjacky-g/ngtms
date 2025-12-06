package com.hwacom.ngtms.hcce.core.message;

import com.hwacom.ngtms.hcce.core.exception.ClusterManagerNotReadyException;
import com.hwacom.ngtms.hcce.core.exception.RegisterNodeException;

public abstract interface CmInternal
{
  public abstract void registerNode(String paramString1, String paramString2)
    throws RegisterNodeException, ClusterManagerNotReadyException;
}


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\hcce-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\hcce\core\message\CmInternal.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */