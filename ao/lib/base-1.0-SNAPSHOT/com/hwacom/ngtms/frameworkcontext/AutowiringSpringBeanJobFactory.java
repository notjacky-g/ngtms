/*    */ package com.hwacom.ngtms.frameworkcontext;
/*    */ 
/*    */ import org.quartz.spi.TriggerFiredBundle;
/*    */ import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
/*    */ import org.springframework.context.ApplicationContext;
/*    */ import org.springframework.context.ApplicationContextAware;
/*    */ import org.springframework.scheduling.quartz.SpringBeanJobFactory;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class AutowiringSpringBeanJobFactory
/*    */   extends SpringBeanJobFactory
/*    */   implements ApplicationContextAware
/*    */ {
/*    */   private transient AutowireCapableBeanFactory beanFactory;
/*    */   
/*    */   public void setApplicationContext(ApplicationContext context)
/*    */   {
/* 22 */     this.beanFactory = context.getAutowireCapableBeanFactory();
/*    */   }
/*    */   
/*    */   protected Object createJobInstance(TriggerFiredBundle bundle) throws Exception
/*    */   {
/* 27 */     Object job = super.createJobInstance(bundle);
/* 28 */     this.beanFactory.autowireBean(job);
/* 29 */     return job;
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\base-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\frameworkcontext\AutowiringSpringBeanJobFactory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */