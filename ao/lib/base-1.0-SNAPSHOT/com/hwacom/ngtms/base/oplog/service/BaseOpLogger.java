/*     */ package com.hwacom.ngtms.base.oplog.service;
/*     */ 
/*     */ import com.hwacom.ngtms.base.i18n.shared.MessageSourceExt;
/*     */ import com.hwacom.ngtms.base.oplog.shared.OperationItem;
/*     */ import com.hwacom.ngtms.base.oplog.shared.OperationResult;
/*     */ import java.util.Date;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BaseOpLogger
/*     */ {
/*     */   private OperationLogService operationLogService;
/*     */   private MessageSourceExt messageSourceExt;
/*     */   
/*     */   public BaseOpLogger(OperationLogService operationLogService, MessageSourceExt messageSourceExt)
/*     */   {
/*  19 */     this.operationLogService = operationLogService;
/*  20 */     this.messageSourceExt = messageSourceExt;
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
/*     */   public void addSetLog(String userId, String cpeIp, String subSysName, String deviceName, String description, Date operationTime, OperationResult operationResult, String remark)
/*     */   {
/*  42 */     this.operationLogService.addOpLog(userId, cpeIp, subSysName, OperationItem.SET
/*     */     
/*     */ 
/*     */ 
/*  46 */       .name(), deviceName, description, operationTime, operationResult, remark);
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
/*     */   public void addGetLog(String userId, String cpeIp, String subSysName, String deviceName, String description, Date operationTime, OperationResult operationResult, String remark)
/*     */   {
/*  72 */     this.operationLogService.addOpLog(userId, cpeIp, subSysName, OperationItem.GET
/*     */     
/*     */ 
/*     */ 
/*  76 */       .name(), deviceName, description, operationTime, operationResult, remark);
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
/*     */   public void addLog(String userId, String cpeIp, String subSysName, String operationItem, String deviceName, String description, Date operationTime, OperationResult operationResult, String remark)
/*     */   {
/* 105 */     this.operationLogService.addOpLog(userId, cpeIp, subSysName, operationItem, deviceName, description, operationTime, operationResult, remark);
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
/*     */   public void addLog(String userId, String cpeIp, String subSystem, String deviceName, OperationResult opResult, String descMsgId, Object... args)
/*     */   {
/* 134 */     addSetLog(userId, cpeIp, subSystem, deviceName, this.messageSourceExt
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 139 */       .getMessage(descMsgId, args), new Date(), opResult, null);
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
/*     */   public void addLog(String userId, String cpeIp, String subSystem, OperationItem operationItem, String deviceName, Date opTime, OperationResult opResult, String remark, String descMsgId, Object... args)
/*     */   {
/* 168 */     addLog(userId, cpeIp, subSystem, operationItem
/*     */     
/*     */ 
/*     */ 
/* 172 */       .toString(), deviceName, this.messageSourceExt
/*     */       
/* 174 */       .getMessage(descMsgId, args), opTime, opResult, remark);
/*     */   }
/*     */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\base-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\base\oplog\service\BaseOpLogger.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */