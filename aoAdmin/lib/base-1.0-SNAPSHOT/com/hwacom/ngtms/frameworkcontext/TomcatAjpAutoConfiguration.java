/*    */ package com.hwacom.ngtms.frameworkcontext;
/*    */ 
/*    */ import javax.servlet.Servlet;
/*    */ import javax.websocket.server.ServerContainer;
/*    */ import org.apache.catalina.connector.Connector;
/*    */ import org.apache.catalina.startup.Tomcat;
/*    */ import org.springframework.beans.factory.annotation.Value;
/*    */ import org.springframework.boot.autoconfigure.AutoConfigureBefore;
/*    */ import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
/*    */ import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
/*    */ import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
/*    */ import org.springframework.boot.autoconfigure.web.embedded.EmbeddedWebServerFactoryCustomizerAutoConfiguration;
/*    */ import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
/*    */ import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
/*    */ import org.springframework.context.annotation.Bean;
/*    */ import org.springframework.context.annotation.Configuration;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Configuration
/*    */ @ConditionalOnClass({Servlet.class, ServerContainer.class})
/*    */ @ConditionalOnWebApplication
/*    */ @AutoConfigureBefore({EmbeddedWebServerFactoryCustomizerAutoConfiguration.class})
/*    */ public class TomcatAjpAutoConfiguration
/*    */ {
/*    */   @Configuration
/*    */   @ConditionalOnClass(name = {"org.apache.tomcat.websocket.server.WsSci"}, value = {Tomcat.class})
/*    */   static class AjpConfiguration
/*    */   {
/*    */     @Value("${connector.ajp.port:8009}")
/*    */     private int connectorAjpPort;
/*    */     
/*    */     @Bean
/*    */     @ConditionalOnMissingBean(name = {"ajpConnector"})
/*    */     public ServletWebServerFactory ajpConnector() {
/* 42 */       TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory();
/* 43 */       tomcat.addAdditionalTomcatConnectors(new Connector[] { createConnector() });
/*    */       
/* 45 */       return (ServletWebServerFactory)tomcat;
/*    */     }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/*    */     private Connector createConnector() {
/* 56 */       Connector connector = new Connector("AJP/1.3");
/* 57 */       connector.setPort(this.connectorAjpPort);
/* 58 */       connector.setScheme("ajp");
/* 59 */       return connector;
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\lib\base-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\frameworkcontext\TomcatAjpAutoConfiguration.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */