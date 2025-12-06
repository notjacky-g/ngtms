/*    */ package com.hwacom.ngtms.ao.fm.model;
/*    */ 
/*    */ import com.hwacom.ngtms.base.annotation.Comment;
/*    */ import java.io.Serializable;
/*    */ import javax.persistence.Entity;
/*    */ import javax.persistence.Id;
/*    */ 
/*    */ 
/*    */ @Entity
/*    */ public class MessageUser
/*    */   implements Serializable
/*    */ {
/*    */   private static final long serialVersionUID = 8203862630728733044L;
/*    */   @Id
/*    */   @Comment("手機號碼")
/*    */   private String id;
/*    */   @Comment("姓名")
/*    */   private String name;
/*    */   
/*    */   public String getId() {
/* 21 */     return this.id;
/*    */   }
/*    */   
/*    */   public void setId(String id) {
/* 25 */     this.id = id;
/*    */   }
/*    */   
/*    */   public String getName() {
/* 29 */     return this.name;
/*    */   }
/*    */   
/*    */   public void setName(String name) {
/* 33 */     this.name = name;
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 38 */     int prime = 31;
/* 39 */     int result = 1;
/* 40 */     result = 31 * result + ((this.id == null) ? 0 : this.id.hashCode());
/* 41 */     return result;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(Object obj) {
/* 46 */     if (this == obj) return true; 
/* 47 */     if (obj == null) return false; 
/* 48 */     if (getClass() != obj.getClass()) return false; 
/* 49 */     MessageUser other = (MessageUser)obj;
/* 50 */     if (this.id == null)
/* 51 */     { if (other.id != null) return false;  }
/* 52 */     else if (!this.id.equals(other.id)) { return false; }
/* 53 */      return true;
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\機房門禁10.121.41.38\ngtms\ao\lib.src\ao-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ao\fm\model\MessageUser.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */