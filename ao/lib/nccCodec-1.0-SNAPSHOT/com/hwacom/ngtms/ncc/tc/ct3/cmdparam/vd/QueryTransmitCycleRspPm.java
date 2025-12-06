/*    */ package com.hwacom.ngtms.ncc.tc.ct3.cmdparam.vd;
/*    */ 
/*    */ import com.hwacom.ngtms.cmdprot.cmdfmtdef.CommandParam;
/*    */ import com.hwacom.ngtms.cmdprot.cmdfmtdef.CommandParams;
/*    */ import java.io.Externalizable;
/*    */ import java.io.IOException;
/*    */ import java.io.ObjectInput;
/*    */ import java.io.ObjectOutput;
/*    */ 
/*    */ 
/*    */ @CommandParams(cmdName="queryTransmitCycleRsp")
/*    */ public class QueryTransmitCycleRspPm
/*    */   implements Externalizable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   public static final int cmdId = 28655;
/*    */   public static final String cmdName = "queryTransmitCycleRsp";
/*    */   @CommandParam(name="transmitCycle")
/*    */   public int transmitCycle;
/*    */   @CommandParam(name="transmitType")
/*    */   public int transmitType;
/*    */   
/*    */   public void writeExternal(ObjectOutput out)
/*    */     throws IOException
/*    */   {
/* 26 */     out.writeByte(this.transmitCycle);
/* 27 */     out.writeByte(this.transmitType);
/*    */   }
/*    */   
/*    */   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException
/*    */   {
/* 32 */     this.transmitCycle = (in.readByte() & 0xFF);
/* 33 */     this.transmitType = (in.readByte() & 0xFF);
/*    */   }
/*    */   
/*    */   public String toString() {
/* 37 */     StringBuilder sb = new StringBuilder();
/* 38 */     sb.append('[');
/* 39 */     sb.append("transmitCycle: ").append(this.transmitCycle).append(", ");
/* 40 */     sb.append("transmitType: ").append(this.transmitType).append(']');
/* 41 */     return sb.toString();
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\nccCodec-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ncc\tc\ct3\cmdparam\vd\QueryTransmitCycleRspPm.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */