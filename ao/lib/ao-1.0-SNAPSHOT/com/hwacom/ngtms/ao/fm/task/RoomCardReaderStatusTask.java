/*    */ package com.hwacom.ngtms.ao.fm.task;
/*    */ 
/*    */ import com.hazelcast.core.IMap;
/*    */ import com.hwacom.ngtms.ao.fm.service.LiveFaceService;
/*    */ import com.hwacom.ngtms.ao.fm.service.RoomCardReaderStatusService;
/*    */ import com.hwacom.ngtms.base.hazelcast.HzDistObjEnum;
/*    */ import com.hwacom.ngtms.base.hazelcast.HzUtils;
/*    */ import com.hwacom.ngtms.c.fm.hz.CommonFmHzMap;
/*    */ import com.hwacom.ngtms.c.fm.model.DeviceTcStatus;
/*    */ import com.hwacom.ngtms.hcce.fme.controller.fm.JobBase;
/*    */ import org.quartz.DisallowConcurrentExecution;
/*    */ import org.quartz.JobDataMap;
/*    */ import org.slf4j.Logger;
/*    */ import org.slf4j.LoggerFactory;
/*    */ import org.springframework.beans.factory.annotation.Autowired;
/*    */ 
/*    */ @DisallowConcurrentExecution
/*    */ public class RoomCardReaderStatusTask extends JobBase {
/* 19 */   private static final Logger logger = LoggerFactory.getLogger(RoomCardReaderStatusTask.class);
/*    */   
/*    */   @Autowired
/*    */   RoomCardReaderStatusService roomCardReaderStatusService;
/*    */   
/*    */   @Autowired
/*    */   LiveFaceService liveFaceService;
/*    */   
/*    */   protected void init(JobDataMap jobDataMap) {}
/*    */   
/*    */   protected void process() {
/* 30 */     logger.debug("RoomCardReaderStatusTask start.");
/* 31 */     long startTime = System.currentTimeMillis();
/* 32 */     this.roomCardReaderStatusService.process();
/*    */     
/* 34 */     IMap<String, DeviceTcStatus> statusMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.DeviceTcStatus);
/* 35 */     statusMap.putAll(this.liveFaceService.getLifeServerStatus());
/* 36 */     logger.debug("RoomCardReaderStatusTask end ,{},time diff {}", 
/*    */         
/* 38 */         Long.valueOf(System.currentTimeMillis()), 
/* 39 */         Long.valueOf(System.currentTimeMillis() - startTime));
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\機房門禁10.121.41.38\ngtms\ao\lib.src\ao-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ao\fm\task\RoomCardReaderStatusTask.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */