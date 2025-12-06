/*    */ package com.hwacom.ngtms.ncc.tc.fw2.cmdparam.scs;
/*    */ 
/*    */ import com.hwacom.ngtms.cmdprot.cmdfmtdef.CommandParam;
/*    */ import com.hwacom.ngtms.cmdprot.cmdfmtdef.CommandParams;
/*    */ import com.hwacom.ngtms.ncc.tc.fw2.cmdparam.HwStatusPm;
/*    */ import com.hwacom.ngtms.ncc.tc.fw2.cmdparam.OpModePm;
/*    */ import com.hwacom.ngtms.ncc.tc.fw2.cmdparam.OpStatusPm;
/*    */ import java.io.Externalizable;
/*    */ import java.io.IOException;
/*    */ import java.io.ObjectInput;
/*    */ import java.io.ObjectOutput;
/*    */ 
/*    */ 
/*    */ 
/*    */ @CommandParams(cmdName="scsControlModeAndPlanChangeReport")
/*    */ public class ScsControlModeAndPlanChangeReportPm
/*    */   implements Externalizable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   public static final int cmdId = 53218;
/*    */   public static final String cmdName = "scsControlModeAndPlanChangeReport";
/*    */   @CommandParam(name="hwStatus")
/*    */   public HwStatusPm hwStatusPm;
/*    */   @CommandParam(name="commState")
/*    */   public int commState;
/*    */   @CommandParam(name="opStatus")
/*    */   public OpStatusPm opStatusPm;
/*    */   @CommandParam(name="opMode")
/*    */   public OpModePm opModePm;
/*    */   @CommandParam(name="controlMode")
/*    */   public int controlMode;
/*    */   @CommandParam(name="planNoParam")
/*    */   public PlanNoParamPm planNoParamPm;
/*    */   
/*    */   public void writeExternal(ObjectOutput out)
/*    */     throws IOException
/*    */   {
/* 38 */     out.writeObject(this.hwStatusPm);
/* 39 */     out.writeByte(this.commState);
/* 40 */     out.writeObject(this.opStatusPm);
/* 41 */     out.writeObject(this.opModePm);
/* 42 */     out.writeByte(this.controlMode);
/* 43 */     out.writeObject(this.planNoParamPm);
/*    */   }
/*    */   
/*    */   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException
/*    */   {
/* 48 */     this.hwStatusPm = ((HwStatusPm)in.readObject());
/* 49 */     this.commState = (in.readByte() & 0xFF);
/* 50 */     this.opStatusPm = ((OpStatusPm)in.readObject());
/* 51 */     this.opModePm = ((OpModePm)in.readObject());
/* 52 */     this.controlMode = (in.readByte() & 0xFF);
/* 53 */     this.planNoParamPm = ((PlanNoParamPm)in.readObject());
/*    */   }
/*    */   
/*    */   public String toString() {
/* 57 */     StringBuilder sb = new StringBuilder();
/* 58 */     sb.append('[');
/* 59 */     sb.append("hwStatusPm: ").append(this.hwStatusPm).append(", ");
/* 60 */     sb.append("commState: ").append(this.commState).append(", ");
/* 61 */     sb.append("opStatusPm: ").append(this.opStatusPm).append(", ");
/* 62 */     sb.append("opModePm: ").append(this.opModePm).append(", ");
/* 63 */     sb.append("controlMode: ").append(this.controlMode).append(", ");
/* 64 */     sb.append("planNoParamPm: ").append(this.planNoParamPm).append(']');
/* 65 */     return sb.toString();
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\nccCodec-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ncc\tc\fw2\cmdparam\scs\ScsControlModeAndPlanChangeReportPm.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */