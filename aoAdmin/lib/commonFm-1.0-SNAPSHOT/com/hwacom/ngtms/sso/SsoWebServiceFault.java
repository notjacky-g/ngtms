/*    */ package com.hwacom.ngtms.sso;
/*    */ 
/*    */ import javax.xml.ws.WebFault;
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
/*    */ @WebFault(name = "SsoWebServiceFault", targetNamespace = "http://sso.ngtms.hwacom.com/")
/*    */ public class SsoWebServiceFault
/*    */   extends Exception
/*    */ {
/*    */   private String ssoWebServiceFault;
/*    */   
/*    */   public SsoWebServiceFault() {}
/*    */   
/*    */   public SsoWebServiceFault(String message) {
/* 23 */     super(message);
/*    */   }
/*    */   
/*    */   public SsoWebServiceFault(String message, Throwable cause) {
/* 27 */     super(message, cause);
/*    */   }
/*    */   
/*    */   public SsoWebServiceFault(String message, String ssoWebServiceFault) {
/* 31 */     super(message);
/* 32 */     this.ssoWebServiceFault = ssoWebServiceFault;
/*    */   }
/*    */   
/*    */   public SsoWebServiceFault(String message, String ssoWebServiceFault, Throwable cause) {
/* 36 */     super(message, cause);
/* 37 */     this.ssoWebServiceFault = ssoWebServiceFault;
/*    */   }
/*    */   
/*    */   public String getFaultInfo() {
/* 41 */     return this.ssoWebServiceFault;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\lib\commonFm-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\sso\SsoWebServiceFault.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */