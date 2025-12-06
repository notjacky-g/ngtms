/*    */ package com.hwacom.ngtms.ncc.tc.ct3.cmdparam.sig;
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
/*    */ @GlobalParams(paramsName="segment")
/*    */ public class SegmentPm
/*    */   implements Externalizable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   @CommandParam(name="hour")
/*    */   public int hour;
/*    */   @CommandParam(name="min")
/*    */   public int min;
/*    */   @CommandParam(name="planId")
/*    */   public int planId;
/*    */   
/*    */   public void writeExternal(ObjectOutput out)
/*    */     throws IOException
/*    */   {
/* 27 */     out.writeByte(this.hour);
/* 28 */     out.writeByte(this.min);
/* 29 */     out.writeByte(this.planId);
/*    */   }
/*    */   
/*    */   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException
/*    */   {
/* 34 */     this.hour = (in.readByte() & 0xFF);
/* 35 */     this.min = (in.readByte() & 0xFF);
/* 36 */     this.planId = (in.readByte() & 0xFF);
/*    */   }
/*    */   
/*    */   public String toString() {
/* 40 */     StringBuilder sb = new StringBuilder();
/* 41 */     sb.append('[');
/* 42 */     sb.append("hour: ").append(this.hour).append(", ");
/* 43 */     sb.append("min: ").append(this.min).append(", ");
/* 44 */     sb.append("planId: ").append(this.planId).append(']');
/* 45 */     return sb.toString();
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\nccCodec-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ncc\tc\ct3\cmdparam\sig\SegmentPm.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */