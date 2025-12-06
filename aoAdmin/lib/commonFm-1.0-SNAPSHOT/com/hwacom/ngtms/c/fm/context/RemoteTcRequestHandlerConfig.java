/*    */ package com.hwacom.ngtms.c.fm.context;
/*    */ 
/*    */ import com.hwacom.ngtms.c.ncc.client.RemoteTcRequestHandler;
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
/*    */ public class RemoteTcRequestHandlerConfig
/*    */ {
/*    */   @Bean
/*    */   public RemoteTcRequestHandler createRemoteTcRequestHandler() {
/* 18 */     RemoteTcRequestHandler remoteTcRequestHandler = new RemoteTcRequestHandler();
/*    */     
/* 20 */     return remoteTcRequestHandler;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\lib\commonFm-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\c\fm\context\RemoteTcRequestHandlerConfig.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */