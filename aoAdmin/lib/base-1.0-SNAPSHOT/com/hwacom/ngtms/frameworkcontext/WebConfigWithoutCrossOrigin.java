/*    */ package com.hwacom.ngtms.frameworkcontext;
/*    */ 
/*    */ import com.hwacom.ngtms.base.oom.MemoryWarningService;
/*    */ import com.hwacom.ngtms.base.web.filter.DisableHttpMethodFilter;
/*    */ import com.hwacom.ngtms.base.web.filter.XSSFilter;
/*    */ import com.hwacom.ngtms.base.web.listener.SessionListenerWithMetrics;
/*    */ import java.util.EventListener;
/*    */ import javax.servlet.Filter;
/*    */ import org.modelmapper.ModelMapper;
/*    */ import org.springframework.beans.factory.annotation.Value;
/*    */ import org.springframework.boot.web.servlet.FilterRegistrationBean;
/*    */ import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
/*    */ import org.springframework.context.annotation.Bean;
/*    */ import org.springframework.context.annotation.Configuration;
/*    */ import org.springframework.context.annotation.EnableAspectJAutoProxy;
/*    */ import org.springframework.context.annotation.Import;
/*    */ import org.springframework.scheduling.annotation.EnableAsync;
/*    */ import org.springframework.web.util.IntrospectorCleanupListener;
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
/*    */ @EnableAspectJAutoProxy
/*    */ @EnableAsync
/*    */ @Import({WebSocketConfig.class, ServletContainerConfig.class})
/*    */ public class WebConfigWithoutCrossOrigin
/*    */ {
/*    */   @Value("${http.session.max.inactive.interval:#{null}}")
/*    */   private Integer maxInactiveInterval;
/*    */   
/*    */   @Bean
/*    */   public ModelMapper modelMapper() {
/* 51 */     return new ModelMapper();
/*    */   }
/*    */   
/*    */   @Bean
/*    */   public MemoryWarningService getMemoryWarningService() {
/* 56 */     MemoryWarningService mws = new MemoryWarningService();
/* 57 */     mws.setPercentageUsageThreshold(0.8D);
/* 58 */     return mws;
/*    */   }
/*    */   
/*    */   @Bean
/*    */   public FilterRegistrationBean<XSSFilter> getXSSFilterRegistration() {
/* 63 */     FilterRegistrationBean<XSSFilter> registration = new FilterRegistrationBean();
/* 64 */     registration.setFilter((Filter)new XSSFilter());
/*    */     
/* 66 */     registration.addUrlPatterns(new String[] { "/*" });
/*    */ 
/*    */ 
/*    */     
/* 70 */     return registration;
/*    */   }
/*    */   
/*    */   @Bean
/*    */   public FilterRegistrationBean<DisableHttpMethodFilter> getDisableHttpMethodFilterRegistration() {
/* 75 */     FilterRegistrationBean<DisableHttpMethodFilter> registration = new FilterRegistrationBean();
/*    */     
/* 77 */     registration.setFilter((Filter)new DisableHttpMethodFilter());
/*    */     
/* 79 */     registration.addUrlPatterns(new String[] { "/*" });
/* 80 */     return registration;
/*    */   }
/*    */   
/*    */   @Bean
/*    */   public ServletListenerRegistrationBean<EventListener> getSessionListenerWithMetrics() {
/* 85 */     ServletListenerRegistrationBean<EventListener> registrationBean = new ServletListenerRegistrationBean();
/*    */     
/* 87 */     registrationBean.setListener((EventListener)new SessionListenerWithMetrics(this.maxInactiveInterval));
/* 88 */     return registrationBean;
/*    */   }
/*    */   
/*    */   @Bean
/*    */   public ServletListenerRegistrationBean<EventListener> getIntrospectorCleanupListener() {
/* 93 */     ServletListenerRegistrationBean<EventListener> registrationBean = new ServletListenerRegistrationBean();
/*    */     
/* 95 */     registrationBean.setListener((EventListener)new IntrospectorCleanupListener());
/* 96 */     return registrationBean;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\lib\base-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\frameworkcontext\WebConfigWithoutCrossOrigin.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */