/*    */ package com.hwacom.ngtms.frameworkcontext;
/*    */ 
/*    */ import javax.annotation.Resource;
/*    */ import org.springframework.context.annotation.Bean;
/*    */ import org.springframework.context.annotation.ComponentScan;
/*    */ import org.springframework.context.annotation.Configuration;
/*    */ import org.springframework.context.annotation.PropertySources;
/*    */ import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
/*    */ import org.springframework.core.env.Environment;
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
/*    */ @ComponentScan(basePackages={"com.hwacom.ngtms"}, basePackageClasses={}, excludeFilters={@org.springframework.context.annotation.ComponentScan.Filter(type=org.springframework.context.annotation.FilterType.REGEX, pattern={"com.hwacom.ngtms.frameworkcontext.*"}), @org.springframework.context.annotation.ComponentScan.Filter(type=org.springframework.context.annotation.FilterType.REGEX, pattern={"com.hwacom.ngtms.hcce.frameworkcontext.*"}), @org.springframework.context.annotation.ComponentScan.Filter(type=org.springframework.context.annotation.FilterType.REGEX, pattern={"com.hwacom.ngtms.base.test.frameworkcontext.*"}), @org.springframework.context.annotation.ComponentScan.Filter(type=org.springframework.context.annotation.FilterType.REGEX, pattern={"com.hwacom.ngtms.hcce.test.frameworkcontext.*"}), @org.springframework.context.annotation.ComponentScan.Filter(type=org.springframework.context.annotation.FilterType.REGEX, pattern={"com.hwacom.ngtms.base.test.*"}), @org.springframework.context.annotation.ComponentScan.Filter(type=org.springframework.context.annotation.FilterType.REGEX, pattern={"com.hwacom.ngtms.hcce.test.*"}), @org.springframework.context.annotation.ComponentScan.Filter(type=org.springframework.context.annotation.FilterType.REGEX, pattern={"com.hwacom.ngtms.*.test.*"}), @org.springframework.context.annotation.ComponentScan.Filter(type=org.springframework.context.annotation.FilterType.REGEX, pattern={"com.hwacom.ngtms.common.am.*"}), @org.springframework.context.annotation.ComponentScan.Filter(type=org.springframework.context.annotation.FilterType.REGEX, pattern={"com.hwacom.ngtms.cam.*"}), @org.springframework.context.annotation.ComponentScan.Filter(type=org.springframework.context.annotation.FilterType.REGEX, pattern={"com.hwacom.ngtms..*.am.*"})})
/*    */ @PropertySources({@org.springframework.context.annotation.PropertySource({"classpath:${hcce.config.file:conf/hcce.properties}"}), @org.springframework.context.annotation.PropertySource(value={"file:${hcce.override.config.file}"}, ignoreResourceNotFound=true)})
/*    */ public class NgtmsScan
/*    */ {
/*    */   @Resource
/*    */   private Environment environment;
/*    */   
/*    */   @Bean
/*    */   public static PropertySourcesPlaceholderConfigurer propertyPlaceholderConfigurer()
/*    */   {
/* 63 */     return new PropertySourcesPlaceholderConfigurer();
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\base-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\frameworkcontext\NgtmsScan.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */