/*    */ package com.hwacom.ngtms.ncc.tc.fw3.cmdparam.scs;
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
/*    */ @CommandParams(cmdName="setScsCharPatternReq")
/*    */ public class SetScsCharPatternReqPm
/*    */   implements Externalizable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   public static final int cmdId = 80;
/*    */   public static final String cmdName = "setScsCharPatternReq";
/*    */   @CommandParam(name="codeId")
/*    */   public int codeId;
/*    */   @CommandParam(name="row")
/*    */   public int row;
/*    */   @CommandParam(name="column")
/*    */   public int column;
/*    */   @CommandParam(name="pattern")
/*    */   public byte[] pattern;
/*    */   
/*    */   public void writeExternal(ObjectOutput out)
/*    */     throws IOException
/*    */   {
/* 32 */     out.writeShort(this.codeId);
/* 33 */     out.writeByte(this.row);
/* 34 */     out.writeByte(this.column);
/* 35 */     out.writeObject(this.pattern);
/*    */   }
/*    */   
/*    */   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException
/*    */   {
/* 40 */     this.codeId = (in.readShort() & 0xFFFF);
/* 41 */     this.row = (in.readByte() & 0xFF);
/* 42 */     this.column = (in.readByte() & 0xFF);
/* 43 */     this.pattern = ((byte[])in.readObject());
/*    */   }
/*    */   
/*    */   public String toString() {
/* 47 */     StringBuilder sb = new StringBuilder();
/* 48 */     sb.append('[');
/* 49 */     sb.append("codeId: ").append(this.codeId).append(", ");
/* 50 */     sb.append("row: ").append(this.row).append(", ");
/* 51 */     sb.append("column: ").append(this.column).append(", ");
/* 52 */     sb.append("pattern: ")
/* 53 */       .append(BytesUtility.toHexString(this.pattern))
/* 54 */       .append(']');
/* 55 */     return sb.toString();
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\nccCodec-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ncc\tc\fw3\cmdparam\scs\SetScsCharPatternReqPm.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */