/*    */ package com.hwacom.ngtms.ncc.tc.fw2.cmdparam;
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
/*    */ @GlobalParams(paramsName="dayTime")
/*    */ public class DayTimePm
/*    */   implements Externalizable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   @CommandParam(name="year")
/*    */   public int year;
/*    */   @CommandParam(name="month")
/*    */   public int month;
/*    */   @CommandParam(name="day")
/*    */   public int day;
/*    */   @CommandParam(name="hour")
/*    */   public int hour;
/*    */   @CommandParam(name="minute")
/*    */   public int minute;
/*    */   @CommandParam(name="second")
/*    */   public int second;
/*    */   
/*    */   public void writeExternal(ObjectOutput out)
/*    */     throws IOException
/*    */   {
/* 35 */     out.writeShort(this.year);
/* 36 */     out.writeByte(this.month);
/* 37 */     out.writeByte(this.day);
/* 38 */     out.writeByte(this.hour);
/* 39 */     out.writeByte(this.minute);
/* 40 */     out.writeByte(this.second);
/*    */   }
/*    */   
/*    */   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException
/*    */   {
/* 45 */     this.year = (in.readShort() & 0xFFFF);
/* 46 */     this.month = (in.readByte() & 0xFF);
/* 47 */     this.day = (in.readByte() & 0xFF);
/* 48 */     this.hour = (in.readByte() & 0xFF);
/* 49 */     this.minute = (in.readByte() & 0xFF);
/* 50 */     this.second = (in.readByte() & 0xFF);
/*    */   }
/*    */   
/*    */   public String toString() {
/* 54 */     StringBuilder sb = new StringBuilder();
/* 55 */     sb.append('[');
/* 56 */     sb.append("year: ").append(this.year).append(", ");
/* 57 */     sb.append("month: ").append(this.month).append(", ");
/* 58 */     sb.append("day: ").append(this.day).append(", ");
/* 59 */     sb.append("hour: ").append(this.hour).append(", ");
/* 60 */     sb.append("minute: ").append(this.minute).append(", ");
/* 61 */     sb.append("second: ").append(this.second).append(']');
/* 62 */     return sb.toString();
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\nccCodec-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ncc\tc\fw2\cmdparam\DayTimePm.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */