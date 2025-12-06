/*    */ package com.hwacom.ngtms.ncc.tc.fw2.cmdparam.rd.r21;
/*    */ 
/*    */ import com.hwacom.ngtms.cmdprot.cmdfmtdef.CommandParam;
/*    */ import com.hwacom.ngtms.cmdprot.cmdfmtdef.CommandParams;
/*    */ import com.hwacom.ngtms.ncc.tc.fw2.cmdparam.DhmPm;
/*    */ import com.hwacom.ngtms.ncc.tc.fw2.cmdparam.HwStatusPm;
/*    */ import java.io.Externalizable;
/*    */ import java.io.IOException;
/*    */ import java.io.ObjectInput;
/*    */ import java.io.ObjectOutput;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @CommandParams(cmdName="getR21RdDataRsp")
/*    */ public class GetR21RdDataRspPm
/*    */   implements Externalizable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   public static final int cmdId = 77;
/*    */   public static final String cmdName = "getR21RdDataRsp";
/*    */   @CommandParam(name="hwStatus")
/*    */   public HwStatusPm hwStatusPm;
/*    */   @CommandParam(name="dhm")
/*    */   public DhmPm dhmPm;
/*    */   @CommandParam(name="currentPluviometric")
/*    */   public int currentPluviometric;
/*    */   @CommandParam(name="accPluviometric")
/*    */   public int accPluviometric;
/*    */   @CommandParam(name="rdDegree")
/*    */   public int rdDegree;
/*    */   
/*    */   public void writeExternal(ObjectOutput out)
/*    */     throws IOException
/*    */   {
/* 36 */     out.writeObject(this.hwStatusPm);
/* 37 */     out.writeObject(this.dhmPm);
/* 38 */     out.writeShort(this.currentPluviometric);
/* 39 */     out.writeShort(this.accPluviometric);
/* 40 */     out.writeByte(this.rdDegree);
/*    */   }
/*    */   
/*    */   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException
/*    */   {
/* 45 */     this.hwStatusPm = ((HwStatusPm)in.readObject());
/* 46 */     this.dhmPm = ((DhmPm)in.readObject());
/* 47 */     this.currentPluviometric = (in.readShort() & 0xFFFF);
/* 48 */     this.accPluviometric = (in.readShort() & 0xFFFF);
/* 49 */     this.rdDegree = (in.readByte() & 0xFF);
/*    */   }
/*    */   
/*    */   public String toString() {
/* 53 */     StringBuilder sb = new StringBuilder();
/* 54 */     sb.append('[');
/* 55 */     sb.append("hwStatusPm: ").append(this.hwStatusPm).append(", ");
/* 56 */     sb.append("dhmPm: ").append(this.dhmPm).append(", ");
/* 57 */     sb.append("currentPluviometric: ").append(this.currentPluviometric).append(", ");
/* 58 */     sb.append("accPluviometric: ").append(this.accPluviometric).append(", ");
/* 59 */     sb.append("rdDegree: ").append(this.rdDegree).append(']');
/* 60 */     return sb.toString();
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\nccCodec-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ncc\tc\fw2\cmdparam\rd\r21\GetR21RdDataRspPm.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */