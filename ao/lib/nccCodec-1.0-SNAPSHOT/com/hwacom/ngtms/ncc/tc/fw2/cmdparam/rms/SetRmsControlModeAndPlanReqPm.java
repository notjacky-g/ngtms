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
/*    */ @CommandParams(cmdName="setRmsControlModeAndPlanReq")
/*    */ public class SetRmsControlModeAndPlanReqPm
/*    */   implements Externalizable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   public static final int cmdId = 130;
/*    */   public static final String cmdName = "setRmsControlModeAndPlanReq";
/*    */   @CommandParam(name="controlMode")
/*    */   public int controlMode;
/*    */   @CommandParam(name="planNo")
/*    */   public int planNo;
/*    */   
/*    */   public void writeExternal(ObjectOutput out)
/*    */     throws IOException
/*    */   {
/* 26 */     out.writeByte(this.controlMode);
/* 27 */     out.writeByte(this.planNo);
/*    */   }
/*    */   
/*    */   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException
/*    */   {
/* 32 */     this.controlMode = (in.readByte() & 0xFF);
/* 33 */     this.planNo = (in.readByte() & 0xFF);
/*    */   }
/*    */   
/*    */   public String toString() {
/* 37 */     StringBuilder sb = new StringBuilder();
/* 38 */     sb.append('[');
/* 39 */     sb.append("controlMode: ").append(this.controlMode).append(", ");
/* 40 */     sb.append("planNo: ").append(this.planNo).append(']');
/* 41 */     return sb.toString();
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\nccCodec-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ncc\tc\fw2\cmdparam\rms\SetRmsControlModeAndPlanReqPm.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */