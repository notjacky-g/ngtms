/*    */ package com.hwacom.ngtms.base.util;
/*    */ 
/*    */ import org.apache.commons.lang.StringUtils;
/*    */ import org.springframework.context.ApplicationContext;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ApplicationContextHelper
/*    */ {
/*    */   private static ApplicationContextHelper theInstance;
/* 15 */   private static volatile Object lock = new Object();
/*    */   
/* 17 */   private ApplicationContext appContext = null;
/*    */ 
/*    */ 
/*    */   
/*    */   public static ApplicationContextHelper getInstance() {
/* 22 */     synchronized (lock) {
/* 23 */       if (theInstance == null) {
/* 24 */         theInstance = new ApplicationContextHelper();
/*    */       }
/*    */     } 
/* 27 */     return theInstance;
/*    */   }
/*    */   
/*    */   public void setAppContext(ApplicationContext appContext) {
/* 31 */     this.appContext = appContext;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public ApplicationContext getAppContext() {
/* 40 */     return this.appContext;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public <T> T getBean(String name, Class<T> requiredType) {
/* 49 */     return (T)this.appContext.getBean(name, requiredType);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public <T> T getBean(Class<T> requiredType) {
/* 58 */     return (T)this.appContext.getBean(StringUtils.uncapitalize(requiredType.getSimpleName()), requiredType);
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\lib\base-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\bas\\util\ApplicationContextHelper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */