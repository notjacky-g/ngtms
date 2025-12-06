/*    */ package com.hwacom.ngtms.frameworkcontext;
/*    */ 
/*    */ import javax.servlet.ServletContext;
/*    */ import org.springframework.context.ApplicationContext;
/*    */ import org.springframework.context.ApplicationContextAware;
/*    */ import org.springframework.context.annotation.Bean;
/*    */ import org.springframework.context.annotation.Configuration;
/*    */ import org.springframework.web.context.ServletContextAware;
/*    */ import org.springframework.web.socket.config.annotation.EnableWebSocket;
/*    */ import org.springframework.web.socket.server.standard.ServerEndpointExporter;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Configuration
/*    */ @EnableWebSocket
/*    */ public class WebSocketConfig
/*    */   implements ApplicationContextAware
/*    */ {
/*    */   private ApplicationContext applicationContext;
/*    */   
/*    */   public void setApplicationContext(ApplicationContext applicationContext) {
/* 25 */     this.applicationContext = applicationContext;
/*    */   }
/*    */   
/*    */   public ApplicationContext getApplicationContext() {
/* 29 */     return this.applicationContext;
/*    */   }
/*    */ 
/*    */   
/*    */   @Bean
/*    */   public ServletContextAware endpointExporterInitializer(final ApplicationContext applicationContext) {
/* 35 */     return new ServletContextAware()
/*    */       {
/*    */         public void setServletContext(ServletContext servletContext) {
/* 38 */           ServerEndpointExporter serverEndpointExporter = new ServerEndpointExporter();
/* 39 */           serverEndpointExporter.setApplicationContext(applicationContext);
/*    */           try {
/* 41 */             serverEndpointExporter.afterPropertiesSet();
/* 42 */           } catch (Exception e) {
/* 43 */             throw new RuntimeException(e);
/*    */           } 
/*    */         }
/*    */       };
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Bean
/*    */   public ServerEndpointExporter serverEndpointExporter() {
/* 58 */     ServerEndpointExporter serverEndpointExporter = new ServerEndpointExporter();
/* 59 */     return serverEndpointExporter;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Bean
/*    */   WebSocketBeanProvider createApplicationContextProvider() {
/* 69 */     return new WebSocketBeanProvider();
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\lib\base-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\frameworkcontext\WebSocketConfig.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */