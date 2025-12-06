/*    */ package com.hwacom.ngtms.sso;
/*    */ 
/*    */ import javax.xml.bind.annotation.XmlAccessType;
/*    */ import javax.xml.bind.annotation.XmlAccessorType;
/*    */ import javax.xml.bind.annotation.XmlType;
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
/*    */ 
/*    */ @XmlAccessorType(XmlAccessType.FIELD)
/*    */ @XmlType(name="ssoWsIsCurPwdValid", propOrder={"userAccount", "pwdCorrect"})
/*    */ public class SsoWsIsCurPwdValid
/*    */ {
/*    */   protected String userAccount;
/*    */   protected boolean pwdCorrect;
/*    */   
/*    */   public String getUserAccount()
/*    */   {
/* 48 */     return this.userAccount;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public void setUserAccount(String value)
/*    */   {
/* 60 */     this.userAccount = value;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public boolean isPwdCorrect()
/*    */   {
/* 68 */     return this.pwdCorrect;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public void setPwdCorrect(boolean value)
/*    */   {
/* 76 */     this.pwdCorrect = value;
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\commonFm-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\sso\SsoWsIsCurPwdValid.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */