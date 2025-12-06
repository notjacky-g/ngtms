/*    */ package com.hwacom.ngtms.ncc.tc.fw2.cmdparam.rgs;
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
/*    */ @CommandParams(cmdName="getRgsMonitorRsp")
/*    */ public class GetRgsMonitorRspPm
/*    */   implements Externalizable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   public static final int cmdId = 157;
/*    */   public static final String cmdName = "getRgsMonitorRsp";
/*    */   @CommandParam(name="hwStatus")
/*    */   public HwStatusPm hwStatusPm;
/*    */   @CommandParam(name="commState")
/*    */   public int commState;
/*    */   @CommandParam(name="opStatus")
/*    */   public OpStatusPm opStatusPm;
/*    */   @CommandParam(name="opMode")
/*    */   public OpModePm opModePm;
/*    */   @CommandParam(name="genericMessage")
/*    */   public GenericMessagePm genericMessagePm;
/*    */   
/*    */   public void writeExternal(ObjectOutput out)
/*    */     throws IOException
/*    */   {
/* 36 */     out.writeObject(this.hwStatusPm);
/* 37 */     out.writeByte(this.commState);
/* 38 */     out.writeObject(this.opStatusPm);
/* 39 */     out.writeObject(this.opModePm);
/* 40 */     out.writeObject(this.genericMessagePm);
/*    */   }
/*    */   
/*    */   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException
/*    */   {
/* 45 */     this.hwStatusPm = ((HwStatusPm)in.readObject());
/* 46 */     this.commState = (in.readByte() & 0xFF);
/* 47 */     this.opStatusPm = ((OpStatusPm)in.readObject());
/* 48 */     this.opModePm = ((OpModePm)in.readObject());
/* 49 */     this.genericMessagePm = ((GenericMessagePm)in.readObject());
/*    */   }
/*    */   
/*    */   public String toString() {
/* 53 */     StringBuilder sb = new StringBuilder();
/* 54 */     sb.append('[');
/* 55 */     sb.append("hwStatusPm: ").append(this.hwStatusPm).append(", ");
/* 56 */     sb.append("commState: ").append(this.commState).append(", ");
/* 57 */     sb.append("opStatusPm: ").append(this.opStatusPm).append(", ");
/* 58 */     sb.append("opModePm: ").append(this.opModePm).append(", ");
/* 59 */     sb.append("genericMessagePm: ").append(this.genericMessagePm).append(']');
/* 60 */     return sb.toString();
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\nccCodec-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ncc\tc\fw2\cmdparam\rgs\GetRgsMonitorRspPm.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */