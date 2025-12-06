/*    */ package com.hwacom.ngtms.ccs.fm.context;
/*    */ 
/*    */ import com.hwacom.ngtms.ccs.fm.hz.CcsHzMap;
/*    */ import com.hwacom.ngtms.hcce.hz.DistObjType;
/*    */ import com.hwacom.ngtms.hcce.hz.HzDistObjRegister;
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
/*    */ @Configuration
/*    */ public class CcsFmConfig
/*    */ {
/*    */   @Resource
/*    */   private Environment environment;
/*    */   @Autowired
/*    */   private HzDistObjRegister hzDistObjRegister;
/*    */   
/*    */   @PostConstruct
/*    */   public void initFmContext()
/*    */   {
/* 38 */     this.hzDistObjRegister.regHzDistObj(DistObjType.MAP, CcsHzMap.class);
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\fm-ccs-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ccs\fm\context\CcsFmConfig.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */