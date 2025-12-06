/*    */ package com.hwacom.ngtms.ncc.tc.fw2.cmdparam.csls;
/*    */ 
/*    */ import com.hwacom.ngtms.cmdprot.cmdfmtdef.BytesUtility;
/*    */ import com.hwacom.ngtms.cmdprot.cmdfmtdef.CommandParam;
/*    */ import com.hwacom.ngtms.cmdprot.cmdfmtdef.GlobalParams;
/*    */ import java.io.Externalizable;
/*    */ import java.io.IOException;
/*    */ import java.io.ObjectInput;
/*    */ import java.io.ObjectOutput;
/*    */ 
/*    */ 
/*    */ 
/*    */ @GlobalParams(paramsName="cslsGraphicPatternColor")
/*    */ public class CslsGraphicPatternColorPm
/*    */   implements Externalizable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   @CommandParam(name="frameId")
/*    */   public int frameId;
/*    */   @CommandParam(name="gWidth")
/*    */   public int gWidth;
/*    */   @CommandParam(name="gHeight")
/*    */   public int gHeight;
/*    */   @CommandParam(name="gPatternColor")
/*    */   public byte[] gPatternColor;
/*    */   
/*    */   public void writeExternal(ObjectOutput out)
/*    */     throws IOException
/*    */   {
/* 30 */     out.writeByte(this.frameId);
/* 31 */     out.writeShort(this.gWidth);
/* 32 */     out.writeShort(this.gHeight);
/* 33 */     out.writeObject(this.gPatternColor);
/*    */   }
/*    */   
/*    */   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException
/*    */   {
/* 38 */     this.frameId = (in.readByte() & 0xFF);
/* 39 */     this.gWidth = (in.readShort() & 0xFFFF);
/* 40 */     this.gHeight = (in.readShort() & 0xFFFF);
/* 41 */     this.gPatternColor = ((byte[])in.readObject());
/*    */   }
/*    */   
/*    */   public String toString() {
/* 45 */     StringBuilder sb = new StringBuilder();
/* 46 */     sb.append('[');
/* 47 */     sb.append("frameId: ").append(this.frameId).append(", ");
/* 48 */     sb.append("gWidth: ").append(this.gWidth).append(", ");
/* 49 */     sb.append("gHeight: ").append(this.gHeight).append(", ");
/* 50 */     sb.append("gPatternColor: ")
/* 51 */       .append(BytesUtility.toHexString(this.gPatternColor))
/* 52 */       .append(']');
/* 53 */     return sb.toString();
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\nccCodec-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ncc\tc\fw2\cmdparam\csls\CslsGraphicPatternColorPm.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */