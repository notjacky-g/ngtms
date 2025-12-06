/*    */ package com.hwacom.ngtms.c.fm.service;
/*    */ 
/*    */ import com.hwacom.ngtms.c.shared.TcOplogResult;
/*    */ import java.util.concurrent.ConcurrentLinkedQueue;
/*    */ import org.slf4j.Logger;
/*    */ import org.slf4j.LoggerFactory;
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
/*    */ 
/*    */ @Service
/*    */ public class TcResponseLogQueueService
/*    */ {
/* 23 */   private static Logger logger = LoggerFactory.getLogger(TcResponseLogQueueService.class);
/* 24 */   private static ConcurrentLinkedQueue<TcOplogResult> queue = new ConcurrentLinkedQueue();
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public TcOplogResult offerQueue(TcOplogResult result)
/*    */   {
/* 33 */     logger.debug("Offer queue.");
/* 34 */     queue.offer(result);
/* 35 */     return (TcOplogResult)queue.poll();
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\commonFm-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\c\fm\service\TcResponseLogQueueService.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */