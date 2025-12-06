/*     */ package com.hwacom.ngtms.hcce.fme.controller.fm;
/*     */ 
/*     */ import com.hazelcast.core.EntryEvent;
/*     */ import com.hazelcast.core.IMap;
/*     */ import com.hazelcast.map.listener.EntryAddedListener;
/*     */ import com.hazelcast.map.listener.EntryRemovedListener;
/*     */ import com.hazelcast.map.listener.EntryUpdatedListener;
/*     */ import com.hazelcast.map.listener.MapListener;
/*     */ import com.hwacom.ngtms.base.hazelcast.HzDistObjEnum;
/*     */ import com.hwacom.ngtms.base.hazelcast.HzUtils;
/*     */ import com.hwacom.ngtms.base.i18n.shared.MessageSourceExt;
/*     */ import com.hwacom.ngtms.base.oplog.service.BaseOpLogger;
/*     */ import com.hwacom.ngtms.base.oplog.shared.OperationResult;
/*     */ import com.hwacom.ngtms.base.rmi.RmiUtils;
/*     */ import com.hwacom.ngtms.base.sysperflog.service.SysPerfLog;
/*     */ import com.hwacom.ngtms.base.sysperflog.service.SysPerfLogService;
/*     */ import com.hwacom.ngtms.hcce.config.model.DynamicConfig;
/*     */ import com.hwacom.ngtms.hcce.config.model.DynamicConfigPk;
/*     */ import com.hwacom.ngtms.hcce.core.exception.FmException;
/*     */ import com.hwacom.ngtms.hcce.fme.controller.runtime.FmResourceInfo;
/*     */ import com.hwacom.ngtms.hcce.fme.controller.runtime.TaskLoadingCalculator;
/*     */ import com.hwacom.ngtms.hcce.hz.HzMap;
/*     */ import com.hwacom.ngtms.hcce.shared.DynamicConfigDeclare;
/*     */ import com.hwacom.ngtms.hcce.shared.FmeExeStatus;
/*     */ import com.hwacom.ngtms.hcce.shared.FmeState;
/*     */ import com.hwacom.ngtms.hcce.shared.HcceEnv;
/*     */ import java.io.Serializable;
/*     */ import java.rmi.RemoteException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.Date;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ import java.util.concurrent.ExecutorService;
/*     */ import java.util.concurrent.Executors;
/*     */ import java.util.concurrent.atomic.AtomicBoolean;
/*     */ import java.util.concurrent.atomic.AtomicLong;
/*     */ import org.quartz.Job;
/*     */ import org.quartz.JobDataMap;
/*     */ import org.quartz.JobExecutionContext;
/*     */ import org.quartz.JobExecutionException;
/*     */ import org.slf4j.Logger;
/*     */ import org.slf4j.LoggerFactory;
/*     */ import org.springframework.aop.framework.Advised;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ import org.springframework.context.ApplicationContext;
/*     */ import org.springframework.core.convert.ConversionService;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class FmeMainBase
/*     */   extends FmScheduleSupport
/*     */   implements FmInstance
/*     */ {
/*  62 */   private static Logger logger = LoggerFactory.getLogger(FmeMainBase.class);
/*     */   
/*  64 */   private static ConcurrentHashMap<String, DynamicConfigDeclare[]> dynamicConfigDeclaresMap = (ConcurrentHashMap)new ConcurrentHashMap<>();
/*     */ 
/*     */   
/*  67 */   private HashMap<String, DynamicConfig> defaultDynamicConfigMap = new HashMap<>();
/*     */   
/*     */   private String fmeName;
/*     */   
/*     */   private String description;
/*  72 */   private AtomicBoolean stopRequested = new AtomicBoolean(false);
/*     */   
/*  74 */   private AtomicBoolean selfStop = new AtomicBoolean(false);
/*     */   
/*  76 */   private AtomicBoolean running = new AtomicBoolean(false);
/*     */   private ExecutorService fmInstancExecutor;
/*     */   private FmThreadGroup fmThreadGroup;
/*     */   private IMap<String, FmeExeStatus> fmeExeStatusMap;
/*     */   protected IMap<String, SysPerfLog> sysPerfLogMap;
/*     */   protected IMap<String, FmResourceInfo> fmResourceMap;
/*     */   protected IMap<DynamicConfigPk, DynamicConfig> dynamicConfigMap;
/*  83 */   private ConcurrentHashMap<String, TaskLoadingCalculator> taskLoadingCalculatorMap = new ConcurrentHashMap<>();
/*     */   
/*     */   protected long fmStartTime;
/*  86 */   private AtomicBoolean freezeFme = new AtomicBoolean();
/*  87 */   private AtomicLong runningTaskCount = new AtomicLong();
/*  88 */   private List<DynaConfigEntryListenerMeta> dynaConfigEntryListenerMetaList = new ArrayList<>();
/*     */ 
/*     */   
/*  91 */   private List<PerfCollectListener> perfCollectListenerList = Collections.synchronizedList(new ArrayList<>()); @Autowired
/*     */   private HcceEnv hcceEnv; @Autowired
/*     */   protected ApplicationContext applicationContext; @Autowired
/*     */   protected MessageSourceExt messageSourceExt; @Autowired
/*     */   protected BaseOpLogger opLogger; @Autowired
/*     */   private ConversionService conversionService; @Autowired
/*     */   private SysPerfLogService sysPerfLogService; private final Runnable fmRunnable;
/*     */   DynaConfigEntryListener dynaConfigEntryListener;
/*     */   
/*     */   static class DynaConfigEntryListenerMeta { String listenerId;
/*     */     DynamicConfigPk key;
/*     */     
/*     */     public DynaConfigEntryListenerMeta(String listenerId, DynamicConfigPk key) {
/* 104 */       this.listenerId = listenerId;
/* 105 */       this.key = key;
/*     */     } }
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
/*     */   public void initFmeMain(ThreadGroup threadGroup, String fmClassName) {
/* 123 */     DynamicConfigDeclare[] dynamicConfigDeclares = dynamicConfigDeclaresMap.get(fmClassName);
/* 124 */     if (dynamicConfigDeclares != null)
/* 125 */       for (DynamicConfigDeclare dynamicConfigDeclare : dynamicConfigDeclares) {
/* 126 */         if (dynamicConfigDeclare.getDefaultValue() != null) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 132 */           DynamicConfig dynamicConfig = new DynamicConfig(this.hcceEnv.getCurrentGroupName(), this.fmeName, dynamicConfigDeclare.getName(), dynamicConfigDeclare.getDefaultValue());
/* 133 */           this.defaultDynamicConfigMap.put(dynamicConfig.getName(), dynamicConfig);
/*     */         } 
/*     */       }  
/* 136 */     this.fmThreadGroup = new FmThreadGroup(this.fmeName, threadGroup);
/* 137 */     this.fmInstancExecutor = Executors.newSingleThreadExecutor(this.fmThreadGroup.getFmThreadFactory());
/*     */   }
/*     */ 
/*     */   
/*     */   public synchronized void start() throws FmException {
/* 142 */     if (this.running.get())
/*     */       return;  try {
/* 144 */       this.stopRequested.set(false);
/* 145 */       this.freezeFme.set(false);
/* 146 */       this.selfStop.set(false);
/* 147 */       this.running.set(true);
/* 148 */       this.fmInstancExecutor.execute(this.fmRunnable);
/*     */       
/* 150 */       for (int i = 0; i < 200 && 
/* 151 */         this.fmStartTime <= 0L; i++) {
/* 152 */         Thread.sleep(10L);
/*     */       }
/* 154 */     } catch (Exception ex) {
/* 155 */       throw new FmException("Failed to start FME:" + this.fmeName, ex);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public synchronized void stop() {
/* 161 */     this.freezeFme.set(true);
/* 162 */     this.stopRequested.set(true);
/*     */     
/* 164 */     this.fmInstancExecutor.shutdownNow();
/*     */     
/* 166 */     for (int i = 0; i < 200 && 
/* 167 */       this.runningTaskCount.get() > 0L; i++) {
/*     */       try {
/* 169 */         Thread.sleep(10L);
/* 170 */       } catch (InterruptedException interruptedException) {}
/*     */     } 
/*     */     
/* 173 */     if (this.fmThreadGroup != null) this.fmThreadGroup.destroyFmThreadGroup();
/*     */   
/*     */   }
/*     */   
/*     */   public synchronized void testStart() throws FmException {
/*     */     try {
/* 179 */       Collection<DynamicConfigDeclare[]> values = (Collection)dynamicConfigDeclaresMap.values();
/* 180 */       logger.debug("DynamicConfigDeclare :{}", values);
/* 181 */       if (values != null) {
/* 182 */         for (DynamicConfigDeclare[] dcd : values) {
/* 183 */           for (int i = 0; i < dcd.length; i++) {
/* 184 */             logger.debug("DynamicConfigDeclare Name:{}, DefaultValue:{}", dcd[i]
/*     */                 
/* 186 */                 .getName(), dcd[i]
/* 187 */                 .getDefaultValue());
/*     */           }
/*     */         } 
/*     */       }
/* 191 */     } catch (Exception exception) {}
/*     */ 
/*     */     
/* 194 */     if (!this.running.get()) {
/* 195 */       startTesting();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void askStopSelf(String reason) {
/* 205 */     logger.warn("FM Instance asks to stop itself, FME: {}, reason: {}", this.fmeName, reason);
/* 206 */     this.selfStop.set(true);
/*     */   }
/*     */   
/*     */   private void setFmeState(FmeState fmeState) {
/* 210 */     if (this.freezeFme.get())
/* 211 */       return;  FmeExeStatus fmeExeStatus = (FmeExeStatus)this.fmeExeStatusMap.get(this.fmeName);
/* 212 */     if (fmeExeStatus != null) {
/* 213 */       fmeExeStatus.setState(fmeState);
/* 214 */       this.fmeExeStatusMap.set(this.fmeName, fmeExeStatus);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static class ExportPerfCounter
/*     */     implements Job
/*     */   {
/*     */     public void execute(JobExecutionContext context) throws JobExecutionException {
/* 223 */       FmeMainBase fmeMainBase = (FmeMainBase)context.getJobDetail().getJobDataMap().get("fmeMainBase");
/* 224 */       fmeMainBase.exportPerfCounters();
/*     */     }
/*     */   }
/*     */   public boolean checkFmKeepRunning() { if (this.stopRequested.get() || this.selfStop.get())
/* 228 */       return false;  return true; } public String getFmeName() { return this.fmeName; } public String getDescription() { return this.description; } public FmeMainBase(String fmeName, String description) { this.fmRunnable = new Runnable()
/*     */       {
/*     */         public void run()
/*     */         {
/*     */           try {
/* 233 */             FmeMainBase.this.init();
/* 234 */             FmeMainBase.this.fmStartTime = System.currentTimeMillis();
/* 235 */             FmeMainBase.this.setFmeState(FmeState.Running);
/*     */             
/* 237 */             JobDataMap jobDataMap = new JobDataMap();
/* 238 */             jobDataMap.put("fmeMainBase", FmeMainBase.this);
/*     */             
/* 240 */             FmeMainBase.this.scheduleJob((Class)FmeMainBase.ExportPerfCounter.class, jobDataMap, "58 * * * * ?");
/* 241 */             FmeMainBase.this.runFm();
/*     */ 
/*     */             
/* 244 */             if (!FmeMainBase.this.stopRequested.get()) {
/* 245 */               FmeMainBase.this.stopRequested.set(true);
/* 246 */               FmeMainBase.this.setFmeState(FmeState.Stop);
/*     */             } 
/* 248 */           } catch (Exception ex) {
/*     */             
/* 250 */             FmeMainBase.this.stopRequested.set(true);
/* 251 */             FmeMainBase.this.setFmeState(FmeState.RuntimeException);
/* 252 */             FmeMainBase.logger.error("Error occured while running FM Instance, FME:{}", FmeMainBase.this.fmeName, ex);
/*     */           } finally {
/* 254 */             for (FmeMainBase.DynaConfigEntryListenerMeta meta : FmeMainBase.this.dynaConfigEntryListenerMetaList) {
/*     */               try {
/* 256 */                 FmeMainBase.this.dynamicConfigMap.removeEntryListener(meta.listenerId);
/* 257 */               } catch (Exception ex) {
/* 258 */                 FmeMainBase.logger.debug("Failed to remove dynamic config entry listener, id={}", meta.listenerId);
/*     */               } 
/*     */             } 
/*     */ 
/*     */ 
/*     */             
/*     */             try {
/* 265 */               FmeMainBase.this.unbindFmRmiService();
/* 266 */             } catch (Exception ex) {
/* 267 */               FmeMainBase.logger.error("Failed to unbind RM RMI Service", ex);
/*     */             } 
/* 269 */             FmeMainBase.this.fmThreadGroup.stopFmThreadGroup();
/* 270 */             FmeMainBase.this.deleteAllJob();
/*     */           } 
/*     */         }
/*     */       };
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
/* 402 */     this.dynaConfigEntryListener = new DynaConfigEntryListener(); this.fmeName = fmeName;
/*     */     this.description = description;
/*     */     this.dynamicConfigMap = HzUtils.getMap((HzDistObjEnum)HzMap.DynamicConfig);
/*     */     this.fmeExeStatusMap = HzUtils.getMap((HzDistObjEnum)HzMap.FmeExeStatus);
/*     */     this.sysPerfLogMap = HzUtils.getMap((HzDistObjEnum)HzMap.SysPerfLog);
/*     */     this.fmResourceMap = HzUtils.getMap((HzDistObjEnum)HzMap.FmResource); }
/*     */   protected Thread createFmThread(Runnable r) { return this.fmThreadGroup.createFmThread(r); }
/*     */   protected void exportResources(Map<String, Serializable> resourceMap) { if (this.freezeFme.get())
/*     */       return; 
/*     */     FmResourceInfo fmResourceInfo = new FmResourceInfo();
/*     */     fmResourceInfo.setResourceMap(resourceMap);
/*     */     fmResourceInfo.setTime(new Date());
/* 414 */     this.fmResourceMap.set(this.fmeName, fmResourceInfo); } protected synchronized void addDyanConfigListener(String... configNames) { for (String configName : configNames)
/* 415 */     { DynamicConfigPk key = new DynamicConfigPk(this.hcceEnv.getCurrentGroupName(), this.fmeName, configName);
/*     */       
/* 417 */       String entryListenerId = this.dynamicConfigMap.addEntryListener((MapListener)this.dynaConfigEntryListener, key, true);
/* 418 */       this.dynaConfigEntryListenerMetaList.add(new DynaConfigEntryListenerMeta(entryListenerId, key)); }  } public void addPerfCollectListener(PerfCollectListener perfCollectListener) { this.perfCollectListenerList.add(perfCollectListener); }
/*     */   public void removePerfCollectListener(PerfCollectListener perfCollectListener) { this.perfCollectListenerList.remove(perfCollectListener); }
/*     */   protected void exportPerfCounters() { if (this.freezeFme.get())
/*     */       return;  synchronized (this.perfCollectListenerList) { for (PerfCollectListener listener : this.perfCollectListenerList)
/*     */         listener.collectPerfCounter(this);  }  long curTime = System.currentTimeMillis(); SysPerfLog sysPerfLog = this.sysPerfLogService.createSysPerfLog(this.fmeName, "Fm", curTime); sysPerfLog.addCounter("elapsedTime", Long.valueOf(curTime - this.fmStartTime)); for (String taskName : this.taskLoadingCalculatorMap.keySet())
/*     */       sysPerfLog.addCounter("task-" + taskName, (Serializable)((TaskLoadingCalculator)this.taskLoadingCalculatorMap.get(taskName)).getTaskLoadingCounter());  sysPerfLog.write(); }
/* 424 */   public synchronized void readdDyanConfigListener() { for (DynaConfigEntryListenerMeta meta : this.dynaConfigEntryListenerMetaList)
/* 425 */       meta.listenerId = this.dynamicConfigMap.addEntryListener((MapListener)this.dynaConfigEntryListener, meta.key, true);  } protected void onDynaConfigRemoved(String key, DynamicConfig dynamicConfig) {} protected void onDynaConfigUpdated(String key, DynamicConfig dynamicConfig) {} class DynaConfigEntryListener implements EntryAddedListener<DynamicConfigPk, DynamicConfig>, EntryRemovedListener<DynamicConfigPk, DynamicConfig>, EntryUpdatedListener<DynamicConfigPk, DynamicConfig> {
/*     */     public void entryUpdated(EntryEvent<DynamicConfigPk, DynamicConfig> event) { if (!FmeMainBase.this.stopRequested.get()) FmeMainBase.this.onDynaConfigUpdated(((DynamicConfigPk)event.getKey()).getName(), (DynamicConfig)event.getValue());  }
/*     */     public void entryRemoved(EntryEvent<DynamicConfigPk, DynamicConfig> event) { if (!FmeMainBase.this.stopRequested.get())
/*     */         FmeMainBase.this.onDynaConfigRemoved(((DynamicConfigPk)event.getKey()).getName(), (DynamicConfig)event.getValue());  }
/*     */     public void entryAdded(EntryEvent<DynamicConfigPk, DynamicConfig> event) { if (!FmeMainBase.this.stopRequested.get())
/*     */         FmeMainBase.this.onDynaConfigUpdated(((DynamicConfigPk)event.getKey()).getName(), (DynamicConfig)event.getValue());  } }
/* 431 */   public DynamicConfig getDynaConfig(String configName) { DynamicConfig dynamicConfig = (DynamicConfig)this.dynamicConfigMap.get(new DynamicConfigPk(this.hcceEnv
/* 432 */           .getCurrentGroupName(), this.fmeName, configName));
/* 433 */     if (dynamicConfig == null) {
/* 434 */       dynamicConfig = this.defaultDynamicConfigMap.get(configName);
/*     */     }
/* 436 */     return dynamicConfig; }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public <T> T getDynaConfig(String configName, Class<T> targetType, T defaultValue) {
/* 442 */     DynamicConfig dynamicConfig = (DynamicConfig)this.dynamicConfigMap.get(new DynamicConfigPk(this.hcceEnv
/* 443 */           .getCurrentGroupName(), this.fmeName, configName));
/* 444 */     if (dynamicConfig == null) {
/* 445 */       dynamicConfig = this.defaultDynamicConfigMap.get(configName);
/*     */     }
/* 447 */     if (dynamicConfig != null) {
/*     */       try {
/* 449 */         return (T)this.conversionService.convert(dynamicConfig.getValue(), targetType);
/* 450 */       } catch (Exception ex) {
/* 451 */         logger.warn("Fail to convert dynamic config value", ex);
/*     */       } 
/*     */     }
/* 454 */     return defaultValue;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setDynaConfig(String key, String value) {
/* 459 */     DynamicConfig dynamicConfig = new DynamicConfig(this.hcceEnv.getCurrentGroupName(), this.fmeName, key, value);
/* 460 */     this.dynamicConfigMap.set(dynamicConfig.getPk(), dynamicConfig);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void rebindFmRmiService(Class<?> serviceInterface) throws RemoteException {
/* 471 */     RmiUtils.rebindRmiService(this.hcceEnv.getNodeName() + "/" + this.fmeName, serviceInterface, this);
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
/*     */   protected <T> void rebindFmRmiService(Class<T> serviceInterface, T serviceInstance) throws RemoteException {
/* 484 */     if (serviceInstance instanceof RemoteInterfaceImpl) {
/* 485 */       ((RemoteInterfaceImpl)serviceInstance).setFmeMainBase(this);
/* 486 */       this.applicationContext.getAutowireCapableBeanFactory().autowireBean(serviceInstance);
/*     */     } else {
/*     */       
/*     */       try {
/* 490 */         RemoteInterfaceImpl base = (RemoteInterfaceImpl)((Advised)serviceInstance).getTargetSource().getTarget();
/* 491 */         base.setFmeMainBase(this);
/* 492 */         this.applicationContext.getAutowireCapableBeanFactory().autowireBean(serviceInstance);
/* 493 */       } catch (Exception ex) {
/* 494 */         throw new RemoteException("Remote Instance of '" + serviceInstance
/*     */             
/* 496 */             .toString() + "' was a JdkDynamicProxy by Spring, but failed to cast to RemoteInterfaceImpl!", ex);
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 501 */     RmiUtils.rebindRmiService(this.hcceEnv
/* 502 */         .getNodeName() + "/" + this.fmeName, serviceInterface, serviceInstance);
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
/*     */   protected <T> void rebindFmRmiService(String serviceName, Class<T> serviceInterface, T serviceInstance) throws RemoteException {
/* 517 */     if (serviceInstance instanceof RemoteInterfaceImpl) {
/* 518 */       ((RemoteInterfaceImpl)serviceInstance).setFmeMainBase(this);
/* 519 */       this.applicationContext.getAutowireCapableBeanFactory().autowireBean(serviceInstance);
/*     */     } else {
/*     */       
/*     */       try {
/* 523 */         RemoteInterfaceImpl base = (RemoteInterfaceImpl)((Advised)serviceInstance).getTargetSource().getTarget();
/* 524 */         base.setFmeMainBase(this);
/* 525 */         this.applicationContext.getAutowireCapableBeanFactory().autowireBean(serviceInstance);
/* 526 */       } catch (Exception ex) {
/* 527 */         throw new RemoteException("Remote Instance of '" + serviceName + "' was a JdkDynamicProxy by Spring, but failed to cast to RemoteInterfaceImpl!", ex);
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 534 */     RmiUtils.rebindRmiService(this.hcceEnv
/* 535 */         .getNodeName() + "/" + this.fmeName + "/" + serviceName, serviceInterface, serviceInstance);
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
/*     */   protected <T> void rebindFmRmiService(String serviceName, int servicePort, Class<T> serviceInterface, T serviceInstance) throws RemoteException {
/* 553 */     if (serviceInstance instanceof RemoteInterfaceImpl) {
/* 554 */       ((RemoteInterfaceImpl)serviceInstance).setFmeMainBase(this);
/* 555 */       this.applicationContext.getAutowireCapableBeanFactory().autowireBean(serviceInstance);
/*     */     } else {
/*     */       
/*     */       try {
/* 559 */         RemoteInterfaceImpl base = (RemoteInterfaceImpl)((Advised)serviceInstance).getTargetSource().getTarget();
/* 560 */         base.setFmeMainBase(this);
/* 561 */         this.applicationContext.getAutowireCapableBeanFactory().autowireBean(serviceInstance);
/* 562 */       } catch (Exception ex) {
/* 563 */         throw new RemoteException("Remote Instance of '" + serviceName + "' was a JdkDynamicProxy by Spring, but failed to cast to RemoteInterfaceImpl!", ex);
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 570 */     RmiUtils.rebindRmiService(this.hcceEnv
/* 571 */         .getNodeName() + "/" + this.fmeName + "/" + serviceName, servicePort, serviceInterface, serviceInstance);
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
/*     */   protected void unbindFmRmiService() throws RemoteException {
/* 583 */     RmiUtils.unbindRmiService(this.hcceEnv.getNodeName() + "/" + this.fmeName);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void unbindFmRmiService(String serviceName) throws RemoteException {
/* 593 */     RmiUtils.unbindRmiService(this.hcceEnv.getNodeName() + "/" + serviceName);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void autowireBean(Object obj) {
/* 602 */     this.applicationContext.getAutowireCapableBeanFactory().autowireBean(obj);
/*     */   }
/*     */   
/*     */   public boolean isInPrimaryGroup() {
/* 606 */     return this.hcceEnv.isInPrimaryGroup();
/*     */   }
/*     */   
/*     */   public long getFmStartTime() {
/* 610 */     return this.fmStartTime;
/*     */   }
/*     */   
/*     */   public boolean isFreezeFme() {
/* 614 */     return this.freezeFme.get();
/*     */   }
/*     */   
/*     */   public void setFreezeFme() {
/* 618 */     logger.info("Freeze {}", this.fmeName);
/* 619 */     this.freezeFme.set(true);
/*     */     
/* 621 */     deleteAllJob();
/*     */   }
/*     */   
/*     */   protected HcceEnv getHcceEnv() {
/* 625 */     return this.hcceEnv;
/*     */   }
/*     */   
/*     */   public String getDynamicConfigValue(DynamicConfig dyanmicConfig) {
/* 629 */     if (dyanmicConfig != null) return dyanmicConfig.getValue(); 
/* 630 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public void addTaskLoadingCalculator(String taskName, TaskLoadingCalculator taskLoadingCalculator) {
/* 635 */     this.taskLoadingCalculatorMap.put(taskName, taskLoadingCalculator);
/*     */   }
/*     */   
/*     */   public TaskLoadingCalculator getTaskLoadingCalculator(String taskName) {
/* 639 */     return this.taskLoadingCalculatorMap.get(taskName);
/*     */   }
/*     */   
/*     */   public void resetTaskLoadingCalculator(String taskName) {
/* 643 */     TaskLoadingCalculator taskLoadingCalculator = this.taskLoadingCalculatorMap.get(taskName);
/* 644 */     if (taskLoadingCalculator != null) {
/* 645 */       taskLoadingCalculator.reset();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void resetTaskLoadingCalculator(Class<? extends Job> jobClass) {
/* 651 */     TaskLoadingCalculator taskLoadingCalculator = this.taskLoadingCalculatorMap.get(jobClass.getSimpleName());
/* 652 */     if (taskLoadingCalculator != null) {
/* 653 */       taskLoadingCalculator.reset();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DynamicConfigDeclare[] getDynamicConfigDeclares() {
/* 663 */     return dynamicConfigDeclaresMap.get(getClass().getName());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static DynamicConfigDeclare[] getDynamicConfigDeclares(String className) {
/* 674 */     return dynamicConfigDeclaresMap.get(className);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static void setDynamicConfigDeclares(DynamicConfigDeclare[] dynamicConfigDeclares) {
/* 683 */     String className = Thread.currentThread().getStackTrace()[2].getClassName();
/* 684 */     dynamicConfigDeclaresMap.put(className, dynamicConfigDeclares);
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
/*     */   public void addOpLog(String userId, String cpeIp, String subSysName, String deviceName, String description, Date operationTime, OperationResult operationResult, String remark) {
/* 696 */     this.opLogger.addSetLog(userId, cpeIp, subSysName, deviceName, description, operationTime, operationResult, remark);
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
/*     */   public void addOpLog(String userId, String cpeIp, String subSysName, String operationItem, String deviceName, String description, Date operationTime, OperationResult operationResult, String remark) {
/* 710 */     this.opLogger.addLog(userId, cpeIp, subSysName, operationItem, deviceName, description, operationTime, operationResult, remark);
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
/*     */   protected long incRunningTaskNo() {
/* 723 */     return this.runningTaskCount.incrementAndGet();
/*     */   }
/*     */   
/*     */   protected long decRunningTaskNo() {
/* 727 */     return this.runningTaskCount.decrementAndGet();
/*     */   }
/*     */   
/*     */   protected long getRunningTaskNo() {
/* 731 */     return this.runningTaskCount.get();
/*     */   }
/*     */   
/*     */   public MessageSourceExt getMessageSourceExt() {
/* 735 */     return this.messageSourceExt;
/*     */   }
/*     */   
/*     */   public BaseOpLogger getOpLogger() {
/* 739 */     return this.opLogger;
/*     */   }
/*     */   
/*     */   public ApplicationContext getApplicationContext() {
/* 743 */     return this.applicationContext;
/*     */   }
/*     */   
/*     */   public abstract void init() throws FmException;
/*     */   
/*     */   protected abstract void runFm() throws Exception;
/*     */   
/*     */   public abstract boolean isAllowConcurrentExecution();
/*     */   
/*     */   public abstract void startTesting() throws FmException;
/*     */ }


/* Location:              C:\User\\user\Desktop\lib\hcce-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\hcce\fme\controller\fm\FmeMainBase.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */