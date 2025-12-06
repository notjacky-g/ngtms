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
/*    */ @GlobalParams(paramsName="time")
/*    */ public class TimePm
/*    */   implements Externalizable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
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
/* 26 */     out.writeByte(this.hour);
/* 27 */     out.writeByte(this.minute);
/* 28 */     out.writeByte(this.second);
/*    */   }
/*    */   
/*    */   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException
/*    */   {
/* 33 */     this.hour = (in.readByte() & 0xFF);
/* 34 */     this.minute = (in.readByte() & 0xFF);
/* 35 */     this.second = (in.readByte() & 0xFF);
/*    */   }
/*    */   
/*    */   public String toString() {
/* 39 */     StringBuilder sb = new StringBuilder();
/* 40 */     sb.append('[');
/* 41 */     sb.append("hour: ").append(this.hour).append(", ");
/* 42 */     sb.append("minute: ").append(this.minute).append(", ");
/* 43 */     sb.append("second: ").append(this.second).append(']');
/* 44 */     return sb.toString();
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\nccCodec-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ncc\tc\fw2\cmdparam\TimePm.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */