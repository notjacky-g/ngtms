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
/*    */ @CommandParams(cmdName="setRegionFlexedAdjustModeParamReq")
/*    */ public class SetRegionFlexedAdjustModeParamReqPm
/*    */   implements Externalizable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   public static final int cmdId = 137;
/*    */   public static final String cmdName = "setRegionFlexedAdjustModeParamReq";
/*    */   @CommandParam(name="maxRedTime")
/*    */   public int maxRedTime;
/*    */   @CommandParam(name="minRedTime")
/*    */   public int minRedTime;
/*    */   @CommandParam(name="maxGreenTime")
/*    */   public int maxGreenTime;
/*    */   @CommandParam(name="minGreenTime")
/*    */   public int minGreenTime;
/*    */   
/*    */   public void writeExternal(ObjectOutput out)
/*    */     throws IOException
/*    */   {
/* 32 */     out.writeShort(this.maxRedTime);
/* 33 */     out.writeByte(this.minRedTime);
/* 34 */     out.writeShort(this.maxGreenTime);
/* 35 */     out.writeByte(this.minGreenTime);
/*    */   }
/*    */   
/*    */   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException
/*    */   {
/* 40 */     this.maxRedTime = (in.readShort() & 0xFFFF);
/* 41 */     this.minRedTime = (in.readByte() & 0xFF);
/* 42 */     this.maxGreenTime = (in.readShort() & 0xFFFF);
/* 43 */     this.minGreenTime = (in.readByte() & 0xFF);
/*    */   }
/*    */   
/*    */   public String toString() {
/* 47 */     StringBuilder sb = new StringBuilder();
/* 48 */     sb.append('[');
/* 49 */     sb.append("maxRedTime: ").append(this.maxRedTime).append(", ");
/* 50 */     sb.append("minRedTime: ").append(this.minRedTime).append(", ");
/* 51 */     sb.append("maxGreenTime: ").append(this.maxGreenTime).append(", ");
/* 52 */     sb.append("minGreenTime: ").append(this.minGreenTime).append(']');
/* 53 */     return sb.toString();
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\nccCodec-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ncc\tc\fw2\cmdparam\rms\SetRegionFlexedAdjustModeParamReqPm.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */