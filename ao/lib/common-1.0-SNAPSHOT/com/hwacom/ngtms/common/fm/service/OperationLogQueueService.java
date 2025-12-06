/*    */ package com.hwacom.ngtms.common.fm.service;
/*    */ 
/*    */ import com.hazelcast.core.IQueue;
/*    */ import com.hwacom.ngtms.base.hazelcast.HzUtils;
/*    */ import com.hwacom.ngtms.base.oplog.service.BaseOpLogger;
/*    */ import com.hwacom.ngtms.base.oplog.shared.OperationItem;
/*    */ import com.hwacom.ngtms.common.fm.hz.CommonHzQueue;
/*    */ import com.hwacom.ngtms.common.shared.OperationLogData;
/*    */ import org.springframework.beans.factory.annotation.Autowired;
/*    */ import org.springframework.stereotype.Service;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Service
/*    */ public class OperationLogQueueService
/*    */ {
/*    */   @Autowired
/*    */   private BaseOpLogger opLogger;
/*    */   
/*    */   public void logFromQueue()
/*    */   {
/* 23 */     IQueue<OperationLogData> queue = HzUtils.getQueue(CommonHzQueue.OPERATION_LOG);
/* 24 */     while (!queue.isEmpty()) {
/* 25 */       OperationLogData data = (OperationLogData)queue.poll();
/* 26 */       this.opLogger.addLog(data
/* 27 */         .getUserId(), data
/* 28 */         .getCpeIp(), data
/* 29 */         .getSubSystem(), data
/* 30 */         .getOperationItem().toString(), data
/* 31 */         .getDeviceName(), data
/* 32 */         .getDescription(), data
/* 33 */         .getOperationTime(), data
/* 34 */         .getOperationResult(), data
/* 35 */         .getRemark());
/*    */     }
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\common-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\common\fm\service\OperationLogQueueService.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */