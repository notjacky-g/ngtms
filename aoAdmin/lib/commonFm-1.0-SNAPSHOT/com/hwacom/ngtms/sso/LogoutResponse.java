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
/*    */ @XmlAccessorType(XmlAccessType.FIELD)
/*    */ @XmlType(name = "logoutResponse", propOrder = {"logoutValue"})
/*    */ public class LogoutResponse
/*    */ {
/*    */   protected boolean logoutValue;
/*    */   
/*    */   public boolean isLogoutValue() {
/* 41 */     return this.logoutValue;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setLogoutValue(boolean value) {
/* 49 */     this.logoutValue = value;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\lib\commonFm-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\sso\LogoutResponse.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */