/*    */ package com.hwacom.ngtms.c.aop;
/*    */ 
/*    */ import com.hwacom.ngtms.base.oplog.shared.OperationResult;
/*    */ import com.hwacom.ngtms.c.fm.service.OpLogger;
/*    */ import com.hwacom.ngtms.c.shared.SubSystem;
/*    */ import com.hwacom.ngtms.c.shared.TcOplogResult;
/*    */ import com.hwacom.ngtms.ncc.remote.TcResponse;
/*    */ import com.hwacom.ngtms.ncc.remote.TcResponse.Result;
/*    */ import java.util.Date;
/*    */ import org.aspectj.lang.annotation.AfterReturning;
/*    */ import org.aspectj.lang.annotation.Aspect;
/*    */ import org.aspectj.lang.annotation.Pointcut;
/*    */ import org.slf4j.Logger;
/*    */ import org.slf4j.LoggerFactory;
/*    */ import org.springframework.beans.factory.annotation.Autowired;
/*    */ import org.springframework.stereotype.Service;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Service
/*    */ @Aspect
/*    */ public class AspectTcServiceLogger
/*    */ {
/* 32 */   private static final Logger logger = LoggerFactory.getLogger(AspectTcServiceLogger.class);
/*    */   
/*    */   @Autowired
/*    */   private OpLogger opLogger;
/*    */   
/*    */ 
/*    */   @Pointcut("execution(* com.hwacom.ngtms..fm.service.*TcResponseLogQueueService*.offerQueue*(..))")
/*    */   private void queuePointCut() {}
/*    */   
/*    */ 
/*    */   @AfterReturning(pointcut="queuePointCut()", returning="retVal")
/*    */   private void tcServiceLogger(Object retVal)
/*    */   {
/* 45 */     if ((retVal instanceof TcOplogResult)) {
/* 46 */       TcOplogResult result = (TcOplogResult)retVal;
/*    */       try {
/* 48 */         logger.debug("TcOplogResult:{}", result);
/* 49 */         String deviceName = result.getDeviceName();
/*    */         
/* 51 */         SubSystem subSystem = result.getSubSystem() == null ? SubSystem.HCCE : result.getSubSystem();
/* 52 */         OperationResult operationResult = OperationResult.FAILURE;
/* 53 */         String remark = result.getRemark();
/*    */         
/* 55 */         if (result.getTcResponse() != null) {
/* 56 */           if (result.getTcResponse().getResult().equals(TcResponse.Result.SUCCESS)) {
/* 57 */             operationResult = OperationResult.SUCCESS;
/* 58 */           } else if (result.getTcResponse().getResult().equals(TcResponse.Result.TIMEOUT)) {
/* 59 */             operationResult = OperationResult.FAILURE;
/* 60 */             remark = "等候逾時";
/* 61 */           } else if (result.getTcResponse().getResult().equals(TcResponse.Result.FAIL)) {
/* 62 */             operationResult = OperationResult.FAILURE;
/* 63 */             remark = result.getTcResponse().getErrorReason();
/*    */           }
/*    */         }
/* 66 */         this.opLogger.addLog(result
/* 67 */           .getUserId(), result
/* 68 */           .getIp(), subSystem, result
/*    */           
/* 70 */           .getOperationItem(), deviceName, new Date(), operationResult, remark, result
/*    */           
/*    */ 
/*    */ 
/*    */ 
/* 75 */           .getLogMessage(), new Object[0]);
/* 76 */         logger.debug("Add tcService log success.");
/*    */       } catch (RuntimeException e) {
/* 78 */         logger.error("Add tcService log failed.", e);
/*    */       }
/*    */     }
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\commonFm-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\c\aop\AspectTcServiceLogger.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */