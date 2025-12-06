/*    */ package com.hwacom.ngtms.common.hazelcast;
/*    */ 
/*    */ import com.hazelcast.core.HazelcastInstance;
/*    */ import com.hazelcast.core.IMap;
/*    */ import com.hazelcast.core.IQueue;
/*    */ import com.hazelcast.core.ITopic;
/*    */ import com.hazelcast.core.LifecycleService;
/*    */ import com.hwacom.ngtms.base.hazelcast.HazelcastClient;
/*    */ import com.hwacom.ngtms.base.hazelcast.HzDistObjEnum;
/*    */ import com.hwacom.ngtms.base.hazelcast.HzStateListener;
/*    */ import org.slf4j.Logger;
/*    */ import org.slf4j.LoggerFactory;
/*    */ import org.springframework.beans.factory.annotation.Autowired;
/*    */ import org.springframework.beans.factory.annotation.Value;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class HazelcastInstanceAgentClientImpl
/*    */   implements HazelcastInstanceAgent
/*    */ {
/* 24 */   private static final Logger logger = LoggerFactory.getLogger(HazelcastInstanceAgentClientImpl.class);
/*    */   
/*    */   @Autowired
/*    */   private HazelcastClient hzClient;
/*    */   @Value("${hcce.clientNode.gorup:hc_client_group}")
/*    */   private String groupName;
/*    */   @Value("${hcce.clientNode.name:client_node}")
/*    */   private String nodeName;
/*    */   
/*    */   public void addHzStateListener(HzStateListener hzStateListener)
/*    */   {
/* 35 */     this.hzClient.addHzStateListener(hzStateListener);
/*    */   }
/*    */   
/*    */   public boolean isHzClientRunning() {
/* 39 */     HazelcastInstance hazelcastInstance = this.hzClient.getHzInstance();
/* 40 */     if (hazelcastInstance != null) {
/* 41 */       return hazelcastInstance.getLifecycleService().isRunning();
/*    */     }
/* 43 */     return false;
/*    */   }
/*    */   
/*    */   public HazelcastInstance getInstance() {
/* 47 */     return this.hzClient.getHzInstance();
/*    */   }
/*    */   
/*    */   public <K, V> IMap<K, V> getMap(HzDistObjEnum hzMap) {
/*    */     try {
/* 52 */       return this.hzClient.getIMap(hzMap);
/*    */     } catch (Exception e) {
/* 54 */       logger.warn("Can not get map. hzMap: '{}', hzRunning: '{}'", new Object[] { hzMap, 
/* 55 */         Boolean.valueOf(this.hzClient.isHzRunning()), e });
/* 56 */       throw e;
/*    */     }
/*    */   }
/*    */   
/*    */   public <E> IQueue<E> getQueue(HzDistObjEnum hzQueue) {
/* 61 */     return this.hzClient.getIQueue(hzQueue);
/*    */   }
/*    */   
/*    */   public <E> ITopic<E> getTopic(HzDistObjEnum hzTopic) {
/* 65 */     return this.hzClient.getITopic(hzTopic);
/*    */   }
/*    */   
/*    */   public String getGroupName()
/*    */   {
/* 70 */     return this.groupName;
/*    */   }
/*    */   
/*    */   public String getNodeName()
/*    */   {
/* 75 */     return this.nodeName;
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\common-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\common\hazelcast\HazelcastInstanceAgentClientImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */