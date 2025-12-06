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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @XmlType(name="valueType")
/*    */ @XmlEnum
/*    */ public enum ValueType
/*    */ {
/* 40 */   BYTE("byte"), 
/*    */   
/* 42 */   BOOLEAN("boolean"), 
/*    */   
/* 44 */   SHORT("short"), 
/*    */   
/* 46 */   INT("int"), 
/*    */   
/* 48 */   FLOAT("float"), 
/*    */   
/* 50 */   STRING("string"), 
/*    */   
/* 52 */   CMD_ID("cmdId");
/*    */   
/*    */   private final String value;
/*    */   
/*    */   private ValueType(String v) {
/* 57 */     this.value = v;
/*    */   }
/*    */   
/*    */   public String value() {
/* 61 */     return this.value;
/*    */   }
/*    */   
/*    */   public static ValueType fromValue(String v) {
/* 65 */     for (ValueType c : ) {
/* 66 */       if (c.value.equals(v)) {
/* 67 */         return c;
/*    */       }
/*    */     }
/* 70 */     throw new IllegalArgumentException(v);
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\commandProtocol-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\cmdprot\cmdfmtdef\ValueType.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */