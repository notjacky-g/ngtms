/*    */ package com.hwacom.ngtms.node.performance.common;
/*    */ 
/*    */ import org.slf4j.Logger;
/*    */ import org.slf4j.LoggerFactory;
/*    */ import org.springframework.beans.factory.annotation.Autowired;
/*    */ import org.springframework.beans.factory.annotation.Value;
/*    */ import org.springframework.scheduling.annotation.Scheduled;
/*    */ import org.springframework.stereotype.Service;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Service
/*    */ public class NodePerformanceTask
/*    */ {
/* 19 */   private static final Logger logger = LoggerFactory.getLogger(NodePerformanceTask.class);
/*    */   @Value("${node.performance.collect:true}")
/*    */   private boolean collect;
/*    */   @Autowired
/*    */   private NodePerformanceLogService nodePerformanceLogService;
/*    */   
/*    */   @Scheduled(cron="59 * * * * ?")
/*    */   public void collectToMap()
/*    */   {
/* 28 */     if (this.collect) {
/* 29 */       logger.debug("Collect NodePerformance.");
/* 30 */       this.nodePerformanceLogService.collect();
/*    */     }
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\nodePerformance-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\node\performance\common\NodePerformanceTask.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */