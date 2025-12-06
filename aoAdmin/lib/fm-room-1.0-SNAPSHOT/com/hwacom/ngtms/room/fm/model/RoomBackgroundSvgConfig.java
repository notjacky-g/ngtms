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
/*    */ public class RoomBackgroundSvgConfig
/*    */   implements Serializable
/*    */ {
/*    */   private static final long serialVersionUID = -3319333470452246148L;
/*    */   @Id
/*    */   @Comment("機房底圖名稱")
/*    */   private String nameId;
/*    */   @Comment("底圖中文描述名稱")
/*    */   private String description;
/*    */   
/*    */   public String getNameId() {
/* 29 */     return this.nameId;
/*    */   }
/*    */   
/*    */   public void setNameId(String nameId) {
/* 33 */     this.nameId = nameId;
/*    */   }
/*    */   
/*    */   public String getDescription() {
/* 37 */     return this.description;
/*    */   }
/*    */   
/*    */   public void setDescription(String description) {
/* 41 */     this.description = description;
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 46 */     int prime = 31;
/* 47 */     int result = 1;
/* 48 */     result = 31 * result + ((this.nameId == null) ? 0 : this.nameId.hashCode());
/* 49 */     return result;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(Object obj) {
/* 54 */     if (this == obj) return true; 
/* 55 */     if (obj == null) return false; 
/* 56 */     if (getClass() != obj.getClass()) return false; 
/* 57 */     RoomBackgroundSvgConfig other = (RoomBackgroundSvgConfig)obj;
/* 58 */     if (this.nameId == null)
/* 59 */     { if (other.nameId != null) return false;  }
/* 60 */     else if (!this.nameId.equals(other.nameId)) { return false; }
/* 61 */      return true;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\lib\fm-room-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\room\fm\model\RoomBackgroundSvgConfig.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */