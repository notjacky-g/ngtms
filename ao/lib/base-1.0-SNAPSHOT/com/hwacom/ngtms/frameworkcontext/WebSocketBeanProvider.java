/*    */ package com.hwacom.ngtms.frameworkcontext;
/*    */ 
/*    */ import org.springframework.beans.BeansException;
/*    */ import org.springframework.context.ApplicationContext;
/*    */ import org.springframework.context.ApplicationContextAware;
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
/*    */ public class WebSocketBeanProvider
/*    */   implements ApplicationContextAware
/*    */ {
/*    */   private static ApplicationContext context;
/*    */   
/*    */   public static ApplicationContext getApplicationContext()
/*    */   {
/* 25 */     return context;
/*    */   }
/*    */   
/*    */   public void setApplicationContext(ApplicationContext ac) throws BeansException
/*    */   {
/* 30 */     context = ac;
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\base-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\frameworkcontext\WebSocketBeanProvider.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */