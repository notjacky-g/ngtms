/*    */ package com.hwacom.ngtms.ncc.tc.fw2.cmdparam.rms;
/*    */ 
/*    */ import com.hwacom.ngtms.cmdprot.cmdfmtdef.CommandParam;
/*    */ import com.hwacom.ngtms.cmdprot.cmdfmtdef.CommandParams;
/*    */ import com.hwacom.ngtms.ncc.tc.fw2.cmdparam.DayPm;
/*    */ import java.io.Externalizable;
/*    */ import java.io.IOException;
/*    */ import java.io.ObjectInput;
/*    */ import java.io.ObjectOutput;
/*    */ import java.util.List;
/*    */ 
/*    */ @CommandParams(cmdName="setSpecificPlanScheduleReq")
/*    */ public class SetSpecificPlanScheduleReqPm
/*    */   implements Externalizable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   public static final int cmdId = 164;
/*    */   public static final String cmdName = "setSpecificPlanScheduleReq";
/*    */   @CommandParam(name="specificNo")
/*    */   public int specificNo;
/*    */   @CommandParam(name="segmentCount")
/*    */   public int segmentCount;
/*    */   @CommandParam(name="day")
/*    */   public DayPm dayPm;
/*    */   @CommandParam(name="segmentList")
/*    */   public List<SegmentListItem> segmentList;
/*    */   
/*    */   public static class SegmentListItem
/*    */     implements Externalizable
/*    */   {
/*    */     private static final long serialVersionUID = 1L;
/*    */     @CommandParam(name="planScheduleTime")
/*    */     public PlanScheduleTimePm planScheduleTimePm;
/*    */     
/*    */     public void writeExternal(ObjectOutput out)
/*    */       throws IOException
/*    */     {
/* 38 */       out.writeObject(this.planScheduleTimePm);
/*    */     }
/*    */     
/*    */     public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException
/*    */     {
/* 43 */       this.planScheduleTimePm = ((PlanScheduleTimePm)in.readObject());
/*    */     }
/*    */     
/*    */     public String toString() {
/* 47 */       StringBuilder sb = new StringBuilder();
/* 48 */       sb.append('[');
/* 49 */       sb.append("planScheduleTimePm: ").append(this.planScheduleTimePm).append(']');
/* 50 */       return sb.toString();
/*    */     }
/*    */   }
/*    */   
/*    */   public void writeExternal(ObjectOutput out) throws IOException
/*    */   {
/* 56 */     out.writeByte(this.specificNo);
/* 57 */     out.writeByte(this.segmentCount);
/* 58 */     out.writeObject(this.dayPm);
/* 59 */     out.writeObject(this.segmentList);
/*    */   }
/*    */   
/*    */   public void readExternal(ObjectInput in)
/*    */     throws IOException, ClassNotFoundException
/*    */   {
/* 65 */     this.specificNo = (in.readByte() & 0xFF);
/* 66 */     this.segmentCount = (in.readByte() & 0xFF);
/* 67 */     this.dayPm = ((DayPm)in.readObject());
/* 68 */     this.segmentList = ((List)in.readObject());
/*    */   }
/*    */   
/*    */   public String toString() {
/* 72 */     StringBuilder sb = new StringBuilder();
/* 73 */     sb.append('[');
/* 74 */     sb.append("specificNo: ").append(this.specificNo).append(", ");
/* 75 */     sb.append("segmentCount: ").append(this.segmentCount).append(", ");
/* 76 */     sb.append("dayPm: ").append(this.dayPm).append(", ");
/* 77 */     sb.append("segmentList: ").append(this.segmentList).append(']');
/* 78 */     return sb.toString();
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\nccCodec-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ncc\tc\fw2\cmdparam\rms\SetSpecificPlanScheduleReqPm.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */