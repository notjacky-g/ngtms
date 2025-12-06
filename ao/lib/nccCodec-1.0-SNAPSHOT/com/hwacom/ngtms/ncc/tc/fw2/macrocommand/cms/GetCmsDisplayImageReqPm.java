/*    */ package com.hwacom.ngtms.ncc.tc.fw2.macrocommand.cms;
/*    */ 
/*    */ import java.io.Serializable;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class GetCmsDisplayImageReqPm
/*    */   implements Serializable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   public static final int cmdId = 65296;
/*    */   public static final String cmdName = "getCmsDisplayImageReqPm";
/*    */   private int gCodeId;
/*    */   private GraphicType graphicType;
/*    */   
/*    */   public static enum GraphicType
/*    */   {
/* 21 */     R, 
/* 22 */     RG, 
/* 23 */     RGB, 
/* 24 */     FullColor;
/*    */     
/*    */ 
/*    */     private GraphicType() {}
/*    */   }
/*    */   
/*    */   public int getgCodeId()
/*    */   {
/* 32 */     return this.gCodeId;
/*    */   }
/*    */   
/*    */   public void setgCodeId(int gCodeId) {
/* 36 */     this.gCodeId = gCodeId;
/*    */   }
/*    */   
/*    */   public GraphicType getGraphicType() {
/* 40 */     return this.graphicType;
/*    */   }
/*    */   
/*    */   public void setGraphicType(GraphicType graphicType) {
/* 44 */     this.graphicType = graphicType;
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\nccCodec-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ncc\tc\fw2\macrocommand\cms\GetCmsDisplayImageReqPm.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */