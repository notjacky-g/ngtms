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
/*    */ @CommandParams(cmdName="getCmdExeResultRsp")
/*    */ public class GetCmdExeResultRspPm
/*    */   implements Externalizable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   public static final int cmdId = 8;
/*    */   public static final String cmdName = "getCmdExeResultRsp";
/*    */   @CommandParam(name="hwStatus")
/*    */   public HwStatusPm hwStatusPm;
/*    */   @CommandParam(name="protocolCode")
/*    */   public int protocolCode;
/*    */   @CommandParam(name="response")
/*    */   public int response;
/*    */   
/*    */   public void writeExternal(ObjectOutput out)
/*    */     throws IOException
/*    */   {
/* 28 */     out.writeObject(this.hwStatusPm);
/* 29 */     out.writeByte(this.protocolCode);
/* 30 */     out.writeByte(this.response);
/*    */   }
/*    */   
/*    */   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException
/*    */   {
/* 35 */     this.hwStatusPm = ((HwStatusPm)in.readObject());
/* 36 */     this.protocolCode = (in.readByte() & 0xFF);
/* 37 */     this.response = (in.readByte() & 0xFF);
/*    */   }
/*    */   
/*    */   public String toString() {
/* 41 */     StringBuilder sb = new StringBuilder();
/* 42 */     sb.append('[');
/* 43 */     sb.append("hwStatusPm: ").append(this.hwStatusPm).append(", ");
/* 44 */     sb.append("protocolCode: ").append(this.protocolCode).append(", ");
/* 45 */     sb.append("response: ").append(this.response).append(']');
/* 46 */     return sb.toString();
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\nccCodec-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ncc\tc\fw2\cmdparam\GetCmdExeResultRspPm.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */