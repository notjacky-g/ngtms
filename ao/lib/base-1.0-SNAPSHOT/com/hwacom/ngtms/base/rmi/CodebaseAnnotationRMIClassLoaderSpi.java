/*    */ package com.hwacom.ngtms.base.rmi;
/*    */ 
/*    */ import java.net.MalformedURLException;
/*    */ import java.rmi.server.RMIClassLoader;
/*    */ import java.rmi.server.RMIClassLoaderSpi;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CodebaseAnnotationRMIClassLoaderSpi
/*    */   extends RMIClassLoaderSpi
/*    */ {
/* 17 */   RMIClassLoaderSpi delegate = RMIClassLoader.getDefaultProviderInstance();
/*    */   
/* 19 */   String codebase = System.getProperty("java.rmi.server.codebase");
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public Class<?> loadProxyClass(String codebase, String[] interfaces, ClassLoader ignored)
/*    */     throws MalformedURLException, ClassNotFoundException
/*    */   {
/* 30 */     return this.delegate.loadProxyClass(codebase, interfaces, 
/* 31 */       Thread.currentThread().getContextClassLoader());
/*    */   }
/*    */   
/*    */   public Class<?> loadClass(String codebase, String name, ClassLoader ignored)
/*    */     throws MalformedURLException, ClassNotFoundException
/*    */   {
/* 37 */     return this.delegate.loadClass(codebase, name, Thread.currentThread().getContextClassLoader());
/*    */   }
/*    */   
/*    */   public ClassLoader getClassLoader(String codebase) throws MalformedURLException
/*    */   {
/* 42 */     return this.delegate.getClassLoader(codebase);
/*    */   }
/*    */   
/*    */   public String getClassAnnotation(Class<?> cl)
/*    */   {
/* 47 */     return this.codebase;
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\base-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\base\rmi\CodebaseAnnotationRMIClassLoaderSpi.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */