/*    */ package com.hwacom.ngtms.ncc.tc.fw2.cmdparam.rgs;
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
/*    */ 
/*    */ 
/*    */ @GlobalParams(paramsName="rgsGraphicPatternColorWithDesc")
/*    */ public class RgsGraphicPatternColorWithDescPm
/*    */   implements Externalizable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   @CommandParam(name="frameId")
/*    */   public int frameId;
/*    */   @CommandParam(name="gWidth")
/*    */   public int gWidth;
/*    */   @CommandParam(name="gHeight")
/*    */   public int gHeight;
/*    */   @CommandParam(name="gDescription")
/*    */   public byte[] gDescription;
/*    */   @CommandParam(name="gPatternColor")
/*    */   public byte[] gPatternColor;
/*    */   
/*    */   public void writeExternal(ObjectOutput out)
/*    */     throws IOException
/*    */   {
/* 34 */     out.writeByte(this.frameId);
/* 35 */     out.writeShort(this.gWidth);
/* 36 */     out.writeShort(this.gHeight);
/* 37 */     out.writeObject(this.gDescription);
/* 38 */     out.writeObject(this.gPatternColor);
/*    */   }
/*    */   
/*    */   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException
/*    */   {
/* 43 */     this.frameId = (in.readByte() & 0xFF);
/* 44 */     this.gWidth = (in.readShort() & 0xFFFF);
/* 45 */     this.gHeight = (in.readShort() & 0xFFFF);
/* 46 */     this.gDescription = ((byte[])in.readObject());
/* 47 */     this.gPatternColor = ((byte[])in.readObject());
/*    */   }
/*    */   
/*    */   public String toString() {
/* 51 */     StringBuilder sb = new StringBuilder();
/* 52 */     sb.append('[');
/* 53 */     sb.append("frameId: ").append(this.frameId).append(", ");
/* 54 */     sb.append("gWidth: ").append(this.gWidth).append(", ");
/* 55 */     sb.append("gHeight: ").append(this.gHeight).append(", ");
/* 56 */     sb.append("gDescription: ")
/* 57 */       .append(BytesUtility.toHexString(this.gDescription))
/* 58 */       .append(", ");
/* 59 */     sb.append("gPatternColor: ")
/* 60 */       .append(BytesUtility.toHexString(this.gPatternColor))
/* 61 */       .append(']');
/* 62 */     return sb.toString();
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\nccCodec-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ncc\tc\fw2\cmdparam\rgs\RgsGraphicPatternColorWithDescPm.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */