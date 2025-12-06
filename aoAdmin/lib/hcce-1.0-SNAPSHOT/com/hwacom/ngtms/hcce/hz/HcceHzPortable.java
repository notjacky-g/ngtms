/*    */ package com.hwacom.ngtms.hcce.hz;
/*    */ 
/*    */ import com.hazelcast.nio.serialization.Portable;
/*    */ import com.hwacom.ngtms.base.hazelcast.HzPortableObjEnum;
/*    */ import com.hwacom.ngtms.base.nodeperf.DiskInfo;
/*    */ import com.hwacom.ngtms.base.nodeperf.NetInfInfo;
/*    */ import com.hwacom.ngtms.base.nodeperf.NetStat;
/*    */ import com.hwacom.ngtms.base.nodeperf.NodePerformance;
/*    */ import com.hwacom.ngtms.hcce.shared.ClientHeartbeat;
/*    */ 
/*    */ public enum HcceHzPortable implements HzPortableObjEnum {
/* 12 */   CLIENT_HEARTBEAT,
/* 13 */   DISK_INFO,
/* 14 */   NETINF_INFO,
/* 15 */   NETSTAT,
/* 16 */   NODE_PERFORMANCE;
/*    */ 
/*    */   
/*    */   public int getId() {
/* 20 */     switch (this) {
/*    */       case CLIENT_HEARTBEAT:
/* 22 */         return 1001;
/*    */       case DISK_INFO:
/* 24 */         return 1002;
/*    */       case NETINF_INFO:
/* 26 */         return 1003;
/*    */       case NETSTAT:
/* 28 */         return 1004;
/*    */       case NODE_PERFORMANCE:
/* 30 */         return 1005;
/*    */     } 
/* 32 */     return 0;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public Class<? extends Portable> getClassType() {
/* 38 */     switch (this) {
/*    */       case CLIENT_HEARTBEAT:
/* 40 */         return (Class)ClientHeartbeat.class;
/*    */       case DISK_INFO:
/* 42 */         return (Class)DiskInfo.class;
/*    */       case NETINF_INFO:
/* 44 */         return (Class)NetInfInfo.class;
/*    */       case NETSTAT:
/* 46 */         return (Class)NetStat.class;
/*    */       case NODE_PERFORMANCE:
/* 48 */         return (Class)NodePerformance.class;
/*    */     } 
/* 50 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\lib\hcce-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\hcce\hz\HcceHzPortable.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */