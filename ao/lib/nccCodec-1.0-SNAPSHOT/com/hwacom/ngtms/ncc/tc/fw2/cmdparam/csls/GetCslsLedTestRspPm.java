/*    */ package com.hwacom.ngtms.ncc.tc.fw2.cmdparam.csls;
/*    */ 
/*    */ import com.hwacom.ngtms.cmdprot.cmdfmtdef.BytesUtility;
/*    */ import com.hwacom.ngtms.cmdprot.cmdfmtdef.CommandParam;
/*    */ import com.hwacom.ngtms.cmdprot.cmdfmtdef.CommandParams;
/*    */ import com.hwacom.ngtms.ncc.tc.fw2.cmdparam.HwStatusPm;
/*    */ import java.io.Externalizable;
/*    */ import java.io.IOException;
/*    */ import java.io.ObjectInput;
/*    */ import java.io.ObjectOutput;
/*    */ 
/*    */ @CommandParams(cmdName="getCslsLedTestRsp")
/*    */ public class GetCslsLedTestRspPm
/*    */   implements Externalizable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   public static final int cmdId = 177;
/*    */   public static final String cmdName = "getCslsLedTestRsp";
/*    */   @CommandParam(name="hwStatus")
/*    */   public HwStatusPm hwStatusPm;
/*    */   @CommandParam(name="boardNo")
/*    */   public int boardNo;
/*    */   @CommandParam(name="ledStatus")
/*    */   public byte[] ledStatus;
/*    */   
/*    */   public void writeExternal(ObjectOutput out)
/*    */     throws IOException
/*    */   {
/* 29 */     out.writeObject(this.hwStatusPm);
/* 30 */     out.writeByte(this.boardNo);
/* 31 */     out.writeObject(this.ledStatus);
/*    */   }
/*    */   
/*    */   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException
/*    */   {
/* 36 */     this.hwStatusPm = ((HwStatusPm)in.readObject());
/* 37 */     this.boardNo = (in.readByte() & 0xFF);
/* 38 */     this.ledStatus = ((byte[])in.readObject());
/*    */   }
/*    */   
/*    */   public String toString() {
/* 42 */     StringBuilder sb = new StringBuilder();
/* 43 */     sb.append('[');
/* 44 */     sb.append("hwStatusPm: ").append(this.hwStatusPm).append(", ");
/* 45 */     sb.append("boardNo: ").append(this.boardNo).append(", ");
/* 46 */     sb.append("ledStatus: ")
/* 47 */       .append(BytesUtility.toHexString(this.ledStatus))
/* 48 */       .append(']');
/* 49 */     return sb.toString();
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\nccCodec-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ncc\tc\fw2\cmdparam\csls\GetCslsLedTestRspPm.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */