/*    */ package com.hwacom.ngtms.ncc.tc.fw2.macrocommand.cms;
/*    */ 
/*    */ import java.io.Serializable;
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
/*    */ public class GetCmsDisplayImageRspPm
/*    */   implements Serializable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   public static final int cmdId = 65296;
/*    */   public static final String cmdName = "getCmsDisplayImageRspPm";
/*    */   private int gCodeId;
/*    */   private int frameNo;
/*    */   private int frameId;
/*    */   private int gWidth;
/*    */   private int gHeight;
/*    */   private String gDescription;
/*    */   private byte[] gPatternColor;
/*    */   
/*    */   public int getgCodeId()
/*    */   {
/* 40 */     return this.gCodeId;
/*    */   }
/*    */   
/*    */   public void setgCodeId(int gCodeId) {
/* 44 */     this.gCodeId = gCodeId;
/*    */   }
/*    */   
/*    */   public int getgWidth() {
/* 48 */     return this.gWidth;
/*    */   }
/*    */   
/*    */   public void setgWidth(int gWidth) {
/* 52 */     this.gWidth = gWidth;
/*    */   }
/*    */   
/*    */   public int getgHeight() {
/* 56 */     return this.gHeight;
/*    */   }
/*    */   
/*    */   public void setgHeight(int gHeight) {
/* 60 */     this.gHeight = gHeight;
/*    */   }
/*    */   
/*    */   public String getgDescription() {
/* 64 */     return this.gDescription;
/*    */   }
/*    */   
/*    */   public void setgDescription(String gDescription) {
/* 68 */     this.gDescription = gDescription;
/*    */   }
/*    */   
/*    */   public byte[] getgPatternColor() {
/* 72 */     return this.gPatternColor;
/*    */   }
/*    */   
/*    */   public void setgPatternColor(byte[] gPatternColor) {
/* 76 */     this.gPatternColor = gPatternColor;
/*    */   }
/*    */   
/*    */   public int getFrameNo() {
/* 80 */     return this.frameNo;
/*    */   }
/*    */   
/*    */   public void setFrameNo(int frameNo) {
/* 84 */     this.frameNo = frameNo;
/*    */   }
/*    */   
/*    */   public int getFrameId() {
/* 88 */     return this.frameId;
/*    */   }
/*    */   
/*    */   public void setFrameId(int frameId) {
/* 92 */     this.frameId = frameId;
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\nccCodec-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ncc\tc\fw2\macrocommand\cms\GetCmsDisplayImageRspPm.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */