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
/*    */ @CommandParams(cmdName="setEttuCellLightOffReq")
/*    */ public class SetEttuCellLightOffReqPm
/*    */   implements Externalizable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   public static final int cmdId = 10000;
/*    */   public static final String cmdName = "setEttuCellLightOffReq";
/*    */   @CommandParam(name="phoneNumber")
/*    */   public PhoneNumberPm phoneNumberPm;
/*    */   @CommandParam(name="measureNo")
/*    */   public int measureNo;
/*    */   @CommandParam(name="lrc")
/*    */   public int lrc;
/*    */   @CommandParam(name="endByte")
/* 27 */   public static final byte[] endByte = { -57 };
/*    */   
/*    */   public void writeExternal(ObjectOutput out) throws IOException
/*    */   {
/* 31 */     out.writeObject(this.phoneNumberPm);
/* 32 */     out.writeByte(this.measureNo);
/* 33 */     out.writeShort(this.lrc);
/*    */   }
/*    */   
/*    */   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException
/*    */   {
/* 38 */     this.phoneNumberPm = ((PhoneNumberPm)in.readObject());
/* 39 */     this.measureNo = (in.readByte() & 0xFF);
/* 40 */     this.lrc = (in.readShort() & 0xFFFF);
/*    */   }
/*    */   
/*    */   public String toString() {
/* 44 */     StringBuilder sb = new StringBuilder();
/* 45 */     sb.append('[');
/* 46 */     sb.append("phoneNumberPm: ").append(this.phoneNumberPm).append(", ");
/* 47 */     sb.append("measureNo: ").append(this.measureNo).append(", ");
/* 48 */     sb.append("lrc: ").append(this.lrc).append(", ");
/* 49 */     sb.append("endByte: ")
/* 50 */       .append(BytesUtility.toHexString(endByte))
/* 51 */       .append(']');
/* 52 */     return sb.toString();
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\nccCodec-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ncc\tc\fw2\cmdparam\ettu\SetEttuCellLightOffReqPm.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */