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
/*    */ @XmlAccessorType(XmlAccessType.FIELD)
/*    */ @XmlType(name="findRolesBySpCodeResponse", propOrder={"roleValue"})
/*    */ public class FindRolesBySpCodeResponse
/*    */ {
/*    */   protected List<SsoWsRole> roleValue;
/*    */   
/*    */   public List<SsoWsRole> getRoleValue()
/*    */   {
/* 61 */     if (this.roleValue == null) {
/* 62 */       this.roleValue = new ArrayList();
/*    */     }
/* 64 */     return this.roleValue;
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\commonFm-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\sso\FindRolesBySpCodeResponse.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */