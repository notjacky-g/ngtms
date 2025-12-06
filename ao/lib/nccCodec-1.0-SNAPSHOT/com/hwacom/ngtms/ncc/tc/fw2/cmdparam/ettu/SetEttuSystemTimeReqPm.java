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
/*    */ 
/*    */ @CommandParams(cmdName="setEttuSystemTimeReq")
/*    */ public class SetEttuSystemTimeReqPm
/*    */   implements Externalizable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   public static final int cmdId = 9985;
/*    */   public static final String cmdName = "setEttuSystemTimeReq";
/*    */   @CommandParam(name="date")
/*    */   public DatePm datePm;
/*    */   @CommandParam(name="time")
/*    */   public TimePm timePm;
/*    */   @CommandParam(name="lrc")
/*    */   public int lrc;
/*    */   @CommandParam(name="endByty")
/* 27 */   public static final byte[] endByty = { -57 };
/*    */   
/*    */   public void writeExternal(ObjectOutput out) throws IOException
/*    */   {
/* 31 */     out.writeObject(this.datePm);
/* 32 */     out.writeObject(this.timePm);
/* 33 */     out.writeShort(this.lrc);
/*    */   }
/*    */   
/*    */   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException
/*    */   {
/* 38 */     this.datePm = ((DatePm)in.readObject());
/* 39 */     this.timePm = ((TimePm)in.readObject());
/* 40 */     this.lrc = (in.readShort() & 0xFFFF);
/*    */   }
/*    */   
/*    */   public String toString() {
/* 44 */     StringBuilder sb = new StringBuilder();
/* 45 */     sb.append('[');
/* 46 */     sb.append("datePm: ").append(this.datePm).append(", ");
/* 47 */     sb.append("timePm: ").append(this.timePm).append(", ");
/* 48 */     sb.append("lrc: ").append(this.lrc).append(", ");
/* 49 */     sb.append("endByty: ")
/* 50 */       .append(BytesUtility.toHexString(endByty))
/* 51 */       .append(']');
/* 52 */     return sb.toString();
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\nccCodec-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ncc\tc\fw2\cmdparam\ettu\SetEttuSystemTimeReqPm.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */