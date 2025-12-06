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
/*    */ @CommandParams(cmdName="queryBig5BitmapReq")
/*    */ public class QueryBig5BitmapReqPm
/*    */   implements Externalizable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   public static final int cmdId = 44870;
/*    */   public static final String cmdName = "queryBig5BitmapReq";
/*    */   @CommandParam(name="big5Code")
/*    */   public int big5Code;
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
/* 32 */     out.writeShort(this.big5Code);
/* 33 */     out.writeByte(this.frameRow);
/* 34 */     out.writeByte(this.frameColumn);
/* 35 */     out.writeByte(this.frameNumber);
/*    */   }
/*    */   
/*    */   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException
/*    */   {
/* 40 */     this.big5Code = (in.readShort() & 0xFFFF);
/* 41 */     this.frameRow = (in.readByte() & 0xFF);
/* 42 */     this.frameColumn = (in.readByte() & 0xFF);
/* 43 */     this.frameNumber = (in.readByte() & 0xFF);
/*    */   }
/*    */   
/*    */   public String toString() {
/* 47 */     StringBuilder sb = new StringBuilder();
/* 48 */     sb.append('[');
/* 49 */     sb.append("big5Code: ").append(this.big5Code).append(", ");
/* 50 */     sb.append("frameRow: ").append(this.frameRow).append(", ");
/* 51 */     sb.append("frameColumn: ").append(this.frameColumn).append(", ");
/* 52 */     sb.append("frameNumber: ").append(this.frameNumber).append(']');
/* 53 */     return sb.toString();
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\nccCodec-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ncc\tc\ct3\cmdparam\cms\QueryBig5BitmapReqPm.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */