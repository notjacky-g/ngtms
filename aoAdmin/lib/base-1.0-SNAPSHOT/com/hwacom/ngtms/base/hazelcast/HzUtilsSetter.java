/*    */ package com.hwacom.ngtms.base.hazelcast;
/*    */ 
/*    */ import com.hazelcast.core.HazelcastInstance;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class HzUtilsSetter
/*    */   extends HzUtils
/*    */ {
/*    */   public static void setHzInstance(HazelcastInstance hzInstance) {
/* 18 */     HzUtils.setHzInstance(hzInstance);
/*    */   }
/*    */   
/*    */   public static void setInstanceInfo(String instanceName, String groupName) {
/* 22 */     HzUtils.setInstanceInfo(instanceName, groupName);
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\lib\base-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\base\hazelcast\HzUtilsSetter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */