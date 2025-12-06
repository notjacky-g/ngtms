/*    */ package com.hwacom.ngtms.common.fm.model;
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
/*    */ @Entity
/*    */ public class UrlPatternRole
/*    */   implements Serializable
/*    */ {
/*    */   private static final long serialVersionUID = 1810785270654085574L;
/*    */   @Id
/*    */   @Comment("流水號")
/*    */   private Long id;
/*    */   @Comment("樣版")
/*    */   private String pattern;
/*    */   @Comment("角色")
/*    */   private String Role;
/*    */   
/*    */   public Long getId() {
/* 30 */     return this.id;
/*    */   }
/*    */   
/*    */   public void setId(Long id) {
/* 34 */     this.id = id;
/*    */   }
/*    */   
/*    */   public String getPattern() {
/* 38 */     return this.pattern;
/*    */   }
/*    */   
/*    */   public void setPattern(String pattern) {
/* 42 */     this.pattern = pattern;
/*    */   }
/*    */   
/*    */   public String getRole() {
/* 46 */     return this.Role;
/*    */   }
/*    */   
/*    */   public void setRole(String role) {
/* 50 */     this.Role = role;
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 55 */     int prime = 31;
/* 56 */     int result = 1;
/* 57 */     result = 31 * result + ((this.id == null) ? 0 : this.id.hashCode());
/* 58 */     return result;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(Object obj) {
/* 63 */     if (this == obj) return true; 
/* 64 */     if (obj == null) return false; 
/* 65 */     if (getClass() != obj.getClass()) return false; 
/* 66 */     UrlPatternRole other = (UrlPatternRole)obj;
/* 67 */     if (this.id == null)
/* 68 */     { if (other.id != null) return false;  }
/* 69 */     else if (!this.id.equals(other.id)) { return false; }
/* 70 */      return true;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\lib\common-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\common\fm\model\UrlPatternRole.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */