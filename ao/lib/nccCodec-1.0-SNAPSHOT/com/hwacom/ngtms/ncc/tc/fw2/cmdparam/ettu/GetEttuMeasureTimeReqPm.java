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
/*    */ @CommandParams(cmdName="getEttuMeasureTimeReq")
/*    */ public class GetEttuMeasureTimeReqPm
/*    */   implements Externalizable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   public static final int cmdId = 9986;
/*    */   public static final String cmdName = "getEttuMeasureTimeReq";
/*    */   @CommandParam(name="lrc")
/*    */   public int lrc;
/*    */   @CommandParam(name="endByty")
/* 21 */   public static final byte[] endByty = { -57 };
/*    */   
/*    */   public void writeExternal(ObjectOutput out) throws IOException
/*    */   {
/* 25 */     out.writeShort(this.lrc);
/*    */   }
/*    */   
/*    */   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException
/*    */   {
/* 30 */     this.lrc = (in.readShort() & 0xFFFF);
/*    */   }
/*    */   
/*    */   public String toString() {
/* 34 */     StringBuilder sb = new StringBuilder();
/* 35 */     sb.append('[');
/* 36 */     sb.append("lrc: ").append(this.lrc).append(", ");
/* 37 */     sb.append("endByty: ")
/* 38 */       .append(BytesUtility.toHexString(endByty))
/* 39 */       .append(']');
/* 40 */     return sb.toString();
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\nccCodec-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ncc\tc\fw2\cmdparam\ettu\GetEttuMeasureTimeReqPm.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */