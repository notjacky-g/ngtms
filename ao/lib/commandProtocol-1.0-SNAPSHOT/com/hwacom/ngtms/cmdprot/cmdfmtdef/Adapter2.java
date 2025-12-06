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
/*    */ public class Adapter2
/*    */   extends XmlAdapter<String, Integer>
/*    */ {
/*    */   public Integer unmarshal(String value)
/*    */   {
/* 19 */     return Integer.valueOf(DatatypeConverter.parseHexInt(value));
/*    */   }
/*    */   
/*    */   public String marshal(Integer value) {
/* 23 */     return DatatypeConverter.printHexInt(value.intValue());
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\commandProtocol-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\cmdprot\cmdfmtdef\Adapter2.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */