/*    */ package com.hwacom.ngtms.room.fm.model;
/*    */ 
/*    */ import com.hwacom.ngtms.base.annotation.Comment;
/*    */ import java.io.Serializable;
/*    */ import javax.persistence.Entity;
/*    */ import javax.persistence.Id;
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
/*    */ @Entity
/*    */ public class RoomDeviceStatus
/*    */   implements Serializable
/*    */ {
/*    */   private static final long serialVersionUID = 1466750295180699448L;
/*    */   @Id
/*    */   @Comment("機房設備名稱")
/*    */   private String deviceName;
/*    */   @Comment("設備狀態")
/*    */   private Integer description;
/*    */   
/*    */   public String getDeviceName() {
/* 29 */     return this.deviceName;
/*    */   }
/*    */   
/*    */   public void setDeviceName(String deviceName) {
/* 33 */     this.deviceName = deviceName;
/*    */   }
/*    */   
/*    */   public Integer getDescription() {
/* 37 */     return this.description;
/*    */   }
/*    */   
/*    */   public void setDescription(Integer description) {
/* 41 */     this.description = description;
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 46 */     int prime = 31;
/* 47 */     int result = 1;
/* 48 */     result = 31 * result + ((this.deviceName == null) ? 0 : this.deviceName.hashCode());
/* 49 */     return result;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(Object obj) {
/* 54 */     if (this == obj) return true; 
/* 55 */     if (obj == null) return false; 
/* 56 */     if (getClass() != obj.getClass()) return false; 
/* 57 */     RoomDeviceStatus other = (RoomDeviceStatus)obj;
/* 58 */     if (this.deviceName == null)
/* 59 */     { if (other.deviceName != null) return false;  }
/* 60 */     else if (!this.deviceName.equals(other.deviceName)) { return false; }
/* 61 */      return true;
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\機房門禁10.121.41.38\ngtms\ao\lib.src\fm-room-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\room\fm\model\RoomDeviceStatus.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */