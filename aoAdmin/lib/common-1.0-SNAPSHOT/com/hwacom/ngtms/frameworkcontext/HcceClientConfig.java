/*    */ package com.hwacom.ngtms.frameworkcontext;
/*    */ 
/*    */ import com.hwacom.ngtms.base.i18n.service.MessageSourceExtImp;
/*    */ import com.hwacom.ngtms.base.i18n.shared.MessageSourceExt;
/*    */ import com.hwacom.ngtms.hcce.hz.HcceHzPortable;
/*    */ import com.hwacom.ngtms.hcce.hz.HzPortableRegister;
/*    */ import java.io.IOException;
/*    */ import javax.annotation.PostConstruct;
/*    */ import javax.annotation.Resource;
/*    */ import org.springframework.boot.web.servlet.ServletComponentScan;
/*    */ import org.springframework.context.annotation.Bean;
/*    */ import org.springframework.context.annotation.ComponentScan;
/*    */ import org.springframework.context.annotation.ComponentScan.Filter;
/*    */ import org.springframework.context.annotation.Configuration;
/*    */ import org.springframework.context.annotation.FilterType;
/*    */ import org.springframework.context.annotation.Import;
/*    */ import org.springframework.context.annotation.Primary;
/*    */ import org.springframework.context.annotation.PropertySource;
/*    */ import org.springframework.context.annotation.PropertySources;
/*    */ import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
/*    */ import org.springframework.core.env.Environment;
/*    */ import org.springframework.scheduling.annotation.EnableScheduling;
/*    */ import org.springframework.web.socket.config.annotation.EnableWebSocket;
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
/*    */ @Import({HazelcastClientConfig.class})
/*    */ @EnableScheduling
/*    */ @ComponentScan(basePackages = {"com.hwacom.ngtms.base.web", "com.hwacom.ngtms.hcce.am", "com.hwacom.ngtms.common.am", "com.hwacom.ngtms.node.performance.common"}, basePackageClasses = {}, excludeFilters = {@Filter(type = FilterType.REGEX, pattern = {"com.hwacom.ngtms..*.test.*"})})
/*    */ @ServletComponentScan(basePackages = {"com.hwacom.ngtms.base.web", "com.hwacom.ngtms.hcce.am", "com.hwacom.ngtms.common.am"})
/*    */ @PropertySources({@PropertySource({"applicationContext.properties"}), @PropertySource(value = {"file:${am.override.config.file}"}, ignoreResourceNotFound = true)})
/*    */ @EnableWebSocket
/*    */ public class HcceClientConfig
/*    */ {
/*    */   @Resource
/*    */   private Environment environment;
/*    */   
/*    */   @PostConstruct
/*    */   public void init() {
/* 70 */     HzPortableRegister.registerPortable(HcceHzPortable.class);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Primary
/*    */   @Bean
/*    */   public static PropertySourcesPlaceholderConfigurer commonPropertyPlaceholderConfigurer() {
/* 84 */     return new PropertySourcesPlaceholderConfigurer();
/*    */   }
/*    */   
/*    */   @Bean
/*    */   public MessageSourceExt messageSource() throws IOException {
/* 89 */     MessageSourceExtImp messageSource = new MessageSourceExtImp();
/* 90 */     messageSource.setBasenamesRegex(this.environment
/* 91 */         .getProperty("i18n.baseDirectory", "classpath*:messages"));
/* 92 */     messageSource.setUseCodeAsDefaultMessage(true);
/* 93 */     messageSource.setDefaultEncoding("UTF-8");
/* 94 */     messageSource.setFallbackToSystemLocale(false);
/* 95 */     messageSource.setCacheSeconds(((Integer)this.environment.getProperty("i18n.cacheSeconds", Integer.class, Integer.valueOf(-1))).intValue());
/* 96 */     return (MessageSourceExt)messageSource;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\lib\common-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\frameworkcontext\HcceClientConfig.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */