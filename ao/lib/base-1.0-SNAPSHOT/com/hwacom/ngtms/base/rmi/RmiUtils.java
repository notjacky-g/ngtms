/*     */ package com.hwacom.ngtms.base.rmi;
/*     */ 
/*     */ import java.rmi.RemoteException;
/*     */ import java.util.HashMap;
/*     */ import org.springframework.remoting.rmi.RmiProxyFactoryBean;
/*     */ import org.springframework.remoting.rmi.RmiServiceExporter;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class RmiUtils
/*     */ {
/*  16 */   private static int rmiRegistryPort = 4320;
/*  17 */   private static final HashMap<String, RmiServiceExporter> rmiExportMap = new HashMap();
/*  18 */   private static final Object syncObj = new Object();
/*     */   
/*     */   public static int getRmiRegistryPort() {
/*  21 */     return rmiRegistryPort;
/*     */   }
/*     */   
/*     */   protected static void setRmiRegistryPort(int port) {
/*  25 */     rmiRegistryPort = port;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private static RmiProxyFactoryBean getRmiClient(String serviceUrl, Class<?> serviceInterface)
/*     */   {
/*  33 */     RmiProxyFactoryBean client = new RmiProxyFactoryBean();
/*  34 */     client.setServiceUrl(serviceUrl);
/*  35 */     client.setServiceInterface(serviceInterface);
/*  36 */     client.afterPropertiesSet();
/*     */     
/*  38 */     return client;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static <T> T getRemoteClient(String serviceUrl, Class<T> serviceInterface)
/*     */   {
/*  47 */     RmiProxyFactoryBean client = getRmiClient(serviceUrl, serviceInterface);
/*     */     
/*  49 */     T remote = client.getObject();
/*  50 */     return remote;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static <T> T getRemoteClient(String ipAddress, String serviceName, Class<T> serviceInterface)
/*     */   {
/*  61 */     String serviceUrl = "rmi://" + ipAddress + ":" + rmiRegistryPort + "/" + serviceName;
/*  62 */     RmiProxyFactoryBean client = getRmiClient(serviceUrl, serviceInterface);
/*     */     
/*  64 */     T remote = client.getObject();
/*  65 */     return remote;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static void rebindRmiService(String serviceName, Class<?> serviceInterface, Object serviceInstance)
/*     */     throws RemoteException
/*     */   {
/*  79 */     rebindRmiService(rmiRegistryPort, serviceName, 0, serviceInterface, serviceInstance);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static void rebindRmiService(int registryPort, String serviceName, Class<?> serviceInterface, Object serviceInstance)
/*     */     throws RemoteException
/*     */   {
/*  93 */     rebindRmiService(registryPort, serviceName, 0, serviceInterface, serviceInstance);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static void rebindRmiService(String serviceName, int servicePort, Class<?> serviceInterface, Object serviceInstance)
/*     */     throws RemoteException
/*     */   {
/* 107 */     rebindRmiService(rmiRegistryPort, serviceName, servicePort, serviceInterface, serviceInstance);
/*     */   }
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
/*     */   public static void rebindRmiService(int registryPort, String serviceName, int servicePort, Class<?> serviceInterface, Object serviceInstance)
/*     */     throws RemoteException
/*     */   {
/* 127 */     synchronized (syncObj) {
/* 128 */       if (rmiExportMap.containsKey(serviceName)) {
/* 129 */         unbindRmiService(serviceName);
/*     */       }
/* 131 */       RmiServiceExporter export = new RmiServiceExporter();
/* 132 */       export.setServiceName(serviceName);
/* 133 */       export.setServicePort(servicePort);
/* 134 */       export.setRegistryPort(registryPort);
/* 135 */       export.setService(serviceInstance);
/* 136 */       export.setServiceInterface(serviceInterface);
/* 137 */       export.setBeanClassLoader(serviceInterface.getClassLoader());
/* 138 */       export.afterPropertiesSet();
/*     */       
/* 140 */       rmiExportMap.put(serviceName, export);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static void unbindRmiService(String serviceName)
/*     */     throws RemoteException
/*     */   {
/* 152 */     synchronized (syncObj) {
/* 153 */       if (rmiExportMap.containsKey(serviceName)) {
/* 154 */         RmiServiceExporter export = (RmiServiceExporter)rmiExportMap.get(serviceName);
/* 155 */         export.destroy();
/* 156 */         rmiExportMap.remove(serviceName);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static void unbindAllRmiService()
/*     */     throws RemoteException
/*     */   {
/* 169 */     synchronized (syncObj) {
/* 170 */       for (RmiServiceExporter export : rmiExportMap.values()) {
/* 171 */         export.destroy();
/*     */       }
/* 173 */       rmiExportMap.clear();
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\base-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\base\rmi\RmiUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */