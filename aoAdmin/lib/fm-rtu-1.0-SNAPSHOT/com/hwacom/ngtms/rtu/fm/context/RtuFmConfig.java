/*    */ package com.hwacom.ngtms.rtu.fm.context;
/*    */ 
/*    */ import com.hwacom.ngtms.hcce.hz.DistObjType;
/*    */ import com.hwacom.ngtms.hcce.hz.HzDistObjRegister;
/*    */ import com.hwacom.ngtms.rtu.fm.hz.RtuHzMap;
/*    */ import javax.annotation.PostConstruct;
/*    */ import javax.annotation.Resource;
/*    */ import org.springframework.beans.factory.annotation.Autowired;
/*    */ import org.springframework.context.annotation.Configuration;
/*    */ import org.springframework.core.env.Environment;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Configuration
/*    */ public class RtuFmConfig
/*    */ {
/*    */   @Resource
/*    */   private Environment environment;
/*    */   @Autowired
/*    */   private HzDistObjRegister hzDistObjRegister;
/*    */   
/*    */   @PostConstruct
/*    */   public void initFmContext() {
/* 38 */     this.hzDistObjRegister.regHzDistObj(DistObjType.MAP, RtuHzMap.class);
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\lib\fm-rtu-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\rtu\fm\context\RtuFmConfig.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */