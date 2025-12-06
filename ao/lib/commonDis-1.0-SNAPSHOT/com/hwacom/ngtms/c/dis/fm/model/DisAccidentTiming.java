/*    */ package com.hwacom.ngtms.c.dis.fm.model;
/*    */ 
/*    */ import com.hwacom.ngtms.base.annotation.Comment;
/*    */ import java.io.Serializable;
/*    */ import java.util.Date;
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
/*    */ public class DisAccidentTiming
/*    */   implements Serializable
/*    */ {
/*    */   private static final long serialVersionUID = 1024247854525415818L;
/*    */   @Comment("設備名稱")
/*    */   private String deviceName;
/*    */   @Comment("反應計畫的事件 originalEventId")
/*    */   private String incidentId;
/*    */   @Comment("反應計畫的事件最新的 Id")
/*    */   private String eventSessionId;
/*    */   @Comment("下載設備內容成功時間")
/*    */   private Date dataTime;
/*    */   
/*    */   public String getDeviceName()
/*    */   {
/* 34 */     return this.deviceName;
/*    */   }
/*    */   
/*    */   public void setDeviceName(String deviceName) {
/* 38 */     this.deviceName = deviceName;
/*    */   }
/*    */   
/*    */   public String getIncidentId() {
/* 42 */     return this.incidentId;
/*    */   }
/*    */   
/*    */   public void setIncidentId(String incidentId) {
/* 46 */     this.incidentId = incidentId;
/*    */   }
/*    */   
/*    */   public String getEventSessionId() {
/* 50 */     return this.eventSessionId;
/*    */   }
/*    */   
/*    */   public void setEventSessionId(String eventSessionId) {
/* 54 */     this.eventSessionId = eventSessionId;
/*    */   }
/*    */   
/*    */   public Date getDataTime() {
/* 58 */     return this.dataTime;
/*    */   }
/*    */   
/*    */   public void setDataTime(Date dataTime) {
/* 62 */     this.dataTime = dataTime;
/*    */   }
/*    */   
/*    */   public int hashCode()
/*    */   {
/* 67 */     int prime = 31;
/* 68 */     int result = 1;
/* 69 */     result = 31 * result + (this.eventSessionId == null ? 0 : this.eventSessionId.hashCode());
/* 70 */     return result;
/*    */   }
/*    */   
/*    */   public boolean equals(Object obj)
/*    */   {
/* 75 */     if (this == obj) return true;
/* 76 */     if (obj == null) return false;
/* 77 */     if (getClass() != obj.getClass()) return false;
/* 78 */     DisAccidentTiming other = (DisAccidentTiming)obj;
/* 79 */     if (this.eventSessionId == null) {
/* 80 */       if (other.eventSessionId != null) return false;
/* 81 */     } else if (!this.eventSessionId.equals(other.eventSessionId)) return false;
/* 82 */     return true;
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\commonDis-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\c\dis\fm\model\DisAccidentTiming.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */