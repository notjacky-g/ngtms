/*    */ package com.hwacom.ngtms.ncc.tc.fw2.cmdparam.rd;
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
/*    */ @CommandParams(cmdName="getRdDataRsp")
/*    */ public class GetRdDataRspPm
/*    */   implements Externalizable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   public static final int cmdId = 77;
/*    */   public static final String cmdName = "getRdDataRsp";
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
/* 35 */     out.writeObject(this.hwStatusPm);
/* 36 */     out.writeObject(this.dhmPm);
/* 37 */     out.writeByte(this.currentPluviometric);
/* 38 */     out.writeShort(this.accPluviometric);
/* 39 */     out.writeByte(this.rdDegree);
/*    */   }
/*    */   
/*    */   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException
/*    */   {
/* 44 */     this.hwStatusPm = ((HwStatusPm)in.readObject());
/* 45 */     this.dhmPm = ((DhmPm)in.readObject());
/* 46 */     this.currentPluviometric = (in.readByte() & 0xFF);
/* 47 */     this.accPluviometric = (in.readShort() & 0xFFFF);
/* 48 */     this.rdDegree = (in.readByte() & 0xFF);
/*    */   }
/*    */   
/*    */   public String toString() {
/* 52 */     StringBuilder sb = new StringBuilder();
/* 53 */     sb.append('[');
/* 54 */     sb.append("hwStatusPm: ").append(this.hwStatusPm).append(", ");
/* 55 */     sb.append("dhmPm: ").append(this.dhmPm).append(", ");
/* 56 */     sb.append("currentPluviometric: ").append(this.currentPluviometric).append(", ");
/* 57 */     sb.append("accPluviometric: ").append(this.accPluviometric).append(", ");
/* 58 */     sb.append("rdDegree: ").append(this.rdDegree).append(']');
/* 59 */     return sb.toString();
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\nccCodec-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ncc\tc\fw2\cmdparam\rd\GetRdDataRspPm.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */