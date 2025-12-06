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
/*    */ @CommandParams(cmdName="congestionStatusReport")
/*    */ public class CongestionStatusReportPm
/*    */   implements Externalizable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   public static final int cmdId = 44803;
/*    */   public static final String cmdName = "congestionStatusReport";
/*    */   @CommandParam(name="hwStatus")
/*    */   public HwStatusPm hwStatusPm;
/*    */   @CommandParam(name="dayTimeNoSec")
/*    */   public DayTimeNoSecPm dayTimeNoSecPm;
/*    */   @CommandParam(name="congestionStatus")
/*    */   public int congestionStatus;
/*    */   
/*    */   public void writeExternal(ObjectOutput out)
/*    */     throws IOException
/*    */   {
/* 29 */     out.writeObject(this.hwStatusPm);
/* 30 */     out.writeObject(this.dayTimeNoSecPm);
/* 31 */     out.writeByte(this.congestionStatus);
/*    */   }
/*    */   
/*    */   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException
/*    */   {
/* 36 */     this.hwStatusPm = ((HwStatusPm)in.readObject());
/* 37 */     this.dayTimeNoSecPm = ((DayTimeNoSecPm)in.readObject());
/* 38 */     this.congestionStatus = (in.readByte() & 0xFF);
/*    */   }
/*    */   
/*    */   public String toString() {
/* 42 */     StringBuilder sb = new StringBuilder();
/* 43 */     sb.append('[');
/* 44 */     sb.append("hwStatusPm: ").append(this.hwStatusPm).append(", ");
/* 45 */     sb.append("dayTimeNoSecPm: ").append(this.dayTimeNoSecPm).append(", ");
/* 46 */     sb.append("congestionStatus: ").append(this.congestionStatus).append(']');
/* 47 */     return sb.toString();
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\nccCodec-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ncc\tc\fw2\cmdparam\rms\CongestionStatusReportPm.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */