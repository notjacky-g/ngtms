/*    */ package com.hwacom.ngtms.ncc.tc.ct3.cmdparam.oh;
/*    */ 
/*    */ import com.hwacom.ngtms.cmdprot.cmdfmtdef.CommandParam;
/*    */ import com.hwacom.ngtms.cmdprot.cmdfmtdef.CommandParams;
/*    */ import java.io.Externalizable;
/*    */ import java.io.IOException;
/*    */ import java.io.ObjectInput;
/*    */ import java.io.ObjectOutput;
/*    */ 
/*    */ 
/*    */ 
/*    */ @CommandParams(cmdName="ohEventReport")
/*    */ public class OhEventReportPm
/*    */   implements Externalizable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   public static final int cmdId = 28497;
/*    */   public static final String cmdName = "ohEventReport";
/*    */   @CommandParam(name="hour")
/*    */   public int hour;
/*    */   @CommandParam(name="min")
/*    */   public int min;
/*    */   @CommandParam(name="second")
/*    */   public int second;
/*    */   
/*    */   public void writeExternal(ObjectOutput out)
/*    */     throws IOException
/*    */   {
/* 29 */     out.writeByte(this.hour);
/* 30 */     out.writeByte(this.min);
/* 31 */     out.writeByte(this.second);
/*    */   }
/*    */   
/*    */   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException
/*    */   {
/* 36 */     this.hour = (in.readByte() & 0xFF);
/* 37 */     this.min = (in.readByte() & 0xFF);
/* 38 */     this.second = (in.readByte() & 0xFF);
/*    */   }
/*    */   
/*    */   public String toString() {
/* 42 */     StringBuilder sb = new StringBuilder();
/* 43 */     sb.append('[');
/* 44 */     sb.append("hour: ").append(this.hour).append(", ");
/* 45 */     sb.append("min: ").append(this.min).append(", ");
/* 46 */     sb.append("second: ").append(this.second).append(']');
/* 47 */     return sb.toString();
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\nccCodec-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ncc\tc\ct3\cmdparam\oh\OhEventReportPm.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */