/*    */ package com.hwacom.ngtms.common.fm.context;
/*    */ 
/*    */ import com.hwacom.ngtms.common.fm.hz.CommonHzMap;
/*    */ import com.hwacom.ngtms.common.fm.hz.CommonHzQueue;
/*    */ import com.hwacom.ngtms.common.fm.hz.CommonHzTopic;
/*    */ import com.hwacom.ngtms.hcce.hz.DistObjType;
/*    */ import com.hwacom.ngtms.hcce.hz.HzDistObjRegister;
/*    */ import javax.annotation.PostConstruct;
/*    */ import org.springframework.beans.factory.annotation.Autowired;
/*    */ import org.springframework.context.annotation.Configuration;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Configuration
/*    */ public class CommonConfig
/*    */ {
/*    */   @Autowired
/*    */   private HzDistObjRegister hzDistObjRegister;
/*    */   
/*    */   @PostConstruct
/*    */   public void initFmContext()
/*    */   {
/* 28 */     this.hzDistObjRegister.regHzDistObj(DistObjType.MAP, CommonHzMap.class);
/* 29 */     this.hzDistObjRegister.regHzDistObj(DistObjType.QUEUE, CommonHzQueue.class);
/* 30 */     this.hzDistObjRegister.regHzDistObj(DistObjType.TOPIC, CommonHzTopic.class);
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\common-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\common\fm\context\CommonConfig.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */