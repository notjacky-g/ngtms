/*    */ package com.hwacom.ngtms.frameworkcontext;
/*    */ 
/*    */ import com.hwacom.ngtms.common.hazelcast.HazelcastInstanceAgent;
/*    */ import com.hwacom.ngtms.common.hazelcast.HazelcastInstanceAgentImpl;
/*    */ import org.springframework.context.annotation.Bean;
/*    */ import org.springframework.context.annotation.Configuration;
/*    */ 
/*    */ @Configuration
/*    */ public class HazelcastInstanceAgentConfig
/*    */ {
/*    */   @Bean
/*    */   public HazelcastInstanceAgent hazelcastInstanceAgent() {
/* 13 */     return (HazelcastInstanceAgent)new HazelcastInstanceAgentImpl();
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\lib\common-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\frameworkcontext\HazelcastInstanceAgentConfig.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */