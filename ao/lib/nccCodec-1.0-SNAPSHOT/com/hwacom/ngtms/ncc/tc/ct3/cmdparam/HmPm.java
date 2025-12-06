/*    */ package com.hwacom.ngtms.ncc.tc.ct3.cmdparam;
/*    */ 
/*    */ import com.hwacom.ngtms.cmdprot.cmdfmtdef.CommandParam;
/*    */ import com.hwacom.ngtms.cmdprot.cmdfmtdef.GlobalParams;
/*    */ import java.io.Externalizable;
/*    */ import java.io.IOException;
/*    */ import java.io.ObjectInput;
/*    */ import java.io.ObjectOutput;
/*    */ 
/*    */ @GlobalParams(paramsName="hm")
/*    */ public class HmPm
/*    */   implements Externalizable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   @CommandParam(name="hour")
/*    */   public int hour;
/*    */   @CommandParam(name="minute")
/*    */   public int minute;
/*    */   
/*    */   public void writeExternal(ObjectOutput out)
/*    */     throws IOException
/*    */   {
/* 23 */     out.writeByte(this.hour);
/* 24 */     out.writeByte(this.minute);
/*    */   }
/*    */   
/*    */   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException
/*    */   {
/* 29 */     this.hour = (in.readByte() & 0xFF);
/* 30 */     this.minute = (in.readByte() & 0xFF);
/*    */   }
/*    */   
/*    */   public String toString() {
/* 34 */     StringBuilder sb = new StringBuilder();
/* 35 */     sb.append('[');
/* 36 */     sb.append("hour: ").append(this.hour).append(", ");
/* 37 */     sb.append("minute: ").append(this.minute).append(']');
/* 38 */     return sb.toString();
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\nccCodec-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ncc\tc\ct3\cmdparam\HmPm.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */