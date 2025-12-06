/*    */ package com.hwacom.ngtms.cmdprot.cmdfmtdef;
/*    */ 
/*    */ import javax.xml.bind.annotation.adapters.XmlAdapter;
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
/*    */ public class Adapter2
/*    */   extends XmlAdapter<String, Integer>
/*    */ {
/*    */   public Integer unmarshal(String value) {
/* 19 */     return Integer.valueOf(DatatypeConverter.parseHexInt(value));
/*    */   }
/*    */   
/*    */   public String marshal(Integer value) {
/* 23 */     return DatatypeConverter.printHexInt(value.intValue());
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\lib\commandProtocol-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\cmdprot\cmdfmtdef\Adapter2.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */