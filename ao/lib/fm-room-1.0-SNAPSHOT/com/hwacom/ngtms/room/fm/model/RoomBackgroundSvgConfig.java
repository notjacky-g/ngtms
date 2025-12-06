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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Entity
/*    */ public class RoomBackgroundSvgConfig
/*    */   implements Serializable
/*    */ {
/*    */   private static final long serialVersionUID = -3319333470452246148L;
/*    */   @Id
/*    */   @Comment("機房底圖名稱")
/*    */   private String nameId;
/*    */   @Comment("機房所屬道路")
/*    */   private String roomLineId;
/*    */   @Comment("機房里程 ")
/*    */   private Integer roomMileage;
/*    */   @Comment("機房分機號碼 ")
/*    */   private String roomPhone;
/*    */   @Comment("底圖中文描述名稱")
/*    */   private String description;
/*    */   
/*    */   public String getNameId() {
/* 41 */     return this.nameId;
/*    */   }
/*    */   
/*    */   public void setNameId(String nameId) {
/* 45 */     this.nameId = nameId;
/*    */   }
/*    */   
/*    */   public String getRoomLineId() {
/* 49 */     return this.roomLineId;
/*    */   }
/*    */   
/*    */   public void setRoomLineId(String roomLineId) {
/* 53 */     this.roomLineId = roomLineId;
/*    */   }
/*    */   
/*    */   public Integer getRoomMileage() {
/* 57 */     return this.roomMileage;
/*    */   }
/*    */   
/*    */   public void setRoomMileage(Integer roomMileage) {
/* 61 */     this.roomMileage = roomMileage;
/*    */   }
/*    */   
/*    */   public String getRoomPhone() {
/* 65 */     return this.roomPhone;
/*    */   }
/*    */   
/*    */   public void setRoomPhone(String roomPhone) {
/* 69 */     this.roomPhone = roomPhone;
/*    */   }
/*    */   
/*    */   public String getDescription() {
/* 73 */     return this.description;
/*    */   }
/*    */   
/*    */   public void setDescription(String description) {
/* 77 */     this.description = description;
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 82 */     int prime = 31;
/* 83 */     int result = 1;
/* 84 */     result = 31 * result + ((this.nameId == null) ? 0 : this.nameId.hashCode());
/* 85 */     return result;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(Object obj) {
/* 90 */     if (this == obj) return true; 
/* 91 */     if (obj == null) return false; 
/* 92 */     if (getClass() != obj.getClass()) return false; 
/* 93 */     RoomBackgroundSvgConfig other = (RoomBackgroundSvgConfig)obj;
/* 94 */     if (this.nameId == null)
/* 95 */     { if (other.nameId != null) return false;  }
/* 96 */     else if (!this.nameId.equals(other.nameId)) { return false; }
/* 97 */      return true;
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\機房門禁10.121.41.38\ngtms\ao\lib.src\fm-room-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\room\fm\model\RoomBackgroundSvgConfig.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */