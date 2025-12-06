/*    */ package com.hwacom.ngtms.alarm.fm.hz;
/*    */ 
/*    */ import com.hwacom.ngtms.base.hazelcast.HzDistObjEnum;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public enum AlarmHzQueue
/*    */   implements HzDistObjEnum
/*    */ {
/* 14 */   Message("用於發送警報訊息");
/*    */   
/*    */   private String description;
/*    */   
/*    */   private AlarmHzQueue(String description)
/*    */   {
/* 20 */     this.description = description;
/*    */   }
/*    */   
/*    */   public String toHzName()
/*    */   {
/* 25 */     return "ALARM_" + toString();
/*    */   }
/*    */   
/*    */   public String getDescription()
/*    */   {
/* 30 */     return this.description;
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\alarm-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\alarm\fm\hz\AlarmHzQueue.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */