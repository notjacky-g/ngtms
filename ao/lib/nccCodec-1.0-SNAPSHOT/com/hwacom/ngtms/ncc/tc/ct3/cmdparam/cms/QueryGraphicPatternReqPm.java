/*    */ package com.hwacom.ngtms.ncc.tc.ct3.cmdparam.cms;
/*    */ 
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
/*    */ @CommandParams(cmdName="queryGraphicPatternReq")
/*    */ public class QueryGraphicPatternReqPm
/*    */   implements Externalizable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   public static final int cmdId = 44872;
/*    */   public static final String cmdName = "queryGraphicPatternReq";
/*    */   @CommandParam(name="patternCode")
/*    */   public int patternCode;
/*    */   @CommandParam(name="frameRow")
/*    */   public int frameRow;
/*    */   @CommandParam(name="frameColumn")
/*    */   public int frameColumn;
/*    */   @CommandParam(name="frameNumber")
/*    */   public int frameNumber;
/*    */   
/*    */   public void writeExternal(ObjectOutput out)
/*    */     throws IOException
/*    */   {
/* 32 */     out.writeShort(this.patternCode);
/* 33 */     out.writeByte(this.frameRow);
/* 34 */     out.writeByte(this.frameColumn);
/* 35 */     out.writeByte(this.frameNumber);
/*    */   }
/*    */   
/*    */   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException
/*    */   {
/* 40 */     this.patternCode = (in.readShort() & 0xFFFF);
/* 41 */     this.frameRow = (in.readByte() & 0xFF);
/* 42 */     this.frameColumn = (in.readByte() & 0xFF);
/* 43 */     this.frameNumber = (in.readByte() & 0xFF);
/*    */   }
/*    */   
/*    */   public String toString() {
/* 47 */     StringBuilder sb = new StringBuilder();
/* 48 */     sb.append('[');
/* 49 */     sb.append("patternCode: ").append(this.patternCode).append(", ");
/* 50 */     sb.append("frameRow: ").append(this.frameRow).append(", ");
/* 51 */     sb.append("frameColumn: ").append(this.frameColumn).append(", ");
/* 52 */     sb.append("frameNumber: ").append(this.frameNumber).append(']');
/* 53 */     return sb.toString();
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\nccCodec-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ncc\tc\ct3\cmdparam\cms\QueryGraphicPatternReqPm.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */