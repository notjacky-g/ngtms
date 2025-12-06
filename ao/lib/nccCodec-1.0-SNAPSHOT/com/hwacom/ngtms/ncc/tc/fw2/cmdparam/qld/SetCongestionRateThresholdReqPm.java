/*    */ package com.hwacom.ngtms.ncc.tc.fw2.cmdparam.qld;
/*    */ 
/*    */ import com.hwacom.ngtms.cmdprot.cmdfmtdef.CommandParam;
/*    */ import com.hwacom.ngtms.cmdprot.cmdfmtdef.CommandParams;
/*    */ import java.io.Externalizable;
/*    */ import java.io.IOException;
/*    */ import java.io.ObjectInput;
/*    */ import java.io.ObjectOutput;
/*    */ 
/*    */ @CommandParams(cmdName="setCongestionRateThresholdReq")
/*    */ public class SetCongestionRateThresholdReqPm
/*    */   implements Externalizable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   public static final int cmdId = 24325;
/*    */   public static final String cmdName = "setCongestionRateThresholdReq";
/*    */   @CommandParam(name="congestionRateThreshold")
/*    */   public int congestionRateThreshold;
/*    */   
/*    */   public void writeExternal(ObjectOutput out)
/*    */     throws IOException
/*    */   {
/* 23 */     out.writeByte(this.congestionRateThreshold);
/*    */   }
/*    */   
/*    */   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException
/*    */   {
/* 28 */     this.congestionRateThreshold = (in.readByte() & 0xFF);
/*    */   }
/*    */   
/*    */   public String toString() {
/* 32 */     StringBuilder sb = new StringBuilder();
/* 33 */     sb.append('[');
/* 34 */     sb.append("congestionRateThreshold: ").append(this.congestionRateThreshold).append(']');
/* 35 */     return sb.toString();
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\nccCodec-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ncc\tc\fw2\cmdparam\qld\SetCongestionRateThresholdReqPm.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */