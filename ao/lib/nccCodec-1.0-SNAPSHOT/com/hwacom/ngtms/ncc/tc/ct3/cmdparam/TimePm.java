/*    */ package com.hwacom.ngtms.ncc.tc.ct3.cmdparam;
/*    */ 
/*    */ import com.hwacom.ngtms.cmdprot.cmdfmtdef.CommandParam;
/*    */ import com.hwacom.ngtms.cmdprot.cmdfmtdef.GlobalParams;
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
/*    */ @GlobalParams(paramsName="time")
/*    */ public class TimePm
/*    */   implements Externalizable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   @CommandParam(name="year")
/*    */   public int year;
/*    */   @CommandParam(name="month")
/*    */   public int month;
/*    */   @CommandParam(name="day")
/*    */   public int day;
/*    */   @CommandParam(name="week")
/*    */   public int week;
/*    */   @CommandParam(name="hour")
/*    */   public int hour;
/*    */   @CommandParam(name="min")
/*    */   public int min;
/*    */   @CommandParam(name="sec")
/*    */   public int sec;
/*    */   
/*    */   public void writeExternal(ObjectOutput out)
/*    */     throws IOException
/*    */   {
/* 38 */     out.writeByte(this.year);
/* 39 */     out.writeByte(this.month);
/* 40 */     out.writeByte(this.day);
/* 41 */     out.writeByte(this.week);
/* 42 */     out.writeByte(this.hour);
/* 43 */     out.writeByte(this.min);
/* 44 */     out.writeByte(this.sec);
/*    */   }
/*    */   
/*    */   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException
/*    */   {
/* 49 */     this.year = (in.readByte() & 0xFF);
/* 50 */     this.month = (in.readByte() & 0xFF);
/* 51 */     this.day = (in.readByte() & 0xFF);
/* 52 */     this.week = (in.readByte() & 0xFF);
/* 53 */     this.hour = (in.readByte() & 0xFF);
/* 54 */     this.min = (in.readByte() & 0xFF);
/* 55 */     this.sec = (in.readByte() & 0xFF);
/*    */   }
/*    */   
/*    */   public String toString() {
/* 59 */     StringBuilder sb = new StringBuilder();
/* 60 */     sb.append('[');
/* 61 */     sb.append("year: ").append(this.year).append(", ");
/* 62 */     sb.append("month: ").append(this.month).append(", ");
/* 63 */     sb.append("day: ").append(this.day).append(", ");
/* 64 */     sb.append("week: ").append(this.week).append(", ");
/* 65 */     sb.append("hour: ").append(this.hour).append(", ");
/* 66 */     sb.append("min: ").append(this.min).append(", ");
/* 67 */     sb.append("sec: ").append(this.sec).append(']');
/* 68 */     return sb.toString();
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\nccCodec-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ncc\tc\ct3\cmdparam\TimePm.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */