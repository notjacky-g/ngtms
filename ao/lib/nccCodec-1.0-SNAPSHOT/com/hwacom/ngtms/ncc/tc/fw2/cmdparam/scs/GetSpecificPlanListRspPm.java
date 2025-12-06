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
/*    */ @CommandParams(cmdName="getSpecificPlanListRsp")
/*    */ public class GetSpecificPlanListRspPm implements Externalizable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   public static final int cmdId = 53222;
/*    */   public static final String cmdName = "getSpecificPlanListRsp";
/*    */   @CommandParam(name="segmentList")
/*    */   public List<SegmentListItem> segmentList;
/*    */   
/*    */   public static class SegmentListItem implements Externalizable
/*    */   {
/*    */     private static final long serialVersionUID = 1L;
/*    */     @CommandParam(name="day")
/*    */     public DayPm dayPm;
/*    */     
/*    */     public void writeExternal(ObjectOutput out) throws IOException
/*    */     {
/* 29 */       out.writeObject(this.dayPm);
/*    */     }
/*    */     
/*    */     public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException
/*    */     {
/* 34 */       this.dayPm = ((DayPm)in.readObject());
/*    */     }
/*    */     
/*    */     public String toString() {
/* 38 */       StringBuilder sb = new StringBuilder();
/* 39 */       sb.append('[');
/* 40 */       sb.append("dayPm: ").append(this.dayPm).append(']');
/* 41 */       return sb.toString();
/*    */     }
/*    */   }
/*    */   
/*    */   public void writeExternal(ObjectOutput out) throws IOException
/*    */   {
/* 47 */     out.writeObject(this.segmentList);
/*    */   }
/*    */   
/*    */   public void readExternal(ObjectInput in)
/*    */     throws IOException, ClassNotFoundException
/*    */   {
/* 53 */     this.segmentList = ((List)in.readObject());
/*    */   }
/*    */   
/*    */   public String toString() {
/* 57 */     StringBuilder sb = new StringBuilder();
/* 58 */     sb.append('[');
/* 59 */     sb.append("segmentList: ").append(this.segmentList).append(']');
/* 60 */     return sb.toString();
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\nccCodec-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ncc\tc\fw2\cmdparam\scs\GetSpecificPlanListRspPm.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */