/*    */ package com.hwacom.ngtms.ncc.tc.fw2.cmdparam.vi;
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
/*    */ @CommandParams(cmdName="periodViDataReport")
/*    */ public class PeriodViDataReportPm
/*    */   implements Externalizable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   public static final int cmdId = 32;
/*    */   public static final String cmdName = "periodViDataReport";
/*    */   @CommandParam(name="responseType")
/*    */   public int responseType;
/*    */   @CommandParam(name="hwStatus")
/*    */   public HwStatusPm hwStatusPm;
/*    */   @CommandParam(name="dhm")
/*    */   public DhmPm dhmPm;
/*    */   @CommandParam(name="viDistance")
/*    */   public int viDistance;
/*    */   @CommandParam(name="viDegree")
/*    */   public int viDegree;
/*    */   
/*    */   public void writeExternal(ObjectOutput out)
/*    */     throws IOException
/*    */   {
/* 35 */     out.writeByte(this.responseType);
/* 36 */     out.writeObject(this.hwStatusPm);
/* 37 */     out.writeObject(this.dhmPm);
/* 38 */     out.writeShort(this.viDistance);
/* 39 */     out.writeByte(this.viDegree);
/*    */   }
/*    */   
/*    */   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException
/*    */   {
/* 44 */     this.responseType = (in.readByte() & 0xFF);
/* 45 */     this.hwStatusPm = ((HwStatusPm)in.readObject());
/* 46 */     this.dhmPm = ((DhmPm)in.readObject());
/* 47 */     this.viDistance = (in.readShort() & 0xFFFF);
/* 48 */     this.viDegree = (in.readByte() & 0xFF);
/*    */   }
/*    */   
/*    */   public String toString() {
/* 52 */     StringBuilder sb = new StringBuilder();
/* 53 */     sb.append('[');
/* 54 */     sb.append("responseType: ").append(this.responseType).append(", ");
/* 55 */     sb.append("hwStatusPm: ").append(this.hwStatusPm).append(", ");
/* 56 */     sb.append("dhmPm: ").append(this.dhmPm).append(", ");
/* 57 */     sb.append("viDistance: ").append(this.viDistance).append(", ");
/* 58 */     sb.append("viDegree: ").append(this.viDegree).append(']');
/* 59 */     return sb.toString();
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\nccCodec-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ncc\tc\fw2\cmdparam\vi\PeriodViDataReportPm.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */