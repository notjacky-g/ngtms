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
/*    */ 
/*    */ @Configuration
/*    */ public class NccClientConfig
/*    */ {
/*    */   @Bean
/*    */   public NccClient createNccClient() {
/* 18 */     NccClient nccClient = new NccClient();
/*    */     
/* 20 */     return nccClient;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\lib\commonFm-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\c\fm\context\NccClientConfig.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */