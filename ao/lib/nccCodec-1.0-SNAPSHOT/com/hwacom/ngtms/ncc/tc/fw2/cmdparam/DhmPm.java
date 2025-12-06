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
/*    */ @GlobalParams(paramsName="dhm")
/*    */ public class DhmPm
/*    */   implements Externalizable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
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
/* 26 */     out.writeByte(this.day);
/* 27 */     out.writeByte(this.hour);
/* 28 */     out.writeByte(this.minute);
/*    */   }
/*    */   
/*    */   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException
/*    */   {
/* 33 */     this.day = (in.readByte() & 0xFF);
/* 34 */     this.hour = (in.readByte() & 0xFF);
/* 35 */     this.minute = (in.readByte() & 0xFF);
/*    */   }
/*    */   
/*    */   public String toString() {
/* 39 */     StringBuilder sb = new StringBuilder();
/* 40 */     sb.append('[');
/* 41 */     sb.append("day: ").append(this.day).append(", ");
/* 42 */     sb.append("hour: ").append(this.hour).append(", ");
/* 43 */     sb.append("minute: ").append(this.minute).append(']');
/* 44 */     return sb.toString();
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\nccCodec-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ncc\tc\fw2\cmdparam\DhmPm.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */