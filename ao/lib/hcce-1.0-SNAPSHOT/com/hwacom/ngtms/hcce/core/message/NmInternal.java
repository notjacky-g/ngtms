package com.hwacom.ngtms.hcce.core.message;

import com.hwacom.ngtms.hcce.core.NodeManager.NmState;
import com.hwacom.ngtms.hcce.core.exception.FmeOperationException;
import com.hwacom.ngtms.hcce.core.exception.NodeManagerNotReadyException;

public abstract interface NmInternal
{
  public abstract void stopNode();
  
  public abstract void startNode();
  
  public abstract void restartNode();
  
  public abstract void addFme(String paramString1, String paramString2, String paramString3)
    throws FmeOperationException, NodeManagerNotReadyException;
  
  public abstract void startFme(String paramString)
    throws NodeManagerNotReadyException, FmeOperationException;
  
  public abstract void removeFme(String paramString)
    throws NodeManagerNotReadyException;
  
  public abstract NodeManager.NmState getNmState();
}


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\hcce-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\hcce\core\message\NmInternal.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */