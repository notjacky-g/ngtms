/*    */ package com.hwacom.ngtms.sso;
/*    */ 
/*    */ import javax.xml.bind.annotation.XmlEnum;
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
/*    */ @XmlType(name="unitType")
/*    */ @XmlEnum
/*    */ public enum UnitType
/*    */ {
/* 29 */   SUPERVISION, 
/* 30 */   VENDOR, 
/* 31 */   CENTER, 
/* 32 */   BRANCH;
/*    */   
/*    */   private UnitType() {}
/* 35 */   public String value() { return name(); }
/*    */   
/*    */   public static UnitType fromValue(String v)
/*    */   {
/* 39 */     return valueOf(v);
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\commonFm-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\sso\UnitType.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */