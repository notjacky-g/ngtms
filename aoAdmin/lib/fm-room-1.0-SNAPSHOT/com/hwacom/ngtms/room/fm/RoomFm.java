/*    */ package com.hwacom.ngtms.room.fm;
/*    */ 
/*    */ import com.hwacom.ngtms.hcce.core.exception.FmException;
/*    */ import com.hwacom.ngtms.hcce.fme.controller.fm.FmeMainBase;
/*    */ import com.hwacom.ngtms.hcce.shared.DynamicConfigDeclare;
/*    */ import com.hwacom.ngtms.room.fm.task.RoomCardReturnDateMonitorTask;
/*    */ import com.hwacom.ngtms.room.fm.task.RoomRegularCardMonitorTask;
/*    */ import com.hwacom.ngtms.room.fm.task.RoomTemporaryCardMonitorTask;
/*    */ import org.slf4j.Logger;
/*    */ import org.slf4j.LoggerFactory;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class RoomFm
/*    */   extends FmeMainBase
/*    */ {
/* 21 */   private static Logger logger = LoggerFactory.getLogger(RoomFm.class);
/*    */ 
/*    */   
/*    */   static {
/* 25 */     setDynamicConfigDeclares(new DynamicConfigDeclare[] { new DynamicConfigDeclare("RoomRegularCardMonitorTaskCron", "0 0 0 * * ?"), new DynamicConfigDeclare("RoomTemporaryCardMonitorTaskCron", "0 0/15 * * * ?"), new DynamicConfigDeclare("RoomCardReturnDateMonitorTaskCron", "0 0 6 * * ?") });
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public RoomFm(String name, String description) {
/* 36 */     super(name, description);
/*    */   }
/*    */ 
/*    */   
/*    */   public void init() throws FmException {}
/*    */ 
/*    */   
/*    */   public boolean isAllowConcurrentExecution() {
/* 44 */     return false;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void runFm() throws Exception {
/*    */     try {
/* 53 */       logger.info("{} started", getFmeName());
/*    */       
/* 55 */       scheduleReschedulableJob("RoomRegularCardMonitorTaskCron", RoomRegularCardMonitorTask.class, null, 
/*    */ 
/*    */ 
/*    */           
/* 59 */           getDynaConfig("RoomRegularCardMonitorTaskCron").getValue());
/*    */       
/* 61 */       scheduleReschedulableJob("RoomTemporaryCardMonitorTaskCron", RoomTemporaryCardMonitorTask.class, null, 
/*    */ 
/*    */ 
/*    */           
/* 65 */           getDynaConfig("RoomTemporaryCardMonitorTaskCron").getValue());
/*    */       
/* 67 */       scheduleReschedulableJob("RoomCardReturnDateMonitorTaskCron", RoomCardReturnDateMonitorTask.class, null, 
/*    */ 
/*    */ 
/*    */           
/* 71 */           getDynaConfig("RoomCardReturnDateMonitorTaskCron").getValue());
/*    */       
/* 73 */       while (checkFmKeepRunning()) {
/*    */         try {
/* 75 */           Thread.sleep(5000L);
/* 76 */         } catch (InterruptedException ex) {}
/*    */       }
/*    */     
/*    */     } finally {
/*    */       
/* 81 */       logger.info("{} stopped", getFmeName());
/*    */     } 
/*    */   }
/*    */   
/*    */   public void startTesting() throws FmException {}
/*    */ }


/* Location:              C:\User\\user\Desktop\lib\fm-room-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\room\fm\RoomFm.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */