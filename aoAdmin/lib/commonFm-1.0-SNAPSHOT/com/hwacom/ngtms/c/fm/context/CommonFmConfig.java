/*    */ package com.hwacom.ngtms.c.fm.context;
/*    */ 
/*    */ import com.hwacom.ngtms.c.fm.hz.CommonFmHzMap;
/*    */ import com.hwacom.ngtms.c.fm.hz.CommonFmHzQueue;
/*    */ import com.hwacom.ngtms.c.fm.hz.CommonFmHzTopic;
/*    */ import com.hwacom.ngtms.hcce.hz.DistObjType;
/*    */ import com.hwacom.ngtms.hcce.hz.HzDistObjRegister;
/*    */ import javax.annotation.PostConstruct;
/*    */ import org.springframework.beans.factory.annotation.Autowired;
/*    */ import org.springframework.context.annotation.Configuration;
/*    */ import org.springframework.context.annotation.Import;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Configuration
/*    */ @Import({NccClientConfig.class})
/*    */ public class CommonFmConfig
/*    */ {
/*    */   @Autowired
/*    */   private HzDistObjRegister hzDistObjRegister;
/*    */   
/*    */   @PostConstruct
/*    */   public void initFmContext() {
/* 30 */     this.hzDistObjRegister.regHzDistObj(DistObjType.MAP, CommonFmHzMap.class);
/* 31 */     this.hzDistObjRegister.regHzDistObj(DistObjType.QUEUE, CommonFmHzQueue.class);
/* 32 */     this.hzDistObjRegister.regHzDistObj(DistObjType.TOPIC, CommonFmHzTopic.class);
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\lib\commonFm-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\c\fm\context\CommonFmConfig.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */