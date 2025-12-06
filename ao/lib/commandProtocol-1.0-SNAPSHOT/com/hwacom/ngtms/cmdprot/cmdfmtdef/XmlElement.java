/*    */ package com.hwacom.ngtms.cmdprot.cmdfmtdef;
/*    */ 
/*    */ public class XmlElement
/*    */ {
/*    */   public ElementType elType;
/*    */   public String id;
/*    */   public boolean useMarshalHelper;
/*    */   
/*    */   public static enum ElementType
/*    */   {
/* 11 */     VALUE, 
/* 12 */     BYTE_ARRAY, 
/* 13 */     BIT_ARRAY, 
/* 14 */     FIX_VALUE, 
/* 15 */     LIST, 
/* 16 */     CT3_VEHICLE_LIST, 
/* 17 */     PARAMS_REF, 
/* 18 */     CMD_PARAMS_REF;
/*    */     
/*    */     private ElementType() {}
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\commandProtocol-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\cmdprot\cmdfmtdef\XmlElement.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */