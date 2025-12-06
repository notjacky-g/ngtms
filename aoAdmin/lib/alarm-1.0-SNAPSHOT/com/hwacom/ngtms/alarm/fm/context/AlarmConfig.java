/*    */ package com.hwacom.ngtms.alarm.fm.context;
/*    */ 
/*    */ import com.hwacom.ngtms.alarm.fm.hz.AlarmHzMap;
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
/*    */ @Configuration
/*    */ public class AlarmConfig
/*    */ {
/*    */   @Autowired
/*    */   private HzDistObjRegister hzDistObjRegister;
/*    */   
/*    */   @PostConstruct
/*    */   public void init() {
/* 23 */     this.hzDistObjRegister.regHzDistObj(DistObjType.MAP, AlarmHzMap.class);
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\lib\alarm-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\alarm\fm\context\AlarmConfig.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */