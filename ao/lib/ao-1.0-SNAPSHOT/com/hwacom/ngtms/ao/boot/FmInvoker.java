/*    */ package com.hwacom.ngtms.ao.boot;
/*    */ 
/*    */ import com.hwacom.ngtms.frameworkcontext.SingleDataSourceHcceAutoConfig;
/*    */ import com.hwacom.ngtms.frameworkcontext.SwaggerConfig;
/*    */ import com.hwacom.ngtms.frameworkcontext.WebConfig;
/*    */ import com.hwacom.ngtms.frameworkcontext.WebSecurityConfigPermitAll;
/*    */ import com.hwacom.ngtms.hcce.core.HcceBootService;
/*    */ import javax.annotation.PostConstruct;
/*    */ import javax.annotation.PreDestroy;
/*    */ import org.activiti.spring.boot.DataSourceProcessEngineAutoConfiguration;
/*    */ import org.activiti.spring.boot.EndpointAutoConfiguration;
/*    */ import org.activiti.spring.boot.JpaProcessEngineAutoConfiguration;
/*    */ import org.activiti.spring.boot.SecurityAutoConfiguration;
/*    */ import org.slf4j.Logger;
/*    */ import org.slf4j.LoggerFactory;
/*    */ import org.springframework.beans.factory.annotation.Autowired;
/*    */ import org.springframework.boot.SpringApplication;
/*    */ import org.springframework.boot.autoconfigure.SpringBootApplication;
/*    */ import org.springframework.boot.autoconfigure.hazelcast.HazelcastAutoConfiguration;
/*    */ import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
/*    */ import org.springframework.boot.autoconfigure.quartz.QuartzAutoConfiguration;
/*    */ import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;
/*    */ import org.springframework.context.annotation.Import;
/*    */ import org.springframework.context.annotation.PropertySource;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @SpringBootApplication(exclude = {HazelcastAutoConfiguration.class, DataSourceAutoConfiguration.class, ErrorMvcAutoConfiguration.class, QuartzAutoConfiguration.class, SecurityAutoConfiguration.class, JpaProcessEngineAutoConfiguration.class, DataSourceProcessEngineAutoConfiguration.class, EndpointAutoConfiguration.class})
/*    */ @Import({SingleDataSourceHcceAutoConfig.class, WebSecurityConfigPermitAll.class, WebConfig.class, SwaggerConfig.class})
/*    */ @PropertySource({"classpath:boot/application.properties"})
/*    */ public class FmInvoker
/*    */ {
/*    */   @Autowired
/*    */   HcceBootService hcNodeInvoker;
/* 47 */   private static Logger logger = LoggerFactory.getLogger(FmInvoker.class);
/*    */   
/*    */   public static void main(String[] args) throws Exception {
/* 50 */     SpringApplication.run(FmInvoker.class, args);
/*    */   }
/*    */   
/* 53 */   private int postConstructCount = 0;
/*    */   
/*    */   @PostConstruct
/*    */   public void postConstruct() {
/* 57 */     if (this.postConstructCount == 0) {
/* 58 */       logger.info("start hc node QQ");
/* 59 */       this.postConstructCount++;
/* 60 */       (new Thread(() -> {
/*    */             sleep100();
/*    */             
/*    */             try {
/*    */               this.hcNodeInvoker.run();
/* 65 */             } catch (InterruptedException e) {
/*    */               
/*    */               logger.error("postInterruptedException", e);
/*    */             } 
/* 69 */           })).start();
/*    */     } else {
/* 71 */       logger.info("PostConstruct executed two times is so weird");
/*    */     } 
/*    */   }
/*    */   
/*    */   private void sleep100() {
/*    */     try {
/* 77 */       Thread.sleep(100L);
/* 78 */     } catch (InterruptedException e) {
/* 79 */       logger.error("sleep100InterruptedException", e);
/*    */     } 
/*    */   }
/*    */   
/*    */   @PreDestroy
/*    */   public void preDestroy() {
/* 85 */     this.hcNodeInvoker.stop();
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\機房門禁10.121.41.38\ngtms\ao\lib.src\ao-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ao\boot\FmInvoker.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */