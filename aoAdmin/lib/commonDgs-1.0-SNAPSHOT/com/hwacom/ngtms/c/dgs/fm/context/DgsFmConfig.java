/*    */ package com.hwacom.ngtms.c.dgs.fm.context;
/*    */ 
/*    */ import com.hwacom.ngtms.c.dgs.fm.hz.DgsHzMap;
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
/*    */ 
/*    */ 
/*    */ @Configuration
/*    */ public class DgsFmConfig
/*    */ {
/*    */   @Autowired
/*    */   private HzDistObjRegister hzDistObjRegister;
/*    */   
/*    */   @PostConstruct
/*    */   public void initFmContext() {
/* 27 */     this.hzDistObjRegister.regHzDistObj(DistObjType.MAP, DgsHzMap.class);
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\lib\commonDgs-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\c\dgs\fm\context\DgsFmConfig.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */