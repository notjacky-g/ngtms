/*    */ package com.hwacom.ngtms.alarm.fm.model;
/*    */ 
/*    */ import com.google.common.base.MoreObjects;
/*    */ import com.google.common.base.MoreObjects.ToStringHelper;
/*    */ import com.hwacom.ngtms.alarm.shared.AlarmState;
/*    */ import com.hwacom.ngtms.base.annotation.Comment;
/*    */ import java.io.Serializable;
/*    */ import javax.persistence.Column;
/*    */ import javax.persistence.Entity;
/*    */ import javax.persistence.Id;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Entity
/*    */ public class Alarm
/*    */   implements Serializable
/*    */ {
/*    */   private static final long serialVersionUID = 5023280464169338888L;
/*    */   @Id
/*    */   @Comment("UUID")
/*    */   private String id;
/*    */   @Column(nullable=false)
/*    */   @Comment("AlarmLog id")
/*    */   private Long logId;
/*    */   @Column(nullable=false)
/*    */   @Comment("告警狀態")
/*    */   private AlarmState state;
/*    */   
/*    */   public String getId()
/*    */   {
/* 35 */     return this.id;
/*    */   }
/*    */   
/*    */   public void setId(String id) {
/* 39 */     this.id = id;
/*    */   }
/*    */   
/*    */   public Long getLogId() {
/* 43 */     return this.logId;
/*    */   }
/*    */   
/*    */   public void setLogId(Long logId) {
/* 47 */     this.logId = logId;
/*    */   }
/*    */   
/*    */   public AlarmState getState() {
/* 51 */     return this.state;
/*    */   }
/*    */   
/*    */   public void setState(AlarmState state) {
/* 55 */     this.state = state;
/*    */   }
/*    */   
/*    */   public int hashCode()
/*    */   {
/* 60 */     int prime = 31;
/* 61 */     int result = 1;
/* 62 */     result = 31 * result + (this.id == null ? 0 : this.id.hashCode());
/* 63 */     result = 31 * result + (this.logId == null ? 0 : this.logId.hashCode());
/* 64 */     result = 31 * result + (this.state == null ? 0 : this.state.hashCode());
/* 65 */     return result;
/*    */   }
/*    */   
/*    */   public boolean equals(Object obj)
/*    */   {
/* 70 */     if (this == obj) return true;
/* 71 */     if (obj == null) return false;
/* 72 */     if (getClass() != obj.getClass()) return false;
/* 73 */     Alarm other = (Alarm)obj;
/* 74 */     if (this.id == null) {
/* 75 */       if (other.id != null) return false;
/* 76 */     } else if (!this.id.equals(other.id)) return false;
/* 77 */     if (this.logId == null) {
/* 78 */       if (other.logId != null) return false;
/* 79 */     } else if (!this.logId.equals(other.logId)) return false;
/* 80 */     if (this.state != other.state) return false;
/* 81 */     return true;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public String toString()
/*    */   {
/* 90 */     return MoreObjects.toStringHelper(this).add("id", this.id).add("logId", this.logId).add("state", this.state).toString();
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\alarm-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\alarm\fm\model\Alarm.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */