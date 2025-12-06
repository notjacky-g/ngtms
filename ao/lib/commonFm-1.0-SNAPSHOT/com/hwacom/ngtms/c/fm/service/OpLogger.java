/*     */ package com.hwacom.ngtms.c.fm.service;
/*     */ 
/*     */ import com.hwacom.ngtms.base.oplog.service.BaseOpLogger;
/*     */ import com.hwacom.ngtms.base.oplog.shared.OperationItem;
/*     */ import com.hwacom.ngtms.base.oplog.shared.OperationResult;
/*     */ import com.hwacom.ngtms.c.shared.SubSystem;
/*     */ import java.util.Date;
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
/*     */ @Service
/*     */ public class OpLogger
/*     */ {
/*     */   @Autowired
/*     */   private BaseOpLogger baseOpLogger;
/*     */   
/*     */   public void addLogS(String userId, String cpeIp, SubSystem subSystem, String deviceName, String descMsgId, Object... args)
/*     */   {
/*  29 */     addLog(userId, cpeIp, subSystem, deviceName, new Date(), OperationResult.SUCCESS, null, descMsgId, args);
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
/*     */   public void addLogF(String userId, String cpeIp, SubSystem subSystem, String deviceName, String descMsgId, Object... args)
/*     */   {
/*  48 */     addLog(userId, cpeIp, subSystem, deviceName, new Date(), OperationResult.FAILURE, null, descMsgId, args);
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
/*     */   public void addLogTS(String userId, String cpeIp, SubSystem subSystem, String deviceName, Date opTime, String descMsgId, Object... args)
/*     */   {
/*  68 */     addLog(userId, cpeIp, subSystem, deviceName, opTime, OperationResult.SUCCESS, null, descMsgId, args);
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
/*     */   public void addLogTF(String userId, String cpeIp, SubSystem subSystem, String deviceName, Date opTime, String descMsgId, Object... args)
/*     */   {
/*  88 */     addLog(userId, cpeIp, subSystem, deviceName, opTime, OperationResult.FAILURE, null, descMsgId, args);
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
/*     */   public void addLogRS(String userId, String cpeIp, SubSystem subSystem, String deviceName, String remark, String descMsgId, Object... args)
/*     */   {
/* 108 */     addLog(userId, cpeIp, subSystem, deviceName, new Date(), OperationResult.SUCCESS, remark, descMsgId, args);
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
/*     */   public void addLogRF(String userId, String cpeIp, SubSystem subSystem, String deviceName, String remark, String descMsgId, Object... args)
/*     */   {
/* 128 */     addLog(userId, cpeIp, subSystem, deviceName, new Date(), OperationResult.FAILURE, remark, descMsgId, args);
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
/*     */   public void addLog(String userId, String cpeIp, SubSystem subSystem, String deviceName, Date opTime, OperationResult opResult, String remark, String descMsgId, Object... args)
/*     */   {
/* 150 */     this.baseOpLogger.addLog(userId, cpeIp, subSystem
/*     */     
/*     */ 
/* 153 */       .toString(), OperationItem.SET, deviceName, opTime, opResult, remark, descMsgId, args);
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
/*     */   public void addLog(String userId, String cpeIp, SubSystem subSystem, OperationItem operationItem, String deviceName, Date opTime, OperationResult opResult, String remark, String descMsgId, Object... args)
/*     */   {
/* 174 */     this.baseOpLogger.addLog(userId, cpeIp, subSystem
/*     */     
/*     */ 
/* 177 */       .toString(), operationItem, deviceName, opTime, opResult, remark, descMsgId, args);
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
/*     */   public void addLog(String userId, String cpeIp, String subSysName, String deviceName, String description, Date operationTime, OperationResult operationResult, String remark)
/*     */   {
/* 196 */     this.baseOpLogger.addSetLog(userId, cpeIp, subSysName, deviceName, description, operationTime, operationResult, remark);
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
/*     */   public void addLog(String userId, String cpeIp, String subSysName, String operationItem, String deviceName, String description, Date operationTime, OperationResult operationResult, String remark)
/*     */   {
/* 210 */     this.baseOpLogger.addLog(userId, cpeIp, subSysName, operationItem, deviceName, description, operationTime, operationResult, remark);
/*     */   }
/*     */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\commonFm-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\c\fm\service\OpLogger.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */