/*    */ package com.hwacom.ngtms.ncc.tc.fw2.cmdparam.ettu;
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
/*    */ 
/*    */ @CommandParams(cmdName="getEttuSegmentStatusRsp")
/*    */ public class GetEttuSegmentStatusRspPm
/*    */   implements Externalizable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   public static final int cmdId = 10517;
/*    */   public static final String cmdName = "getEttuSegmentStatusRsp";
/*    */   @CommandParam(name="phoneNumber")
/*    */   public PhoneNumberPm phoneNumberPm;
/*    */   @CommandParam(name="failInfo")
/*    */   public FailInfoPm failInfoPm;
/*    */   @CommandParam(name="date")
/*    */   public DatePm datePm;
/*    */   @CommandParam(name="time")
/*    */   public TimePm timePm;
/*    */   @CommandParam(name="lrc")
/*    */   public int lrc;
/*    */   @CommandParam(name="endByte")
/*    */   public int endByte;
/*    */   
/*    */   public void writeExternal(ObjectOutput out)
/*    */     throws IOException
/*    */   {
/* 37 */     out.writeObject(this.phoneNumberPm);
/* 38 */     out.writeObject(this.failInfoPm);
/* 39 */     out.writeObject(this.datePm);
/* 40 */     out.writeObject(this.timePm);
/* 41 */     out.writeShort(this.lrc);
/* 42 */     out.writeByte(this.endByte);
/*    */   }
/*    */   
/*    */   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException
/*    */   {
/* 47 */     this.phoneNumberPm = ((PhoneNumberPm)in.readObject());
/* 48 */     this.failInfoPm = ((FailInfoPm)in.readObject());
/* 49 */     this.datePm = ((DatePm)in.readObject());
/* 50 */     this.timePm = ((TimePm)in.readObject());
/* 51 */     this.lrc = (in.readShort() & 0xFFFF);
/* 52 */     this.endByte = (in.readByte() & 0xFF);
/*    */   }
/*    */   
/*    */   public String toString() {
/* 56 */     StringBuilder sb = new StringBuilder();
/* 57 */     sb.append('[');
/* 58 */     sb.append("phoneNumberPm: ").append(this.phoneNumberPm).append(", ");
/* 59 */     sb.append("failInfoPm: ").append(this.failInfoPm).append(", ");
/* 60 */     sb.append("datePm: ").append(this.datePm).append(", ");
/* 61 */     sb.append("timePm: ").append(this.timePm).append(", ");
/* 62 */     sb.append("lrc: ").append(this.lrc).append(", ");
/* 63 */     sb.append("endByte: ").append(this.endByte).append(']');
/* 64 */     return sb.toString();
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\nccCodec-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ncc\tc\fw2\cmdparam\ettu\GetEttuSegmentStatusRspPm.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */