/*    */ package com.hwacom.ngtms.common.hazelcast;
/*    */ 
/*    */ import com.hazelcast.core.HazelcastInstance;
/*    */ import com.hazelcast.core.IMap;
/*    */ import com.hazelcast.core.IQueue;
/*    */ import com.hazelcast.core.ITopic;
/*    */ import com.hwacom.ngtms.base.hazelcast.HzDistObjEnum;
/*    */ import com.hwacom.ngtms.base.hazelcast.HzUtils;
/*    */ import com.hwacom.ngtms.hcce.shared.HcceEnv;
/*    */ import org.springframework.beans.factory.annotation.Autowired;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class HazelcastInstanceAgentImpl
/*    */   implements HazelcastInstanceAgent
/*    */ {
/*    */   @Autowired
/*    */   private HcceEnv hcceEnv;
/*    */   
/*    */   public <K, V> IMap<K, V> getMap(HzDistObjEnum hzMap)
/*    */   {
/* 23 */     return HzUtils.getMap(hzMap);
/*    */   }
/*    */   
/*    */   public <E> IQueue<E> getQueue(HzDistObjEnum hzQueue) {
/* 27 */     return HzUtils.getQueue(hzQueue);
/*    */   }
/*    */   
/*    */   public <E> ITopic<E> getTopic(HzDistObjEnum hzTopic) {
/* 31 */     return HzUtils.getTopic(hzTopic);
/*    */   }
/*    */   
/*    */   public HazelcastInstance getInstance() {
/* 35 */     return HzUtils.getHzInstance();
/*    */   }
/*    */   
/*    */   public String getGroupName()
/*    */   {
/* 40 */     return this.hcceEnv.getCurrentGroupName();
/*    */   }
/*    */   
/*    */   public String getNodeName()
/*    */   {
/* 45 */     return this.hcceEnv.getNodeName();
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\common-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\common\hazelcast\HazelcastInstanceAgentImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */