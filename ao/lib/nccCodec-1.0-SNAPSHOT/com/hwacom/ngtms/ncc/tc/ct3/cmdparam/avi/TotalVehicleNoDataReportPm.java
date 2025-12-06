/*    */ package com.hwacom.ngtms.ncc.tc.ct3.cmdparam.avi;
/*    */ 
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
/*    */ @CommandParams(cmdName="totalVehicleNoDataReport")
/*    */ public class TotalVehicleNoDataReportPm
/*    */   implements Externalizable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   public static final int cmdId = 58371;
/*    */   public static final String cmdName = "totalVehicleNoDataReport";
/*    */   @CommandParam(name="year")
/*    */   public int year;
/*    */   @CommandParam(name="month")
/*    */   public int month;
/*    */   @CommandParam(name="day")
/*    */   public int day;
/*    */   @CommandParam(name="hour")
/*    */   public int hour;
/*    */   @CommandParam(name="min")
/*    */   public int min;
/*    */   @CommandParam(name="VehicleNo")
/*    */   public int VehicleNo;
/*    */   
/*    */   public void writeExternal(ObjectOutput out)
/*    */     throws IOException
/*    */   {
/* 38 */     out.writeByte(this.year);
/* 39 */     out.writeByte(this.month);
/* 40 */     out.writeByte(this.day);
/* 41 */     out.writeByte(this.hour);
/* 42 */     out.writeByte(this.min);
/* 43 */     out.writeShort(this.VehicleNo);
/*    */   }
/*    */   
/*    */   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException
/*    */   {
/* 48 */     this.year = (in.readByte() & 0xFF);
/* 49 */     this.month = (in.readByte() & 0xFF);
/* 50 */     this.day = (in.readByte() & 0xFF);
/* 51 */     this.hour = (in.readByte() & 0xFF);
/* 52 */     this.min = (in.readByte() & 0xFF);
/* 53 */     this.VehicleNo = (in.readShort() & 0xFFFF);
/*    */   }
/*    */   
/*    */   public String toString() {
/* 57 */     StringBuilder sb = new StringBuilder();
/* 58 */     sb.append('[');
/* 59 */     sb.append("year: ").append(this.year).append(", ");
/* 60 */     sb.append("month: ").append(this.month).append(", ");
/* 61 */     sb.append("day: ").append(this.day).append(", ");
/* 62 */     sb.append("hour: ").append(this.hour).append(", ");
/* 63 */     sb.append("min: ").append(this.min).append(", ");
/* 64 */     sb.append("VehicleNo: ").append(this.VehicleNo).append(']');
/* 65 */     return sb.toString();
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\nccCodec-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ncc\tc\ct3\cmdparam\avi\TotalVehicleNoDataReportPm.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */