/*    */ package com.hwacom.ngtms.cmdprot.cmdfmtdef;
/*    */ 
/*    */ import javax.xml.bind.annotation.XmlEnum;
/*    */ import javax.xml.bind.annotation.XmlEnumValue;
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
/*    */ @XmlType(name = "cmdType")
/*    */ @XmlEnum
/*    */ public enum CmdType
/*    */ {
/* 36 */   SET("set"),
/*    */   
/* 38 */   QUERY("query"),
/*    */   
/* 40 */   REPORT("report");
/*    */   
/*    */   private final String value;
/*    */   
/*    */   CmdType(String v) {
/* 45 */     this.value = v;
/*    */   }
/*    */   
/*    */   public String value() {
/* 49 */     return this.value;
/*    */   }
/*    */   
/*    */   public static CmdType fromValue(String v) {
/* 53 */     for (CmdType c : values()) {
/* 54 */       if (c.value.equals(v)) {
/* 55 */         return c;
/*    */       }
/*    */     } 
/* 58 */     throw new IllegalArgumentException(v);
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\lib\commandProtocol-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\cmdprot\cmdfmtdef\CmdType.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */