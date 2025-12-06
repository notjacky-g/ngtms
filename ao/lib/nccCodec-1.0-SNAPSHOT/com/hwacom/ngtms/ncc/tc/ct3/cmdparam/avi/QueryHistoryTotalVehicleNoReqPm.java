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
/*    */ @CommandParams(cmdName="queryHistoryTotalVehicleNoReq")
/*    */ public class QueryHistoryTotalVehicleNoReqPm
/*    */   implements Externalizable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   public static final int cmdId = 58436;
/*    */   public static final String cmdName = "queryHistoryTotalVehicleNoReq";
/*    */   @CommandParam(name="year")
/*    */   public int year;
/*    */   @CommandParam(name="month")
/*    */   public int month;
/*    */   @CommandParam(name="day")
/*    */   public int day;
/*    */   @CommandParam(name="hour")
/*    */   public int hour;
/*    */   
/*    */   public void writeExternal(ObjectOutput out)
/*    */     throws IOException
/*    */   {
/* 32 */     out.writeByte(this.year);
/* 33 */     out.writeByte(this.month);
/* 34 */     out.writeByte(this.day);
/* 35 */     out.writeByte(this.hour);
/*    */   }
/*    */   
/*    */   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException
/*    */   {
/* 40 */     this.year = (in.readByte() & 0xFF);
/* 41 */     this.month = (in.readByte() & 0xFF);
/* 42 */     this.day = (in.readByte() & 0xFF);
/* 43 */     this.hour = (in.readByte() & 0xFF);
/*    */   }
/*    */   
/*    */   public String toString() {
/* 47 */     StringBuilder sb = new StringBuilder();
/* 48 */     sb.append('[');
/* 49 */     sb.append("year: ").append(this.year).append(", ");
/* 50 */     sb.append("month: ").append(this.month).append(", ");
/* 51 */     sb.append("day: ").append(this.day).append(", ");
/* 52 */     sb.append("hour: ").append(this.hour).append(']');
/* 53 */     return sb.toString();
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\nccCodec-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ncc\tc\ct3\cmdparam\avi\QueryHistoryTotalVehicleNoReqPm.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */