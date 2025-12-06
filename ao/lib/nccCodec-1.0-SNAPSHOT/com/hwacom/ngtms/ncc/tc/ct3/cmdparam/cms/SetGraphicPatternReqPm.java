/*    */ package com.hwacom.ngtms.ncc.tc.ct3.cmdparam.cms;
/*    */ 
/*    */ import com.hwacom.ngtms.cmdprot.cmdfmtdef.BytesUtility;
/*    */ import com.hwacom.ngtms.cmdprot.cmdfmtdef.CommandParam;
/*    */ import com.hwacom.ngtms.cmdprot.cmdfmtdef.CommandParams;
/*    */ import java.io.Externalizable;
/*    */ import java.io.IOException;
/*    */ import java.io.ObjectInput;
/*    */ import java.io.ObjectOutput;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @CommandParams(cmdName="setGraphicPatternReq")
/*    */ public class SetGraphicPatternReqPm
/*    */   implements Externalizable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   public static final int cmdId = 44824;
/*    */   public static final String cmdName = "setGraphicPatternReq";
/*    */   @CommandParam(name="patternCode")
/*    */   public int patternCode;
/*    */   @CommandParam(name="frameRow")
/*    */   public int frameRow;
/*    */   @CommandParam(name="frameColumn")
/*    */   public int frameColumn;
/*    */   @CommandParam(name="frameTotal")
/*    */   public int frameTotal;
/*    */   @CommandParam(name="frameNumber")
/*    */   public int frameNumber;
/*    */   @CommandParam(name="colorMap")
/*    */   public byte[] colorMap;
/*    */   
/*    */   public void writeExternal(ObjectOutput out)
/*    */     throws IOException
/*    */   {
/* 38 */     out.writeShort(this.patternCode);
/* 39 */     out.writeByte(this.frameRow);
/* 40 */     out.writeByte(this.frameColumn);
/* 41 */     out.writeByte(this.frameTotal);
/* 42 */     out.writeByte(this.frameNumber);
/* 43 */     out.writeObject(this.colorMap);
/*    */   }
/*    */   
/*    */   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException
/*    */   {
/* 48 */     this.patternCode = (in.readShort() & 0xFFFF);
/* 49 */     this.frameRow = (in.readByte() & 0xFF);
/* 50 */     this.frameColumn = (in.readByte() & 0xFF);
/* 51 */     this.frameTotal = (in.readByte() & 0xFF);
/* 52 */     this.frameNumber = (in.readByte() & 0xFF);
/* 53 */     this.colorMap = ((byte[])in.readObject());
/*    */   }
/*    */   
/*    */   public String toString() {
/* 57 */     StringBuilder sb = new StringBuilder();
/* 58 */     sb.append('[');
/* 59 */     sb.append("patternCode: ").append(this.patternCode).append(", ");
/* 60 */     sb.append("frameRow: ").append(this.frameRow).append(", ");
/* 61 */     sb.append("frameColumn: ").append(this.frameColumn).append(", ");
/* 62 */     sb.append("frameTotal: ").append(this.frameTotal).append(", ");
/* 63 */     sb.append("frameNumber: ").append(this.frameNumber).append(", ");
/* 64 */     sb.append("colorMap: ")
/* 65 */       .append(BytesUtility.toHexString(this.colorMap))
/* 66 */       .append(']');
/* 67 */     return sb.toString();
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\nccCodec-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ncc\tc\ct3\cmdparam\cms\SetGraphicPatternReqPm.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */