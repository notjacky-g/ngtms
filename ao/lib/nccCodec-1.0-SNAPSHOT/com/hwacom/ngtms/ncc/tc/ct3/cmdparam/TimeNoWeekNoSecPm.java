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
/*    */ @GlobalParams(paramsName="timeNoWeekNoSec")
/*    */ public class TimeNoWeekNoSecPm
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
/*    */   @CommandParam(name="min")
/*    */   public int min;
/*    */   
/*    */   public void writeExternal(ObjectOutput out)
/*    */     throws IOException
/*    */   {
/* 32 */     out.writeByte(this.year);
/* 33 */     out.writeByte(this.month);
/* 34 */     out.writeByte(this.day);
/* 35 */     out.writeByte(this.hour);
/* 36 */     out.writeByte(this.min);
/*    */   }
/*    */   
/*    */   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException
/*    */   {
/* 41 */     this.year = (in.readByte() & 0xFF);
/* 42 */     this.month = (in.readByte() & 0xFF);
/* 43 */     this.day = (in.readByte() & 0xFF);
/* 44 */     this.hour = (in.readByte() & 0xFF);
/* 45 */     this.min = (in.readByte() & 0xFF);
/*    */   }
/*    */   
/*    */   public String toString() {
/* 49 */     StringBuilder sb = new StringBuilder();
/* 50 */     sb.append('[');
/* 51 */     sb.append("year: ").append(this.year).append(", ");
/* 52 */     sb.append("month: ").append(this.month).append(", ");
/* 53 */     sb.append("day: ").append(this.day).append(", ");
/* 54 */     sb.append("hour: ").append(this.hour).append(", ");
/* 55 */     sb.append("min: ").append(this.min).append(']');
/* 56 */     return sb.toString();
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\nccCodec-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ncc\tc\ct3\cmdparam\TimeNoWeekNoSecPm.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */