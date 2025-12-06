/*    */ package com.hwacom.ngtms.ncc.tc.fw3.cmdparam.scs;
/*    */ 
/*    */ import com.hwacom.ngtms.cmdprot.cmdfmtdef.CommandParam;
/*    */ import com.hwacom.ngtms.cmdprot.cmdfmtdef.CommandParams;
/*    */ import java.io.Externalizable;
/*    */ import java.io.IOException;
/*    */ import java.io.ObjectInput;
/*    */ import java.io.ObjectOutput;
/*    */ 
/*    */ 
/*    */ @CommandParams(cmdName="setScsControlModeAndPlanReq")
/*    */ public class SetScsControlModeAndPlanReqPm
/*    */   implements Externalizable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   public static final int cmdId = 53216;
/*    */   public static final String cmdName = "setScsControlModeAndPlanReq";
/*    */   @CommandParam(name="controlMode")
/*    */   public int controlMode;
/*    */   @CommandParam(name="planNoParam")
/*    */   public PlanNoParamPm planNoParamPm;
/*    */   
/*    */   public void writeExternal(ObjectOutput out)
/*    */     throws IOException
/*    */   {
/* 26 */     out.writeByte(this.controlMode);
/* 27 */     out.writeObject(this.planNoParamPm);
/*    */   }
/*    */   
/*    */   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException
/*    */   {
/* 32 */     this.controlMode = (in.readByte() & 0xFF);
/* 33 */     this.planNoParamPm = ((PlanNoParamPm)in.readObject());
/*    */   }
/*    */   
/*    */   public String toString() {
/* 37 */     StringBuilder sb = new StringBuilder();
/* 38 */     sb.append('[');
/* 39 */     sb.append("controlMode: ").append(this.controlMode).append(", ");
/* 40 */     sb.append("planNoParamPm: ").append(this.planNoParamPm).append(']');
/* 41 */     return sb.toString();
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\nccCodec-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ncc\tc\fw3\cmdparam\scs\SetScsControlModeAndPlanReqPm.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */