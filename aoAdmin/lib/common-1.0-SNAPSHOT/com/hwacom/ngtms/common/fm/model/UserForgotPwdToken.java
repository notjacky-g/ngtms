/*    */ package com.hwacom.ngtms.common.fm.model;
/*    */ 
/*    */ import com.hwacom.ngtms.base.annotation.Comment;
/*    */ import com.hwacom.ngtms.base.annotation.NoOperationLog;
/*    */ import java.io.Serializable;
/*    */ import java.util.Date;
/*    */ import javax.persistence.Entity;
/*    */ import javax.persistence.Id;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Entity
/*    */ @NoOperationLog
/*    */ public class UserForgotPwdToken
/*    */   implements Serializable
/*    */ {
/*    */   private static final long serialVersionUID = 8599963218840885478L;
/*    */   @Id
/*    */   @Comment("使用者帳號")
/*    */   private String login;
/*    */   @Comment("使用者電子郵件")
/*    */   private String email;
/*    */   @Comment("一次性符記")
/*    */   private String token;
/*    */   @Comment("符記的有效時間")
/*    */   private Date expirationDate;
/*    */   
/*    */   public int hashCode() {
/* 31 */     int prime = 31;
/* 32 */     int result = 1;
/* 33 */     result = 31 * result + ((this.login == null) ? 0 : this.login.hashCode());
/* 34 */     return result;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(Object obj) {
/* 39 */     if (this == obj) return true; 
/* 40 */     if (obj == null) return false; 
/* 41 */     if (getClass() != obj.getClass()) return false; 
/* 42 */     UserForgotPwdToken other = (UserForgotPwdToken)obj;
/* 43 */     if (this.login == null)
/* 44 */     { if (other.login != null) return false;  }
/* 45 */     else if (!this.login.equals(other.login)) { return false; }
/* 46 */      return true;
/*    */   }
/*    */   
/*    */   public String getLogin() {
/* 50 */     return this.login;
/*    */   }
/*    */   
/*    */   public void setLogin(String login) {
/* 54 */     this.login = login;
/*    */   }
/*    */   
/*    */   public String getEmail() {
/* 58 */     return this.email;
/*    */   }
/*    */   
/*    */   public void setEmail(String email) {
/* 62 */     this.email = email;
/*    */   }
/*    */   
/*    */   public String getToken() {
/* 66 */     return this.token;
/*    */   }
/*    */   
/*    */   public void setToken(String token) {
/* 70 */     this.token = token;
/*    */   }
/*    */   
/*    */   public Date getExpirationDate() {
/* 74 */     return this.expirationDate;
/*    */   }
/*    */   
/*    */   public void setExpirationDate(Date expirationDate) {
/* 78 */     this.expirationDate = expirationDate;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\lib\common-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\common\fm\model\UserForgotPwdToken.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */