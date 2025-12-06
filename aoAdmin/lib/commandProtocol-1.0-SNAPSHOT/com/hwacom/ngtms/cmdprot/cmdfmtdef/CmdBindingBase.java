/*    */ package com.hwacom.ngtms.cmdprot.cmdfmtdef;
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
/*    */ public class CmdBindingBase
/*    */   implements Cloneable
/*    */ {
/*    */   private EncodePostProcessor encodePostProcessor;
/*    */   
/*    */   public void setEncodePostProcessor(EncodePostProcessor encodePostProcessor) {
/* 25 */     this.encodePostProcessor = encodePostProcessor;
/*    */   }
/*    */   
/*    */   public EncodePostProcessor getEncodePostProcessor() {
/* 29 */     return this.encodePostProcessor;
/*    */   }
/*    */   
/*    */   public Object clone() throws CloneNotSupportedException {
/* 33 */     return super.clone();
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\lib\commandProtocol-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\cmdprot\cmdfmtdef\CmdBindingBase.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */