/*     */ package com.hwacom.ngtms.base.oplog.service;
/*     */ 
/*     */ import com.google.common.util.concurrent.ThreadFactoryBuilder;
/*     */ import com.hwacom.ngtms.base.oplog.model.OperationLog;
/*     */ import com.hwacom.ngtms.base.oplog.repository.OperationLogRepository;
/*     */ import com.hwacom.ngtms.base.oplog.shared.OperationResult;
/*     */ import java.util.Date;
/*     */ import java.util.List;
/*     */ import java.util.concurrent.ExecutorService;
/*     */ import java.util.concurrent.Executors;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ import javax.annotation.PreDestroy;
/*     */ import org.slf4j.Logger;
/*     */ import org.slf4j.LoggerFactory;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ import org.springframework.beans.factory.annotation.Value;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class OperationLogServiceImpl
/*     */   implements OperationLogService
/*     */ {
/*  26 */   private static Logger logger = LoggerFactory.getLogger(OperationLogServiceImpl.class);
/*     */   
/*     */   private Logger syslogger;
/*     */   
/*     */   @Autowired
/*     */   private OperationLogRepository operationLogRepository;
/*     */   
/*     */   @Value("${oplog.syslog.enabled:false}")
/*     */   private boolean syslogEnabled;
/*     */   
/*     */   @Value("${oplog.syslog.logger.name:ngtms}")
/*     */   private String syslogLoggerName;
/*     */   
/*  39 */   private ExecutorService opLogWriter = Executors.newSingleThreadExecutor((new ThreadFactoryBuilder())
/*  40 */       .setNameFormat("opLog-thread").build());
/*     */   
/*  42 */   private ExecutorService sysExecutorService = Executors.newSingleThreadExecutor();
/*     */   
/*     */   @PreDestroy
/*     */   public void destroy() {
/*  46 */     logger.info("Shutdown operation log service");
/*  47 */     this.opLogWriter.shutdown();
/*     */     try {
/*  49 */       this.opLogWriter.awaitTermination(3L, TimeUnit.SECONDS);
/*  50 */     } catch (InterruptedException interruptedException) {}
/*     */ 
/*     */     
/*  53 */     this.sysExecutorService.shutdownNow();
/*     */   }
/*     */   
/*     */   private void writeLog(final OperationLog opLog) {
/*  57 */     this.opLogWriter.submit(new Runnable()
/*     */         {
/*     */           public void run()
/*     */           {
/*     */             try {
/*  62 */               String desc = opLog.getDescription();
/*  63 */               if (desc.length() > 2000) {
/*  64 */                 OperationLogServiceImpl.logger.warn("The description of the operation log is too long, truncate the description, originial log: {}", opLog);
/*     */ 
/*     */                 
/*  67 */                 opLog.setDescription(desc.substring(0, 2000));
/*     */               } 
/*  69 */               OperationLogServiceImpl.this.operationLogRepository.save(opLog);
/*  70 */               OperationLogServiceImpl.this.logSyslog(opLog);
/*  71 */             } catch (Exception ex) {
/*  72 */               OperationLogServiceImpl.logger.error("Failed to write operation log: {}", opLog, ex);
/*     */             } 
/*     */           }
/*     */         });
/*     */   }
/*     */   
/*     */   private void logSyslog(OperationLog opLog) {
/*  79 */     if (this.syslogEnabled) {
/*  80 */       if (this.syslogger == null) {
/*  81 */         this.syslogger = LoggerFactory.getLogger(this.syslogLoggerName);
/*     */       }
/*  83 */       this.sysExecutorService.execute(() -> this.syslogger.info("'{}', IP: '{}', 時間: '{}', 結果: '{}', 使用者: '{}', 設備名稱: '{}', 備註: '{}'", new Object[] { paramOperationLog.getDescription(), paramOperationLog.getCpeIp(), paramOperationLog.getOperationTime(), paramOperationLog.getOperationResult(), paramOperationLog.getUserId(), paramOperationLog.getDeviceName(), paramOperationLog.getRemark() }));
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
/* 108 */     OperationLog operationLog = new OperationLog(userId, cpeIp, null, subSysName, operationItem, description, deviceName, operationTime, operationResult, remark);
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
/* 120 */     writeLog(operationLog);
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
/*     */   public void addSchOpLog(String schId, String subSysName, String operationItem, String deviceName, String description, Date operationTime, OperationResult operationResult, String remark) {
/* 133 */     OperationLog operationLog = new OperationLog(null, null, schId, subSysName, operationItem, description, deviceName, operationTime, operationResult, remark);
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
/* 145 */     writeLog(operationLog);
/*     */   }
/*     */ 
/*     */   
/*     */   public OperationLog getOperationLogById(Long id) {
/* 150 */     return this.operationLogRepository.findById(id).orElse(null);
/*     */   }
/*     */ 
/*     */   
/*     */   public List<OperationLog> findByOperationTimeBetween(Date startTime, Date endTime) {
/* 155 */     return this.operationLogRepository.findByOperationTimeBetween(startTime, endTime);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public List<OperationLog> findByUserIdAndOperationTimeBetween(String userId, Date startTime, Date endTime) {
/* 161 */     return this.operationLogRepository.findByUserIdAndOperationTimeBetween(userId, startTime, endTime);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public List<OperationLog> findBySchIdAndOperationTimeBetween(String schId, Date startTime, Date endTime) {
/* 167 */     return this.operationLogRepository.findBySchIdAndOperationTimeBetween(schId, startTime, endTime);
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\lib\base-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\base\oplog\service\OperationLogServiceImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */