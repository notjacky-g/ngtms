/*    */ package com.hwacom.ngtms.ncc.tc.fw2.cmdparam.scs;
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
/*    */ @CommandParams(cmdName="getSpecificPlanScheduleRsp")
/*    */ public class GetSpecificPlanScheduleRspPm
/*    */   implements Externalizable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   public static final int cmdId = 53223;
/*    */   public static final String cmdName = "getSpecificPlanScheduleRsp";
/*    */   @CommandParam(name="day")
/*    */   public DayPm dayPm;
/*    */   @CommandParam(name="segmentList")
/*    */   public List<SegmentListItem> segmentList;
/*    */   
/*    */   public static class SegmentListItem implements Externalizable
/*    */   {
/*    */     private static final long serialVersionUID = 1L;
/*    */     @CommandParam(name="planScheduleTime")
/*    */     public PlanScheduleTimePm planScheduleTimePm;
/*    */     
/*    */     public void writeExternal(ObjectOutput out) throws IOException
/*    */     {
/* 32 */       out.writeObject(this.planScheduleTimePm);
/*    */     }
/*    */     
/*    */     public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException
/*    */     {
/* 37 */       this.planScheduleTimePm = ((PlanScheduleTimePm)in.readObject());
/*    */     }
/*    */     
/*    */     public String toString() {
/* 41 */       StringBuilder sb = new StringBuilder();
/* 42 */       sb.append('[');
/* 43 */       sb.append("planScheduleTimePm: ").append(this.planScheduleTimePm).append(']');
/* 44 */       return sb.toString();
/*    */     }
/*    */   }
/*    */   
/*    */   public void writeExternal(ObjectOutput out) throws IOException
/*    */   {
/* 50 */     out.writeObject(this.dayPm);
/* 51 */     out.writeObject(this.segmentList);
/*    */   }
/*    */   
/*    */   public void readExternal(ObjectInput in)
/*    */     throws IOException, ClassNotFoundException
/*    */   {
/* 57 */     this.dayPm = ((DayPm)in.readObject());
/* 58 */     this.segmentList = ((List)in.readObject());
/*    */   }
/*    */   
/*    */   public String toString() {
/* 62 */     StringBuilder sb = new StringBuilder();
/* 63 */     sb.append('[');
/* 64 */     sb.append("dayPm: ").append(this.dayPm).append(", ");
/* 65 */     sb.append("segmentList: ").append(this.segmentList).append(']');
/* 66 */     return sb.toString();
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\nccCodec-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ncc\tc\fw2\cmdparam\scs\GetSpecificPlanScheduleRspPm.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */