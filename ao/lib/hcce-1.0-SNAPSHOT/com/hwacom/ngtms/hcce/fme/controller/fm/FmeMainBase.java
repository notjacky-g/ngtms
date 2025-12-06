/*     */ package com.hwacom.ngtms.hcce.fme.controller.fm;
/*     */ 
/*     */ import com.hazelcast.core.EntryEvent;
/*     */ import com.hazelcast.core.IMap;
/*     */ import com.hazelcast.map.listener.EntryAddedListener;
/*     */ import com.hazelcast.map.listener.EntryRemovedListener;
/*     */ import com.hazelcast.map.listener.EntryUpdatedListener;
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
/*     */ import org.quartz.JobDetail;
/*     */ import org.quartz.JobExecutionContext;
/*     */ import org.quartz.JobExecutionException;
/*     */ import org.slf4j.Logger;
/*     */ import org.slf4j.LoggerFactory;
/*     */ import org.springframework.aop.TargetSource;
/*     */ import org.springframework.aop.framework.Advised;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
/*     */ import org.springframework.context.ApplicationContext;
/*     */ import org.springframework.core.convert.ConversionService;
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
/*  64 */   private static ConcurrentHashMap<String, DynamicConfigDeclare[]> dynamicConfigDeclaresMap = new ConcurrentHashMap();
/*     */   
/*     */ 
/*  67 */   private HashMap<String, DynamicConfig> defaultDynamicConfigMap = new HashMap();
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
/*  83 */   private ConcurrentHashMap<String, TaskLoadingCalculator> taskLoadingCalculatorMap = new ConcurrentHashMap();
/*     */   
/*     */   protected long fmStartTime;
/*  86 */   private AtomicBoolean freezeFme = new AtomicBoolean();
/*  87 */   private AtomicLong runningTaskCount = new AtomicLong();
/*  88 */   private List<DynaConfigEntryListenerMeta> dynaConfigEntryListenerMetaList = new ArrayList();
/*     */   
/*     */ 
/*  91 */   private List<PerfCollectListener> perfCollectListenerList = Collections.synchronizedList(new ArrayList());
/*     */   
/*     */   @Autowired
/*     */   private HcceEnv hcceEnv;
/*     */   @Autowired
/*     */   protected ApplicationContext applicationContext;
/*     */   @Autowired
/*     */   protected MessageSourceExt messageSourceExt;
/*     */   
/*     */   static class DynaConfigEntryListenerMeta
/*     */   {
/*     */     public DynaConfigEntryListenerMeta(String listenerId, DynamicConfigPk key)
/*     */     {
/* 104 */       this.listenerId = listenerId;
/* 105 */       this.key = key;
/*     */     }
/*     */     
/*     */     String listenerId;
/*     */     DynamicConfigPk key;
/*     */   }
/*     */   
/*     */   public FmeMainBase(String fmeName, String description)
/*     */   {
/* 114 */     this.fmeName = fmeName;
/* 115 */     this.description = description;
/* 116 */     this.dynamicConfigMap = HzUtils.getMap(HzMap.DynamicConfig);
/* 117 */     this.fmeExeStatusMap = HzUtils.getMap(HzMap.FmeExeStatus);
/* 118 */     this.sysPerfLogMap = HzUtils.getMap(HzMap.SysPerfLog);
/* 119 */     this.fmResourceMap = HzUtils.getMap(HzMap.FmResource);
/*     */   }
/*     */   
/*     */   public void initFmeMain(ThreadGroup threadGroup, String fmClassName) {
/* 123 */     DynamicConfigDeclare[] dynamicConfigDeclares = (DynamicConfigDeclare[])dynamicConfigDeclaresMap.get(fmClassName);
/* 124 */     if (dynamicConfigDeclares != null) {
/* 125 */       for (DynamicConfigDeclare dynamicConfigDeclare : dynamicConfigDeclares)
/* 126 */         if (dynamicConfigDeclare.getDefaultValue() != null)
/*     */         {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 132 */           DynamicConfig dynamicConfig = new DynamicConfig(this.hcceEnv.getCurrentGroupName(), this.fmeName, dynamicConfigDeclare.getName(), dynamicConfigDeclare.getDefaultValue());
/* 133 */           this.defaultDynamicConfigMap.put(dynamicConfig.getName(), dynamicConfig);
/*     */         }
/*     */     }
/* 136 */     this.fmThreadGroup = new FmThreadGroup(this.fmeName, threadGroup);
/* 137 */     this.fmInstancExecutor = Executors.newSingleThreadExecutor(this.fmThreadGroup.getFmThreadFactory());
/*     */   }
/*     */   
/*     */   public synchronized void start() throws FmException
/*     */   {
/* 142 */     if (this.running.get()) return;
/*     */     try {
/* 144 */       this.stopRequested.set(false);
/* 145 */       this.freezeFme.set(false);
/* 146 */       this.selfStop.set(false);
/* 147 */       this.running.set(true);
/* 148 */       this.fmInstancExecutor.execute(this.fmRunnable);
/*     */       
/* 150 */       for (int i = 0; i < 200; i++) {
/* 151 */         if (this.fmStartTime > 0L) break;
/* 152 */         Thread.sleep(10L);
/*     */       }
/*     */     } catch (Exception ex) {
/* 155 */       throw new FmException("Failed to start FME:" + this.fmeName, ex);
/*     */     }
/*     */   }
/*     */   
/*     */   public synchronized void stop()
/*     */   {
/* 161 */     this.freezeFme.set(true);
/* 162 */     this.stopRequested.set(true);
/*     */     
/* 164 */     this.fmInstancExecutor.shutdownNow();
/*     */     
/* 166 */     for (int i = 0; i < 200; i++) {
/* 167 */       if (this.runningTaskCount.get() <= 0L)
/*     */         break;
/* 169 */       try { Thread.sleep(10L);
/*     */       }
/*     */       catch (InterruptedException localInterruptedException) {}
/*     */     }
/* 173 */     if (this.fmThreadGroup != null) this.fmThreadGroup.destroyFmThreadGroup();
/*     */   }
/*     */   
/*     */   public synchronized void testStart() throws FmException
/*     */   {
/*     */     try {
/* 179 */       Collection<DynamicConfigDeclare[]> values = dynamicConfigDeclaresMap.values();
/* 180 */       logger.debug("DynamicConfigDeclare :{}", values);
/* 181 */       if (values != null) {
/* 182 */         for (DynamicConfigDeclare[] dcd : values) {
/* 183 */           for (int i = 0; i < dcd.length; i++) {
/* 184 */             logger.debug("DynamicConfigDeclare Name:{}, DefaultValue:{}", dcd[i]
/*     */             
/* 186 */               .getName(), dcd[i]
/* 187 */               .getDefaultValue());
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */     catch (Exception localException) {}
/*     */     
/* 194 */     if (!this.running.get())
/* 195 */       startTesting();
/*     */   }
/*     */   
/*     */   @Autowired
/*     */   protected BaseOpLogger opLogger;
/*     */   @Autowired
/*     */   private ConversionService conversionService;
/*     */   @Autowired
/*     */   private SysPerfLogService sysPerfLogService;
/*     */   protected void askStopSelf(String reason) {
/* 205 */     logger.warn("FM Instance asks to stop itself, FME: {}, reason: {}", this.fmeName, reason);
/* 206 */     this.selfStop.set(true);
/*     */   }
/*     */   
/*     */   private void setFmeState(FmeState fmeState) {
/* 210 */     if (this.freezeFme.get()) return;
/* 211 */     FmeExeStatus fmeExeStatus = (FmeExeStatus)this.fmeExeStatusMap.get(this.fmeName);
/* 212 */     if (fmeExeStatus != null) {
/* 213 */       fmeExeStatus.setState(fmeState);
/* 214 */       this.fmeExeStatusMap.set(this.fmeName, fmeExeStatus);
/*     */     }
/*     */   }
/*     */   
/*     */   public static class ExportPerfCounter
/*     */     implements Job
/*     */   {
/*     */     public void execute(JobExecutionContext context) throws JobExecutionException
/*     */     {
/* 223 */       FmeMainBase fmeMainBase = (FmeMainBase)context.getJobDetail().getJobDataMap().get("fmeMainBase");
/* 224 */       fmeMainBase.exportPerfCounters();
/*     */     }
/*     */   }
/*     */   
/* 228 */   private final Runnable fmRunnable = new Runnable()
/*     */   {
/*     */     public void run()
/*     */     {
/*     */       try {
/* 233 */         FmeMainBase.this.init();
/* 234 */         FmeMainBase.this.fmStartTime = System.currentTimeMillis();
/* 235 */         FmeMainBase.this.setFmeState(FmeState.Running);
/*     */         
/* 237 */         jobDataMap = new JobDataMap();
/* 238 */         jobDataMap.put("fmeMainBase", FmeMainBase.this);
/*     */         
/* 240 */         FmeMainBase.this.scheduleJob(FmeMainBase.ExportPerfCounter.class, jobDataMap, "58 * * * * ?");
/* 241 */         FmeMainBase.this.runFm();
/*     */         
/*     */ 
/* 244 */         if (!FmeMainBase.this.stopRequested.get()) {
/* 245 */           FmeMainBase.this.stopRequested.set(true);
/* 246 */           FmeMainBase.this.setFmeState(FmeState.Stop);
/*     */         }
/*     */       } catch (Exception ex) { JobDataMap jobDataMap;
/*     */         FmeMainBase.DynaConfigEntryListenerMeta meta;
/* 250 */         FmeMainBase.this.stopRequested.set(true);
/* 251 */         FmeMainBase.this.setFmeState(FmeState.RuntimeException);
/* 252 */         FmeMainBase.logger.error("Error occured while running FM Instance, FME:{}", FmeMainBase.this.fmeName, ex);
/*     */       } finally { FmeMainBase.DynaConfigEntryListenerMeta meta;
/* 254 */         for (FmeMainBase.DynaConfigEntryListenerMeta meta : FmeMainBase.this.dynaConfigEntryListenerMetaList) {
/*     */           try {
/* 256 */             FmeMainBase.this.dynamicConfigMap.removeEntryListener(meta.listenerId);
/*     */           } catch (Exception ex) {
/* 258 */             FmeMainBase.logger.debug("Failed to remove dynamic config entry listener, id={}", meta.listenerId);
/*     */           }
/*     */         }
/*     */         
/*     */ 
/*     */         try
/*     */         {
/* 265 */           FmeMainBase.this.unbindFmRmiService();
/*     */         } catch (Exception ex) {
/* 267 */           FmeMainBase.logger.error("Failed to unbind RM RMI Service", ex);
/*     */         }
/* 269 */         FmeMainBase.this.fmThreadGroup.stopFmThreadGroup();
/* 270 */         FmeMainBase.this.deleteAllJob();
/*     */       }
/*     */     }
/*     */   };
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean checkFmKeepRunning()
/*     */   {
/* 281 */     if ((this.stopRequested.get()) || (this.selfStop.get())) return false;
/* 282 */     return true;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public abstract void init()
/*     */     throws FmException;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected abstract void runFm()
/*     */     throws Exception;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public abstract boolean isAllowConcurrentExecution();
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public abstract void startTesting()
/*     */     throws FmException;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public String getFmeName()
/*     */   {
/* 315 */     return this.fmeName;
/*     */   }
/*     */   
/*     */   public String getDescription() {
/* 319 */     return this.description;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected Thread createFmThread(Runnable r)
/*     */   {
/* 329 */     return this.fmThreadGroup.createFmThread(r);
/*     */   }
/*     */   
/*     */   protected void exportResources(Map<String, Serializable> resourceMap) {
/* 333 */     if (this.freezeFme.get()) return;
/* 334 */     FmResourceInfo fmResourceInfo = new FmResourceInfo();
/* 335 */     fmResourceInfo.setResourceMap(resourceMap);
/* 336 */     fmResourceInfo.setTime(new Date());
/* 337 */     this.fmResourceMap.set(this.fmeName, fmResourceInfo);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void addPerfCollectListener(PerfCollectListener perfCollectListener)
/*     */   {
/* 347 */     this.perfCollectListenerList.add(perfCollectListener);
/*     */   }
/*     */   
/*     */   public void removePerfCollectListener(PerfCollectListener perfCollectListener) {
/* 351 */     this.perfCollectListenerList.remove(perfCollectListener);
/*     */   }
/*     */   
/*     */   protected void exportPerfCounters() {
/* 355 */     if (this.freezeFme.get()) return;
/* 356 */     synchronized (this.perfCollectListenerList) {
/* 357 */       for (PerfCollectListener listener : this.perfCollectListenerList) {
/* 358 */         listener.collectPerfCounter(this);
/*     */       }
/*     */     }
/* 361 */     long curTime = System.currentTimeMillis();
/* 362 */     SysPerfLog sysPerfLog = this.sysPerfLogService.createSysPerfLog(this.fmeName, "Fm", curTime);
/* 363 */     sysPerfLog.addCounter("elapsedTime", Long.valueOf(curTime - this.fmStartTime));
/* 364 */     for (String taskName : this.taskLoadingCalculatorMap.keySet()) {
/* 365 */       sysPerfLog.addCounter("task-" + taskName, 
/* 366 */         ((TaskLoadingCalculator)this.taskLoadingCalculatorMap.get(taskName)).getTaskLoadingCounter());
/*     */     }
/* 368 */     sysPerfLog.write();
/*     */   }
/*     */   
/*     */   protected void onDynaConfigRemoved(String key, DynamicConfig dynamicConfig) {}
/*     */   
/*     */   protected void onDynaConfigUpdated(String key, DynamicConfig dynamicConfig) {}
/*     */   
/*     */   class DynaConfigEntryListener
/*     */     implements EntryAddedListener<DynamicConfigPk, DynamicConfig>, EntryRemovedListener<DynamicConfigPk, DynamicConfig>, EntryUpdatedListener<DynamicConfigPk, DynamicConfig>
/*     */   {
/*     */     DynaConfigEntryListener() {}
/*     */     
/*     */     public void entryUpdated(EntryEvent<DynamicConfigPk, DynamicConfig> event)
/*     */     {
/* 382 */       if (!FmeMainBase.this.stopRequested.get()) {
/* 383 */         FmeMainBase.this.onDynaConfigUpdated(((DynamicConfigPk)event.getKey()).getName(), (DynamicConfig)event.getValue());
/*     */       }
/*     */     }
/*     */     
/*     */     public void entryRemoved(EntryEvent<DynamicConfigPk, DynamicConfig> event)
/*     */     {
/* 389 */       if (!FmeMainBase.this.stopRequested.get()) {
/* 390 */         FmeMainBase.this.onDynaConfigRemoved(((DynamicConfigPk)event.getKey()).getName(), (DynamicConfig)event.getValue());
/*     */       }
/*     */     }
/*     */     
/*     */     public void entryAdded(EntryEvent<DynamicConfigPk, DynamicConfig> event)
/*     */     {
/* 396 */       if (!FmeMainBase.this.stopRequested.get()) {
/* 397 */         FmeMainBase.this.onDynaConfigUpdated(((DynamicConfigPk)event.getKey()).getName(), (DynamicConfig)event.getValue());
/*     */       }
/*     */     }
/*     */   }
/*     */   
/* 402 */   DynaConfigEntryListener dynaConfigEntryListener = new DynaConfigEntryListener();
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected synchronized void addDyanConfigListener(String... configNames)
/*     */   {
/* 414 */     for (String configName : configNames) {
/* 415 */       DynamicConfigPk key = new DynamicConfigPk(this.hcceEnv.getCurrentGroupName(), this.fmeName, configName);
/*     */       
/* 417 */       String entryListenerId = this.dynamicConfigMap.addEntryListener(this.dynaConfigEntryListener, key, true);
/* 418 */       this.dynaConfigEntryListenerMetaList.add(new DynaConfigEntryListenerMeta(entryListenerId, key));
/*     */     }
/*     */   }
/*     */   
/*     */   public synchronized void readdDyanConfigListener()
/*     */   {
/* 424 */     for (DynaConfigEntryListenerMeta meta : this.dynaConfigEntryListenerMetaList) {
/* 425 */       meta.listenerId = this.dynamicConfigMap.addEntryListener(this.dynaConfigEntryListener, meta.key, true);
/*     */     }
/*     */   }
/*     */   
/*     */   public DynamicConfig getDynaConfig(String configName)
/*     */   {
/* 431 */     DynamicConfig dynamicConfig = (DynamicConfig)this.dynamicConfigMap.get(new DynamicConfigPk(this.hcceEnv
/* 432 */       .getCurrentGroupName(), this.fmeName, configName));
/* 433 */     if (dynamicConfig == null) {
/* 434 */       dynamicConfig = (DynamicConfig)this.defaultDynamicConfigMap.get(configName);
/*     */     }
/* 436 */     return dynamicConfig;
/*     */   }
/*     */   
/*     */ 
/*     */   public <T> T getDynaConfig(String configName, Class<T> targetType, T defaultValue)
/*     */   {
/* 442 */     DynamicConfig dynamicConfig = (DynamicConfig)this.dynamicConfigMap.get(new DynamicConfigPk(this.hcceEnv
/* 443 */       .getCurrentGroupName(), this.fmeName, configName));
/* 444 */     if (dynamicConfig == null) {
/* 445 */       dynamicConfig = (DynamicConfig)this.defaultDynamicConfigMap.get(configName);
/*     */     }
/* 447 */     if (dynamicConfig != null) {
/*     */       try {
/* 449 */         return (T)this.conversionService.convert(dynamicConfig.getValue(), targetType);
/*     */       } catch (Exception ex) {
/* 451 */         logger.warn("Fail to convert dynamic config value", ex);
/*     */       }
/*     */     }
/* 454 */     return defaultValue;
/*     */   }
/*     */   
/*     */   public void setDynaConfig(String key, String value)
/*     */   {
/* 459 */     DynamicConfig dynamicConfig = new DynamicConfig(this.hcceEnv.getCurrentGroupName(), this.fmeName, key, value);
/* 460 */     this.dynamicConfigMap.set(dynamicConfig.getPk(), dynamicConfig);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected void rebindFmRmiService(Class<?> serviceInterface)
/*     */     throws RemoteException
/*     */   {
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
/*     */   protected <T> void rebindFmRmiService(Class<T> serviceInterface, T serviceInstance)
/*     */     throws RemoteException
/*     */   {
/* 484 */     if ((serviceInstance instanceof RemoteInterfaceImpl)) {
/* 485 */       ((RemoteInterfaceImpl)serviceInstance).setFmeMainBase(this);
/* 486 */       this.applicationContext.getAutowireCapableBeanFactory().autowireBean(serviceInstance);
/*     */     }
/*     */     else {
/*     */       try {
/* 490 */         RemoteInterfaceImpl base = (RemoteInterfaceImpl)((Advised)serviceInstance).getTargetSource().getTarget();
/* 491 */         base.setFmeMainBase(this);
/* 492 */         this.applicationContext.getAutowireCapableBeanFactory().autowireBean(serviceInstance);
/*     */       }
/*     */       catch (Exception ex)
/*     */       {
/* 496 */         throw new RemoteException("Remote Instance of '" + serviceInstance.toString() + "' was a JdkDynamicProxy by Spring, but failed to cast to RemoteInterfaceImpl!", ex);
/*     */       }
/*     */     }
/*     */     
/*     */ 
/* 501 */     RmiUtils.rebindRmiService(
/* 502 */       this.hcceEnv.getNodeName() + "/" + this.fmeName, serviceInterface, serviceInstance);
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
/*     */   protected <T> void rebindFmRmiService(String serviceName, Class<T> serviceInterface, T serviceInstance)
/*     */     throws RemoteException
/*     */   {
/* 517 */     if ((serviceInstance instanceof RemoteInterfaceImpl)) {
/* 518 */       ((RemoteInterfaceImpl)serviceInstance).setFmeMainBase(this);
/* 519 */       this.applicationContext.getAutowireCapableBeanFactory().autowireBean(serviceInstance);
/*     */     }
/*     */     else {
/*     */       try {
/* 523 */         RemoteInterfaceImpl base = (RemoteInterfaceImpl)((Advised)serviceInstance).getTargetSource().getTarget();
/* 524 */         base.setFmeMainBase(this);
/* 525 */         this.applicationContext.getAutowireCapableBeanFactory().autowireBean(serviceInstance);
/*     */       } catch (Exception ex) {
/* 527 */         throw new RemoteException("Remote Instance of '" + serviceName + "' was a JdkDynamicProxy by Spring, but failed to cast to RemoteInterfaceImpl!", ex);
/*     */       }
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 534 */     RmiUtils.rebindRmiService(
/* 535 */       this.hcceEnv.getNodeName() + "/" + this.fmeName + "/" + serviceName, serviceInterface, serviceInstance);
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
/*     */   protected <T> void rebindFmRmiService(String serviceName, int servicePort, Class<T> serviceInterface, T serviceInstance)
/*     */     throws RemoteException
/*     */   {
/* 553 */     if ((serviceInstance instanceof RemoteInterfaceImpl)) {
/* 554 */       ((RemoteInterfaceImpl)serviceInstance).setFmeMainBase(this);
/* 555 */       this.applicationContext.getAutowireCapableBeanFactory().autowireBean(serviceInstance);
/*     */     }
/*     */     else {
/*     */       try {
/* 559 */         RemoteInterfaceImpl base = (RemoteInterfaceImpl)((Advised)serviceInstance).getTargetSource().getTarget();
/* 560 */         base.setFmeMainBase(this);
/* 561 */         this.applicationContext.getAutowireCapableBeanFactory().autowireBean(serviceInstance);
/*     */       } catch (Exception ex) {
/* 563 */         throw new RemoteException("Remote Instance of '" + serviceName + "' was a JdkDynamicProxy by Spring, but failed to cast to RemoteInterfaceImpl!", ex);
/*     */       }
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 570 */     RmiUtils.rebindRmiService(
/* 571 */       this.hcceEnv.getNodeName() + "/" + this.fmeName + "/" + serviceName, servicePort, serviceInterface, serviceInstance);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected void unbindFmRmiService()
/*     */     throws RemoteException
/*     */   {
/* 583 */     RmiUtils.unbindRmiService(this.hcceEnv.getNodeName() + "/" + this.fmeName);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected void unbindFmRmiService(String serviceName)
/*     */     throws RemoteException
/*     */   {
/* 593 */     RmiUtils.unbindRmiService(this.hcceEnv.getNodeName() + "/" + serviceName);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected void autowireBean(Object obj)
/*     */   {
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
/*     */   public void addTaskLoadingCalculator(String taskName, TaskLoadingCalculator taskLoadingCalculator)
/*     */   {
/* 635 */     this.taskLoadingCalculatorMap.put(taskName, taskLoadingCalculator);
/*     */   }
/*     */   
/*     */   public TaskLoadingCalculator getTaskLoadingCalculator(String taskName) {
/* 639 */     return (TaskLoadingCalculator)this.taskLoadingCalculatorMap.get(taskName);
/*     */   }
/*     */   
/*     */   public void resetTaskLoadingCalculator(String taskName) {
/* 643 */     TaskLoadingCalculator taskLoadingCalculator = (TaskLoadingCalculator)this.taskLoadingCalculatorMap.get(taskName);
/* 644 */     if (taskLoadingCalculator != null) {
/* 645 */       taskLoadingCalculator.reset();
/*     */     }
/*     */   }
/*     */   
/*     */   public void resetTaskLoadingCalculator(Class<? extends Job> jobClass)
/*     */   {
/* 651 */     TaskLoadingCalculator taskLoadingCalculator = (TaskLoadingCalculator)this.taskLoadingCalculatorMap.get(jobClass.getSimpleName());
/* 652 */     if (taskLoadingCalculator != null) {
/* 653 */       taskLoadingCalculator.reset();
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public DynamicConfigDeclare[] getDynamicConfigDeclares()
/*     */   {
/* 663 */     return (DynamicConfigDeclare[])dynamicConfigDeclaresMap.get(getClass().getName());
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static DynamicConfigDeclare[] getDynamicConfigDeclares(String className)
/*     */   {
/* 674 */     return (DynamicConfigDeclare[])dynamicConfigDeclaresMap.get(className);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected static void setDynamicConfigDeclares(DynamicConfigDeclare[] dynamicConfigDeclares)
/*     */   {
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
/*     */   public void addOpLog(String userId, String cpeIp, String subSysName, String deviceName, String description, Date operationTime, OperationResult operationResult, String remark)
/*     */   {
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
/*     */   public void addOpLog(String userId, String cpeIp, String subSysName, String operationItem, String deviceName, String description, Date operationTime, OperationResult operationResult, String remark)
/*     */   {
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
/*     */   protected long incRunningTaskNo()
/*     */   {
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
/*     */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\hcce-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\hcce\fme\controller\fm\FmeMainBase.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */