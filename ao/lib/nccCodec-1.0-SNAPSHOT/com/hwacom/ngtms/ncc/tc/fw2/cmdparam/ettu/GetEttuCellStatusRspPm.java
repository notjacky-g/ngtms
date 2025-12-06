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
/*    */ 
/*    */ 
/*    */ 
/*    */ @CommandParams(cmdName="getEttuCellStatusRsp")
/*    */ public class GetEttuCellStatusRspPm
/*    */   implements Externalizable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   public static final int cmdId = 10516;
/*    */   public static final String cmdName = "getEttuCellStatusRsp";
/*    */   @CommandParam(name="phoneNumber")
/*    */   public PhoneNumberPm phoneNumberPm;
/*    */   @CommandParam(name="failInfo")
/*    */   public FailInfoPm failInfoPm;
/*    */   @CommandParam(name="date")
/*    */   public DatePm datePm;
/*    */   @CommandParam(name="time")
/*    */   public TimePm timePm;
/*    */   @CommandParam(name="etState")
/*    */   public EtStatePm etStatePm;
/*    */   @CommandParam(name="lrc")
/*    */   public int lrc;
/*    */   @CommandParam(name="endByte")
/* 36 */   public static final byte[] endByte = { -57 };
/*    */   
/*    */   public void writeExternal(ObjectOutput out) throws IOException
/*    */   {
/* 40 */     out.writeObject(this.phoneNumberPm);
/* 41 */     out.writeObject(this.failInfoPm);
/* 42 */     out.writeObject(this.datePm);
/* 43 */     out.writeObject(this.timePm);
/* 44 */     out.writeObject(this.etStatePm);
/* 45 */     out.writeShort(this.lrc);
/*    */   }
/*    */   
/*    */   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException
/*    */   {
/* 50 */     this.phoneNumberPm = ((PhoneNumberPm)in.readObject());
/* 51 */     this.failInfoPm = ((FailInfoPm)in.readObject());
/* 52 */     this.datePm = ((DatePm)in.readObject());
/* 53 */     this.timePm = ((TimePm)in.readObject());
/* 54 */     this.etStatePm = ((EtStatePm)in.readObject());
/* 55 */     this.lrc = (in.readShort() & 0xFFFF);
/*    */   }
/*    */   
/*    */   public String toString() {
/* 59 */     StringBuilder sb = new StringBuilder();
/* 60 */     sb.append('[');
/* 61 */     sb.append("phoneNumberPm: ").append(this.phoneNumberPm).append(", ");
/* 62 */     sb.append("failInfoPm: ").append(this.failInfoPm).append(", ");
/* 63 */     sb.append("datePm: ").append(this.datePm).append(", ");
/* 64 */     sb.append("timePm: ").append(this.timePm).append(", ");
/* 65 */     sb.append("etStatePm: ").append(this.etStatePm).append(", ");
/* 66 */     sb.append("lrc: ").append(this.lrc).append(", ");
/* 67 */     sb.append("endByte: ")
/* 68 */       .append(BytesUtility.toHexString(endByte))
/* 69 */       .append(']');
/* 70 */     return sb.toString();
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\nccCodec-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ncc\tc\fw2\cmdparam\ettu\GetEttuCellStatusRspPm.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */