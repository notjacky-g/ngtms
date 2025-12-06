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
/*    */ public class Adapter3
/*    */   extends XmlAdapter<String, int[]>
/*    */ {
/*    */   public int[] unmarshal(String value)
/*    */   {
/* 19 */     return DatatypeConverter.parseIntArray(value);
/*    */   }
/*    */   
/*    */   public String marshal(int[] value) {
/* 23 */     return DatatypeConverter.printIntArray(value);
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\commandProtocol-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\cmdprot\cmdfmtdef\Adapter3.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */