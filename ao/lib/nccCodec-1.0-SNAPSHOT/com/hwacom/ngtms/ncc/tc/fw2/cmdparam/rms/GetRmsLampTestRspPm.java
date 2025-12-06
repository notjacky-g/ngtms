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
/*    */ @CommandParams(cmdName="getRmsLampTestRsp")
/*    */ public class GetRmsLampTestRspPm
/*    */   implements Externalizable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   public static final int cmdId = 133;
/*    */   public static final String cmdName = "getRmsLampTestRsp";
/*    */   @CommandParam(name="lampHwStatus")
/*    */   public LampHwStatusPm lampHwStatusPm;
/*    */   @CommandParam(name="bosHwStatus")
/*    */   public BosHwStatusPm bosHwStatusPm;
/*    */   
/*    */   public void writeExternal(ObjectOutput out)
/*    */     throws IOException
/*    */   {
/* 26 */     out.writeObject(this.lampHwStatusPm);
/* 27 */     out.writeObject(this.bosHwStatusPm);
/*    */   }
/*    */   
/*    */   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException
/*    */   {
/* 32 */     this.lampHwStatusPm = ((LampHwStatusPm)in.readObject());
/* 33 */     this.bosHwStatusPm = ((BosHwStatusPm)in.readObject());
/*    */   }
/*    */   
/*    */   public String toString() {
/* 37 */     StringBuilder sb = new StringBuilder();
/* 38 */     sb.append('[');
/* 39 */     sb.append("lampHwStatusPm: ").append(this.lampHwStatusPm).append(", ");
/* 40 */     sb.append("bosHwStatusPm: ").append(this.bosHwStatusPm).append(']');
/* 41 */     return sb.toString();
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\nccCodec-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ncc\tc\fw2\cmdparam\rms\GetRmsLampTestRspPm.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */