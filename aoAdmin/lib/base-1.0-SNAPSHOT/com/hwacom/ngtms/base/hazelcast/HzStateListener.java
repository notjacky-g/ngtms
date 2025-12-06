package com.hwacom.ngtms.base.hazelcast;

public interface HzStateListener {
  void hzRecreate();
  
  void hzShutdown();
  
  void hzClientConnected();
  
  void hzClientDisconnected();
}


/* Location:              C:\User\\user\Desktop\lib\base-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\base\hazelcast\HzStateListener.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */