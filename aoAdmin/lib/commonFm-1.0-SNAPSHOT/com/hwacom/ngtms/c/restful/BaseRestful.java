/*     */ package com.hwacom.ngtms.c.restful;
/*     */ 
/*     */ import com.hwacom.ngtms.base.crypto.TripleDESUtils;
/*     */ import com.hwacom.ngtms.base.oplog.shared.OperationItem;
/*     */ import com.hwacom.ngtms.base.oplog.shared.OperationResult;
/*     */ import com.hwacom.ngtms.c.fm.service.OpLogger;
/*     */ import com.hwacom.ngtms.c.shared.SubSystem;
/*     */ import java.util.Date;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import org.slf4j.Logger;
/*     */ import org.slf4j.LoggerFactory;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ 
/*     */ public abstract class BaseRestful
/*     */ {
/*  16 */   private static final Logger logger = LoggerFactory.getLogger(BaseRestful.class);
/*     */   
/*  18 */   private String userId = "userId";
/*     */ 
/*     */   
/*  21 */   private SubSystem subSystem = SubSystem.HCCE;
/*     */ 
/*     */ 
/*     */   
/*     */   @Autowired
/*     */   private OpLogger opLogger;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void logSetS(String ip, String messageId, Object... args) {
/*  32 */     this.opLogger.addLog(this.userId, ip, this.subSystem, OperationItem.SET, null, new Date(), OperationResult.SUCCESS, null, messageId, args);
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
/*     */   public void logSetF(String ip, String messageId, Object... args) {
/*  52 */     this.opLogger.addLog(this.userId, ip, this.subSystem, OperationItem.SET, null, new Date(), OperationResult.FAILURE, null, messageId, args);
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
/*     */   public void logGetS(String ip, String messageId, Object... args) {
/*  72 */     this.opLogger.addLog(this.userId, ip, this.subSystem, OperationItem.GET, null, new Date(), OperationResult.SUCCESS, null, messageId, args);
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
/*     */   public void logGetF(String ip, String messageId, Object... args) {
/*  92 */     this.opLogger.addLog(this.userId, ip, this.subSystem, OperationItem.GET, null, new Date(), OperationResult.FAILURE, null, messageId, args);
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
/*     */   public void logSetSWithDeviceAndRemark(String ip, String deviceName, String remark, String messageId, Object... args) {
/* 115 */     logger.debug("userId=" + this.userId);
/* 116 */     logger.debug("ip=" + ip);
/* 117 */     logger.debug("subSystem=" + this.subSystem.toString());
/* 118 */     logger.debug("deviceName=" + deviceName);
/* 119 */     logger.debug("remark=" + remark);
/* 120 */     logger.debug("messageId=" + messageId);
/* 121 */     this.opLogger.addLog(this.userId, ip, this.subSystem, OperationItem.SET, deviceName, new Date(), OperationResult.SUCCESS, remark, messageId, args);
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
/*     */   public void logSetFWithDeviceAndRemark(String ip, String deviceName, String remark, String messageId, Object... args) {
/* 144 */     this.opLogger.addLog(this.userId, ip, this.subSystem, OperationItem.SET, deviceName, new Date(), OperationResult.FAILURE, remark, messageId, args);
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
/*     */   public void logGetSWithDeviceAndRemark(String ip, String deviceName, String remark, String messageId, Object... args) {
/* 167 */     this.opLogger.addLog(this.userId, ip, this.subSystem, OperationItem.GET, deviceName, new Date(), OperationResult.SUCCESS, remark, messageId, args);
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
/*     */   public void logGetFWithDeviceAndRemark(String ip, String deviceName, String remark, String messageId, Object... args) {
/* 190 */     this.opLogger.addLog(this.userId, ip, this.subSystem, OperationItem.GET, deviceName, new Date(), OperationResult.FAILURE, remark, messageId, args);
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
/*     */   public void logSetSWithSubSystem(String ip, SubSystem subSystem, String messageId, Object... args) {
/* 212 */     this.opLogger.addLog(this.userId, ip, subSystem, OperationItem.SET, null, new Date(), OperationResult.SUCCESS, null, messageId, args);
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
/*     */   public void logSetFWithSubSystem(String ip, SubSystem subSystem, String messageId, Object... args) {
/* 234 */     this.opLogger.addLog(this.userId, ip, subSystem, OperationItem.SET, null, new Date(), OperationResult.FAILURE, null, messageId, args);
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
/*     */   public void logGetSWithSubSystem(String ip, SubSystem subSystem, String messageId, Object... args) {
/* 256 */     this.opLogger.addLog(this.userId, ip, subSystem, OperationItem.GET, null, new Date(), OperationResult.SUCCESS, null, messageId, args);
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
/*     */   public void logGetFWithSubSystem(String ip, SubSystem subSystem, String messageId, Object... args) {
/* 278 */     this.opLogger.addLog(this.userId, ip, subSystem, OperationItem.GET, null, new Date(), OperationResult.FAILURE, null, messageId, args);
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
/*     */   public void log(HttpServletRequest request, OperationItem item, String deviceName, OperationResult result, String remark, String messageId, Object... args) {
/*     */     try {
/* 300 */       String userId = TripleDESUtils.decrypt(request.getHeader("encryptedUserLogin"));
/* 301 */       String ip = request.getRemoteHost();
/* 302 */       this.opLogger.addLog(userId, ip, this.subSystem, item, deviceName, new Date(), result, remark, messageId, args);
/*     */     }
/* 304 */     catch (RuntimeException e) {
/* 305 */       logger.error("log failed.", e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public String getUserId() {
/* 310 */     return this.userId;
/*     */   }
/*     */   
/*     */   public void setUserId(String userId) {
/* 314 */     this.userId = userId;
/*     */   }
/*     */   
/*     */   public SubSystem getSubSystem() {
/* 318 */     return this.subSystem;
/*     */   }
/*     */   
/*     */   public void setSubSystem(SubSystem subSystem) {
/* 322 */     this.subSystem = subSystem;
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\lib\commonFm-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\c\restful\BaseRestful.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */