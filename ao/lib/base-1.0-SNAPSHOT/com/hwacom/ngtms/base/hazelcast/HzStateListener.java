package com.hwacom.ngtms.base.hazelcast;

public abstract interface HzStateListener
{
  public abstract void hzRecreate();
  
  public abstract void hzShutdown();
  
  public abstract void hzClientConnected();
  
  public abstract void hzClientDisconnected();
}


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\base-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\base\hazelcast\HzStateListener.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */