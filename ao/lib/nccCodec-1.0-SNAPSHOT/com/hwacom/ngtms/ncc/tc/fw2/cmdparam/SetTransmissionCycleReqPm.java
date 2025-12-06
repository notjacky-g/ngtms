/*    */ package com.hwacom.ngtms.ncc.tc.fw2.cmdparam;
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
/*    */ @CommandParams(cmdName="setTransmissionCycleReq")
/*    */ public class SetTransmissionCycleReqPm
/*    */   implements Externalizable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   public static final int cmdId = 3;
/*    */   public static final String cmdName = "setTransmissionCycleReq";
/*    */   @CommandParam(name="deviceType")
/*    */   public int deviceType;
/*    */   @CommandParam(name="transmissionCycle")
/*    */   public int transmissionCycle;
/*    */   @CommandParam(name="transmitMode")
/*    */   public int transmitMode;
/*    */   @CommandParam(name="hwcyc")
/*    */   public int hwcyc;
/*    */   
/*    */   public void writeExternal(ObjectOutput out)
/*    */     throws IOException
/*    */   {
/* 31 */     out.writeByte(this.deviceType);
/* 32 */     out.writeByte(this.transmissionCycle);
/* 33 */     out.writeByte(this.transmitMode);
/* 34 */     out.writeByte(this.hwcyc);
/*    */   }
/*    */   
/*    */   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException
/*    */   {
/* 39 */     this.deviceType = (in.readByte() & 0xFF);
/* 40 */     this.transmissionCycle = (in.readByte() & 0xFF);
/* 41 */     this.transmitMode = (in.readByte() & 0xFF);
/* 42 */     this.hwcyc = (in.readByte() & 0xFF);
/*    */   }
/*    */   
/*    */   public String toString() {
/* 46 */     StringBuilder sb = new StringBuilder();
/* 47 */     sb.append('[');
/* 48 */     sb.append("deviceType: ").append(this.deviceType).append(", ");
/* 49 */     sb.append("transmissionCycle: ").append(this.transmissionCycle).append(", ");
/* 50 */     sb.append("transmitMode: ").append(this.transmitMode).append(", ");
/* 51 */     sb.append("hwcyc: ").append(this.hwcyc).append(']');
/* 52 */     return sb.toString();
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\nccCodec-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ncc\tc\fw2\cmdparam\SetTransmissionCycleReqPm.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */