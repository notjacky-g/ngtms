package com.hwacom.ngtms.common.hazelcast;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;
import com.hazelcast.core.IQueue;
import com.hazelcast.core.ITopic;
import com.hwacom.ngtms.base.hazelcast.HzDistObjEnum;

public interface HazelcastInstanceAgent {
  <K, V> IMap<K, V> getMap(HzDistObjEnum paramHzDistObjEnum);
  
  <E> IQueue<E> getQueue(HzDistObjEnum paramHzDistObjEnum);
  
  <E> ITopic<E> getTopic(HzDistObjEnum paramHzDistObjEnum);
  
  HazelcastInstance getInstance();
  
  String getGroupName();
  
  String getNodeName();
}


/* Location:              C:\User\\user\Desktop\lib\common-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\common\hazelcast\HazelcastInstanceAgent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */