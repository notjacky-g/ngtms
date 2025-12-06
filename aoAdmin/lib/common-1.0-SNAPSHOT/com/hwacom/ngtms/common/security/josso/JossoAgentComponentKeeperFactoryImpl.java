/*    */ package com.hwacom.ngtms.common.security.josso;
/*    */ 
/*    */ import org.josso.agent.config.ComponentKeeper;
/*    */ import org.josso.agent.config.ComponentKeeperFactory;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class JossoAgentComponentKeeperFactoryImpl
/*    */   extends ComponentKeeperFactory
/*    */ {
/*    */   public ComponentKeeper newComponentKeeper() {
/* 16 */     return new JossoAgentComponentKeeperImpl(getResourceFileName());
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\lib\common-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\common\security\josso\JossoAgentComponentKeeperFactoryImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */