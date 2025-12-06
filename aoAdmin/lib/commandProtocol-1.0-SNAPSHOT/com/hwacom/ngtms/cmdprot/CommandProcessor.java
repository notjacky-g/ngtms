/*     */ package com.hwacom.ngtms.cmdprot;
/*     */ 
/*     */ import com.hwacom.ngtms.cmdprot.cmdfmtdef.CommandExecutionException;
/*     */ import io.netty.util.concurrent.Promise;
/*     */ import io.netty.util.concurrent.ScheduledFuture;
/*     */ import java.lang.reflect.Method;
/*     */ import java.util.HashMap;
/*     */ import java.util.concurrent.ExecutorService;
/*     */ import java.util.concurrent.Executors;
/*     */ import java.util.concurrent.ScheduledExecutorService;
/*     */ import java.util.concurrent.ScheduledFuture;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ import org.slf4j.Logger;
/*     */ import org.slf4j.LoggerFactory;
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
/*     */ public abstract class CommandProcessor
/*     */ {
/*  27 */   private static Logger logger = LoggerFactory.getLogger(CommandProcessor.class);
/*  28 */   private HashMap<String, MeothdMapping> cmdMethodMap = new HashMap<>();
/*  29 */   private HashMap<String, MeothdMapping> scheduleMethodMap = new HashMap<>();
/*     */   protected ExecutorService executorService;
/*     */   private ScheduledExecutorService scheduledExecutorService;
/*  32 */   protected int scheduleServiceCorePoolSize = 3;
/*     */ 
/*     */   
/*     */   static class MeothdMapping
/*     */   {
/*     */     String name;
/*     */     
/*     */     boolean async;
/*     */     Method method;
/*     */     int paramNumbers;
/*     */     long paramPattern;
/*     */     boolean returnObject;
/*     */   }
/*     */   
/*     */   public void init() throws Exception {
/*  47 */     scanCommandMethod(getClass());
/*  48 */     this.executorService = Executors.newCachedThreadPool();
/*     */   }
/*     */   
/*     */   public void destroy() {
/*  52 */     this.executorService.shutdownNow();
/*  53 */     if (this.scheduledExecutorService != null) this.scheduledExecutorService.shutdownNow();
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void scanCommandMethod(Class<?> theClass) throws Exception {
/*  63 */     if (theClass.equals(CommandProcessor.class))
/*  64 */       return;  Method[] methods = theClass.getDeclaredMethods();
/*  65 */     boolean hasScheduleMethod = false;
/*  66 */     for (Method method : methods) {
/*  67 */       CommandMethod commandMethod = method.<CommandMethod>getAnnotation(CommandMethod.class);
/*  68 */       if (commandMethod != null) {
/*  69 */         Class<?>[] paramClasses = method.getParameterTypes();
/*  70 */         int paramNumbers = 0;
/*  71 */         long paramPattern = 0L;
/*  72 */         for (Class<?> paramClasse : paramClasses) {
/*  73 */           if (CommandMessage.class.isAssignableFrom(paramClasse)) {
/*     */ 
/*     */             
/*  76 */             paramPattern |= (1 << paramNumbers * 4);
/*  77 */             paramNumbers++;
/*  78 */           } else if (Promise.class.isAssignableFrom(paramClasse)) {
/*  79 */             paramPattern |= (2 << paramNumbers * 4);
/*  80 */             paramNumbers++;
/*  81 */           } else if (ConnectionSession.class.isAssignableFrom(paramClasse)) {
/*  82 */             paramPattern |= (3 << paramNumbers * 4);
/*  83 */             paramNumbers++;
/*  84 */           } else if (CommandSession.class.isAssignableFrom(paramClasse)) {
/*  85 */             paramPattern |= (4 << paramNumbers * 4);
/*  86 */             paramNumbers++;
/*     */           } else {
/*  88 */             logger.error("Bad Command Method: {}, unsupported parameter type: {}", method
/*     */                 
/*  90 */                 .getName(), paramClasse
/*  91 */                 .getName());
/*     */           } 
/*     */         } 
/*     */         
/*  95 */         if (!method.getReturnType().equals(void.class)) {
/*  96 */           logger.warn("Bad Command Method: {}, a Command Method should not return result", method
/*     */               
/*  98 */               .getName());
/*     */           continue;
/*     */         } 
/* 101 */         method.setAccessible(true);
/* 102 */         String cmdName = commandMethod.name();
/* 103 */         if (cmdName.length() == 0) cmdName = method.getName();
/*     */         
/* 105 */         if (this.cmdMethodMap.get(cmdName) != null)
/* 106 */           continue;  MeothdMapping meothdMapping = new MeothdMapping();
/* 107 */         meothdMapping.name = cmdName;
/* 108 */         meothdMapping.async = commandMethod.async();
/* 109 */         meothdMapping.method = method;
/* 110 */         meothdMapping.paramNumbers = paramNumbers;
/* 111 */         meothdMapping.paramPattern = paramPattern;
/* 112 */         this.cmdMethodMap.put(cmdName, meothdMapping);
/*     */       } 
/* 114 */       ScheduleMethod sechduleMethod = method.<ScheduleMethod>getAnnotation(ScheduleMethod.class);
/* 115 */       if (sechduleMethod != null) {
/* 116 */         hasScheduleMethod = true;
/* 117 */         Class<?>[] paramClasses = method.getParameterTypes();
/* 118 */         if (paramClasses.length != 0) {
/* 119 */           logger.warn("Bad Schedule Method: {}, a Schedule Method must have no parameter", method
/*     */               
/* 121 */               .getName());
/*     */         }
/*     */         else {
/*     */           
/* 125 */           method.setAccessible(true);
/* 126 */           String cmdName = sechduleMethod.name();
/* 127 */           if (cmdName.length() == 0) cmdName = method.getName();
/*     */           
/* 129 */           if (this.scheduleMethodMap.get(cmdName) == null)
/* 130 */           { MeothdMapping meothdMapping = new MeothdMapping();
/* 131 */             meothdMapping.name = cmdName;
/* 132 */             meothdMapping.async = sechduleMethod.async();
/* 133 */             meothdMapping.method = method;
/* 134 */             if (!method.getReturnType().equals(void.class)) {
/* 135 */               meothdMapping.returnObject = true;
/*     */             }
/* 137 */             this.scheduleMethodMap.put(cmdName, meothdMapping); } 
/*     */         } 
/*     */       }  continue;
/* 140 */     }  if (hasScheduleMethod && this.scheduledExecutorService == null) {
/* 141 */       this.scheduledExecutorService = Executors.newScheduledThreadPool(this.scheduleServiceCorePoolSize);
/*     */     }
/* 143 */     scanCommandMethod(theClass.getSuperclass());
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
/*     */   public void addScheduleTask(final ConnectionSession connectionSession, final String scheduleTaskName, long delay, TimeUnit unit) {
/*     */     ScheduledFuture scheduledFuture;
/* 190 */     final MeothdMapping meothdMapping = this.scheduleMethodMap.get(scheduleTaskName);
/* 191 */     if (meothdMapping == null) {
/* 192 */       logger.error("The correspond method of schedule task name: {} not found", scheduleTaskName);
/*     */       return;
/*     */     } 
/* 195 */     Runnable runnable = new Runnable()
/*     */       {
/*     */         public void run()
/*     */         {
/* 199 */           Object result = null;
/*     */           try {
/* 201 */             result = meothdMapping.method.invoke(CommandProcessor.this, new Object[0]);
/* 202 */           } catch (Exception ex) {
/* 203 */             result = ex;
/*     */           } finally {
/* 205 */             connectionSession.removeScheduledFuture(scheduleTaskName);
/* 206 */             if (meothdMapping.returnObject) {
/* 207 */               CommandProcessor.this.onScheduleCommandComplete(connectionSession, scheduleTaskName, result);
/*     */             }
/*     */           } 
/*     */         }
/*     */       };
/*     */     
/* 213 */     if (meothdMapping.async) {
/* 214 */       ScheduledFuture<?> scheduledFuture1 = this.scheduledExecutorService.schedule(runnable, delay, unit);
/*     */     } else {
/* 216 */       scheduledFuture = connectionSession.getCtx().executor().schedule(runnable, delay, unit);
/*     */     } 
/* 218 */     connectionSession.addScheduledFuture(scheduleTaskName, (ScheduledFuture<?>)scheduledFuture);
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
/*     */   public void addScheduleAtFixedRate(final ConnectionSession connectionSession, final String scheduleTaskName, long initialDelay, long period, TimeUnit unit) {
/*     */     ScheduledFuture scheduledFuture;
/* 236 */     final MeothdMapping meothdMapping = this.scheduleMethodMap.get(scheduleTaskName);
/* 237 */     if (meothdMapping == null) {
/* 238 */       logger.error("The correspond method of schedule task name: {} not found", scheduleTaskName);
/*     */       return;
/*     */     } 
/* 241 */     Runnable runnable = new Runnable()
/*     */       {
/*     */         public void run()
/*     */         {
/* 245 */           Object result = null;
/*     */           try {
/* 247 */             result = meothdMapping.method.invoke(CommandProcessor.this, new Object[0]);
/* 248 */           } catch (Exception ex) {
/* 249 */             result = ex;
/*     */           } finally {
/* 251 */             if (meothdMapping.returnObject) {
/* 252 */               CommandProcessor.this.onScheduleCommandComplete(connectionSession, scheduleTaskName, result);
/*     */             }
/*     */           } 
/*     */         }
/*     */       };
/*     */     
/* 258 */     if (meothdMapping.async) {
/*     */       
/* 260 */       ScheduledFuture<?> scheduledFuture1 = this.scheduledExecutorService.scheduleAtFixedRate(runnable, initialDelay, period, unit);
/*     */     
/*     */     }
/*     */     else {
/*     */ 
/*     */       
/* 266 */       scheduledFuture = connectionSession.getCtx().executor().scheduleAtFixedRate(runnable, initialDelay, period, unit);
/*     */     } 
/* 268 */     connectionSession.addScheduledFuture(scheduleTaskName, (ScheduledFuture<?>)scheduledFuture);
/*     */   }
/*     */   
/*     */   public void removeSchedule(ConnectionSession connectionSession, String taskName) {
/* 272 */     connectionSession.removeScheduledFuture(taskName);
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
/*     */   public void invokeCommandMethod(CommandMessage commandMessage, Promise<Object> promise) {
/* 307 */     invokeCommandMethod(commandMessage, null, null, promise);
/*     */   }
/*     */   
/*     */   public void invokeCommandMethod(CommandMessage commandMessage) {
/* 311 */     invokeCommandMethod(commandMessage, null, null, null);
/*     */   }
/*     */   
/*     */   public void invokeCommandMethod(CommandMessage commandMessage, CommandSession commandSession) {
/* 315 */     invokeCommandMethod(commandMessage, null, commandSession, null);
/*     */   }
/*     */ 
/*     */   
/*     */   public void invokeCommandMethod(CommandMessage commandMessage, ConnectionSession connectionSession) {
/* 320 */     invokeCommandMethod(commandMessage, connectionSession, null, null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void invokeCommandMethod(CommandMessage commandMessage, ConnectionSession connectionSession, CommandSession commandSession) {
/* 327 */     invokeCommandMethod(commandMessage, connectionSession, commandSession, null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void invokeCommandMethod(final CommandMessage commandMessage, final ConnectionSession connectionSession, final CommandSession commandSession, final Promise<Object> promise) {
/* 335 */     final MeothdMapping meothdMapping = this.cmdMethodMap.get(commandMessage.getCmdName());
/*     */     try {
/* 337 */       if (meothdMapping != null) {
/* 338 */         if (meothdMapping.async) {
/* 339 */           this.executorService.execute(new Runnable()
/*     */               {
/*     */                 public void run()
/*     */                 {
/*     */                   try {
/* 344 */                     Object[] params = new Object[meothdMapping.paramNumbers];
/* 345 */                     for (int i = 0; i < meothdMapping.paramNumbers; i++) {
/* 346 */                       int classType = (int)(meothdMapping.paramPattern >> i * 4 & 0xFL);
/* 347 */                       switch (classType) {
/*     */                         case 1:
/* 349 */                           params[i] = commandMessage;
/*     */                           break;
/*     */                         case 2:
/* 352 */                           params[i] = promise;
/*     */                           break;
/*     */                         case 3:
/* 355 */                           params[i] = connectionSession;
/*     */                           break;
/*     */                         case 4:
/* 358 */                           params[i] = commandSession;
/*     */                           break;
/*     */                       } 
/*     */                     
/*     */                     } 
/* 363 */                     meothdMapping.method.invoke(CommandProcessor.this, params);
/* 364 */                   } catch (Exception ex) {
/* 365 */                     CommandProcessor.logger.error("Invoke Command Methdod {} failed", meothdMapping.name, ex);
/* 366 */                     promise.setFailure(ex);
/*     */                   } 
/*     */                 }
/*     */               });
/*     */         } else {
/* 371 */           Object[] params = new Object[meothdMapping.paramNumbers];
/* 372 */           for (int i = 0; i < meothdMapping.paramNumbers; i++) {
/* 373 */             int classType = (int)(meothdMapping.paramPattern >> i * 4 & 0xFL);
/* 374 */             switch (classType) {
/*     */               case 1:
/* 376 */                 params[i] = commandMessage;
/*     */                 break;
/*     */               case 2:
/* 379 */                 params[i] = promise;
/*     */                 break;
/*     */               case 3:
/* 382 */                 params[i] = connectionSession;
/*     */                 break;
/*     */               case 4:
/* 385 */                 params[i] = commandSession;
/*     */                 break;
/*     */             } 
/*     */           
/*     */           } 
/* 390 */           meothdMapping.method.invoke(this, params);
/*     */         } 
/*     */       } else {
/* 393 */         throw new CommandExecutionException("Command " + commandMessage
/* 394 */             .getCmdName() + " not implemented!");
/*     */       } 
/* 396 */     } catch (Exception ex) {
/* 397 */       logger.error("Invoke Command Methdod {} failed", commandMessage.getCmdName(), ex);
/* 398 */       if (promise != null) promise.setFailure(ex); 
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean isImplemented(String cmdName) {
/* 403 */     MeothdMapping meothdMapping = this.cmdMethodMap.get(cmdName);
/* 404 */     if (meothdMapping != null) return true; 
/* 405 */     return false;
/*     */   }
/*     */   
/*     */   protected abstract void onScheduleCommandComplete(ConnectionSession paramConnectionSession, String paramString, Object paramObject);
/*     */ }


/* Location:              C:\User\\user\Desktop\lib\commandProtocol-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\cmdprot\CommandProcessor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */