/*    */ package com.hwacom.ngtms.room.fm.model;
/*    */ 
/*    */ import com.hwacom.ngtms.base.annotation.Comment;
/*    */ import java.io.Serializable;
/*    */ import javax.persistence.Column;
/*    */ import javax.persistence.Entity;
/*    */ import javax.persistence.GeneratedValue;
/*    */ import javax.persistence.GenerationType;
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
/*    */ @Entity
/*    */ public class RoomCardGroupConfig
/*    */   implements Serializable
/*    */ {
/*    */   private static final long serialVersionUID = -4462836378845820594L;
/*    */   @Id
/*    */   @GeneratedValue(strategy = GenerationType.IDENTITY)
/*    */   private Long id;
/*    */   @Comment("群組名稱,不可重複")
/*    */   @Column(length = 100, nullable = false)
/*    */   private String name;
/*    */   @Comment("備註")
/*    */   private String memo;
/*    */   
/*    */   public Long getId() {
/* 39 */     return this.id;
/*    */   }
/*    */   
/*    */   public void setId(Long id) {
/* 43 */     this.id = id;
/*    */   }
/*    */   
/*    */   public String getName() {
/* 47 */     return this.name;
/*    */   }
/*    */   
/*    */   public void setName(String name) {
/* 51 */     this.name = name;
/*    */   }
/*    */   
/*    */   public String getMemo() {
/* 55 */     return this.memo;
/*    */   }
/*    */   
/*    */   public void setMemo(String memo) {
/* 59 */     this.memo = memo;
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 64 */     int prime = 31;
/* 65 */     int result = 1;
/* 66 */     result = 31 * result + ((this.id == null) ? 0 : this.id.hashCode());
/* 67 */     return result;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(Object obj) {
/* 72 */     if (this == obj) return true; 
/* 73 */     if (obj == null) return false; 
/* 74 */     if (getClass() != obj.getClass()) return false; 
/* 75 */     RoomCardGroupConfig other = (RoomCardGroupConfig)obj;
/* 76 */     if (this.id == null)
/* 77 */     { if (other.id != null) return false;  }
/* 78 */     else if (!this.id.equals(other.id)) { return false; }
/* 79 */      return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 84 */     return "RoomCardGroupConfig [id=" + this.id + ", name=" + this.name + ", memo=" + this.memo + "]";
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\機房門禁10.121.41.38\ngtms\ao\lib.src\fm-room-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\room\fm\model\RoomCardGroupConfig.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */