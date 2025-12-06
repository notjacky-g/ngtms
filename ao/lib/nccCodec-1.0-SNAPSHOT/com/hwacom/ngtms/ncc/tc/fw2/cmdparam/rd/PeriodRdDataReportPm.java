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
/*    */ 
/*    */ @CommandParams(cmdName="periodRdDataReport")
/*    */ public class PeriodRdDataReportPm
/*    */   implements Externalizable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   public static final int cmdId = 72;
/*    */   public static final String cmdName = "periodRdDataReport";
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
/* 38 */     out.writeByte(this.responseType);
/* 39 */     out.writeObject(this.hwStatusPm);
/* 40 */     out.writeObject(this.dhmPm);
/* 41 */     out.writeByte(this.currentPluviometric);
/* 42 */     out.writeShort(this.accPluviometric);
/* 43 */     out.writeByte(this.rdDegree);
/*    */   }
/*    */   
/*    */   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException
/*    */   {
/* 48 */     this.responseType = (in.readByte() & 0xFF);
/* 49 */     this.hwStatusPm = ((HwStatusPm)in.readObject());
/* 50 */     this.dhmPm = ((DhmPm)in.readObject());
/* 51 */     this.currentPluviometric = (in.readByte() & 0xFF);
/* 52 */     this.accPluviometric = (in.readShort() & 0xFFFF);
/* 53 */     this.rdDegree = (in.readByte() & 0xFF);
/*    */   }
/*    */   
/*    */   public String toString() {
/* 57 */     StringBuilder sb = new StringBuilder();
/* 58 */     sb.append('[');
/* 59 */     sb.append("responseType: ").append(this.responseType).append(", ");
/* 60 */     sb.append("hwStatusPm: ").append(this.hwStatusPm).append(", ");
/* 61 */     sb.append("dhmPm: ").append(this.dhmPm).append(", ");
/* 62 */     sb.append("currentPluviometric: ").append(this.currentPluviometric).append(", ");
/* 63 */     sb.append("accPluviometric: ").append(this.accPluviometric).append(", ");
/* 64 */     sb.append("rdDegree: ").append(this.rdDegree).append(']');
/* 65 */     return sb.toString();
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\nccCodec-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ncc\tc\fw2\cmdparam\rd\PeriodRdDataReportPm.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */