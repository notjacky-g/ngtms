/*    */ package com.hwacom.ngtms.cmdprot.cmdfmtdef;
/*    */ 
/*    */ public class XmlElement
/*    */ {
/*    */   public ElementType elType;
/*    */   public String id;
/*    */   public boolean useMarshalHelper;
/*    */   
/*    */   public enum ElementType
/*    */   {
/* 11 */     VALUE,
/* 12 */     BYTE_ARRAY,
/* 13 */     BIT_ARRAY,
/* 14 */     FIX_VALUE,
/* 15 */     LIST,
/* 16 */     CT3_VEHICLE_LIST,
/* 17 */     PARAMS_REF,
/* 18 */     CMD_PARAMS_REF;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\lib\commandProtocol-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\cmdprot\cmdfmtdef\XmlElement.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */