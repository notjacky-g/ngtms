/*    */ package com.hwacom.ngtms.common.security.josso;
/*    */ 
/*    */ import com.hwacom.ngtms.base.util.ApplicationContextHelper;
/*    */ import java.util.Map;
/*    */ import org.josso.agent.SSOAgent;
/*    */ import org.josso.agent.config.ComponentKeeper;
/*    */ import org.josso.agent.reverseproxy.ReverseProxyConfiguration;
/*    */ import org.josso.gateway.GatewayServiceLocator;
/*    */ import org.josso.gateway.SSOException;
/*    */ import org.springframework.context.ApplicationContext;
/*    */ import org.springframework.context.support.ClassPathXmlApplicationContext;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class JossoAgentComponentKeeperImpl
/*    */   implements ComponentKeeper
/*    */ {
/*    */   private static String endpoint;
/*    */   private static String identityManagerWsdlLocation;
/*    */   private static String identityProviderWsdlLocation;
/*    */   private static String sessionManagerWsdlLocation;
/*    */   private ApplicationContext context;
/*    */   
/*    */   public JossoAgentComponentKeeperImpl(String resource) {
/* 31 */     ClassPathXmlApplicationContext appContext = new ClassPathXmlApplicationContext();
/* 32 */     appContext.setValidating(false);
/* 33 */     appContext.setConfigLocation(resource);
/* 34 */     appContext.refresh();
/* 35 */     this.context = (ApplicationContext)appContext;
/* 36 */     ApplicationContextHelper.getInstance().setAppContext((ApplicationContext)appContext);
/*    */   }
/*    */ 
/*    */   
/*    */   public SSOAgent fetchSSOAgent() throws Exception {
/* 41 */     Map agents = this.context.getBeansOfType(SSOAgent.class);
/* 42 */     if (agents.values().size() < 1)
/* 43 */       throw new SSOException("No agent defined. Verify JOSSO Configuration"); 
/* 44 */     if (agents.values().size() > 1) {
/* 45 */       throw new SSOException("Multiple agent definitions are not supported! Found : " + agents
/* 46 */           .values().size());
/*    */     }
/*    */     
/* 49 */     SSOAgent agent = agents.values().iterator().next();
/* 50 */     JossoJAXWSWebserviceGatewayServiceLocator locator = new JossoJAXWSWebserviceGatewayServiceLocator();
/*    */     
/* 52 */     locator.setEndpoint(endpoint);
/* 53 */     locator.setIdentityManagerWsdlLocation(identityManagerWsdlLocation);
/* 54 */     locator.setIdentityProviderWsdlLocation(identityProviderWsdlLocation);
/* 55 */     locator.setSessionManagerWsdlLocation(sessionManagerWsdlLocation);
/* 56 */     agent.setGatewayServiceLocator((GatewayServiceLocator)locator);
/* 57 */     return agent;
/*    */   }
/*    */ 
/*    */   
/*    */   public ReverseProxyConfiguration fetchReverseProxyConfiguration() throws Exception {
/* 62 */     return (ReverseProxyConfiguration)this.context.getBean("reverseProxyConfiguration");
/*    */   }
/*    */   
/*    */   public ApplicationContext getSpringContext() {
/* 66 */     return this.context;
/*    */   }
/*    */   
/*    */   public static void setEndpoint(String endpoint) {
/* 70 */     JossoAgentComponentKeeperImpl.endpoint = endpoint;
/*    */   }
/*    */   
/*    */   public static void setIdentityManagerWsdlLocation(String identityManagerWsdlLocation) {
/* 74 */     JossoAgentComponentKeeperImpl.identityManagerWsdlLocation = identityManagerWsdlLocation;
/*    */   }
/*    */   
/*    */   public static void setIdentityProviderWsdlLocation(String identityProviderWsdlLocation) {
/* 78 */     JossoAgentComponentKeeperImpl.identityProviderWsdlLocation = identityProviderWsdlLocation;
/*    */   }
/*    */   
/*    */   public static void setSessionManagerWsdlLocation(String sessionManagerWsdlLocation) {
/* 82 */     JossoAgentComponentKeeperImpl.sessionManagerWsdlLocation = sessionManagerWsdlLocation;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\lib\common-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\common\security\josso\JossoAgentComponentKeeperImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */