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
/*    */ 
/*    */ 
/*    */ 
/*    */ @GlobalParams(paramsName="segmentDayRange")
/*    */ public class SegmentDayRangePm
/*    */   implements Externalizable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   @CommandParam(name="startYear")
/*    */   public int startYear;
/*    */   @CommandParam(name="startMonth")
/*    */   public int startMonth;
/*    */   @CommandParam(name="startDay")
/*    */   public int startDay;
/*    */   @CommandParam(name="endYear")
/*    */   public int endYear;
/*    */   @CommandParam(name="endMonth")
/*    */   public int endMonth;
/*    */   @CommandParam(name="endDay")
/*    */   public int endDay;
/*    */   
/*    */   public void writeExternal(ObjectOutput out)
/*    */     throws IOException
/*    */   {
/* 36 */     out.writeByte(this.startYear);
/* 37 */     out.writeByte(this.startMonth);
/* 38 */     out.writeByte(this.startDay);
/* 39 */     out.writeByte(this.endYear);
/* 40 */     out.writeByte(this.endMonth);
/* 41 */     out.writeByte(this.endDay);
/*    */   }
/*    */   
/*    */   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException
/*    */   {
/* 46 */     this.startYear = (in.readByte() & 0xFF);
/* 47 */     this.startMonth = (in.readByte() & 0xFF);
/* 48 */     this.startDay = (in.readByte() & 0xFF);
/* 49 */     this.endYear = (in.readByte() & 0xFF);
/* 50 */     this.endMonth = (in.readByte() & 0xFF);
/* 51 */     this.endDay = (in.readByte() & 0xFF);
/*    */   }
/*    */   
/*    */   public String toString() {
/* 55 */     StringBuilder sb = new StringBuilder();
/* 56 */     sb.append('[');
/* 57 */     sb.append("startYear: ").append(this.startYear).append(", ");
/* 58 */     sb.append("startMonth: ").append(this.startMonth).append(", ");
/* 59 */     sb.append("startDay: ").append(this.startDay).append(", ");
/* 60 */     sb.append("endYear: ").append(this.endYear).append(", ");
/* 61 */     sb.append("endMonth: ").append(this.endMonth).append(", ");
/* 62 */     sb.append("endDay: ").append(this.endDay).append(']');
/* 63 */     return sb.toString();
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\nccCodec-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ncc\tc\ct3\cmdparam\sig\SegmentDayRangePm.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */