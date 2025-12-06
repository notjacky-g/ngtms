/*    */ package com.hwacom.ngtms.frameworkcontext;
/*    */ 
/*    */ import javax.annotation.Resource;
/*    */ import org.springframework.context.annotation.Bean;
/*    */ import org.springframework.context.annotation.ComponentScan;
/*    */ import org.springframework.context.annotation.ComponentScan.Filter;
/*    */ import org.springframework.context.annotation.Configuration;
/*    */ import org.springframework.context.annotation.FilterType;
/*    */ import org.springframework.context.annotation.PropertySource;
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
/*    */ @Configuration
/*    */ @ComponentScan(basePackages = {"com.hwacom.ngtms"}, basePackageClasses = {}, excludeFilters = {@Filter(type = FilterType.REGEX, pattern = {"com.hwacom.ngtms.frameworkcontext.*"}), @Filter(type = FilterType.REGEX, pattern = {"com.hwacom.ngtms.hcce.frameworkcontext.*"}), @Filter(type = FilterType.REGEX, pattern = {"com.hwacom.ngtms.base.test.frameworkcontext.*"}), @Filter(type = FilterType.REGEX, pattern = {"com.hwacom.ngtms.hcce.test.frameworkcontext.*"}), @Filter(type = FilterType.REGEX, pattern = {"com.hwacom.ngtms.base.test.*"}), @Filter(type = FilterType.REGEX, pattern = {"com.hwacom.ngtms.hcce.test.*"}), @Filter(type = FilterType.REGEX, pattern = {"com.hwacom.ngtms.*.test.*"}), @Filter(type = FilterType.REGEX, pattern = {"com.hwacom.ngtms.common.am.*"}), @Filter(type = FilterType.REGEX, pattern = {"com.hwacom.ngtms.cam.*"}), @Filter(type = FilterType.REGEX, pattern = {"com.hwacom.ngtms..*.am.*"})})
/*    */ @PropertySources({@PropertySource({"classpath:${hcce.config.file:conf/hcce.properties}"}), @PropertySource(value = {"file:${hcce.override.config.file}"}, ignoreResourceNotFound = true)})
/*    */ public class NgtmsScan
/*    */ {
/*    */   @Resource
/*    */   private Environment environment;
/*    */   
/*    */   @Bean
/*    */   public static PropertySourcesPlaceholderConfigurer propertyPlaceholderConfigurer() {
/* 63 */     return new PropertySourcesPlaceholderConfigurer();
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\lib\base-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\frameworkcontext\NgtmsScan.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */