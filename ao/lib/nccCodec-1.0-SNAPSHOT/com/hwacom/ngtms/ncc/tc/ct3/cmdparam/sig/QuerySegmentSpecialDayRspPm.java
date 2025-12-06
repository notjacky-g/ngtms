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
/*    */ @CommandParams(cmdName="querySegmentSpecialDayRsp")
/*    */ public class QuerySegmentSpecialDayRspPm implements Externalizable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   public static final int cmdId = 24519;
/*    */   public static final String cmdName = "querySegmentSpecialDayRsp";
/*    */   @CommandParam(name="segmentType")
/*    */   public int segmentType;
/*    */   @CommandParam(name="segmentList")
/*    */   public List<SegmentListItem> segmentList;
/*    */   @CommandParam(name="segmentDayRange")
/*    */   public SegmentDayRangePm segmentDayRangePm;
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
/*    */ 
/*    */   public void writeExternal(ObjectOutput out)
/*    */     throws IOException
/*    */   {
/* 53 */     out.writeByte(this.segmentType);
/* 54 */     out.writeObject(this.segmentList);
/* 55 */     out.writeObject(this.segmentDayRangePm);
/*    */   }
/*    */   
/*    */   public void readExternal(ObjectInput in)
/*    */     throws IOException, ClassNotFoundException
/*    */   {
/* 61 */     this.segmentType = (in.readByte() & 0xFF);
/* 62 */     this.segmentList = ((List)in.readObject());
/* 63 */     this.segmentDayRangePm = ((SegmentDayRangePm)in.readObject());
/*    */   }
/*    */   
/*    */   public String toString() {
/* 67 */     StringBuilder sb = new StringBuilder();
/* 68 */     sb.append('[');
/* 69 */     sb.append("segmentType: ").append(this.segmentType).append(", ");
/* 70 */     sb.append("segmentList: ").append(this.segmentList).append(", ");
/* 71 */     sb.append("segmentDayRangePm: ").append(this.segmentDayRangePm).append(']');
/* 72 */     return sb.toString();
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\nccCodec-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ncc\tc\ct3\cmdparam\sig\QuerySegmentSpecialDayRspPm.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */