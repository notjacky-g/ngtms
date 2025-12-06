/*    */ package com.hwacom.ngtms.ncc.tc.fw2.cmdparam.rms;
/*    */ 
/*    */ import com.hwacom.ngtms.cmdprot.cmdfmtdef.CommandParam;
/*    */ import com.hwacom.ngtms.cmdprot.cmdfmtdef.CommandParams;
/*    */ import com.hwacom.ngtms.ncc.tc.fw2.cmdparam.DayTimeNoSecPm;
/*    */ import com.hwacom.ngtms.ncc.tc.fw2.cmdparam.HwStatusPm;
/*    */ import java.io.Externalizable;
/*    */ import java.io.IOException;
/*    */ import java.io.ObjectInput;
/*    */ import java.io.ObjectOutput;
/*    */ 
/*    */ 
/*    */ 
/*    */ @CommandParams(cmdName="rmsPlanChangeReport")
/*    */ public class RmsPlanChangeReportPm
/*    */   implements Externalizable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   public static final int cmdId = 44802;
/*    */   public static final String cmdName = "rmsPlanChangeReport";
/*    */   @CommandParam(name="hwStatus")
/*    */   public HwStatusPm hwStatusPm;
/*    */   @CommandParam(name="dayTimeNoSec")
/*    */   public DayTimeNoSecPm dayTimeNoSecPm;
/*    */   @CommandParam(name="vdrVolume")
/*    */   public long vdrVolume;
/*    */   @CommandParam(name="nextPlanNo")
/*    */   public int nextPlanNo;
/*    */   @CommandParam(name="nextControlMode")
/*    */   public int nextControlMode;
/*    */   
/*    */   public void writeExternal(ObjectOutput out)
/*    */     throws IOException
/*    */   {
/* 35 */     out.writeObject(this.hwStatusPm);
/* 36 */     out.writeObject(this.dayTimeNoSecPm);
/* 37 */     out.writeInt((int)this.vdrVolume);
/* 38 */     out.writeByte(this.nextPlanNo);
/* 39 */     out.writeByte(this.nextControlMode);
/*    */   }
/*    */   
/*    */   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException
/*    */   {
/* 44 */     this.hwStatusPm = ((HwStatusPm)in.readObject());
/* 45 */     this.dayTimeNoSecPm = ((DayTimeNoSecPm)in.readObject());
/* 46 */     this.vdrVolume = (in.readInt() & 0xFFFFFFFF);
/* 47 */     this.nextPlanNo = (in.readByte() & 0xFF);
/* 48 */     this.nextControlMode = (in.readByte() & 0xFF);
/*    */   }
/*    */   
/*    */   public String toString() {
/* 52 */     StringBuilder sb = new StringBuilder();
/* 53 */     sb.append('[');
/* 54 */     sb.append("hwStatusPm: ").append(this.hwStatusPm).append(", ");
/* 55 */     sb.append("dayTimeNoSecPm: ").append(this.dayTimeNoSecPm).append(", ");
/* 56 */     sb.append("vdrVolume: ").append(this.vdrVolume).append(", ");
/* 57 */     sb.append("nextPlanNo: ").append(this.nextPlanNo).append(", ");
/* 58 */     sb.append("nextControlMode: ").append(this.nextControlMode).append(']');
/* 59 */     return sb.toString();
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\nccCodec-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ncc\tc\fw2\cmdparam\rms\RmsPlanChangeReportPm.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */