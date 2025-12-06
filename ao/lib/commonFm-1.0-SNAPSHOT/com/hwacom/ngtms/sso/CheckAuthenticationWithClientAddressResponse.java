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
/*    */ @XmlAccessorType(XmlAccessType.FIELD)
/*    */ @XmlType(name="checkAuthenticationWithClientAddressResponse", propOrder={"authenticationValue"})
/*    */ public class CheckAuthenticationWithClientAddressResponse
/*    */ {
/*    */   protected SsoWsAuthentication authenticationValue;
/*    */   
/*    */   public SsoWsAuthentication getAuthenticationValue()
/*    */   {
/* 45 */     return this.authenticationValue;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public void setAuthenticationValue(SsoWsAuthentication value)
/*    */   {
/* 57 */     this.authenticationValue = value;
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\commonFm-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\sso\CheckAuthenticationWithClientAddressResponse.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */