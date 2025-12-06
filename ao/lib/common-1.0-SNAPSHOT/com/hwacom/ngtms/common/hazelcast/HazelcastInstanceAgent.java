package com.hwacom.ngtms.common.hazelcast;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;
import com.hazelcast.core.IQueue;
import com.hazelcast.core.ITopic;
import com.hwacom.ngtms.base.hazelcast.HzDistObjEnum;

public abstract interface HazelcastInstanceAgent
{
  public abstract <K, V> IMap<K, V> getMap(HzDistObjEnum paramHzDistObjEnum);
  
  public abstract <E> IQueue<E> getQueue(HzDistObjEnum paramHzDistObjEnum);
  
  public abstract <E> ITopic<E> getTopic(HzDistObjEnum paramHzDistObjEnum);
  
  public abstract HazelcastInstance getInstance();
  
  public abstract String getGroupName();
  
  public abstract String getNodeName();
}


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\common-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\common\hazelcast\HazelcastInstanceAgent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */