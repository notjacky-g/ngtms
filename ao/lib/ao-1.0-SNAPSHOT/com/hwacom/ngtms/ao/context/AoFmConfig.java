/*    */ package com.hwacom.ngtms.ao.context;
/*    */ 
/*    */ import com.hwacom.ngtms.ao.fm.hz.AoHzMap;
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
/*    */ 
/*    */ @Configuration
/*    */ public class AoFmConfig
/*    */ {
/*    */   @Resource
/*    */   private Environment environment;
/*    */   @Autowired
/*    */   private HzDistObjRegister hzDistObjRegister;
/*    */   
/*    */   @PostConstruct
/*    */   public void initFmContext() {
/* 38 */     this.hzDistObjRegister.regHzDistObj(DistObjType.MAP, AoHzMap.class);
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\機房門禁10.121.41.38\ngtms\ao\lib.src\ao-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ao\context\AoFmConfig.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */