/*    */ package com.hwacom.ngtms.sso;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
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
/*    */ @XmlType(name = "findUsersBySpCodeResponse", propOrder = {"userValue"})
/*    */ public class FindUsersBySpCodeResponse
/*    */ {
/*    */   protected List<SsoWsUser> userValue;
/*    */   
/*    */   public List<SsoWsUser> getUserValue() {
/* 61 */     if (this.userValue == null) {
/* 62 */       this.userValue = new ArrayList<>();
/*    */     }
/* 64 */     return this.userValue;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\lib\commonFm-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\sso\FindUsersBySpCodeResponse.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */