/*     */ package com.hwacom.ngtms.base.rmi;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.lang.reflect.Method;
/*     */ import java.rmi.NoSuchObjectException;
/*     */ import java.rmi.Remote;
/*     */ import java.rmi.RemoteException;
/*     */ import java.rmi.server.UnicastRemoteObject;
/*     */ import java.util.Map;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ import org.aopalliance.intercept.MethodInterceptor;
/*     */ import org.aopalliance.intercept.MethodInvocation;
/*     */ import org.springframework.aop.framework.ProxyFactory;
/*     */ import org.springframework.remoting.RemoteAccessException;
/*     */ import org.springframework.remoting.rmi.RmiBasedExporter;
/*     */ import org.springframework.remoting.rmi.RmiInvocationHandler;
/*     */ import org.springframework.remoting.support.DefaultRemoteInvocationFactory;
/*     */ import org.springframework.remoting.support.RemoteInvocation;
/*     */ import org.springframework.remoting.support.RemoteInvocationFactory;
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
/*     */ public class RmiCallbackUtils
/*     */ {
/*  37 */   private static Map<Object, Remote> staticRefMap = new ConcurrentHashMap();
/*     */   
/*     */   public static <E> E export(Object target, Class<E> clazz)
/*     */   {
/*  41 */     AnonymousRmiServiceExporter a = new AnonymousRmiServiceExporter();
/*     */     
/*  43 */     a.setService(target);
/*  44 */     a.setServiceInterface(clazz);
/*  45 */     a.setRegisterTraceInterceptor(false);
/*  46 */     Remote remoteProxy = a.getObjectToExport();
/*     */     
/*  48 */     staticRefMap.put(target, remoteProxy);
/*  49 */     RmiInvocationHandler stub = null;
/*     */     try {
/*  51 */       stub = (RmiInvocationHandler)UnicastRemoteObject.exportObject(remoteProxy, 0);
/*     */     }
/*     */     catch (RemoteException e1) {
/*  54 */       throw new RuntimeException(e1);
/*     */     }
/*     */     
/*  57 */     MethodInterceptor mi = new TargetInterfaceWrapper(stub);
/*  58 */     ProxyFactory pf = new ProxyFactory(clazz, mi);
/*  59 */     pf.addInterface(Serializable.class);
/*  60 */     return (E)pf.getProxy();
/*     */   }
/*     */   
/*     */   public static void unexport(Object target) {
/*  64 */     Remote remoteProxy = (Remote)staticRefMap.remove(target);
/*     */     try {
/*  66 */       UnicastRemoteObject.unexportObject(remoteProxy, true);
/*     */     }
/*     */     catch (NoSuchObjectException localNoSuchObjectException) {}
/*     */   }
/*     */   
/*     */   public static class AnonymousRmiServiceExporter extends RmiBasedExporter
/*     */   {
/*     */     public Remote getObjectToExport() {
/*  74 */       return super.getObjectToExport();
/*     */     }
/*     */   }
/*     */   
/*     */   public static class TargetInterfaceWrapper
/*     */     implements MethodInterceptor, Serializable
/*     */   {
/*     */     private static final long serialVersionUID = 1L;
/*  82 */     private static RemoteInvocationFactory remoteInvocationFactory = new DefaultRemoteInvocationFactory();
/*     */     
/*     */ 
/*  85 */     private RmiInvocationHandler stub = null;
/*     */     
/*     */     public TargetInterfaceWrapper(RmiInvocationHandler stub) {
/*  88 */       this.stub = stub;
/*     */     }
/*     */     
/*     */     public Object invoke(MethodInvocation invocation)
/*     */       throws Throwable
/*     */     {
/*  94 */       RemoteInvocation remoteInvocation = remoteInvocationFactory.createRemoteInvocation(invocation);
/*     */       try {
/*  96 */         return this.stub.invoke(remoteInvocation);
/*     */       } catch (Exception e) {
/*  98 */         Class<?>[] declaredExceptions = invocation.getMethod().getExceptionTypes();
/*  99 */         for (Class exceptionClass : declaredExceptions) {
/* 100 */           if (exceptionClass.isAssignableFrom(e.getClass())) {
/* 101 */             throw e;
/*     */           }
/*     */         }
/* 104 */         throw new RemoteAccessException(e.getMessage(), e);
/*     */       }
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\base-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\base\rmi\RmiCallbackUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */