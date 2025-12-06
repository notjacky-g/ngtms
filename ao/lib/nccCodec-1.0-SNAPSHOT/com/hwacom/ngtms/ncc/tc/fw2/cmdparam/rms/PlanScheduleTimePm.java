/*    */ package com.hwacom.ngtms.ncc.tc.fw2.cmdparam.rms;
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
/*    */ @GlobalParams(paramsName="planScheduleTime")
/*    */ public class PlanScheduleTimePm
/*    */   implements Externalizable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   @CommandParam(name="startTimeHour")
/*    */   public int startTimeHour;
/*    */   @CommandParam(name="startTimeMinute")
/*    */   public int startTimeMinute;
/*    */   @CommandParam(name="endTimeHour")
/*    */   public int endTimeHour;
/*    */   @CommandParam(name="endTimeMinute")
/*    */   public int endTimeMinute;
/*    */   @CommandParam(name="planNo")
/*    */   public int planNo;
/*    */   
/*    */   public void writeExternal(ObjectOutput out)
/*    */     throws IOException
/*    */   {
/* 33 */     out.writeByte(this.startTimeHour);
/* 34 */     out.writeByte(this.startTimeMinute);
/* 35 */     out.writeByte(this.endTimeHour);
/* 36 */     out.writeByte(this.endTimeMinute);
/* 37 */     out.writeByte(this.planNo);
/*    */   }
/*    */   
/*    */   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException
/*    */   {
/* 42 */     this.startTimeHour = (in.readByte() & 0xFF);
/* 43 */     this.startTimeMinute = (in.readByte() & 0xFF);
/* 44 */     this.endTimeHour = (in.readByte() & 0xFF);
/* 45 */     this.endTimeMinute = (in.readByte() & 0xFF);
/* 46 */     this.planNo = (in.readByte() & 0xFF);
/*    */   }
/*    */   
/*    */   public String toString() {
/* 50 */     StringBuilder sb = new StringBuilder();
/* 51 */     sb.append('[');
/* 52 */     sb.append("startTimeHour: ").append(this.startTimeHour).append(", ");
/* 53 */     sb.append("startTimeMinute: ").append(this.startTimeMinute).append(", ");
/* 54 */     sb.append("endTimeHour: ").append(this.endTimeHour).append(", ");
/* 55 */     sb.append("endTimeMinute: ").append(this.endTimeMinute).append(", ");
/* 56 */     sb.append("planNo: ").append(this.planNo).append(']');
/* 57 */     return sb.toString();
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\nccCodec-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ncc\tc\fw2\cmdparam\rms\PlanScheduleTimePm.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */