/*    */ package com.hwacom.ngtms.ncc.tc.ct3.cmdparam;
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
/*    */ @CommandParams(cmdName="powerRestartReport")
/*    */ public class PowerRestartReportPm
/*    */   implements Externalizable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   public static final int cmdId = 3840;
/*    */   public static final String cmdName = "powerRestartReport";
/*    */   @CommandParam(name="month")
/*    */   public int month;
/*    */   @CommandParam(name="day")
/*    */   public int day;
/*    */   @CommandParam(name="hour")
/*    */   public int hour;
/*    */   @CommandParam(name="min")
/*    */   public int min;
/*    */   
/*    */   public void writeExternal(ObjectOutput out)
/*    */     throws IOException
/*    */   {
/* 31 */     out.writeByte(this.month);
/* 32 */     out.writeByte(this.day);
/* 33 */     out.writeByte(this.hour);
/* 34 */     out.writeByte(this.min);
/*    */   }
/*    */   
/*    */   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException
/*    */   {
/* 39 */     this.month = (in.readByte() & 0xFF);
/* 40 */     this.day = (in.readByte() & 0xFF);
/* 41 */     this.hour = (in.readByte() & 0xFF);
/* 42 */     this.min = (in.readByte() & 0xFF);
/*    */   }
/*    */   
/*    */   public String toString() {
/* 46 */     StringBuilder sb = new StringBuilder();
/* 47 */     sb.append('[');
/* 48 */     sb.append("month: ").append(this.month).append(", ");
/* 49 */     sb.append("day: ").append(this.day).append(", ");
/* 50 */     sb.append("hour: ").append(this.hour).append(", ");
/* 51 */     sb.append("min: ").append(this.min).append(']');
/* 52 */     return sb.toString();
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\nccCodec-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ncc\tc\ct3\cmdparam\PowerRestartReportPm.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */