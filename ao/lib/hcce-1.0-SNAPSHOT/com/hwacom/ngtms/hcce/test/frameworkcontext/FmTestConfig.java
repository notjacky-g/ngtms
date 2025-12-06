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
/*     */ import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
/*     */ import org.jasypt.util.password.rfc2307.RFC2307SSHAPasswordEncryptor;
/*     */ import org.springframework.context.MessageSource;
/*     */ import org.springframework.context.annotation.Bean;
/*     */ import org.springframework.context.annotation.ComponentScan;
/*     */ import org.springframework.context.annotation.Configuration;
/*     */ import org.springframework.context.annotation.EnableAspectJAutoProxy;
/*     */ import org.springframework.context.annotation.Import;
/*     */ import org.springframework.context.annotation.PropertySources;
/*     */ import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
/*     */ import org.springframework.core.convert.ConversionService;
/*     */ import org.springframework.core.convert.support.DefaultConversionService;
/*     */ import org.springframework.core.env.Environment;
/*     */ import org.springframework.mail.javamail.JavaMailSenderImpl;
/*     */ import org.springframework.transaction.annotation.EnableTransactionManagement;
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
/*     */ @ComponentScan(basePackages={"com.hwacom.ngtms"}, basePackageClasses={}, excludeFilters={@org.springframework.context.annotation.ComponentScan.Filter(type=org.springframework.context.annotation.FilterType.REGEX, pattern={"com.hwacom.ngtms.frameworkcontext.*"}), @org.springframework.context.annotation.ComponentScan.Filter(type=org.springframework.context.annotation.FilterType.REGEX, pattern={"com.hwacom.ngtms.hcce.frameworkcontext.*"}), @org.springframework.context.annotation.ComponentScan.Filter(type=org.springframework.context.annotation.FilterType.REGEX, pattern={"com.hwacom.ngtms.base.test.frameworkcontext.*"}), @org.springframework.context.annotation.ComponentScan.Filter(type=org.springframework.context.annotation.FilterType.REGEX, pattern={"com.hwacom.ngtms.base.test.*"}), @org.springframework.context.annotation.ComponentScan.Filter(type=org.springframework.context.annotation.FilterType.REGEX, pattern={"com.hwacom.ngtms.*.test.*"}), @org.springframework.context.annotation.ComponentScan.Filter(type=org.springframework.context.annotation.FilterType.ANNOTATION, value={org.springframework.stereotype.Controller.class}), @org.springframework.context.annotation.ComponentScan.Filter(type=org.springframework.context.annotation.FilterType.ANNOTATION, value={org.springframework.web.bind.annotation.RestController.class}), @org.springframework.context.annotation.ComponentScan.Filter(type=org.springframework.context.annotation.FilterType.ANNOTATION, value={javax.servlet.annotation.WebServlet.class}), @org.springframework.context.annotation.ComponentScan.Filter(type=org.springframework.context.annotation.FilterType.ANNOTATION, value={javax.servlet.annotation.WebListener.class}), @org.springframework.context.annotation.ComponentScan.Filter(type=org.springframework.context.annotation.FilterType.ANNOTATION, value={javax.servlet.annotation.WebFilter.class}), @org.springframework.context.annotation.ComponentScan.Filter(type=org.springframework.context.annotation.FilterType.REGEX, pattern={"com.hwacom.ngtms.common.am.*"}), @org.springframework.context.annotation.ComponentScan.Filter(type=org.springframework.context.annotation.FilterType.REGEX, pattern={"com.hwacom.ngtms.cam.*"}), @org.springframework.context.annotation.ComponentScan.Filter(type=org.springframework.context.annotation.FilterType.REGEX, pattern={"com.hwacom.ngtms..*.am.*"}), @org.springframework.context.annotation.ComponentScan.Filter(type=org.springframework.context.annotation.FilterType.REGEX, pattern={"com.hwacom.ngtms..*.boot.*"}), @org.springframework.context.annotation.ComponentScan.Filter(type=org.springframework.context.annotation.FilterType.REGEX, pattern={"com.hwacom.ngtms..*.web.*"}), @org.springframework.context.annotation.ComponentScan.Filter(type=org.springframework.context.annotation.FilterType.REGEX, pattern={"com.hwacom.ngtms.ncc.*"})})
/*     */ @PropertySources({@org.springframework.context.annotation.PropertySource({"classpath:${hcce.config.file:conf/test/fmTest.properties}"}), @org.springframework.context.annotation.PropertySource(value={"file:${hcce.override.config.file}"}, ignoreResourceNotFound=true)})
/*     */ @Import({OnlineDataBaseConfig.class, HistoryDataBaseConfig.class, DisasterRecoveryDataBaseConfig.class, HazelcastConfig.class, SchedulerConfig.class, HttpClientConfig.class})
/*     */ public class FmTestConfig
/*     */ {
/*     */   private static final String DATASOURCE_PASSWORD_ENCRYPTOR_KEY = "com.hwacom.ngtms.release";
/*     */   @Resource
/*     */   private Environment environment;
/*     */   
/*     */   @Bean
/*     */   public static PropertySourcesPlaceholderConfigurer propertyPlaceholderConfigurer()
/*     */   {
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
/* 133 */       .getProperty("i18n.baseDirectory", "classpath*:messages"));
/* 134 */     messageSource.setUseCodeAsDefaultMessage(true);
/* 135 */     messageSource.setDefaultEncoding("UTF-8");
/* 136 */     messageSource.setFallbackToSystemLocale(false);
/* 137 */     messageSource.setCacheSeconds(((Integer)this.environment.getProperty("i18n.cacheSeconds", Integer.class, Integer.valueOf(-1))).intValue());
/* 138 */     return messageSource;
/*     */   }
/*     */   
/*     */   @Bean
/*     */   public OperationLogService operationLogService() {
/* 143 */     OperationLogService operationLogService = new OperationLogServiceImpl();
/* 144 */     return operationLogService;
/*     */   }
/*     */   
/*     */   @Bean(name={"baseOpLogger"})
/*     */   public BaseOpLogger baseOpLogger() throws IOException
/*     */   {
/* 150 */     BaseOpLogger baseOpLogger = new BaseOpLogger(operationLogService(), (MessageSourceExt)messageSource());
/* 151 */     return baseOpLogger;
/*     */   }
/*     */   
/*     */   @Bean
/*     */   public ConversionService getConversionService() {
/* 156 */     ConversionService conversionService = new DefaultConversionService();
/* 157 */     return conversionService;
/*     */   }
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
/* 176 */       .getRequiredProperty("mail.smtp.starttls.enable"));
/* 177 */     p.setProperty("mail.debug", this.environment.getRequiredProperty("mail.debug"));
/* 178 */     javaMailSenderImpl.setJavaMailProperties(p);
/* 179 */     return javaMailSenderImpl;
/*     */   }
/*     */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\hcce-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\hcce\test\frameworkcontext\FmTestConfig.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */