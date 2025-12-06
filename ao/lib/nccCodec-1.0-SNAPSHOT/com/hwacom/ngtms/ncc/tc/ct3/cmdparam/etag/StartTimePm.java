/*    */ package com.hwacom.ngtms.ncc.tc.ct3.cmdparam.etag;
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
/*    */ @GlobalParams(paramsName="startTime")
/*    */ public class StartTimePm
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
/*    */   @CommandParam(name="sec")
/*    */   public int sec;
/*    */   
/*    */   public void writeExternal(ObjectOutput out)
/*    */     throws IOException
/*    */   {
/* 36 */     out.writeByte(this.year);
/* 37 */     out.writeByte(this.month);
/* 38 */     out.writeByte(this.day);
/* 39 */     out.writeByte(this.hour);
/* 40 */     out.writeByte(this.min);
/* 41 */     out.writeByte(this.sec);
/*    */   }
/*    */   
/*    */   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException
/*    */   {
/* 46 */     this.year = (in.readByte() & 0xFF);
/* 47 */     this.month = (in.readByte() & 0xFF);
/* 48 */     this.day = (in.readByte() & 0xFF);
/* 49 */     this.hour = (in.readByte() & 0xFF);
/* 50 */     this.min = (in.readByte() & 0xFF);
/* 51 */     this.sec = (in.readByte() & 0xFF);
/*    */   }
/*    */   
/*    */   public String toString() {
/* 55 */     StringBuilder sb = new StringBuilder();
/* 56 */     sb.append('[');
/* 57 */     sb.append("year: ").append(this.year).append(", ");
/* 58 */     sb.append("month: ").append(this.month).append(", ");
/* 59 */     sb.append("day: ").append(this.day).append(", ");
/* 60 */     sb.append("hour: ").append(this.hour).append(", ");
/* 61 */     sb.append("min: ").append(this.min).append(", ");
/* 62 */     sb.append("sec: ").append(this.sec).append(']');
/* 63 */     return sb.toString();
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\nccCodec-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ncc\tc\ct3\cmdparam\etag\StartTimePm.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */