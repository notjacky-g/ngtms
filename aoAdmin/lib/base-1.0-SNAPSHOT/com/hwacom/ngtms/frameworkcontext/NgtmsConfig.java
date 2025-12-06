/*    */ package com.hwacom.ngtms.frameworkcontext;
/*    */ 
/*    */ import com.hwacom.ngtms.base.i18n.service.MessageSourceExtImp;
/*    */ import com.hwacom.ngtms.base.i18n.shared.MessageSourceExt;
/*    */ import com.hwacom.ngtms.base.oplog.service.BaseOpLogger;
/*    */ import com.hwacom.ngtms.base.oplog.service.OperationLogService;
/*    */ import com.hwacom.ngtms.base.oplog.service.OperationLogServiceImpl;
/*    */ import com.hwacom.ngtms.base.sysperflog.service.SysPerfLogService;
/*    */ import java.io.IOException;
/*    */ import javax.annotation.Resource;
/*    */ import org.springframework.context.MessageSource;
/*    */ import org.springframework.context.annotation.Bean;
/*    */ import org.springframework.context.annotation.Configuration;
/*    */ import org.springframework.context.annotation.Primary;
/*    */ import org.springframework.core.convert.ConversionService;
/*    */ import org.springframework.core.convert.support.DefaultConversionService;
/*    */ import org.springframework.core.env.Environment;
/*    */ 
/*    */ @Configuration
/*    */ public class NgtmsConfig {
/*    */   @Resource
/*    */   private Environment environment;
/*    */   
/*    */   @Primary
/*    */   @Bean
/*    */   public ConversionService getConversionService() {
/* 27 */     return (ConversionService)new DefaultConversionService();
/*    */   }
/*    */ 
/*    */   
/*    */   @Bean
/*    */   public MessageSource messageSource() throws IOException {
/* 33 */     MessageSourceExtImp messageSource = new MessageSourceExtImp();
/* 34 */     messageSource.setBasenamesRegex(this.environment
/* 35 */         .getProperty("i18n.baseDirectory", "classpath*:messages"));
/* 36 */     messageSource.setUseCodeAsDefaultMessage(true);
/* 37 */     messageSource.setDefaultEncoding("UTF-8");
/* 38 */     messageSource.setFallbackToSystemLocale(false);
/* 39 */     messageSource.setCacheSeconds(((Integer)this.environment.getProperty("i18n.cacheSeconds", Integer.class, Integer.valueOf(-1))).intValue());
/* 40 */     return (MessageSource)messageSource;
/*    */   }
/*    */   
/*    */   @Bean
/*    */   public OperationLogService operationLogService() {
/* 45 */     return (OperationLogService)new OperationLogServiceImpl();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   @Primary
/*    */   @Bean
/*    */   public BaseOpLogger getOpLogger() throws IOException {
/* 53 */     BaseOpLogger opLogger = new BaseOpLogger(operationLogService(), (MessageSourceExt)messageSource());
/* 54 */     return opLogger;
/*    */   }
/*    */   
/*    */   @Bean
/*    */   public SysPerfLogService sysPerfLogService() {
/* 59 */     return new SysPerfLogService();
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\lib\base-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\frameworkcontext\NgtmsConfig.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */