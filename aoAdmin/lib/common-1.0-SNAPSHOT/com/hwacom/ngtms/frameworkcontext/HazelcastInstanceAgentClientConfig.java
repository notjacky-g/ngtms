/*    */ package com.hwacom.ngtms.frameworkcontext;
/*    */ 
/*    */ import com.hwacom.ngtms.common.hazelcast.HazelcastInstanceAgent;
/*    */ import com.hwacom.ngtms.common.hazelcast.HazelcastInstanceAgentClientImpl;
/*    */ import org.springframework.context.annotation.Bean;
/*    */ import org.springframework.context.annotation.Configuration;
/*    */ 
/*    */ 
/*    */ @Configuration
/*    */ public class HazelcastInstanceAgentClientConfig
/*    */ {
/*    */   @Bean
/*    */   public HazelcastInstanceAgent hazelcastInstanceAgent() {
/* 14 */     return (HazelcastInstanceAgent)new HazelcastInstanceAgentClientImpl();
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\lib\common-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\frameworkcontext\HazelcastInstanceAgentClientConfig.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */