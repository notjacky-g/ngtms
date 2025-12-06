/*    */ package com.hwacom.ngtms.room.fm.task;
/*    */ 
/*    */ import com.hwacom.ngtms.hcce.fme.controller.fm.JobBase;
/*    */ import com.hwacom.ngtms.room.service.RoomCardMonitorService;
/*    */ import org.quartz.JobDataMap;
/*    */ import org.slf4j.Logger;
/*    */ import org.slf4j.LoggerFactory;
/*    */ import org.springframework.beans.factory.annotation.Autowired;
/*    */ 
/*    */ public class RoomTemporaryCardMonitorTask
/*    */   extends JobBase {
/* 12 */   private Logger logger = LoggerFactory.getLogger(RoomTemporaryCardMonitorTask.class);
/*    */   
/*    */   @Autowired
/*    */   RoomCardMonitorService roomCardMonitorService;
/*    */ 
/*    */   
/*    */   protected void init(JobDataMap jobDataMap) {}
/*    */   
/*    */   protected void process() {
/* 21 */     this.logger.debug("RoomTemporaryCardMonitorTask start.");
/* 22 */     this.roomCardMonitorService.doTemporaryCardAndUnlimiteCardMonitor();
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\lib\fm-room-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\room\fm\task\RoomTemporaryCardMonitorTask.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */