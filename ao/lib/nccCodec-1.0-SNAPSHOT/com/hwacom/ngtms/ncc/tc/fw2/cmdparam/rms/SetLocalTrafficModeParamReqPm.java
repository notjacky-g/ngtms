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
/*    */ 
/*    */ 
/*    */ 
/*    */ @CommandParams(cmdName="setLocalTrafficModeParamReq")
/*    */ public class SetLocalTrafficModeParamReqPm
/*    */   implements Externalizable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   public static final int cmdId = 136;
/*    */   public static final String cmdName = "setLocalTrafficModeParamReq";
/*    */   @CommandParam(name="mainOccupyThreshold")
/*    */   public int mainOccupyThreshold;
/*    */   @CommandParam(name="maxRate")
/*    */   public int maxRate;
/*    */   @CommandParam(name="minRate")
/*    */   public int minRate;
/*    */   @CommandParam(name="rampFlowThreshold")
/*    */   public int rampFlowThreshold;
/*    */   @CommandParam(name="rampTerminateCountThreshold")
/*    */   public int rampTerminateCountThreshold;
/*    */   @CommandParam(name="nextRampCapacity")
/*    */   public int nextRampCapacity;
/*    */   
/*    */   public void writeExternal(ObjectOutput out)
/*    */     throws IOException
/*    */   {
/* 38 */     out.writeByte(this.mainOccupyThreshold);
/* 39 */     out.writeByte(this.maxRate);
/* 40 */     out.writeByte(this.minRate);
/* 41 */     out.writeShort(this.rampFlowThreshold);
/* 42 */     out.writeByte(this.rampTerminateCountThreshold);
/* 43 */     out.writeShort(this.nextRampCapacity);
/*    */   }
/*    */   
/*    */   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException
/*    */   {
/* 48 */     this.mainOccupyThreshold = (in.readByte() & 0xFF);
/* 49 */     this.maxRate = (in.readByte() & 0xFF);
/* 50 */     this.minRate = (in.readByte() & 0xFF);
/* 51 */     this.rampFlowThreshold = (in.readShort() & 0xFFFF);
/* 52 */     this.rampTerminateCountThreshold = (in.readByte() & 0xFF);
/* 53 */     this.nextRampCapacity = (in.readShort() & 0xFFFF);
/*    */   }
/*    */   
/*    */   public String toString() {
/* 57 */     StringBuilder sb = new StringBuilder();
/* 58 */     sb.append('[');
/* 59 */     sb.append("mainOccupyThreshold: ").append(this.mainOccupyThreshold).append(", ");
/* 60 */     sb.append("maxRate: ").append(this.maxRate).append(", ");
/* 61 */     sb.append("minRate: ").append(this.minRate).append(", ");
/* 62 */     sb.append("rampFlowThreshold: ").append(this.rampFlowThreshold).append(", ");
/* 63 */     sb.append("rampTerminateCountThreshold: ").append(this.rampTerminateCountThreshold).append(", ");
/* 64 */     sb.append("nextRampCapacity: ").append(this.nextRampCapacity).append(']');
/* 65 */     return sb.toString();
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\nccCodec-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ncc\tc\fw2\cmdparam\rms\SetLocalTrafficModeParamReqPm.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */