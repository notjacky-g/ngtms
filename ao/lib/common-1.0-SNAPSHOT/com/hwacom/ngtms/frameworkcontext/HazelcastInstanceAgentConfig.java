/*    */ package com.hwacom.ngtms.frameworkcontext;
/*    */ 
/*    */ import com.hwacom.ngtms.common.hazelcast.HazelcastInstanceAgent;
/*    */ import org.springframework.context.annotation.Bean;
/*    */ import org.springframework.context.annotation.Configuration;
/*    */ 
/*    */ @Configuration
/*    */ public class HazelcastInstanceAgentConfig
/*    */ {
/*    */   @Bean
/*    */   public HazelcastInstanceAgent hazelcastInstanceAgent()
/*    */   {
/* 13 */     return new com.hwacom.ngtms.common.hazelcast.HazelcastInstanceAgentImpl();
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\common-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\frameworkcontext\HazelcastInstanceAgentConfig.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */