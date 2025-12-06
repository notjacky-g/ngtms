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
/*    */ 
/*    */ @CommandParams(cmdName="r21PeriodRdDataReport")
/*    */ public class R21PeriodRdDataReportPm
/*    */   implements Externalizable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   public static final int cmdId = 72;
/*    */   public static final String cmdName = "r21PeriodRdDataReport";
/*    */   @CommandParam(name="responseType")
/*    */   public int responseType;
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
/* 39 */     out.writeByte(this.responseType);
/* 40 */     out.writeObject(this.hwStatusPm);
/* 41 */     out.writeObject(this.dhmPm);
/* 42 */     out.writeShort(this.currentPluviometric);
/* 43 */     out.writeShort(this.accPluviometric);
/* 44 */     out.writeByte(this.rdDegree);
/*    */   }
/*    */   
/*    */   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException
/*    */   {
/* 49 */     this.responseType = (in.readByte() & 0xFF);
/* 50 */     this.hwStatusPm = ((HwStatusPm)in.readObject());
/* 51 */     this.dhmPm = ((DhmPm)in.readObject());
/* 52 */     this.currentPluviometric = (in.readShort() & 0xFFFF);
/* 53 */     this.accPluviometric = (in.readShort() & 0xFFFF);
/* 54 */     this.rdDegree = (in.readByte() & 0xFF);
/*    */   }
/*    */   
/*    */   public String toString() {
/* 58 */     StringBuilder sb = new StringBuilder();
/* 59 */     sb.append('[');
/* 60 */     sb.append("responseType: ").append(this.responseType).append(", ");
/* 61 */     sb.append("hwStatusPm: ").append(this.hwStatusPm).append(", ");
/* 62 */     sb.append("dhmPm: ").append(this.dhmPm).append(", ");
/* 63 */     sb.append("currentPluviometric: ").append(this.currentPluviometric).append(", ");
/* 64 */     sb.append("accPluviometric: ").append(this.accPluviometric).append(", ");
/* 65 */     sb.append("rdDegree: ").append(this.rdDegree).append(']');
/* 66 */     return sb.toString();
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\nccCodec-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ncc\tc\fw2\cmdparam\rd\r21\R21PeriodRdDataReportPm.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */