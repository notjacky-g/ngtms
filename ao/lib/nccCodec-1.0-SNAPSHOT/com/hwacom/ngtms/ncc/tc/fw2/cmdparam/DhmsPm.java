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
/*    */ @GlobalParams(paramsName="dhms")
/*    */ public class DhmsPm
/*    */   implements Externalizable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
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
/* 29 */     out.writeByte(this.day);
/* 30 */     out.writeByte(this.hour);
/* 31 */     out.writeByte(this.minute);
/* 32 */     out.writeByte(this.second);
/*    */   }
/*    */   
/*    */   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException
/*    */   {
/* 37 */     this.day = (in.readByte() & 0xFF);
/* 38 */     this.hour = (in.readByte() & 0xFF);
/* 39 */     this.minute = (in.readByte() & 0xFF);
/* 40 */     this.second = (in.readByte() & 0xFF);
/*    */   }
/*    */   
/*    */   public String toString() {
/* 44 */     StringBuilder sb = new StringBuilder();
/* 45 */     sb.append('[');
/* 46 */     sb.append("day: ").append(this.day).append(", ");
/* 47 */     sb.append("hour: ").append(this.hour).append(", ");
/* 48 */     sb.append("minute: ").append(this.minute).append(", ");
/* 49 */     sb.append("second: ").append(this.second).append(']');
/* 50 */     return sb.toString();
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\nccCodec-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ncc\tc\fw2\cmdparam\DhmsPm.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */