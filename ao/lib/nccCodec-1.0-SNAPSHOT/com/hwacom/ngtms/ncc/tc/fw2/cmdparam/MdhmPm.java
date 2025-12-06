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
/*    */ @GlobalParams(paramsName="mdhm")
/*    */ public class MdhmPm
/*    */   implements Externalizable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   @CommandParam(name="month")
/*    */   public int month;
/*    */   @CommandParam(name="day")
/*    */   public int day;
/*    */   @CommandParam(name="hour")
/*    */   public int hour;
/*    */   @CommandParam(name="minute")
/*    */   public int minute;
/*    */   
/*    */   public void writeExternal(ObjectOutput out)
/*    */     throws IOException
/*    */   {
/* 29 */     out.writeByte(this.month);
/* 30 */     out.writeByte(this.day);
/* 31 */     out.writeByte(this.hour);
/* 32 */     out.writeByte(this.minute);
/*    */   }
/*    */   
/*    */   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException
/*    */   {
/* 37 */     this.month = (in.readByte() & 0xFF);
/* 38 */     this.day = (in.readByte() & 0xFF);
/* 39 */     this.hour = (in.readByte() & 0xFF);
/* 40 */     this.minute = (in.readByte() & 0xFF);
/*    */   }
/*    */   
/*    */   public String toString() {
/* 44 */     StringBuilder sb = new StringBuilder();
/* 45 */     sb.append('[');
/* 46 */     sb.append("month: ").append(this.month).append(", ");
/* 47 */     sb.append("day: ").append(this.day).append(", ");
/* 48 */     sb.append("hour: ").append(this.hour).append(", ");
/* 49 */     sb.append("minute: ").append(this.minute).append(']');
/* 50 */     return sb.toString();
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\nccCodec-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ncc\tc\fw2\cmdparam\MdhmPm.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */