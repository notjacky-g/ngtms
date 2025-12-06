/*    */ package com.hwacom.ngtms.c.fm.context;
/*    */ 
/*    */ import com.hwacom.ngtms.c.ncc.client.NccClient;
/*    */ import org.springframework.context.annotation.Bean;
/*    */ import org.springframework.context.annotation.Configuration;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Configuration
/*    */ public class NccClientConfig
/*    */ {
/*    */   @Bean
/*    */   public NccClient createNccClient()
/*    */   {
/* 18 */     NccClient nccClient = new NccClient();
/*    */     
/* 20 */     return nccClient;
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\commonFm-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\c\fm\context\NccClientConfig.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */