/*    */ package com.hwacom.ngtms.ncc.tc.fw2.cmdparam.rms;
/*    */ 
/*    */ import com.hwacom.ngtms.cmdprot.cmdfmtdef.CommandParam;
/*    */ import com.hwacom.ngtms.cmdprot.cmdfmtdef.CommandParams;
/*    */ import com.hwacom.ngtms.ncc.tc.fw2.cmdparam.DayTimePm;
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
/*    */ 
/*    */ @CommandParams(cmdName="rmsPeriodVdDataReport")
/*    */ public class RmsPeriodVdDataReportPm
/*    */   implements Externalizable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   public static final int cmdId = 44801;
/*    */   public static final String cmdName = "rmsPeriodVdDataReport";
/*    */   @CommandParam(name="hwStatus")
/*    */   public HwStatusPm hwStatusPm;
/*    */   @CommandParam(name="dayTime")
/*    */   public DayTimePm dayTimePm;
/*    */   @CommandParam(name="mainOcc")
/*    */   public int mainOcc;
/*    */   @CommandParam(name="mainVolume")
/*    */   public int mainVolume;
/*    */   @CommandParam(name="vdqOcc")
/*    */   public int vdqOcc;
/*    */   @CommandParam(name="vdqVolume")
/*    */   public int vdqVolume;
/*    */   @CommandParam(name="vdrOcc")
/*    */   public int vdrOcc;
/*    */   @CommandParam(name="vdrVolume")
/*    */   public int vdrVolume;
/*    */   
/*    */   public void writeExternal(ObjectOutput out)
/*    */     throws IOException
/*    */   {
/* 44 */     out.writeObject(this.hwStatusPm);
/* 45 */     out.writeObject(this.dayTimePm);
/* 46 */     out.writeByte(this.mainOcc);
/* 47 */     out.writeByte(this.mainVolume);
/* 48 */     out.writeByte(this.vdqOcc);
/* 49 */     out.writeByte(this.vdqVolume);
/* 50 */     out.writeByte(this.vdrOcc);
/* 51 */     out.writeByte(this.vdrVolume);
/*    */   }
/*    */   
/*    */   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException
/*    */   {
/* 56 */     this.hwStatusPm = ((HwStatusPm)in.readObject());
/* 57 */     this.dayTimePm = ((DayTimePm)in.readObject());
/* 58 */     this.mainOcc = (in.readByte() & 0xFF);
/* 59 */     this.mainVolume = (in.readByte() & 0xFF);
/* 60 */     this.vdqOcc = (in.readByte() & 0xFF);
/* 61 */     this.vdqVolume = (in.readByte() & 0xFF);
/* 62 */     this.vdrOcc = (in.readByte() & 0xFF);
/* 63 */     this.vdrVolume = (in.readByte() & 0xFF);
/*    */   }
/*    */   
/*    */   public String toString() {
/* 67 */     StringBuilder sb = new StringBuilder();
/* 68 */     sb.append('[');
/* 69 */     sb.append("hwStatusPm: ").append(this.hwStatusPm).append(", ");
/* 70 */     sb.append("dayTimePm: ").append(this.dayTimePm).append(", ");
/* 71 */     sb.append("mainOcc: ").append(this.mainOcc).append(", ");
/* 72 */     sb.append("mainVolume: ").append(this.mainVolume).append(", ");
/* 73 */     sb.append("vdqOcc: ").append(this.vdqOcc).append(", ");
/* 74 */     sb.append("vdqVolume: ").append(this.vdqVolume).append(", ");
/* 75 */     sb.append("vdrOcc: ").append(this.vdrOcc).append(", ");
/* 76 */     sb.append("vdrVolume: ").append(this.vdrVolume).append(']');
/* 77 */     return sb.toString();
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\nccCodec-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ncc\tc\fw2\cmdparam\rms\RmsPeriodVdDataReportPm.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */