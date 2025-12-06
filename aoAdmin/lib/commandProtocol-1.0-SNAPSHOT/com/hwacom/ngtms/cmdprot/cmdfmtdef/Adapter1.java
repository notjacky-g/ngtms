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
/*    */ public class Adapter1
/*    */   extends XmlAdapter<String, int[]>
/*    */ {
/*    */   public int[] unmarshal(String value) {
/* 19 */     return DatatypeConverter.parseHexIntArray(value);
/*    */   }
/*    */   
/*    */   public String marshal(int[] value) {
/* 23 */     return DatatypeConverter.printHexIntArray(value);
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\lib\commandProtocol-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\cmdprot\cmdfmtdef\Adapter1.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */