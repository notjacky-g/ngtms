/*     */ package com.hwacom.ngtms.frameworkcontext;
/*     */ 
/*     */ import javax.annotation.Resource;
/*     */ import javax.sql.DataSource;
/*     */ import org.apache.catalina.Container;
/*     */ import org.apache.catalina.Context;
/*     */ import org.apache.catalina.connector.Connector;
/*     */ import org.apache.catalina.startup.Tomcat;
/*     */ import org.apache.tomcat.util.descriptor.web.ContextResource;
/*     */ import org.springframework.beans.factory.annotation.Value;
/*     */ import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
/*     */ import org.springframework.boot.web.embedded.tomcat.TomcatWebServer;
/*     */ import org.springframework.context.annotation.Bean;
/*     */ import org.springframework.context.annotation.Configuration;
/*     */ import org.springframework.core.env.Environment;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Configuration
/*     */ public class TomcatAjpConfigWithJndi
/*     */ {
/*     */   @Value("${connector.ajp.port :8009}")
/*     */   private int connectorAjpPort;
/*     */   @Value("${spring.datasource.jndi-name:jdbc/DefaultDS}")
/*     */   private String jndiName;
/*     */   @Resource
/*     */   private Environment environment;
/*     */   
/*     */   @Bean
/*     */   public TomcatServletWebServerFactory tomcatFactory() {
/*  36 */     TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory()
/*     */       {
/*     */         
/*     */         protected void postProcessContext(Context context)
/*     */         {
/*  41 */           String driverClassName = TomcatAjpConfigWithJndi.this.environment.getRequiredProperty("olds.driverClassName");
/*  42 */           String url = TomcatAjpConfigWithJndi.this.environment.getRequiredProperty("olds.url");
/*  43 */           String username = TomcatAjpConfigWithJndi.this.environment.getRequiredProperty("olds.username");
/*  44 */           String password = TomcatAjpConfigWithJndi.this.environment.getRequiredProperty("olds.password");
/*     */           
/*  46 */           ContextResource resource = new ContextResource();
/*  47 */           resource.setName(TomcatAjpConfigWithJndi.this.jndiName);
/*  48 */           resource.setAuth("Container");
/*  49 */           resource.setType(DataSource.class.getName());
/*  50 */           resource.setScope("Sharable");
/*  51 */           resource.setProperty("driverClassName", driverClassName);
/*  52 */           resource.setProperty("url", url);
/*  53 */           resource.setProperty("username", username);
/*  54 */           resource.setProperty("password", password);
/*     */           
/*  56 */           context.getNamingResources().addResource(resource);
/*     */         }
/*     */ 
/*     */         
/*     */         protected TomcatWebServer getTomcatWebServer(Tomcat tomcat) {
/*  61 */           tomcat.enableNaming();
/*  62 */           TomcatWebServer webServer = super.getTomcatWebServer(tomcat);
/*  63 */           for (Container child : webServer.getTomcat().getHost().findChildren()) {
/*  64 */             if (child instanceof Context) {
/*  65 */               ClassLoader contextClassLoader = ((Context)child).getLoader().getClassLoader();
/*  66 */               Thread.currentThread().setContextClassLoader(contextClassLoader);
/*     */               break;
/*     */             } 
/*     */           } 
/*  70 */           return webServer;
/*     */         }
/*     */       };
/*  73 */     tomcat.addAdditionalTomcatConnectors(new Connector[] { createAjpConnector() });
/*     */     
/*  75 */     return tomcat;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Connector createAjpConnector() {
/*  98 */     Connector connector = new Connector("AJP/1.3");
/*     */     
/* 100 */     connector.setPort(this.connectorAjpPort);
/* 101 */     connector.setSecure(false);
/* 102 */     connector.setAllowTrace(false);
/* 103 */     connector.setScheme("ajp");
/* 104 */     return connector;
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\lib\base-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\frameworkcontext\TomcatAjpConfigWithJndi.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */