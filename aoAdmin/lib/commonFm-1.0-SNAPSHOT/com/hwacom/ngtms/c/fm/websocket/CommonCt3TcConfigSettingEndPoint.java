/*     */ package com.hwacom.ngtms.c.fm.websocket;
/*     */ 
/*     */ import com.hwacom.ngtms.base.i18n.shared.MessageSourceExt;
/*     */ import com.hwacom.ngtms.base.oplog.shared.OperationItem;
/*     */ import com.hwacom.ngtms.c.fm.service.CommonCt3TcService;
/*     */ import com.hwacom.ngtms.c.shared.CommonCt3CommRestartAndTestMessage;
/*     */ import com.hwacom.ngtms.c.shared.CommonCt3DeviceTimeMessage;
/*     */ import com.hwacom.ngtms.c.shared.CommonCt3FirmwareDateAndVersionMessage;
/*     */ import com.hwacom.ngtms.c.shared.CommonCt3LockDbMessage;
/*     */ import com.hwacom.ngtms.c.shared.CommonCt3RebootMessage;
/*     */ import com.hwacom.ngtms.c.shared.CommonCt3ReportHwStatusCycleMessage;
/*     */ import com.hwacom.ngtms.c.shared.SubSystem;
/*     */ import com.hwacom.ngtms.common.websocket.SpringConfigurator;
/*     */ import com.hwacom.ngtms.ncc.remote.TcResponse;
/*     */ import java.io.StringReader;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Calendar;
/*     */ import java.util.List;
/*     */ import java.util.function.BiConsumer;
/*     */ import javax.json.Json;
/*     */ import javax.json.JsonArray;
/*     */ import javax.json.JsonObject;
/*     */ import javax.websocket.CloseReason;
/*     */ import javax.websocket.DecodeException;
/*     */ import javax.websocket.Decoder;
/*     */ import javax.websocket.EndpointConfig;
/*     */ import javax.websocket.OnClose;
/*     */ import javax.websocket.OnError;
/*     */ import javax.websocket.OnMessage;
/*     */ import javax.websocket.OnOpen;
/*     */ import javax.websocket.Session;
/*     */ import javax.websocket.server.ServerEndpoint;
/*     */ import org.slf4j.Logger;
/*     */ import org.slf4j.LoggerFactory;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ import org.springframework.stereotype.Service;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @ServerEndpoint(value = "/websocket/ct3/common/tcConfigSetting", configurator = SpringConfigurator.class, decoders = {CommonCt3TcConfigSettingEndPoint.CommonCt3DeviceTimeMessageDecoder.class, CommonCt3TcConfigSettingEndPoint.CommonCt3FirmwareDateAndVersionMessageDecoder.class, CommonCt3TcConfigSettingEndPoint.CommonCt3ReportHwStatusCycleMessageDecoder.class, CommonCt3TcConfigSettingEndPoint.CommonCt3CommRestartAndTestMessageDecoder.class, CommonCt3TcConfigSettingEndPoint.CommonCt3RebootMessageDecoder.class, CommonCt3TcConfigSettingEndPoint.CommonCt3LockDbMessageDecoder.class})
/*     */ @Service
/*     */ public class CommonCt3TcConfigSettingEndPoint
/*     */   extends AbstractServerEndpoint
/*     */ {
/*  59 */   private static final Logger logger = LoggerFactory.getLogger(CommonCt3TcConfigSettingEndPoint.class);
/*     */   
/*     */   @Autowired
/*     */   private CommonCt3TcService commonTcService;
/*     */   
/*     */   @OnOpen
/*     */   public void start(Session session, EndpointConfig config) {
/*  66 */     init(session, config, OperationItem.SET, (SubSystem)null);
/*  67 */     logger.info("Common Tc Config Setting Websocket:'{}' Started...", session.getId());
/*     */   } @Autowired
/*     */   private MessageSourceExt messageSourceExt;
/*     */   @OnMessage
/*     */   public void onMessage(CommonTcConfigSettingMessage commonTcConfigSettingMessage) {
/*  72 */     logger.debug("Common Tc Config Setting message='{}'", commonTcConfigSettingMessage);
/*     */     
/*  74 */     if (commonTcConfigSettingMessage instanceof CommonCt3CommRestartAndTestMessage) {
/*  75 */       CommonCt3CommRestartAndTestMessage message = (CommonCt3CommRestartAndTestMessage)commonTcConfigSettingMessage;
/*     */       
/*  77 */       String deviceType = message.getDeviceType();
/*  78 */       setSubSystem(SubSystem.valueOf(deviceType));
/*  79 */       List<String> deviceNames = message.getDeviceNames();
/*     */       try {
/*  81 */         this.commonTcService.setCt3CommRestart(deviceNames, new Callback(deviceNames, "common.ct3.tc.configSet.comm.restart", null));
/*     */       }
/*  83 */       catch (RuntimeException e) {
/*  84 */         logger.warn("Communication restart and test failed! deviceNames:{}", deviceNames, e);
/*  85 */         offerExceptionLog(deviceNames, e, this.messageSourceExt
/*  86 */             .getMessage("common.ct3.tc.configSet.comm.restart"));
/*  87 */         send("error");
/*     */       } 
/*  89 */       logger.debug("Websocket Session:'{}' setCommonCt3CommRestartAndTest:'{}'", this.session
/*     */           
/*  91 */           .getId(), message
/*  92 */           .getDeviceNames());
/*     */     } 
/*     */     
/*  95 */     if (commonTcConfigSettingMessage instanceof CommonCt3DeviceTimeMessage) {
/*  96 */       CommonCt3DeviceTimeMessage message = (CommonCt3DeviceTimeMessage)commonTcConfigSettingMessage;
/*     */       
/*  98 */       List<String> deviceNames = message.getDeviceNames();
/*  99 */       String deviceType = message.getDeviceType();
/* 100 */       setSubSystem(SubSystem.valueOf(deviceType));
/*     */       
/* 102 */       Calendar time = Calendar.getInstance();
/* 103 */       if (message.getYear() != null && message
/* 104 */         .getMonth() != null && message
/* 105 */         .getDay() != null && message
/* 106 */         .getHour() != null && message
/* 107 */         .getMin() != null && message
/* 108 */         .getSec() != null) {
/* 109 */         time.set(message
/* 110 */             .getYear().intValue(), message
/* 111 */             .getMonth().intValue(), message
/* 112 */             .getDay().intValue(), message
/* 113 */             .getHour().intValue(), message
/* 114 */             .getMin().intValue(), message
/* 115 */             .getSec().intValue());
/*     */       }
/*     */       try {
/* 118 */         this.commonTcService.setCt3Time(deviceNames, time, new Callback(deviceNames, "common.ct3.tc.configSet.deviceTime", time
/*     */ 
/*     */               
/* 121 */               .toString()));
/* 122 */       } catch (RuntimeException e) {
/* 123 */         logger.warn("Set device time failed! deviceNames:{}", deviceNames, e);
/* 124 */         offerExceptionLog(deviceNames, e, "common.ct3.tc.configSet.deviceTime");
/* 125 */         send("error");
/*     */       } 
/* 127 */       logger.debug("Websocket Session:'{}' setCommonCt3DeviceTime:'{}'", this.session
/*     */           
/* 129 */           .getId(), message
/* 130 */           .getDeviceNames());
/*     */     } 
/*     */     
/* 133 */     if (commonTcConfigSettingMessage instanceof CommonCt3FirmwareDateAndVersionMessage) {
/* 134 */       CommonCt3FirmwareDateAndVersionMessage message = (CommonCt3FirmwareDateAndVersionMessage)commonTcConfigSettingMessage;
/*     */       
/* 136 */       String deviceType = message.getDeviceType();
/* 137 */       setSubSystem(SubSystem.valueOf(deviceType));
/* 138 */       List<String> deviceNames = message.getDeviceNames();
/*     */       try {
/* 140 */         this.commonTcService.setCt3CommandSet(deviceNames, message
/*     */             
/* 142 */             .getCommandSet().intValue(), new Callback(deviceNames, "common.ct3.tc.configSet.message.level", 
/*     */ 
/*     */ 
/*     */               
/* 146 */               String.valueOf(message.getCommandSet())));
/* 147 */       } catch (RuntimeException e) {
/* 148 */         logger.warn("Set message level failed! deviceNames:{}", deviceNames, e);
/* 149 */         offerExceptionLog(deviceNames, e, "common.ct3.tc.configSet.message.level");
/* 150 */         send("error");
/*     */       } 
/* 152 */       logger.debug("Websocket Session:'{}' setCommonCt3FirmwareDateAndVersion:'{}'", this.session
/*     */           
/* 154 */           .getId(), message
/* 155 */           .getDeviceNames());
/*     */     } 
/*     */     
/* 158 */     if (commonTcConfigSettingMessage instanceof CommonCt3ReportHwStatusCycleMessage) {
/* 159 */       CommonCt3ReportHwStatusCycleMessage message = (CommonCt3ReportHwStatusCycleMessage)commonTcConfigSettingMessage;
/*     */       
/* 161 */       String deviceType = message.getDeviceType();
/* 162 */       setSubSystem(SubSystem.valueOf(deviceType));
/* 163 */       List<String> deviceNames = message.getDeviceNames();
/*     */       try {
/* 165 */         this.commonTcService.setCt3ReportHwStatusCycle(deviceNames, message
/*     */             
/* 167 */             .getHardwareCycle().intValue(), new Callback(deviceNames, "common.ct3.tc.configSet.report.hardware.status.cycle", 
/*     */ 
/*     */ 
/*     */               
/* 171 */               String.valueOf(message.getHardwareCycle())));
/* 172 */       } catch (RuntimeException e) {
/* 173 */         logger.warn("Set report hardware status cycle failed! deviceNames:{}", deviceNames, e);
/* 174 */         offerExceptionLog(deviceNames, e, "common.ct3.tc.configSet.report.hardware.status.cycle");
/* 175 */         send("error");
/*     */       } 
/* 177 */       logger.debug("Websocket Session:'{}' setCommonCt3ReportHwStatusCycle:'{}'", this.session
/*     */           
/* 179 */           .getId(), message
/* 180 */           .getDeviceNames());
/*     */     } 
/*     */     
/* 183 */     if (commonTcConfigSettingMessage instanceof CommonCt3RebootMessage) {
/* 184 */       CommonCt3RebootMessage message = (CommonCt3RebootMessage)commonTcConfigSettingMessage;
/* 185 */       String deviceType = message.getDeviceType();
/* 186 */       setSubSystem(SubSystem.valueOf(deviceType));
/* 187 */       List<String> deviceNames = message.getDeviceNames();
/*     */       try {
/* 189 */         this.commonTcService.setCt3ResetDevice(deviceNames, new Callback(deviceNames, "common.ct3.tc.configSet.reset", null));
/*     */       }
/* 191 */       catch (RuntimeException e) {
/* 192 */         logger.warn("Set report hardware status cycle failed! deviceNames:{}", deviceNames, e);
/* 193 */         offerExceptionLog(deviceNames, e, this.messageSourceExt
/*     */ 
/*     */             
/* 196 */             .getMessage("common.ct3.tc.configSet.report.hardware.status.cycle"));
/* 197 */         send("error");
/*     */       } 
/* 199 */       logger.debug("Websocket Session:'{}' setCommonCt3Reset:'{}'", this.session
/*     */           
/* 201 */           .getId(), message
/* 202 */           .getDeviceNames());
/*     */     } 
/*     */     
/* 205 */     if (commonTcConfigSettingMessage instanceof CommonCt3LockDbMessage) {
/* 206 */       CommonCt3LockDbMessage message = (CommonCt3LockDbMessage)commonTcConfigSettingMessage;
/* 207 */       String deviceType = message.getDeviceType();
/* 208 */       setSubSystem(SubSystem.valueOf(deviceType));
/* 209 */       List<String> deviceNames = message.getDeviceNames();
/*     */       try {
/* 211 */         this.commonTcService.setCt3LockDb(deviceNames, message
/*     */             
/* 213 */             .getLockDb().intValue(), new Callback(deviceNames, "common.ct3.tc.configSet.lockDb", 
/*     */ 
/*     */ 
/*     */               
/* 217 */               String.valueOf(message.getLockDb())));
/* 218 */       } catch (RuntimeException e) {
/* 219 */         logger.warn("Communication restart and test failed! deviceNames:{}", deviceNames, e);
/* 220 */         offerExceptionLog(deviceNames, e, this.messageSourceExt
/* 221 */             .getMessage("common.ct3.tc.configSet.comm.restart"));
/* 222 */         send("error");
/*     */       } 
/* 224 */       logger.debug("Websocket Session:'{}' setCommonCt3CommRestartAndTest:'{}'", this.session
/*     */           
/* 226 */           .getId(), message
/* 227 */           .getDeviceNames());
/*     */     } 
/*     */   }
/*     */   
/*     */   @OnClose
/*     */   public void end(Session session, CloseReason closeReason) {
/* 233 */     logger.info("Common Tc Config Setting Websocket Session:'{}' Ended..., reason:'{}'", session
/*     */         
/* 235 */         .getId(), closeReason);
/*     */   }
/*     */ 
/*     */   
/*     */   @OnError
/*     */   public void error(Session session, Throwable t) {
/* 241 */     logger.warn("Websocket Session:'{}' Error!", session.getId(), t);
/*     */   }
/*     */ 
/*     */   
/*     */   private class Callback
/*     */     implements BiConsumer<String, TcResponse>
/*     */   {
/*     */     private int count;
/*     */     private String messageId;
/*     */     private String param;
/*     */     
/*     */     public Callback(List<String> deviceNames, String messageId, String param) {
/* 253 */       this.count = deviceNames.size();
/* 254 */       this.messageId = messageId;
/* 255 */       this.param = param;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void accept(String deviceName, TcResponse response) {
/* 263 */       String logMessage = (this.param == null) ? CommonCt3TcConfigSettingEndPoint.this.messageSourceExt.getMessage(this.messageId) : CommonCt3TcConfigSettingEndPoint.this.messageSourceExt.getMessage(this.messageId, new Object[] { this.param });
/* 264 */       CommonCt3TcConfigSettingEndPoint.this.offerTcResponseLog(deviceName, response, (String)null, logMessage);
/* 265 */       this.count--;
/* 266 */       if (this.count == 0) {
/* 267 */         CommonCt3TcConfigSettingEndPoint.this.send("complete");
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private static interface CommonTcConfigSettingMessage {}
/*     */ 
/*     */   
/*     */   private static class CommonCt3CommRestartAndTestMessageImpl
/*     */     implements CommonCt3CommRestartAndTestMessage, CommonTcConfigSettingMessage
/*     */   {
/*     */     private String type;
/*     */     private String deviceType;
/*     */     private List<String> deviceNames;
/*     */     private byte[] hardwareStatus;
/*     */     
/*     */     private CommonCt3CommRestartAndTestMessageImpl() {}
/*     */     
/*     */     public String getType() {
/* 287 */       return this.type;
/*     */     }
/*     */ 
/*     */     
/*     */     public void setType(String type) {
/* 292 */       this.type = type;
/*     */     }
/*     */ 
/*     */     
/*     */     public String getDeviceType() {
/* 297 */       return this.deviceType;
/*     */     }
/*     */ 
/*     */     
/*     */     public void setDeviceType(String deviceType) {
/* 302 */       this.deviceType = deviceType;
/*     */     }
/*     */ 
/*     */     
/*     */     public List<String> getDeviceNames() {
/* 307 */       return this.deviceNames;
/*     */     }
/*     */ 
/*     */     
/*     */     public void setDeviceNames(List<String> deviceNames) {
/* 312 */       this.deviceNames = deviceNames;
/*     */     }
/*     */ 
/*     */     
/*     */     public byte[] getHardwareStatus() {
/* 317 */       return this.hardwareStatus;
/*     */     }
/*     */ 
/*     */     
/*     */     public void setHardwareStatus(byte[] hardwareStatus) {
/* 322 */       this.hardwareStatus = hardwareStatus;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private static class CommonCt3DeviceTimeMessageImpl
/*     */     implements CommonCt3DeviceTimeMessage, CommonTcConfigSettingMessage
/*     */   {
/*     */     private String type;
/*     */     
/*     */     private String deviceType;
/*     */     
/*     */     private List<String> deviceNames;
/*     */     
/*     */     private Integer year;
/*     */     
/*     */     private Integer month;
/*     */     
/*     */     private Integer day;
/*     */     
/*     */     private Integer week;
/*     */     
/*     */     private Integer hour;
/*     */     private Integer min;
/*     */     private Integer sec;
/*     */     
/*     */     private CommonCt3DeviceTimeMessageImpl() {}
/*     */     
/*     */     public String getType() {
/* 351 */       return this.type;
/*     */     }
/*     */ 
/*     */     
/*     */     public void setType(String type) {
/* 356 */       this.type = type;
/*     */     }
/*     */ 
/*     */     
/*     */     public String getDeviceType() {
/* 361 */       return this.deviceType;
/*     */     }
/*     */ 
/*     */     
/*     */     public void setDeviceType(String deviceType) {
/* 366 */       this.deviceType = deviceType;
/*     */     }
/*     */ 
/*     */     
/*     */     public List<String> getDeviceNames() {
/* 371 */       return this.deviceNames;
/*     */     }
/*     */ 
/*     */     
/*     */     public void setDeviceNames(List<String> deviceNames) {
/* 376 */       this.deviceNames = deviceNames;
/*     */     }
/*     */ 
/*     */     
/*     */     public Integer getYear() {
/* 381 */       return this.year;
/*     */     }
/*     */ 
/*     */     
/*     */     public void setYear(Integer year) {
/* 386 */       this.year = year;
/*     */     }
/*     */ 
/*     */     
/*     */     public Integer getMonth() {
/* 391 */       return this.month;
/*     */     }
/*     */ 
/*     */     
/*     */     public void setMonth(Integer month) {
/* 396 */       this.month = month;
/*     */     }
/*     */ 
/*     */     
/*     */     public Integer getDay() {
/* 401 */       return this.day;
/*     */     }
/*     */ 
/*     */     
/*     */     public void setDay(Integer day) {
/* 406 */       this.day = day;
/*     */     }
/*     */ 
/*     */     
/*     */     public Integer getWeek() {
/* 411 */       return this.week;
/*     */     }
/*     */ 
/*     */     
/*     */     public void setWeek(Integer week) {
/* 416 */       this.week = week;
/*     */     }
/*     */ 
/*     */     
/*     */     public Integer getHour() {
/* 421 */       return this.hour;
/*     */     }
/*     */ 
/*     */     
/*     */     public void setHour(Integer hour) {
/* 426 */       this.hour = hour;
/*     */     }
/*     */ 
/*     */     
/*     */     public Integer getMin() {
/* 431 */       return this.min;
/*     */     }
/*     */ 
/*     */     
/*     */     public void setMin(Integer min) {
/* 436 */       this.min = min;
/*     */     }
/*     */ 
/*     */     
/*     */     public Integer getSec() {
/* 441 */       return this.sec;
/*     */     }
/*     */ 
/*     */     
/*     */     public void setSec(Integer sec) {
/* 446 */       this.sec = sec;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private static class CommonCt3FirmwareDateAndVersionMessageImpl
/*     */     implements CommonCt3FirmwareDateAndVersionMessage, CommonTcConfigSettingMessage
/*     */   {
/*     */     private String type;
/*     */     
/*     */     private String deviceType;
/*     */     
/*     */     private List<String> deviceNames;
/*     */     
/*     */     private Integer commandSet;
/*     */     
/*     */     private Integer year;
/*     */     
/*     */     private Integer month;
/*     */     
/*     */     private Integer day;
/*     */     private Integer companyId;
/*     */     private Integer version;
/*     */     
/*     */     private CommonCt3FirmwareDateAndVersionMessageImpl() {}
/*     */     
/*     */     public String getType() {
/* 473 */       return this.type;
/*     */     }
/*     */ 
/*     */     
/*     */     public void setType(String type) {
/* 478 */       this.type = type;
/*     */     }
/*     */ 
/*     */     
/*     */     public String getDeviceType() {
/* 483 */       return this.deviceType;
/*     */     }
/*     */ 
/*     */     
/*     */     public void setDeviceType(String deviceType) {
/* 488 */       this.deviceType = deviceType;
/*     */     }
/*     */ 
/*     */     
/*     */     public List<String> getDeviceNames() {
/* 493 */       return this.deviceNames;
/*     */     }
/*     */ 
/*     */     
/*     */     public void setDeviceNames(List<String> deviceNames) {
/* 498 */       this.deviceNames = deviceNames;
/*     */     }
/*     */ 
/*     */     
/*     */     public Integer getCommandSet() {
/* 503 */       return this.commandSet;
/*     */     }
/*     */ 
/*     */     
/*     */     public void setCommandSet(Integer commandSet) {
/* 508 */       this.commandSet = commandSet;
/*     */     }
/*     */ 
/*     */     
/*     */     public Integer getYear() {
/* 513 */       return this.year;
/*     */     }
/*     */ 
/*     */     
/*     */     public void setYear(Integer year) {
/* 518 */       this.year = year;
/*     */     }
/*     */ 
/*     */     
/*     */     public Integer getMonth() {
/* 523 */       return this.month;
/*     */     }
/*     */ 
/*     */     
/*     */     public void setMonth(Integer month) {
/* 528 */       this.month = month;
/*     */     }
/*     */ 
/*     */     
/*     */     public Integer getDay() {
/* 533 */       return this.day;
/*     */     }
/*     */ 
/*     */     
/*     */     public void setDay(Integer day) {
/* 538 */       this.day = day;
/*     */     }
/*     */ 
/*     */     
/*     */     public Integer getCompanyId() {
/* 543 */       return this.companyId;
/*     */     }
/*     */ 
/*     */     
/*     */     public void setCompanyId(Integer companyId) {
/* 548 */       this.companyId = companyId;
/*     */     }
/*     */ 
/*     */     
/*     */     public Integer getVersion() {
/* 553 */       return this.version;
/*     */     }
/*     */ 
/*     */     
/*     */     public void setVersion(Integer version) {
/* 558 */       this.version = version;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private static class CommonCt3ReportHwStatusCycleMessageImpl
/*     */     implements CommonCt3ReportHwStatusCycleMessage, CommonTcConfigSettingMessage
/*     */   {
/*     */     private String type;
/*     */     
/*     */     private String deviceType;
/*     */     private List<String> deviceNames;
/*     */     private Integer hardwareCycle;
/*     */     
/*     */     private CommonCt3ReportHwStatusCycleMessageImpl() {}
/*     */     
/*     */     public String getType() {
/* 575 */       return this.type;
/*     */     }
/*     */ 
/*     */     
/*     */     public void setType(String type) {
/* 580 */       this.type = type;
/*     */     }
/*     */ 
/*     */     
/*     */     public String getDeviceType() {
/* 585 */       return this.deviceType;
/*     */     }
/*     */ 
/*     */     
/*     */     public void setDeviceType(String deviceType) {
/* 590 */       this.deviceType = deviceType;
/*     */     }
/*     */ 
/*     */     
/*     */     public List<String> getDeviceNames() {
/* 595 */       return this.deviceNames;
/*     */     }
/*     */ 
/*     */     
/*     */     public void setDeviceNames(List<String> deviceNames) {
/* 600 */       this.deviceNames = deviceNames;
/*     */     }
/*     */ 
/*     */     
/*     */     public Integer getHardwareCycle() {
/* 605 */       return this.hardwareCycle;
/*     */     }
/*     */ 
/*     */     
/*     */     public void setHardwareCycle(Integer hardwareCycle) {
/* 610 */       this.hardwareCycle = hardwareCycle;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private static class CommonCt3RebootMessageImpl
/*     */     implements CommonCt3RebootMessage, CommonTcConfigSettingMessage
/*     */   {
/*     */     private String type;
/*     */     private String deviceType;
/*     */     private List<String> deviceNames;
/*     */     
/*     */     private CommonCt3RebootMessageImpl() {}
/*     */     
/*     */     public String getType() {
/* 625 */       return this.type;
/*     */     }
/*     */ 
/*     */     
/*     */     public void setType(String type) {
/* 630 */       this.type = type;
/*     */     }
/*     */ 
/*     */     
/*     */     public String getDeviceType() {
/* 635 */       return this.deviceType;
/*     */     }
/*     */ 
/*     */     
/*     */     public void setDeviceType(String deviceType) {
/* 640 */       this.deviceType = deviceType;
/*     */     }
/*     */ 
/*     */     
/*     */     public List<String> getDeviceNames() {
/* 645 */       return this.deviceNames;
/*     */     }
/*     */ 
/*     */     
/*     */     public void setDeviceNames(List<String> deviceNames) {
/* 650 */       this.deviceNames = deviceNames;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private static class CommonCt3LockDbMessageImpl
/*     */     implements CommonCt3LockDbMessage, CommonTcConfigSettingMessage
/*     */   {
/*     */     private String type;
/*     */     
/*     */     private String deviceType;
/*     */     private List<String> deviceNames;
/*     */     private Integer lockDb;
/*     */     
/*     */     private CommonCt3LockDbMessageImpl() {}
/*     */     
/*     */     public String getType() {
/* 667 */       return this.type;
/*     */     }
/*     */ 
/*     */     
/*     */     public void setType(String type) {
/* 672 */       this.type = type;
/*     */     }
/*     */ 
/*     */     
/*     */     public String getDeviceType() {
/* 677 */       return this.deviceType;
/*     */     }
/*     */ 
/*     */     
/*     */     public void setDeviceType(String deviceType) {
/* 682 */       this.deviceType = deviceType;
/*     */     }
/*     */ 
/*     */     
/*     */     public List<String> getDeviceNames() {
/* 687 */       return this.deviceNames;
/*     */     }
/*     */ 
/*     */     
/*     */     public void setDeviceNames(List<String> deviceNames) {
/* 692 */       this.deviceNames = deviceNames;
/*     */     }
/*     */     
/*     */     public Integer getLockDb() {
/* 696 */       return this.lockDb;
/*     */     }
/*     */     
/*     */     public void setLockDb(Integer lockDb) {
/* 700 */       this.lockDb = lockDb;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static class CommonCt3CommRestartAndTestMessageDecoder
/*     */     implements Decoder.Text<CommonTcConfigSettingMessage>
/*     */   {
/*     */     public void init(EndpointConfig config) {}
/*     */ 
/*     */     
/*     */     public void destroy() {}
/*     */ 
/*     */     
/*     */     public CommonCt3TcConfigSettingEndPoint.CommonTcConfigSettingMessage decode(String s) throws DecodeException {
/* 715 */       CommonCt3TcConfigSettingEndPoint.logger.debug("in CommonCt3CommRestartAndTestMessageDecoder.");
/* 716 */       JsonObject jsonObject = Json.createReader(new StringReader(s)).readObject();
/* 717 */       CommonCt3TcConfigSettingEndPoint.CommonCt3CommRestartAndTestMessageImpl msg = new CommonCt3TcConfigSettingEndPoint.CommonCt3CommRestartAndTestMessageImpl();
/* 718 */       msg.setDeviceType(jsonObject.getString("deviceType"));
/* 719 */       msg.setDeviceNames(extractStringList(jsonObject, "deviceNames"));
/* 720 */       return msg;
/*     */     }
/*     */     
/*     */     private static List<String> extractStringList(JsonObject jsonObject, String name) {
/* 724 */       List<String> result = new ArrayList<>();
/* 725 */       JsonArray jsonArray = jsonObject.getJsonArray(name);
/* 726 */       for (int i = 0; i < jsonArray.size(); i++) {
/* 727 */         result.add(jsonArray.getString(i));
/*     */       }
/* 729 */       return result;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean willDecode(String s) {
/*     */       try {
/* 735 */         JsonObject jsonObject = Json.createReader(new StringReader(s)).readObject();
/* 736 */         return "CommRestartAndTest".equals(jsonObject.getString("type"));
/* 737 */       } catch (RuntimeException e) {
/* 738 */         CommonCt3TcConfigSettingEndPoint.logger.warn("CmsCt3CommRestartAndTestMessage format Error, will not be decoded! '{}'", s);
/* 739 */         return false;
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static class CommonCt3DeviceTimeMessageDecoder
/*     */     implements Decoder.Text<CommonTcConfigSettingMessage>
/*     */   {
/*     */     public void init(EndpointConfig config) {}
/*     */ 
/*     */     
/*     */     public void destroy() {}
/*     */ 
/*     */     
/*     */     public CommonCt3TcConfigSettingEndPoint.CommonTcConfigSettingMessage decode(String s) throws DecodeException {
/* 755 */       CommonCt3TcConfigSettingEndPoint.logger.debug("in CommonCt3DeviceTimeMessageDecoder.");
/* 756 */       JsonObject jsonObject = Json.createReader(new StringReader(s)).readObject();
/* 757 */       CommonCt3TcConfigSettingEndPoint.CommonCt3DeviceTimeMessageImpl msg = new CommonCt3TcConfigSettingEndPoint.CommonCt3DeviceTimeMessageImpl();
/* 758 */       msg.setDeviceType(jsonObject.getString("deviceType"));
/* 759 */       msg.setDeviceNames(extractStringList(jsonObject, "deviceNames"));
/*     */       
/* 761 */       if (jsonObject.get("year") != null) {
/* 762 */         msg.setYear(Integer.valueOf(jsonObject.getInt("year")));
/*     */       }
/* 764 */       if (jsonObject.get("month") != null) {
/* 765 */         msg.setMonth(Integer.valueOf(jsonObject.getInt("month")));
/*     */       }
/* 767 */       if (jsonObject.get("day") != null) {
/* 768 */         msg.setDay(Integer.valueOf(jsonObject.getInt("day")));
/*     */       }
/* 770 */       if (jsonObject.get("week") != null) {
/* 771 */         msg.setWeek(Integer.valueOf(jsonObject.getInt("week")));
/*     */       }
/* 773 */       if (jsonObject.get("hour") != null) {
/* 774 */         msg.setHour(Integer.valueOf(jsonObject.getInt("hour")));
/*     */       }
/* 776 */       if (jsonObject.get("min") != null) {
/* 777 */         msg.setMin(Integer.valueOf(jsonObject.getInt("min")));
/*     */       }
/* 779 */       if (jsonObject.get("sec") != null) {
/* 780 */         msg.setSec(Integer.valueOf(jsonObject.getInt("sec")));
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 796 */       return msg;
/*     */     }
/*     */     
/*     */     private static List<String> extractStringList(JsonObject jsonObject, String name) {
/* 800 */       List<String> result = new ArrayList<>();
/* 801 */       JsonArray jsonArray = jsonObject.getJsonArray(name);
/* 802 */       for (int i = 0; i < jsonArray.size(); i++) {
/* 803 */         result.add(jsonArray.getString(i));
/*     */       }
/* 805 */       return result;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean willDecode(String s) {
/*     */       try {
/* 811 */         JsonObject jsonObject = Json.createReader(new StringReader(s)).readObject();
/* 812 */         return "DeviceTime".equals(jsonObject.getString("type"));
/* 813 */       } catch (RuntimeException e) {
/* 814 */         CommonCt3TcConfigSettingEndPoint.logger.warn("CommonCt3DeviceTimeMessage format Error, will not be decoded! '{}'", s);
/* 815 */         return false;
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static class CommonCt3FirmwareDateAndVersionMessageDecoder
/*     */     implements Decoder.Text<CommonTcConfigSettingMessage>
/*     */   {
/*     */     public void init(EndpointConfig config) {}
/*     */ 
/*     */     
/*     */     public void destroy() {}
/*     */ 
/*     */     
/*     */     public CommonCt3TcConfigSettingEndPoint.CommonTcConfigSettingMessage decode(String s) throws DecodeException {
/* 831 */       CommonCt3TcConfigSettingEndPoint.logger.debug("in CommonCt3FirmwareDateAndVersionMessageDecoder.");
/* 832 */       JsonObject jsonObject = Json.createReader(new StringReader(s)).readObject();
/* 833 */       CommonCt3TcConfigSettingEndPoint.CommonCt3FirmwareDateAndVersionMessageImpl msg = new CommonCt3TcConfigSettingEndPoint.CommonCt3FirmwareDateAndVersionMessageImpl();
/*     */       
/* 835 */       msg.setDeviceType(jsonObject.getString("deviceType"));
/* 836 */       msg.setDeviceNames(extractStringList(jsonObject, "deviceNames"));
/* 837 */       msg.setCommandSet(Integer.valueOf(jsonObject.getInt("commandSet")));
/* 838 */       return msg;
/*     */     }
/*     */     
/*     */     private static List<String> extractStringList(JsonObject jsonObject, String name) {
/* 842 */       List<String> result = new ArrayList<>();
/* 843 */       JsonArray jsonArray = jsonObject.getJsonArray(name);
/* 844 */       for (int i = 0; i < jsonArray.size(); i++) {
/* 845 */         result.add(jsonArray.getString(i));
/*     */       }
/* 847 */       return result;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean willDecode(String s) {
/*     */       try {
/* 853 */         JsonObject jsonObject = Json.createReader(new StringReader(s)).readObject();
/* 854 */         return "FirmwareDateAndVersion".equals(jsonObject.getString("type"));
/* 855 */       } catch (RuntimeException e) {
/* 856 */         CommonCt3TcConfigSettingEndPoint.logger.warn("CommonCt3FirmwareDateAndVersionMessage format Error, will not be decoded! '{}'", s);
/*     */         
/* 858 */         return false;
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static class CommonCt3ReportHwStatusCycleMessageDecoder
/*     */     implements Decoder.Text<CommonTcConfigSettingMessage>
/*     */   {
/*     */     public void init(EndpointConfig config) {}
/*     */ 
/*     */     
/*     */     public void destroy() {}
/*     */ 
/*     */     
/*     */     public CommonCt3TcConfigSettingEndPoint.CommonTcConfigSettingMessage decode(String s) throws DecodeException {
/* 874 */       CommonCt3TcConfigSettingEndPoint.logger.debug("in CommonCt3ReportHwStatusCycleMessageDecoder.");
/* 875 */       JsonObject jsonObject = Json.createReader(new StringReader(s)).readObject();
/* 876 */       CommonCt3TcConfigSettingEndPoint.CommonCt3ReportHwStatusCycleMessageImpl msg = new CommonCt3TcConfigSettingEndPoint.CommonCt3ReportHwStatusCycleMessageImpl();
/* 877 */       msg.setDeviceType(jsonObject.getString("deviceType"));
/* 878 */       msg.setDeviceNames(extractStringList(jsonObject, "deviceNames"));
/* 879 */       msg.setHardwareCycle(Integer.valueOf(jsonObject.getInt("hardwareCycle")));
/* 880 */       return msg;
/*     */     }
/*     */     
/*     */     private static List<String> extractStringList(JsonObject jsonObject, String name) {
/* 884 */       List<String> result = new ArrayList<>();
/* 885 */       JsonArray jsonArray = jsonObject.getJsonArray(name);
/* 886 */       for (int i = 0; i < jsonArray.size(); i++) {
/* 887 */         result.add(jsonArray.getString(i));
/*     */       }
/* 889 */       return result;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean willDecode(String s) {
/*     */       try {
/* 895 */         JsonObject jsonObject = Json.createReader(new StringReader(s)).readObject();
/* 896 */         return "ReportHwStatusCycle".equals(jsonObject.getString("type"));
/* 897 */       } catch (RuntimeException e) {
/* 898 */         CommonCt3TcConfigSettingEndPoint.logger.warn("CommonCt3ReportHwStatusCycleMessage format Error, will not be decoded! '{}'", s);
/*     */         
/* 900 */         return false;
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static class CommonCt3RebootMessageDecoder
/*     */     implements Decoder.Text<CommonTcConfigSettingMessage>
/*     */   {
/*     */     public void init(EndpointConfig config) {}
/*     */ 
/*     */     
/*     */     public void destroy() {}
/*     */     
/*     */     public CommonCt3TcConfigSettingEndPoint.CommonTcConfigSettingMessage decode(String s) throws DecodeException {
/* 915 */       CommonCt3TcConfigSettingEndPoint.logger.debug("in CommonCt3RebootMessageDecoder.");
/* 916 */       JsonObject jsonObject = Json.createReader(new StringReader(s)).readObject();
/* 917 */       CommonCt3TcConfigSettingEndPoint.CommonCt3RebootMessageImpl msg = new CommonCt3TcConfigSettingEndPoint.CommonCt3RebootMessageImpl();
/* 918 */       msg.setDeviceType(jsonObject.getString("deviceType"));
/* 919 */       msg.setDeviceNames(extractStringList(jsonObject, "deviceNames"));
/* 920 */       return msg;
/*     */     }
/*     */     
/*     */     private static List<String> extractStringList(JsonObject jsonObject, String name) {
/* 924 */       List<String> result = new ArrayList<>();
/* 925 */       JsonArray jsonArray = jsonObject.getJsonArray(name);
/* 926 */       for (int i = 0; i < jsonArray.size(); i++) {
/* 927 */         result.add(jsonArray.getString(i));
/*     */       }
/* 929 */       return result;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean willDecode(String s) {
/*     */       try {
/* 935 */         JsonObject jsonObject = Json.createReader(new StringReader(s)).readObject();
/* 936 */         return "CommonCt3RebootMessage".equals(jsonObject.getString("type"));
/* 937 */       } catch (RuntimeException e) {
/* 938 */         CommonCt3TcConfigSettingEndPoint.logger.warn("CommonCt3RebootMessageDecoder format Error, will not be decoded! '{}'", s);
/* 939 */         return false;
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static class CommonCt3LockDbMessageDecoder
/*     */     implements Decoder.Text<CommonTcConfigSettingMessage>
/*     */   {
/*     */     public void init(EndpointConfig config) {}
/*     */ 
/*     */     
/*     */     public void destroy() {}
/*     */     
/*     */     public CommonCt3TcConfigSettingEndPoint.CommonTcConfigSettingMessage decode(String s) throws DecodeException {
/* 954 */       CommonCt3TcConfigSettingEndPoint.logger.debug("in CommonCt3LockDbMessageDecoder.");
/* 955 */       JsonObject jsonObject = Json.createReader(new StringReader(s)).readObject();
/* 956 */       CommonCt3TcConfigSettingEndPoint.CommonCt3LockDbMessageImpl msg = new CommonCt3TcConfigSettingEndPoint.CommonCt3LockDbMessageImpl();
/* 957 */       msg.setDeviceType(jsonObject.getString("deviceType"));
/* 958 */       msg.setDeviceNames(extractStringList(jsonObject, "deviceNames"));
/* 959 */       msg.setLockDb(Integer.valueOf(jsonObject.getInt("lockDb")));
/* 960 */       msg.setType(jsonObject.getString("type"));
/* 961 */       return msg;
/*     */     }
/*     */     
/*     */     private static List<String> extractStringList(JsonObject jsonObject, String name) {
/* 965 */       List<String> result = new ArrayList<>();
/* 966 */       JsonArray jsonArray = jsonObject.getJsonArray(name);
/* 967 */       for (int i = 0; i < jsonArray.size(); i++) {
/* 968 */         result.add(jsonArray.getString(i));
/*     */       }
/* 970 */       return result;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean willDecode(String s) {
/*     */       try {
/* 976 */         JsonObject jsonObject = Json.createReader(new StringReader(s)).readObject();
/* 977 */         return "LockDb".equals(jsonObject.getString("type"));
/* 978 */       } catch (RuntimeException e) {
/* 979 */         CommonCt3TcConfigSettingEndPoint.logger.warn("CommonCt3FirmwareDateAndVersionMessage format Error, will not be decoded! '{}'", s);
/*     */         
/* 981 */         return false;
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\lib\commonFm-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\c\fm\websocket\CommonCt3TcConfigSettingEndPoint.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */