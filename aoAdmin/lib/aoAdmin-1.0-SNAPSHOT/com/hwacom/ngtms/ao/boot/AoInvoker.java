/*    */ package com.hwacom.ngtms.ao.boot;
/*    */ 
/*    */ import com.hwacom.ngtms.frameworkcontext.HazelcastClientConfig;
/*    */ import com.hwacom.ngtms.frameworkcontext.HcceClientConfig;
/*    */ import com.hwacom.ngtms.frameworkcontext.WebConfig;
/*    */ import com.hwacom.ngtms.frameworkcontext.WebSecurityConfigPermitAll;
/*    */ import org.activiti.spring.boot.DataSourceProcessEngineAutoConfiguration;
/*    */ import org.activiti.spring.boot.EndpointAutoConfiguration;
/*    */ import org.activiti.spring.boot.JpaProcessEngineAutoConfiguration;
/*    */ import org.activiti.spring.boot.SecurityAutoConfiguration;
/*    */ import org.springframework.boot.SpringApplication;
/*    */ import org.springframework.boot.autoconfigure.SpringBootApplication;
/*    */ import org.springframework.boot.autoconfigure.hazelcast.HazelcastAutoConfiguration;
/*    */ import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
/*    */ import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
/*    */ import org.springframework.boot.autoconfigure.quartz.QuartzAutoConfiguration;
/*    */ import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
/*    */ import org.springframework.boot.web.servlet.ServletComponentScan;
/*    */ import org.springframework.context.annotation.ComponentScan;
/*    */ import org.springframework.context.annotation.Import;
/*    */ import org.springframework.context.annotation.PropertySource;
/*    */ import org.springframework.scheduling.annotation.EnableScheduling;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ @SpringBootApplication(exclude = {HazelcastAutoConfiguration.class, DataSourceAutoConfiguration.class, HibernateJpaAutoConfiguration.class, SecurityAutoConfiguration.class, SecurityAutoConfiguration.class, JpaProcessEngineAutoConfiguration.class, EndpointAutoConfiguration.class, DataSourceProcessEngineAutoConfiguration.class, QuartzAutoConfiguration.class})
/*    */ @Import({HcceClientConfig.class, HazelcastClientConfig.class, WebSecurityConfigPermitAll.class, WebConfig.class})
/*    */ @EnableScheduling
/*    */ @ComponentScan(basePackages = {"com.hwacom.ngtms.ao.web", "com.hwacom.ngtms.ao.service", "com.hwacom.ngtms.ao.am.server", "com.hwacom.ngtms.rtu.service"}, basePackageClasses = {}, useDefaultFilters = true)
/*    */ @ServletComponentScan(basePackages = {"com.hwacom.ngtms.ao.web", "com.hwacom.ngtms.ao.service", "com.hwacom.ngtms.ao.am.server", "com.hwacom.ngtms.rtu.service"})
/*    */ @PropertySource({"classpath:boot/application.properties"})
/*    */ public class AoInvoker
/*    */ {
/*    */   public static void main(String[] args) {
/* 65 */     SpringApplication.run(AoInvoker.class, args);
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\lib\aoAdmin-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ao\boot\AoInvoker.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */