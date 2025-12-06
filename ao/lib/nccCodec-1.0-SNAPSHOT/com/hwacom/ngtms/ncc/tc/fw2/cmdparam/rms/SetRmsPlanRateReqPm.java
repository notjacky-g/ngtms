/*    */ package com.hwacom.ngtms.ncc.tc.fw2.cmdparam.rms;
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
/*    */ @CommandParams(cmdName="setRmsPlanRateReq")
/*    */ public class SetRmsPlanRateReqPm
/*    */   implements Externalizable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   public static final int cmdId = 131;
/*    */   public static final String cmdName = "setRmsPlanRateReq";
/*    */   @CommandParam(name="planNo")
/*    */   public int planNo;
/*    */   @CommandParam(name="rate")
/*    */   public int rate;
/*    */   @CommandParam(name="vehicleNo")
/*    */   public int vehicleNo;
/*    */   
/*    */   public void writeExternal(ObjectOutput out)
/*    */     throws IOException
/*    */   {
/* 29 */     out.writeByte(this.planNo);
/* 30 */     out.writeByte(this.rate);
/* 31 */     out.writeByte(this.vehicleNo);
/*    */   }
/*    */   
/*    */   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException
/*    */   {
/* 36 */     this.planNo = (in.readByte() & 0xFF);
/* 37 */     this.rate = (in.readByte() & 0xFF);
/* 38 */     this.vehicleNo = (in.readByte() & 0xFF);
/*    */   }
/*    */   
/*    */   public String toString() {
/* 42 */     StringBuilder sb = new StringBuilder();
/* 43 */     sb.append('[');
/* 44 */     sb.append("planNo: ").append(this.planNo).append(", ");
/* 45 */     sb.append("rate: ").append(this.rate).append(", ");
/* 46 */     sb.append("vehicleNo: ").append(this.vehicleNo).append(']');
/* 47 */     return sb.toString();
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\nccCodec-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ncc\tc\fw2\cmdparam\rms\SetRmsPlanRateReqPm.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */