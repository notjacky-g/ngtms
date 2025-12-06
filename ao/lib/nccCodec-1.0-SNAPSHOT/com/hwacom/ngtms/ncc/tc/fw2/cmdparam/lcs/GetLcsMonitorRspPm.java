/*    */ package com.hwacom.ngtms.ncc.tc.fw2.cmdparam.lcs;
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
/*    */ @CommandParams(cmdName="getLcsMonitorRsp")
/*    */ public class GetLcsMonitorRspPm
/*    */   implements Externalizable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   public static final int cmdId = 196;
/*    */   public static final String cmdName = "getLcsMonitorRsp";
/*    */   @CommandParam(name="hwStatus")
/*    */   public HwStatusPm hwStatusPm;
/*    */   @CommandParam(name="commState")
/*    */   public int commState;
/*    */   @CommandParam(name="opStatus")
/*    */   public OpStatusPm opStatusPm;
/*    */   @CommandParam(name="opMode")
/*    */   public OpModePm opModePm;
/*    */   @CommandParam(name="signStatusParam")
/*    */   public SignStatusParamPm signStatusParamPm;
/*    */   
/*    */   public void writeExternal(ObjectOutput out)
/*    */     throws IOException
/*    */   {
/* 35 */     out.writeObject(this.hwStatusPm);
/* 36 */     out.writeByte(this.commState);
/* 37 */     out.writeObject(this.opStatusPm);
/* 38 */     out.writeObject(this.opModePm);
/* 39 */     out.writeObject(this.signStatusParamPm);
/*    */   }
/*    */   
/*    */   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException
/*    */   {
/* 44 */     this.hwStatusPm = ((HwStatusPm)in.readObject());
/* 45 */     this.commState = (in.readByte() & 0xFF);
/* 46 */     this.opStatusPm = ((OpStatusPm)in.readObject());
/* 47 */     this.opModePm = ((OpModePm)in.readObject());
/* 48 */     this.signStatusParamPm = ((SignStatusParamPm)in.readObject());
/*    */   }
/*    */   
/*    */   public String toString() {
/* 52 */     StringBuilder sb = new StringBuilder();
/* 53 */     sb.append('[');
/* 54 */     sb.append("hwStatusPm: ").append(this.hwStatusPm).append(", ");
/* 55 */     sb.append("commState: ").append(this.commState).append(", ");
/* 56 */     sb.append("opStatusPm: ").append(this.opStatusPm).append(", ");
/* 57 */     sb.append("opModePm: ").append(this.opModePm).append(", ");
/* 58 */     sb.append("signStatusParamPm: ").append(this.signStatusParamPm).append(']');
/* 59 */     return sb.toString();
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\nccCodec-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ncc\tc\fw2\cmdparam\lcs\GetLcsMonitorRspPm.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */