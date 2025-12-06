/*    */ package com.hwacom.ngtms.ncc.tc.fw3.cmdparam.scs;
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
/*    */ @GlobalParams(paramsName="graphicPatternColorWithDesc")
/*    */ public class GraphicPatternColorWithDescPm
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
/* 33 */     out.writeByte(this.frameId);
/* 34 */     out.writeShort(this.gWidth);
/* 35 */     out.writeShort(this.gHeight);
/* 36 */     out.writeObject(this.gDescription);
/* 37 */     out.writeObject(this.gPatternColor);
/*    */   }
/*    */   
/*    */   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException
/*    */   {
/* 42 */     this.frameId = (in.readByte() & 0xFF);
/* 43 */     this.gWidth = (in.readShort() & 0xFFFF);
/* 44 */     this.gHeight = (in.readShort() & 0xFFFF);
/* 45 */     this.gDescription = ((byte[])in.readObject());
/* 46 */     this.gPatternColor = ((byte[])in.readObject());
/*    */   }
/*    */   
/*    */   public String toString() {
/* 50 */     StringBuilder sb = new StringBuilder();
/* 51 */     sb.append('[');
/* 52 */     sb.append("frameId: ").append(this.frameId).append(", ");
/* 53 */     sb.append("gWidth: ").append(this.gWidth).append(", ");
/* 54 */     sb.append("gHeight: ").append(this.gHeight).append(", ");
/* 55 */     sb.append("gDescription: ")
/* 56 */       .append(BytesUtility.toHexString(this.gDescription))
/* 57 */       .append(", ");
/* 58 */     sb.append("gPatternColor: ")
/* 59 */       .append(BytesUtility.toHexString(this.gPatternColor))
/* 60 */       .append(']');
/* 61 */     return sb.toString();
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\nccCodec-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ncc\tc\fw3\cmdparam\scs\GraphicPatternColorWithDescPm.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */