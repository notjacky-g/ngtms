/*    */ package com.hwacom.ngtms.node.performance.fm.context;
/*    */ 
/*    */ import com.hwacom.ngtms.hcce.hz.DistObjType;
/*    */ import com.hwacom.ngtms.hcce.hz.HzDistObjRegister;
/*    */ import com.hwacom.ngtms.node.performance.fm.hz.NodePerformanceHzMap;
/*    */ import javax.annotation.PostConstruct;
/*    */ import org.springframework.beans.factory.annotation.Autowired;
/*    */ import org.springframework.context.annotation.Configuration;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Configuration
/*    */ public class NodePerformanceConfig
/*    */ {
/*    */   @Autowired
/*    */   private HzDistObjRegister hzDistObjRegister;
/*    */   
/*    */   @PostConstruct
/*    */   public void init()
/*    */   {
/* 23 */     this.hzDistObjRegister.regHzDistObj(DistObjType.MAP, NodePerformanceHzMap.class);
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\nodePerformance-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\node\performance\fm\context\NodePerformanceConfig.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */