/*     */ package com.hwacom.ngtms.hcce.fme.controller;
/*     */ 
/*     */ import com.hazelcast.core.DistributedObjectEvent;
/*     */ import com.hazelcast.core.DistributedObjectListener;
/*     */ import com.hwacom.ngtms.base.hazelcast.HzUtils;
/*     */ import com.hwacom.ngtms.hcce.core.exception.FmeOperationException;
/*     */ import com.hwacom.ngtms.hcce.fme.controller.fm.FmeMainBase;
/*     */ import com.hwacom.ngtms.hcce.hz.HzMap;
/*     */ import java.lang.reflect.Constructor;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ import org.slf4j.Logger;
/*     */ import org.slf4j.LoggerFactory;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ import org.springframework.context.ApplicationContext;
/*     */ import org.springframework.stereotype.Component;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Component
/*     */ public class FmeController
/*     */ {
/*  25 */   private static Logger logger = LoggerFactory.getLogger(FmeController.class);
/*     */   
/*  27 */   private ConcurrentHashMap<String, FmeMainBase> fmeExecutorsMap = new ConcurrentHashMap<>();
/*     */   
/*     */   @Autowired
/*     */   private ApplicationContext applicationContext;
/*     */   
/*     */   public FmeController() {
/*  33 */     this.threadGroup = Thread.currentThread().getThreadGroup();
/*     */   }
/*     */   private ThreadGroup threadGroup; private String distributedObjectListenerId;
/*     */   public void start() {
/*  37 */     logger.info("FmeController started");
/*  38 */     this
/*  39 */       .distributedObjectListenerId = HzUtils.getHzInstance().addDistributedObjectListener(new MyDistributedObjectListener());
/*     */   }
/*     */   
/*     */   public void stop() {
/*  43 */     if (this.distributedObjectListenerId != null) {
/*     */       try {
/*  45 */         HzUtils.getHzInstance().removeDistributedObjectListener(this.distributedObjectListenerId);
/*  46 */       } catch (Exception ex) {
/*  47 */         logger.error("removeDistributedObjectListener failed", ex);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*  52 */     freezeAllFmes();
/*     */     
/*  54 */     for (String fmeName : this.fmeExecutorsMap.keySet()) {
/*     */       try {
/*  56 */         removeFme(fmeName);
/*  57 */       } catch (Exception ex) {
/*  58 */         logger.error("Failed to remove FME:" + fmeName, ex);
/*     */       } 
/*     */     } 
/*  61 */     logger.info("FmeController stopped");
/*     */   }
/*     */   
/*     */   public void freezeAllFmes() {
/*  65 */     logger.info("Freeze all FMEs");
/*  66 */     for (FmeMainBase fmeMainBase : this.fmeExecutorsMap.values()) {
/*  67 */       fmeMainBase.setFreezeFme();
/*     */     }
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
/*     */   public synchronized void addFme(String fmeName, String className, String description) throws FmeOperationException {
/*  82 */     FmeMainBase fmeMainBase = null;
/*     */     try {
/*  84 */       if (this.fmeExecutorsMap.containsKey(fmeName)) {
/*  85 */         logger.warn("The FME:{} has existed, remove the old one", fmeName);
/*  86 */         removeFme(fmeName);
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  92 */       Constructor<?> c = this.applicationContext.getClassLoader().loadClass(className).getConstructor(new Class[] { String.class, String.class });
/*  93 */       fmeMainBase = (FmeMainBase)c.newInstance(new Object[] { fmeName, description });
/*  94 */       this.applicationContext.getAutowireCapableBeanFactory().autowireBean(fmeMainBase);
/*  95 */       fmeMainBase.initFmeMain(this.threadGroup, className);
/*     */       
/*  97 */       fmeMainBase.testStart();
/*     */       
/*  99 */       this.fmeExecutorsMap.put(fmeName, fmeMainBase);
/* 100 */     } catch (Exception ex) {
/* 101 */       throw new FmeOperationException("Failed to add FME:" + fmeName, ex);
/*     */     } 
/* 103 */     logger.info("Add FME successfully! name: {}, className: {}, description :{}", new Object[] { fmeName, className, description });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void startFme(String fmeName) throws FmeOperationException {
/* 111 */     FmeMainBase fmeMainBase = this.fmeExecutorsMap.get(fmeName);
/* 112 */     if (fmeMainBase == null) {
/* 113 */       throw new FmeOperationException("The FME:" + fmeName + " not found");
/*     */     }
/*     */     try {
/* 116 */       fmeMainBase.start();
/* 117 */     } catch (Exception ex) {
/* 118 */       throw new FmeOperationException("Failed to start FME:" + fmeName, ex);
/*     */     } 
/* 120 */     logger.info("Start FME successfully! name: {}", fmeName);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void removeFme(String fmeName) {
/* 130 */     FmeMainBase fmeMainBase = this.fmeExecutorsMap.remove(fmeName);
/* 131 */     if (fmeMainBase != null) {
/* 132 */       fmeMainBase.stop();
/* 133 */       logger.info("Remove FME successfully! name: {}", fmeName);
/*     */     } 
/*     */   }
/*     */   
/*     */   class MyDistributedObjectListener
/*     */     implements DistributedObjectListener {
/*     */     public void distributedObjectCreated(DistributedObjectEvent event) {
/* 140 */       if (event.getDistributedObject().getName().equals(HzMap.DynamicConfig.toHzName())) {
/* 141 */         FmeController.logger.info("{} was created", HzMap.DynamicConfig.toHzName());
/* 142 */         for (FmeMainBase fmeMainBase : FmeController.this.fmeExecutorsMap.values()) {
/*     */           try {
/* 144 */             fmeMainBase.readdDyanConfigListener();
/* 145 */           } catch (Exception ex) {
/* 146 */             FmeController.logger.error("Failed to readdDyanConfigListener, fmeName: " + fmeMainBase
/* 147 */                 .getFmeName(), ex);
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public void distributedObjectDestroyed(DistributedObjectEvent event) {
/* 155 */       if (event.getObjectName().equals(HzMap.DynamicConfig.toHzName())) {
/* 156 */         FmeController.logger.info("{} was detroyed", HzMap.DynamicConfig.toHzName());
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public FmeMainBase getFme(String fmeName) {
/* 162 */     return this.fmeExecutorsMap.get(fmeName);
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\lib\hcce-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\hcce\fme\controller\FmeController.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */