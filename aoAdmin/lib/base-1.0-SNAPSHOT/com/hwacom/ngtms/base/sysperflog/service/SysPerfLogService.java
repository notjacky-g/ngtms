/*     */ package com.hwacom.ngtms.base.sysperflog.service;
/*     */ 
/*     */ import com.hwacom.ngtms.base.sysperflog.SysPerfLogListener;
/*     */ import java.net.InetAddress;
/*     */ import java.net.UnknownHostException;
/*     */ import java.text.DateFormat;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.Date;
/*     */ import java.util.LinkedList;
/*     */ import java.util.Map;
/*     */ import java.util.TreeMap;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ import java.util.concurrent.ExecutorService;
/*     */ import java.util.concurrent.Executors;
/*     */ import java.util.concurrent.ScheduledExecutorService;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ import java.util.concurrent.atomic.AtomicLong;
/*     */ import javax.annotation.PostConstruct;
/*     */ import javax.annotation.PreDestroy;
/*     */ import javax.annotation.Resource;
/*     */ import javax.mail.MessagingException;
/*     */ import javax.mail.internet.MimeMessage;
/*     */ import org.apache.commons.configuration.ConfigurationException;
/*     */ import org.apache.commons.configuration.PropertiesConfiguration;
/*     */ import org.apache.commons.configuration.reloading.FileChangedReloadingStrategy;
/*     */ import org.apache.commons.configuration.reloading.ReloadingStrategy;
/*     */ import org.slf4j.Logger;
/*     */ import org.slf4j.LoggerFactory;
/*     */ import org.slf4j.MDC;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ import org.springframework.context.ApplicationContext;
/*     */ import org.springframework.core.env.Environment;
/*     */ import org.springframework.mail.javamail.JavaMailSenderImpl;
/*     */ import org.springframework.mail.javamail.MimeMessageHelper;
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
/*     */ public class SysPerfLogService
/*     */ {
/*  80 */   private final Logger logger = LoggerFactory.getLogger(SysPerfLogService.class);
/*     */   
/*  82 */   private final Logger sysPerfLogger = LoggerFactory.getLogger(SysPerfLogService.class.getName() + ".SysPerfLog");
/*     */   @Autowired
/*     */   private ApplicationContext applicationContext;
/*     */   @Resource
/*     */   private Environment environment;
/*  87 */   private ThreadLocal<DateFormat> dateFormat = new ThreadLocal<>(); private JavaMailSenderImpl mailSender; private PropertiesConfiguration thresholdConfig;
/*  88 */   private ConcurrentHashMap<Long, SysPerfLogListener> sysPerfLogListenerMap = new ConcurrentHashMap<>();
/*     */   
/*  90 */   private AtomicLong sysPerfLogListenerId = new AtomicLong();
/*  91 */   private ConcurrentHashMap<String, LinkedList<LogMessage>> logMessageMap = new ConcurrentHashMap<>();
/*     */   
/*  93 */   private AtomicLong logMessageId = new AtomicLong();
/*  94 */   private int maxLogBufferSize = 20;
/*  95 */   private long minAlarmPeriod = 3600000L;
/*     */   
/*     */   private String emailSubject;
/*     */   private String emailFrom;
/*     */   private String[] emailTo;
/* 100 */   private ExecutorService writeLogExecutor = Executors.newSingleThreadExecutor();
/*     */ 
/*     */   
/* 103 */   private ScheduledExecutorService emailSchedulerExecutor = Executors.newSingleThreadScheduledExecutor();
/*     */   
/*     */   private boolean emailEnabled;
/*     */   
/*     */   private Runnable emailTask;
/*     */   
/*     */   @PostConstruct
/*     */   public void init() {
/* 111 */     if (((Boolean)this.environment.getProperty("sysPerfLog.mail.enabled", Boolean.class, Boolean.valueOf(false))).booleanValue()) {
/* 112 */       String hostName; this.emailEnabled = true;
/* 113 */       this.mailSender = (JavaMailSenderImpl)this.applicationContext.getBean(JavaMailSenderImpl.class);
/*     */       
/*     */       try {
/* 116 */         hostName = InetAddress.getLocalHost().getHostName();
/* 117 */       } catch (UnknownHostException e) {
/* 118 */         this.logger.warn("Failed to get local host name", e);
/* 119 */         hostName = "Unknown";
/*     */       } 
/* 121 */       this.emailSubject = this.environment.getProperty("sysPerfLog.mail.subject", "SysPerfLog notify");
/* 122 */       this.emailSubject += " [" + hostName + "]";
/* 123 */       this
/* 124 */         .maxLogBufferSize = ((Integer)this.environment.getProperty("sysPerfLog.mail.maxLogBufferSize", Integer.class, Integer.valueOf(20))).intValue();
/* 125 */       this
/* 126 */         .minAlarmPeriod = ((Long)this.environment.getProperty("sysPerfLog.mail.alarm.period", Long.class, Long.valueOf(3600L))).longValue() * 1000L;
/* 127 */       this.emailFrom = this.environment.getProperty("sysPerfLog.mail.from");
/* 128 */       String to = this.environment.getProperty("sysPerfLog.mail.to");
/* 129 */       if (to != null) {
/* 130 */         to = to.trim();
/* 131 */         if (to.length() > 0) this.emailTo = to.split("\\s*,\\s*"); 
/*     */       } 
/* 133 */       if (this.emailTo == null) this.emailEnabled = false; 
/*     */     } 
/* 135 */     this.logger.info("SysPerfLog email alarm enabled: " + this.emailEnabled);
/*     */     
/* 137 */     if (this.emailEnabled)
/*     */     {
/* 139 */       this.emailSchedulerExecutor.scheduleWithFixedDelay(this.emailTask, 60L, 60L, TimeUnit.SECONDS);
/*     */     }
/* 141 */     this.thresholdConfig = new PropertiesConfiguration();
/* 142 */     this.thresholdConfig.setEncoding("UTF-8");
/* 143 */     String propertiesPath = this.environment.getProperty("sysPerfLog.mail.alarm.properties");
/* 144 */     if (propertiesPath != null) {
/*     */       try {
/* 146 */         this.thresholdConfig.load(propertiesPath);
/* 147 */         this.thresholdConfig.setReloadingStrategy((ReloadingStrategy)new FileChangedReloadingStrategy());
/* 148 */       } catch (ConfigurationException e) {
/* 149 */         this.logger.warn("Failed to load SysPerfLog alarm threshold", (Throwable)e);
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   @PreDestroy
/*     */   public void destroy() {
/* 156 */     this.writeLogExecutor.shutdownNow();
/* 157 */     this.emailSchedulerExecutor.shutdownNow();
/*     */   }
/*     */   
/*     */   public SysPerfLog createSysPerfLog(String module, String jobName, long startTime) {
/* 161 */     return SysPerfLog.createSysPerfLog(this, this.logMessageId
/* 162 */         .incrementAndGet(), module, jobName, startTime, 0L);
/*     */   }
/*     */   
/*     */   public SysPerfLog createSysPerfLog(String module, String jobName, long startTime, long endTime) {
/* 166 */     return SysPerfLog.createSysPerfLog(this, this.logMessageId
/* 167 */         .incrementAndGet(), module, jobName, startTime, endTime);
/*     */   }
/*     */   
/*     */   public int getAlarmThresholdInt(String key, int defaultValue) {
/*     */     try {
/* 172 */       return this.thresholdConfig.getInt(key);
/* 173 */     } catch (Exception ex) {
/* 174 */       this.logger.warn("Failed to get SysPerfLog alarm threshold, type: integer, key: {}, errMsg: {}", key, ex
/*     */ 
/*     */           
/* 177 */           .getMessage());
/* 178 */       return defaultValue;
/*     */     } 
/*     */   }
/*     */   
/*     */   public double getAlarmThresholdDouble(String key, double defaultValue) {
/*     */     try {
/* 184 */       return this.thresholdConfig.getDouble(key);
/* 185 */     } catch (Exception ex) {
/* 186 */       this.logger.warn("Failed to get SysPerfLog alarm threshold, type: double, key: {}, errMsg: {}", key, ex
/*     */ 
/*     */           
/* 189 */           .getMessage());
/* 190 */       return defaultValue;
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean getAlarmThresholdBoolean(String key, boolean defaultValue) {
/*     */     try {
/* 196 */       return this.thresholdConfig.getBoolean(key);
/* 197 */     } catch (Exception ex) {
/* 198 */       this.logger.warn("Failed to get SysPerfLog alarm threshold, type: boolean, key: {}, errMsg: {}", key, ex
/*     */ 
/*     */           
/* 201 */           .getMessage());
/* 202 */       return defaultValue;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeSysPerfLog(final SysPerfLog sysPerfLog) {
/* 212 */     this.writeLogExecutor.execute(new Runnable()
/*     */         {
/*     */           public void run()
/*     */           {
/* 216 */             StringBuilder sb = new StringBuilder();
/* 217 */             DateFormat df = SysPerfLogService.this.dateFormat.get();
/* 218 */             if (df == null) {
/* 219 */               df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
/* 220 */               SysPerfLogService.this.dateFormat.set(df);
/*     */             } 
/*     */             
/* 223 */             String key = sysPerfLog.getModule() + "." + sysPerfLog.getJobName();
/* 224 */             MDC.put("module", sysPerfLog.getModule());
/*     */             
/* 226 */             sb.append('[').append(sysPerfLog.getId()).append("] ");
/* 227 */             sb.append(key);
/* 228 */             if (sysPerfLog.getStartTime() > 0L) {
/* 229 */               sb.append("\n\tStart: ").append(df.format(new Date(sysPerfLog.getStartTime())));
/* 230 */               if (sysPerfLog.getEndTime() > 0L)
/* 231 */                 sb.append(", End: ").append(df.format(new Date(sysPerfLog.getEndTime()))); 
/*     */             } 
/* 233 */             if (sysPerfLog.getDescription() != null)
/* 234 */               sb.append("\n\tDescription: ").append(sysPerfLog.getDescription()); 
/* 235 */             int i = 0;
/* 236 */             for (String name : sysPerfLog.getCounterMap().keySet()) {
/* 237 */               if (i % 4 == 0) { sb.append("\n\t"); }
/* 238 */               else { sb.append(", "); }
/* 239 */                Object value = sysPerfLog.getCounterMap().get(name);
/* 240 */               sb.append(name).append('=').append(value);
/* 241 */               i++;
/*     */             } 
/* 243 */             String message = sb.toString();
/* 244 */             if (sysPerfLog.isIssueAlarm()) { SysPerfLogService.this.sysPerfLogger.warn(message); }
/* 245 */             else { SysPerfLogService.this.sysPerfLogger.info(message); }
/*     */             
/* 247 */             if (SysPerfLogService.this.mailSender != null) {
/*     */               
/* 249 */               LinkedList<SysPerfLogService.LogMessage> msgList = SysPerfLogService.this.logMessageMap.computeIfAbsent(key, k -> new LinkedList());
/* 250 */               SysPerfLogService.LogMessage logMessage = new SysPerfLogService.LogMessage(sysPerfLog);
/*     */               
/* 252 */               synchronized (msgList) {
/* 253 */                 msgList.add(logMessage);
/* 254 */                 if (msgList.size() > SysPerfLogService.this.maxLogBufferSize) {
/* 255 */                   msgList.removeFirst();
/*     */                 }
/*     */               } 
/*     */             } 
/*     */             
/* 260 */             for (SysPerfLogListener sysPerfLogListener : SysPerfLogService.this.sysPerfLogListenerMap.values())
/* 261 */               sysPerfLogListener.onSysPerfLog(sysPerfLog); 
/*     */           }
/*     */         });
/*     */   }
/*     */   
/*     */   public SysPerfLogService() {
/* 267 */     this.emailTask = new Runnable()
/*     */       {
/*     */         private long lastEmailTime;
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
/*     */         public void run() {
/* 282 */           if (System.currentTimeMillis() - this.lastEmailTime < SysPerfLogService.this.minAlarmPeriod)
/*     */             return; 
/* 284 */           TreeMap<String, LinkedList<SysPerfLogService.LogMessage>> tempMap = new TreeMap<>();
/*     */           
/* 286 */           for (Map.Entry<String, LinkedList<SysPerfLogService.LogMessage>> e : (Iterable<Map.Entry<String, LinkedList<SysPerfLogService.LogMessage>>>)SysPerfLogService.this.logMessageMap.entrySet()) {
/* 287 */             String key = e.getKey();
/* 288 */             LinkedList<SysPerfLogService.LogMessage> msgList = e.getValue();
/* 289 */             synchronized (msgList) {
/* 290 */               if (msgList.size() > 0) {
/* 291 */                 for (SysPerfLogService.LogMessage logMessage : msgList) {
/* 292 */                   if (logMessage.sysPerfLog.isIssueAlarm() && !logMessage.alarmIssued) {
/* 293 */                     tempMap.put(key, msgList);
/*     */                     break;
/*     */                   } 
/*     */                 } 
/*     */               }
/*     */             } 
/*     */           } 
/* 300 */           if (tempMap.size() > 0) {
/* 301 */             DateFormat df = SysPerfLogService.this.dateFormat.get();
/* 302 */             if (df == null) {
/* 303 */               df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
/* 304 */               SysPerfLogService.this.dateFormat.set(df);
/*     */             } 
/* 306 */             MimeMessage message = SysPerfLogService.this.mailSender.createMimeMessage();
/* 307 */             MimeMessageHelper helper = new MimeMessageHelper(message, "UTF-8");
/* 308 */             StringBuilder sb = new StringBuilder();
/* 309 */             sb.append("<html><body>");
/* 310 */             for (String key : tempMap.keySet()) {
/* 311 */               sb.append("<h3>").append(key).append("</h3>");
/* 312 */               LinkedList<SysPerfLogService.LogMessage> msgList = tempMap.get(key);
/* 313 */               for (SysPerfLogService.LogMessage logMessage : msgList) {
/* 314 */                 SysPerfLog sysPerfLog = logMessage.sysPerfLog;
/* 315 */                 if (sysPerfLog.isIssueAlarm()) { sb.append("<p style='color: red'>"); }
/* 316 */                 else { sb.append("<p>"); }
/* 317 */                  sb.append('[').append(sysPerfLog.getId()).append("] ");
/* 318 */                 sb.append(key);
/* 319 */                 if (sysPerfLog.getStartTime() > 0L) {
/* 320 */                   sb.append(" Start: ").append(df.format(new Date(sysPerfLog.getStartTime())));
/* 321 */                   if (sysPerfLog.getEndTime() > 0L)
/* 322 */                     sb.append(", End: ").append(df.format(new Date(sysPerfLog.getEndTime()))); 
/*     */                 } 
/* 324 */                 if (sysPerfLog.getDescription() != null) {
/* 325 */                   sb.append("<br/>Description: " + sysPerfLog.getDescription());
/*     */                 }
/* 327 */                 int i = 0;
/* 328 */                 for (String name : sysPerfLog.getCounterMap().keySet()) {
/* 329 */                   if (i % 4 == 0) { sb.append("<br/>"); }
/* 330 */                   else { sb.append(", "); }
/* 331 */                    Object value = sysPerfLog.getCounterMap().get(name);
/* 332 */                   sb.append(name).append('=').append(value);
/* 333 */                   i++;
/*     */                 } 
/* 335 */                 sb.append("</p>");
/* 336 */                 logMessage.alarmIssued = true;
/*     */               } 
/*     */             } 
/* 339 */             sb.append("</body></html>");
/*     */             
/*     */             try {
/* 342 */               helper.setFrom(SysPerfLogService.this.emailFrom);
/* 343 */               helper.setTo(SysPerfLogService.this.emailTo);
/* 344 */               helper.setSubject(SysPerfLogService.this.emailSubject + ", " + tempMap.size() + " alarms");
/* 345 */               helper.setText(sb.toString(), true);
/* 346 */               this.lastEmailTime = System.currentTimeMillis();
/* 347 */               SysPerfLogService.this.mailSender.send(message);
/* 348 */             } catch (MessagingException e1) {
/* 349 */               SysPerfLogService.this.logger.error("Failed to generate sysPerfLog alarm email", (Throwable)e1);
/*     */             } 
/*     */           } 
/*     */         }
/*     */       };
/*     */   }
/*     */   public long addSysPerfLogListener(SysPerfLogListener sysPerfLogListener) {
/* 356 */     long id = this.sysPerfLogListenerId.incrementAndGet();
/* 357 */     this.sysPerfLogListenerMap.put(Long.valueOf(id), sysPerfLogListener);
/* 358 */     return id;
/*     */   }
/*     */   
/*     */   public void removeSysPerfLogListener(long id) {
/* 362 */     this.sysPerfLogListenerMap.remove(Long.valueOf(id));
/*     */   }
/*     */   
/*     */   static class LogMessage {
/*     */     SysPerfLog sysPerfLog;
/*     */     boolean alarmIssued;
/*     */     
/*     */     public LogMessage(SysPerfLog sysPerfLog) {
/* 370 */       this.sysPerfLog = sysPerfLog;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\lib\base-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\base\sysperflog\service\SysPerfLogService.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */