/*    */ package com.hwacom.ngtms.pd.fm.context;
/*    */ 
/*    */ import com.hwacom.ngtms.hcce.hz.DistObjType;
/*    */ import com.hwacom.ngtms.hcce.hz.HzDistObjRegister;
/*    */ import com.hwacom.ngtms.pd.fm.hz.PdHzMap;
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
/*    */ public class PdFmConfig
/*    */ {
/*    */   @Resource
/*    */   private Environment environment;
/*    */   @Autowired
/*    */   private HzDistObjRegister hzDistObjRegister;
/*    */   
/*    */   @PostConstruct
/*    */   public void initFmContext()
/*    */   {
/* 38 */     this.hzDistObjRegister.regHzDistObj(DistObjType.MAP, PdHzMap.class);
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\fm-pd-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\pd\fm\context\PdFmConfig.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */