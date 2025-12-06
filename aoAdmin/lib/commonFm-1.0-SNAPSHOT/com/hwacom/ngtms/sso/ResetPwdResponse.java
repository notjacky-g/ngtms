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
/*    */ @XmlAccessorType(XmlAccessType.FIELD)
/*    */ @XmlType(name = "resetPwdResponse", propOrder = {"resetResult"})
/*    */ public class ResetPwdResponse
/*    */ {
/*    */   protected SsoWsResetPwd resetResult;
/*    */   
/*    */   public SsoWsResetPwd getResetResult() {
/* 45 */     return this.resetResult;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setResetResult(SsoWsResetPwd value) {
/* 57 */     this.resetResult = value;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\lib\commonFm-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\sso\ResetPwdResponse.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */