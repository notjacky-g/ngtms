/*    */ package com.hwacom.ngtms.c.dis.fm.context;
/*    */ 
/*    */ import com.hwacom.ngtms.c.dis.fm.hz.DisHzMap;
/*    */ import com.hwacom.ngtms.c.dis.fm.hz.DisHzQueue;
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
/*    */ @Configuration
/*    */ public class DisFmConfig
/*    */ {
/*    */   @Autowired
/*    */   private HzDistObjRegister hzDistObjRegister;
/*    */   
/*    */   @PostConstruct
/*    */   public void initFmContext()
/*    */   {
/* 28 */     this.hzDistObjRegister.regHzDistObj(DistObjType.MAP, DisHzMap.class);
/* 29 */     this.hzDistObjRegister.regHzDistObj(DistObjType.QUEUE, DisHzQueue.class);
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\commonDis-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\c\dis\fm\context\DisFmConfig.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */