/*    */ package com.hwacom.ngtms.ncc.tc.ct3.cmdparam.sig;
/*    */ 
/*    */ import com.hwacom.ngtms.cmdprot.cmdfmtdef.CommandParam;
/*    */ import com.hwacom.ngtms.cmdprot.cmdfmtdef.CommandParams;
/*    */ import java.io.Externalizable;
/*    */ import java.io.IOException;
/*    */ import java.io.ObjectInput;
/*    */ import java.io.ObjectOutput;
/*    */ import java.util.List;
/*    */ 
/*    */ @CommandParams(cmdName="setSegmentNormalDayReq")
/*    */ public class SetSegmentNormalDayReqPm implements Externalizable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   public static final int cmdId = 24342;
/*    */   public static final String cmdName = "setSegmentNormalDayReq";
/*    */   @CommandParam(name="segmentType")
/*    */   public int segmentType;
/*    */   @CommandParam(name="segmentList")
/*    */   public List<SegmentListItem> segmentList;
/*    */   @CommandParam(name="weekDayList")
/*    */   public List<WeekDayListItem> weekDayList;
/*    */   
/*    */   public static class SegmentListItem implements Externalizable
/*    */   {
/*    */     private static final long serialVersionUID = 1L;
/*    */     @CommandParam(name="segment")
/*    */     public SegmentPm segmentPm;
/*    */     
/*    */     public void writeExternal(ObjectOutput out) throws IOException
/*    */     {
/* 32 */       out.writeObject(this.segmentPm);
/*    */     }
/*    */     
/*    */     public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException
/*    */     {
/* 37 */       this.segmentPm = ((SegmentPm)in.readObject());
/*    */     }
/*    */     
/*    */     public String toString() {
/* 41 */       StringBuilder sb = new StringBuilder();
/* 42 */       sb.append('[');
/* 43 */       sb.append("segmentPm: ").append(this.segmentPm).append(']');
/* 44 */       return sb.toString();
/*    */     }
/*    */   }
/*    */   
/*    */ 
/*    */   public static class WeekDayListItem
/*    */     implements Externalizable
/*    */   {
/*    */     private static final long serialVersionUID = 1L;
/*    */     @CommandParam(name="weekDay")
/*    */     public int weekDay;
/*    */     
/*    */     public void writeExternal(ObjectOutput out)
/*    */       throws IOException
/*    */     {
/* 59 */       out.writeByte(this.weekDay);
/*    */     }
/*    */     
/*    */     public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException
/*    */     {
/* 64 */       this.weekDay = (in.readByte() & 0xFF);
/*    */     }
/*    */     
/*    */     public String toString() {
/* 68 */       StringBuilder sb = new StringBuilder();
/* 69 */       sb.append('[');
/* 70 */       sb.append("weekDay: ").append(this.weekDay).append(']');
/* 71 */       return sb.toString();
/*    */     }
/*    */   }
/*    */   
/*    */   public void writeExternal(ObjectOutput out) throws IOException
/*    */   {
/* 77 */     out.writeByte(this.segmentType);
/* 78 */     out.writeObject(this.segmentList);
/* 79 */     out.writeObject(this.weekDayList);
/*    */   }
/*    */   
/*    */   public void readExternal(ObjectInput in)
/*    */     throws IOException, ClassNotFoundException
/*    */   {
/* 85 */     this.segmentType = (in.readByte() & 0xFF);
/* 86 */     this.segmentList = ((List)in.readObject());
/* 87 */     this.weekDayList = ((List)in.readObject());
/*    */   }
/*    */   
/*    */   public String toString() {
/* 91 */     StringBuilder sb = new StringBuilder();
/* 92 */     sb.append('[');
/* 93 */     sb.append("segmentType: ").append(this.segmentType).append(", ");
/* 94 */     sb.append("segmentList: ").append(this.segmentList).append(", ");
/* 95 */     sb.append("weekDayList: ").append(this.weekDayList).append(']');
/* 96 */     return sb.toString();
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\nccCodec-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ncc\tc\ct3\cmdparam\sig\SetSegmentNormalDayReqPm.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */