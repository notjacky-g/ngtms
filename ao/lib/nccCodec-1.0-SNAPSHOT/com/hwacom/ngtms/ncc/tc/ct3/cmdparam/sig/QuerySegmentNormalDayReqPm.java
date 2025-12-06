/*    */ package com.hwacom.ngtms.ncc.tc.ct3.cmdparam.sig;
/*    */ 
/*    */ import com.hwacom.ngtms.cmdprot.cmdfmtdef.CommandParam;
/*    */ import com.hwacom.ngtms.cmdprot.cmdfmtdef.CommandParams;
/*    */ import java.io.Externalizable;
/*    */ import java.io.IOException;
/*    */ import java.io.ObjectInput;
/*    */ import java.io.ObjectOutput;
/*    */ 
/*    */ 
/*    */ @CommandParams(cmdName="querySegmentNormalDayReq")
/*    */ public class QuerySegmentNormalDayReqPm
/*    */   implements Externalizable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   public static final int cmdId = 24390;
/*    */   public static final String cmdName = "querySegmentNormalDayReq";
/*    */   @CommandParam(name="segmentType")
/*    */   public int segmentType;
/*    */   @CommandParam(name="weekDay")
/*    */   public int weekDay;
/*    */   
/*    */   public void writeExternal(ObjectOutput out)
/*    */     throws IOException
/*    */   {
/* 26 */     out.writeByte(this.segmentType);
/* 27 */     out.writeByte(this.weekDay);
/*    */   }
/*    */   
/*    */   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException
/*    */   {
/* 32 */     this.segmentType = (in.readByte() & 0xFF);
/* 33 */     this.weekDay = (in.readByte() & 0xFF);
/*    */   }
/*    */   
/*    */   public String toString() {
/* 37 */     StringBuilder sb = new StringBuilder();
/* 38 */     sb.append('[');
/* 39 */     sb.append("segmentType: ").append(this.segmentType).append(", ");
/* 40 */     sb.append("weekDay: ").append(this.weekDay).append(']');
/* 41 */     return sb.toString();
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\nccCodec-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ncc\tc\ct3\cmdparam\sig\QuerySegmentNormalDayReqPm.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */