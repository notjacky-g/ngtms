/*    */ package com.hwacom.ngtms.frameworkcontext;
/*    */ 
/*    */ import com.hwacom.ngtms.base.hazelcast.HazelcastClient;
/*    */ import org.springframework.context.annotation.Bean;
/*    */ import org.springframework.context.annotation.Configuration;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Configuration
/*    */ public class HazelcastClientConfig
/*    */ {
/*    */   @Bean
/*    */   public HazelcastClient createHazelcastClient()
/*    */   {
/* 18 */     return new HazelcastClient();
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\base-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\frameworkcontext\HazelcastClientConfig.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */