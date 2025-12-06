/*    */ package com.hwacom.ngtms.ncc.tc.fw2.cmdparam.lcs;
/*    */ 
/*    */ import com.hwacom.ngtms.cmdprot.cmdfmtdef.CommandParam;
/*    */ import com.hwacom.ngtms.cmdprot.cmdfmtdef.CommandParams;
/*    */ import java.io.Externalizable;
/*    */ import java.io.IOException;
/*    */ import java.io.ObjectInput;
/*    */ import java.io.ObjectOutput;
/*    */ 
/*    */ @CommandParams(cmdName="lcsSignStatusReport")
/*    */ public class LcsSignStatusReportPm
/*    */   implements Externalizable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   public static final int cmdId = 195;
/*    */   public static final String cmdName = "lcsSignStatusReport";
/*    */   @CommandParam(name="signStatusParam")
/*    */   public SignStatusParamPm signStatusParamPm;
/*    */   
/*    */   public void writeExternal(ObjectOutput out)
/*    */     throws IOException
/*    */   {
/* 23 */     out.writeObject(this.signStatusParamPm);
/*    */   }
/*    */   
/*    */   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException
/*    */   {
/* 28 */     this.signStatusParamPm = ((SignStatusParamPm)in.readObject());
/*    */   }
/*    */   
/*    */   public String toString() {
/* 32 */     StringBuilder sb = new StringBuilder();
/* 33 */     sb.append('[');
/* 34 */     sb.append("signStatusParamPm: ").append(this.signStatusParamPm).append(']');
/* 35 */     return sb.toString();
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\nccCodec-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ncc\tc\fw2\cmdparam\lcs\LcsSignStatusReportPm.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */