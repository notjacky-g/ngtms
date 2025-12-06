/*    */ package com.hwacom.ngtms.ncc.tc.fw2.cmdparam.ettu;
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
/*    */ @CommandParams(cmdName="setEttuSunshineTableRsp")
/*    */ public class SetEttuSunshineTableRspPm
/*    */   implements Externalizable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   public static final int cmdId = 10529;
/*    */   public static final String cmdName = "setEttuSunshineTableRsp";
/*    */   @CommandParam(name="tableCount")
/*    */   public int tableCount;
/*    */   @CommandParam(name="lrc")
/*    */   public int lrc;
/*    */   @CommandParam(name="endByty")
/* 24 */   public static final byte[] endByty = { -57 };
/*    */   
/*    */   public void writeExternal(ObjectOutput out) throws IOException
/*    */   {
/* 28 */     out.writeByte(this.tableCount);
/* 29 */     out.writeShort(this.lrc);
/*    */   }
/*    */   
/*    */   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException
/*    */   {
/* 34 */     this.tableCount = (in.readByte() & 0xFF);
/* 35 */     this.lrc = (in.readShort() & 0xFFFF);
/*    */   }
/*    */   
/*    */   public String toString() {
/* 39 */     StringBuilder sb = new StringBuilder();
/* 40 */     sb.append('[');
/* 41 */     sb.append("tableCount: ").append(this.tableCount).append(", ");
/* 42 */     sb.append("lrc: ").append(this.lrc).append(", ");
/* 43 */     sb.append("endByty: ")
/* 44 */       .append(BytesUtility.toHexString(endByty))
/* 45 */       .append(']');
/* 46 */     return sb.toString();
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\nccCodec-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ncc\tc\fw2\cmdparam\ettu\SetEttuSunshineTableRspPm.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */