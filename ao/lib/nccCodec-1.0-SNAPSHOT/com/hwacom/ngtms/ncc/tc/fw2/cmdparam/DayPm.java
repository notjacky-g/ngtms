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
/*    */ @GlobalParams(paramsName="day")
/*    */ public class DayPm
/*    */   implements Externalizable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   @CommandParam(name="year")
/*    */   public int year;
/*    */   @CommandParam(name="month")
/*    */   public int month;
/*    */   @CommandParam(name="day")
/*    */   public int day;
/*    */   
/*    */   public void writeExternal(ObjectOutput out)
/*    */     throws IOException
/*    */   {
/* 26 */     out.writeShort(this.year);
/* 27 */     out.writeByte(this.month);
/* 28 */     out.writeByte(this.day);
/*    */   }
/*    */   
/*    */   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException
/*    */   {
/* 33 */     this.year = (in.readShort() & 0xFFFF);
/* 34 */     this.month = (in.readByte() & 0xFF);
/* 35 */     this.day = (in.readByte() & 0xFF);
/*    */   }
/*    */   
/*    */   public String toString() {
/* 39 */     StringBuilder sb = new StringBuilder();
/* 40 */     sb.append('[');
/* 41 */     sb.append("year: ").append(this.year).append(", ");
/* 42 */     sb.append("month: ").append(this.month).append(", ");
/* 43 */     sb.append("day: ").append(this.day).append(']');
/* 44 */     return sb.toString();
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\nccCodec-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ncc\tc\fw2\cmdparam\DayPm.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */