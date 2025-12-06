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
/*    */ @CommandParams(cmdName="queryHistoryTotalVehicleNoRsp")
/*    */ public class QueryHistoryTotalVehicleNoRspPm
/*    */   implements Externalizable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   public static final int cmdId = 58564;
/*    */   public static final String cmdName = "queryHistoryTotalVehicleNoRsp";
/*    */   @CommandParam(name="year")
/*    */   public int year;
/*    */   @CommandParam(name="month")
/*    */   public int month;
/*    */   @CommandParam(name="day")
/*    */   public int day;
/*    */   @CommandParam(name="hour")
/*    */   public int hour;
/*    */   @CommandParam(name="VehicleNo")
/*    */   public int VehicleNo;
/*    */   
/*    */   public void writeExternal(ObjectOutput out)
/*    */     throws IOException
/*    */   {
/* 35 */     out.writeByte(this.year);
/* 36 */     out.writeByte(this.month);
/* 37 */     out.writeByte(this.day);
/* 38 */     out.writeByte(this.hour);
/* 39 */     out.writeShort(this.VehicleNo);
/*    */   }
/*    */   
/*    */   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException
/*    */   {
/* 44 */     this.year = (in.readByte() & 0xFF);
/* 45 */     this.month = (in.readByte() & 0xFF);
/* 46 */     this.day = (in.readByte() & 0xFF);
/* 47 */     this.hour = (in.readByte() & 0xFF);
/* 48 */     this.VehicleNo = (in.readShort() & 0xFFFF);
/*    */   }
/*    */   
/*    */   public String toString() {
/* 52 */     StringBuilder sb = new StringBuilder();
/* 53 */     sb.append('[');
/* 54 */     sb.append("year: ").append(this.year).append(", ");
/* 55 */     sb.append("month: ").append(this.month).append(", ");
/* 56 */     sb.append("day: ").append(this.day).append(", ");
/* 57 */     sb.append("hour: ").append(this.hour).append(", ");
/* 58 */     sb.append("VehicleNo: ").append(this.VehicleNo).append(']');
/* 59 */     return sb.toString();
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\nccCodec-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ncc\tc\ct3\cmdparam\avi\QueryHistoryTotalVehicleNoRspPm.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */