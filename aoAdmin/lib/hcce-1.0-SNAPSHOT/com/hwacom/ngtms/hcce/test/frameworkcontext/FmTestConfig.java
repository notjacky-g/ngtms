/*     */ package com.hwacom.ngtms.hcce.test.frameworkcontext;
/*     */ 
/*     */ import com.hwacom.ngtms.base.i18n.service.MessageSourceExtImp;
/*     */ import com.hwacom.ngtms.base.i18n.shared.MessageSourceExt;
/*     */ import com.hwacom.ngtms.base.oplog.service.BaseOpLogger;
/*     */ import com.hwacom.ngtms.base.oplog.service.OperationLogService;
/*     */ import com.hwacom.ngtms.base.oplog.service.OperationLogServiceImpl;
/*     */ import com.hwacom.ngtms.base.sysperflog.service.SysPerfLogService;
/*     */ import com.hwacom.ngtms.frameworkcontext.HistoryDataBaseConfig;
/*     */ import com.hwacom.ngtms.frameworkcontext.HttpClientConfig;
/*     */ import com.hwacom.ngtms.frameworkcontext.OnlineDataBaseConfig;
/*     */ import com.hwacom.ngtms.frameworkcontext.SchedulerConfig;
/*     */ import com.hwacom.ngtms.hcce.frameworkcontext.DisasterRecoveryDataBaseConfig;
/*     */ import com.hwacom.ngtms.hcce.frameworkcontext.HazelcastConfig;
/*     */ import java.io.IOException;
/*     */ import java.util.Properties;
/*     */ import javax.annotation.Resource;
/*     */ import javax.servlet.annotation.WebFilter;
/*     */ import javax.servlet.annotation.WebListener;
/*     */ import javax.servlet.annotation.WebServlet;
/*     */ import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
/*     */ import org.jasypt.util.password.rfc2307.RFC2307SSHAPasswordEncryptor;
/*     */ import org.springframework.context.MessageSource;
/*     */ import org.springframework.context.annotation.Bean;
/*     */ import org.springframework.context.annotation.ComponentScan;
/*     */ import org.springframework.context.annotation.ComponentScan.Filter;
/*     */ import org.springframework.context.annotation.Configuration;
/*     */ import org.springframework.context.annotation.EnableAspectJAutoProxy;
/*     */ import org.springframework.context.annotation.FilterType;
/*     */ import org.springframework.context.annotation.Import;
/*     */ import org.springframework.context.annotation.PropertySource;
/*     */ import org.springframework.context.annotation.PropertySources;
/*     */ import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
/*     */ import org.springframework.core.convert.ConversionService;
/*     */ import org.springframework.core.convert.support.DefaultConversionService;
/*     */ import org.springframework.core.env.Environment;
/*     */ import org.springframework.mail.javamail.JavaMailSenderImpl;
/*     */ import org.springframework.stereotype.Controller;
/*     */ import org.springframework.transaction.annotation.EnableTransactionManagement;
/*     */ import org.springframework.web.bind.annotation.RestController;
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
/*     */ @Configuration
/*     */ @EnableAspectJAutoProxy
/*     */ @EnableTransactionManagement
/*     */ @ComponentScan(basePackages = {"com.hwacom.ngtms"}, basePackageClasses = {}, excludeFilters = {@Filter(type = FilterType.REGEX, pattern = {"com.hwacom.ngtms.frameworkcontext.*"}), @Filter(type = FilterType.REGEX, pattern = {"com.hwacom.ngtms.hcce.frameworkcontext.*"}), @Filter(type = FilterType.REGEX, pattern = {"com.hwacom.ngtms.base.test.frameworkcontext.*"}), @Filter(type = FilterType.REGEX, pattern = {"com.hwacom.ngtms.base.test.*"}), @Filter(type = FilterType.REGEX, pattern = {"com.hwacom.ngtms.*.test.*"}), @Filter(type = FilterType.ANNOTATION, value = {Controller.class}), @Filter(type = FilterType.ANNOTATION, value = {RestController.class}), @Filter(type = FilterType.ANNOTATION, value = {WebServlet.class}), @Filter(type = FilterType.ANNOTATION, value = {WebListener.class}), @Filter(type = FilterType.ANNOTATION, value = {WebFilter.class}), @Filter(type = FilterType.REGEX, pattern = {"com.hwacom.ngtms.common.am.*"}), @Filter(type = FilterType.REGEX, pattern = {"com.hwacom.ngtms.cam.*"}), @Filter(type = FilterType.REGEX, pattern = {"com.hwacom.ngtms..*.am.*"}), @Filter(type = FilterType.REGEX, pattern = {"com.hwacom.ngtms..*.boot.*"}), @Filter(type = FilterType.REGEX, pattern = {"com.hwacom.ngtms..*.web.*"}), @Filter(type = FilterType.REGEX, pattern = {"com.hwacom.ngtms.ncc.*"})})
/*     */ @PropertySources({@PropertySource({"classpath:${hcce.config.file:conf/test/fmTest.properties}"}), @PropertySource(value = {"file:${hcce.override.config.file}"}, ignoreResourceNotFound = true)})
/*     */ @Import({OnlineDataBaseConfig.class, HistoryDataBaseConfig.class, DisasterRecoveryDataBaseConfig.class, HazelcastConfig.class, SchedulerConfig.class, HttpClientConfig.class})
/*     */ public class FmTestConfig
/*     */ {
/*     */   private static final String DATASOURCE_PASSWORD_ENCRYPTOR_KEY = "com.hwacom.ngtms.release";
/*     */   @Resource
/*     */   private Environment environment;
/*     */   
/*     */   @Bean
/*     */   public static PropertySourcesPlaceholderConfigurer propertyPlaceholderConfigurer() {
/* 113 */     return new PropertySourcesPlaceholderConfigurer();
/*     */   }
/*     */   
/*     */   @Bean
/*     */   public StandardPBEStringEncryptor standardPBEStringEncryptor() {
/* 118 */     StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
/* 119 */     encryptor.setPassword("com.hwacom.ngtms.release");
/* 120 */     return encryptor;
/*     */   }
/*     */   
/*     */   @Bean
/*     */   public RFC2307SSHAPasswordEncryptor ldapSSHAPasswordEncryptor() {
/* 125 */     RFC2307SSHAPasswordEncryptor encryptor = new RFC2307SSHAPasswordEncryptor();
/* 126 */     return encryptor;
/*     */   }
/*     */   
/*     */   @Bean
/*     */   public MessageSource messageSource() throws IOException {
/* 131 */     MessageSourceExtImp messageSource = new MessageSourceExtImp();
/* 132 */     messageSource.setBasenamesRegex(this.environment
/* 133 */         .getProperty("i18n.baseDirectory", "classpath*:messages"));
/* 134 */     messageSource.setUseCodeAsDefaultMessage(true);
/* 135 */     messageSource.setDefaultEncoding("UTF-8");
/* 136 */     messageSource.setFallbackToSystemLocale(false);
/* 137 */     messageSource.setCacheSeconds(((Integer)this.environment.getProperty("i18n.cacheSeconds", Integer.class, Integer.valueOf(-1))).intValue());
/* 138 */     return (MessageSource)messageSource;
/*     */   }
/*     */   
/*     */   @Bean
/*     */   public OperationLogService operationLogService() {
/* 143 */     return (OperationLogService)new OperationLogServiceImpl();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Bean(name = {"baseOpLogger"})
/*     */   public BaseOpLogger baseOpLogger() throws IOException {
/* 150 */     BaseOpLogger baseOpLogger = new BaseOpLogger(operationLogService(), (MessageSourceExt)messageSource());
/* 151 */     return baseOpLogger;
/*     */   }
/*     */   
/*     */   @Bean
/*     */   public ConversionService getConversionService() {
/* 156 */     return (ConversionService)new DefaultConversionService();
/*     */   }
/*     */ 
/*     */   
/*     */   @Bean
/*     */   public SysPerfLogService sysPerfLogService() {
/* 162 */     return new SysPerfLogService();
/*     */   }
/*     */   
/*     */   @Bean
/*     */   public JavaMailSenderImpl mailSender() {
/* 167 */     JavaMailSenderImpl javaMailSenderImpl = new JavaMailSenderImpl();
/* 168 */     javaMailSenderImpl.setHost(this.environment.getRequiredProperty("mail.host"));
/* 169 */     javaMailSenderImpl.setPort(((Integer)this.environment.getRequiredProperty("mail.port", Integer.class)).intValue());
/* 170 */     javaMailSenderImpl.setUsername(this.environment.getRequiredProperty("mail.username"));
/* 171 */     javaMailSenderImpl.setPassword(this.environment.getRequiredProperty("mail.password"));
/*     */     
/* 173 */     Properties p = new Properties();
/* 174 */     p.setProperty("mail.smtp.auth", this.environment.getRequiredProperty("mail.smtp.auth"));
/* 175 */     p.setProperty("mail.smtp.starttls.enable", this.environment
/* 176 */         .getRequiredProperty("mail.smtp.starttls.enable"));
/* 177 */     p.setProperty("mail.debug", this.environment.getRequiredProperty("mail.debug"));
/* 178 */     javaMailSenderImpl.setJavaMailProperties(p);
/* 179 */     return javaMailSenderImpl;
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\lib\hcce-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\hcce\test\frameworkcontext\FmTestConfig.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */